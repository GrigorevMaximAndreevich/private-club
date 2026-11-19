package com.github.grigorevmaximandreevich.privateclub.service;

import com.github.grigorevmaximandreevich.privateclub.exception.AccessDeniedException;
import com.github.grigorevmaximandreevich.privateclub.exception.EntityNotFoundException;
import com.github.grigorevmaximandreevich.privateclub.mapper.GuestBriefDto;
import com.github.grigorevmaximandreevich.privateclub.mapper.GuestMapper;
import com.github.grigorevmaximandreevich.privateclub.mapper.QRCodeMapper;
import com.github.grigorevmaximandreevich.privateclub.model.dto.QRCodeDto;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;
import com.github.grigorevmaximandreevich.privateclub.model.entity.QRCode;
import com.github.grigorevmaximandreevich.privateclub.repository.GuestRepository;
import com.github.grigorevmaximandreevich.privateclub.repository.QRCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class QRCodeService {

    private final QRCodeRepository qrCodeRepository;
    private final GuestRepository guestRepository;

    public QRCodeService(QRCodeRepository qrCodeRepository, GuestRepository guestRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.guestRepository = guestRepository;
    }

    @Transactional(readOnly = true)
    public List<QRCodeDto> findAll() {
        return qrCodeRepository.findAll().stream()
                .map(QRCodeMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public QRCodeDto findById(Long id) {
        QRCode qrCode = qrCodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QR-код с ID " + id + " не найден"));
        return QRCodeMapper.toDto(qrCode);
    }

    @Transactional
    public QRCodeDto create(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Гость с ID " + guestId + " не найден"));

        QRCode qrCode = new QRCode();
        qrCode.setCode(UUID.randomUUID());
        qrCode.setCreatedTime(LocalDateTime.now());
        qrCode.setActive(true);
        guest.addQrCode(qrCode);

        QRCode savedQrCode = qrCodeRepository.save(qrCode);
        return QRCodeMapper.toDto(savedQrCode);
    }

    public GuestBriefDto enterByQrCode(UUID code) {
        QRCode qrCode = qrCodeRepository.findByCodeAndIsActiveTrueWithGuest(code)
                .orElseThrow(() -> new AccessDeniedException("Вход запрещен: QR-код недействителен или уже использован"));

        Guest guest = qrCode.getGuest();
        qrCode.setActive(false);
        qrCodeRepository.save(qrCode);

        QRCode newQrCode = new QRCode();
        newQrCode.setCode(UUID.randomUUID());
        newQrCode.setCreatedTime(LocalDateTime.now());
        newQrCode.setActive(true);
        guest.addQrCode(newQrCode);
        qrCodeRepository.save(newQrCode);

        return GuestMapper.toBriefDto(guest);
    }

    @Transactional
    public void delete(Long id) {
        if (!qrCodeRepository.existsById(id)) {
            throw new EntityNotFoundException("QR-код с ID " + id + " не найден");
        }
        qrCodeRepository.deleteById(id);
    }
}
