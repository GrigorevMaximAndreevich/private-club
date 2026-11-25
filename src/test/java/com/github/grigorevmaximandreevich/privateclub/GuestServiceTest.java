package com.github.grigorevmaximandreevich.privateclub;

import com.github.grigorevmaximandreevich.privateclub.exception.EntityNotFoundException;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestCreateRequest;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestResponse;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestUpdateRequest;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;
import com.github.grigorevmaximandreevich.privateclub.repository.GuestRepository;
import com.github.grigorevmaximandreevich.privateclub.service.GuestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @Test
    void create_shouldSaveGuest() {
        GuestCreateRequest request = new GuestCreateRequest(
                "Имя", "Фамилия", "Отчество");
        when(guestRepository.save(any(Guest.class))).thenAnswer(inv -> {
            Guest g = inv.getArgument(0);
            g.setId(2L);
            return g;
        });

        GuestResponse response = guestService.create(request);

        assertThat(response.id()).isEqualTo(2L);
        assertThat(response.firstName()).isEqualTo("Имя");
        assertThat(response.lastName()).isEqualTo("Фамилия");
        assertThat(response.qrCodes()).isEmpty();
    }

    @Test
    void update_shouldChangeOnlyProvidedFields() {
        Guest existing = new Guest();
        existing.setFirstName("Имя");
        existing.setLastName("Фамилия");
        existing.setPatronymic("Отчество");
        existing.setId(1L);
        when(guestRepository.findByIdWithQrCodes(1L)).thenReturn(Optional.of(existing));
        when(guestRepository.save(any(Guest.class))).thenAnswer(inv -> inv.getArgument(0));

        GuestResponse response = guestService.update(
                1L, new GuestUpdateRequest(null, "Иванов", null));

        assertThat(response.lastName()).isEqualTo("Иванов");
        assertThat(response.firstName()).isEqualTo("Имя");
        assertThat(response.patronymic()).isEqualTo("Отчество");
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(guestRepository.findByIdWithQrCodes(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> guestService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void delete_shouldThrowWhenNotFound() {
        when(guestRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> guestService.delete(99L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(guestRepository, never()).deleteById(any());
    }
}
