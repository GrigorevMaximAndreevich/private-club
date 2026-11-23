package com.github.grigorevmaximandreevich.privateclub;

import com.github.grigorevmaximandreevich.privateclub.exception.AccessDeniedException;
import com.github.grigorevmaximandreevich.privateclub.exception.EntityNotFoundException;
import com.github.grigorevmaximandreevich.privateclub.model.dto.QRCodeDto;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;
import com.github.grigorevmaximandreevich.privateclub.model.entity.QRCode;
import com.github.grigorevmaximandreevich.privateclub.repository.GuestRepository;
import com.github.grigorevmaximandreevich.privateclub.repository.QRCodeRepository;
import com.github.grigorevmaximandreevich.privateclub.service.QRCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QRCodeServiceTest {

    @Mock
    private QRCodeRepository qrCodeRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private QRCodeService qrCodeService;

    private Guest guest;
    private QRCode activeCode;
    private UUID codeValue;

    @BeforeEach
    void setUp() {
        guest = new Guest();
        guest.setId(1L);
        guest.setFirstName("Имя");
        guest.setLastName("Фамилия");
        guest.setPatronymic("Отчество");

        codeValue = UUID.randomUUID();
        activeCode = new QRCode();
        activeCode.setId(10L);
        activeCode.setCode(codeValue);
        activeCode.setActive(true);
        guest.addQrCode(activeCode);
    }

    @Test
    void enterByQrCode_shouldReturnGuestInfo() {
        when(qrCodeRepository.findByCodeAndIsActiveTrueWithGuest(codeValue))
                .thenReturn(Optional.of(activeCode));

        var result = qrCodeService.enterByQrCode(codeValue);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.firstName()).isEqualTo("Имя");
        assertThat(result.lastName()).isEqualTo("Фамилия");
        assertThat(result.patronymic()).isEqualTo("Отчество");
    }

    @Test
    void enterByQrCode_shouldDeactivateUsedCode() {
        when(qrCodeRepository.findByCodeAndIsActiveTrueWithGuest(codeValue))
                .thenReturn(Optional.of(activeCode));

        qrCodeService.enterByQrCode(codeValue);

        assertThat(activeCode.isActive()).isFalse();
    }

    @Test
    void enterByQrCode_shouldGenerateNewActiveCode() {
        when(qrCodeRepository.findByCodeAndIsActiveTrueWithGuest(codeValue))
                .thenReturn(Optional.of(activeCode));

        qrCodeService.enterByQrCode(codeValue);

        ArgumentCaptor<QRCode> captor = ArgumentCaptor.forClass(QRCode.class);
        verify(qrCodeRepository, atLeastOnce()).save(captor.capture());

        QRCode newCode = captor.getAllValues().stream()
                .filter(qr -> !qr.getCode().equals(codeValue))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Новый QR-код не был создан"));

        assertThat(newCode.getCode()).isNotEqualTo(codeValue);
        assertThat(newCode.isActive()).isTrue();
        assertThat(newCode.getCreatedTime()).isNotNull();
        assertThat(newCode.getGuest()).isEqualTo(guest);
    }

    @Test
    void enterByQrCode_shouldThrowWhenCodeNotFound() {
        UUID unknown = UUID.randomUUID();
        when(qrCodeRepository.findByCodeAndIsActiveTrueWithGuest(unknown))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> qrCodeService.enterByQrCode(unknown))
                .isInstanceOf(AccessDeniedException.class);

        verify(qrCodeRepository, never()).save(any());
    }

    @Test
    void create_shouldGenerateActiveCodeForGuest() {
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        when(qrCodeRepository.save(any(QRCode.class)))
                .thenAnswer(invocation -> {
                    QRCode saved = invocation.getArgument(0);
                    saved.setId(99L);
                    return saved;
                });

        QRCodeDto result = qrCodeService.create(1L);

        assertThat(result.code()).isNotNull();
        assertThat(result.isActive()).isTrue();
        assertThat(result.guest().id()).isEqualTo(1L);
    }

    @Test
    void create_shouldThrowWhenGuestNotFound() {
        when(guestRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> qrCodeService.create(99L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(qrCodeRepository, never()).save(any());
    }

}
