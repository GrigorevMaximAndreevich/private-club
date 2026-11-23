package com.github.grigorevmaximandreevich.privateclub.service;

import com.github.grigorevmaximandreevich.privateclub.exception.EntityNotFoundException;
import com.github.grigorevmaximandreevich.privateclub.mapper.GuestMapper;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestCreateRequest;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestResponse;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestUpdateRequest;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;
import com.github.grigorevmaximandreevich.privateclub.repository.GuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Transactional(readOnly = true)
    public List<GuestResponse> findAll() {
        return guestRepository.findAllWithQrCodes().stream()
                .map(GuestMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public GuestResponse findById(Long id) {
        Guest guest = guestRepository.findByIdWithQrCodes(id)
                .orElseThrow(() -> new EntityNotFoundException("Гость с ID " + id + " не найден"));
        return GuestMapper.toResponse(guest);
    }

    @Transactional
    public GuestResponse create(GuestCreateRequest request) {
        Guest guest = GuestMapper.toEntity(request);
        Guest savedGuest = guestRepository.save(guest);
        return GuestMapper.toDto(savedGuest);
    }

    @Transactional
    public GuestResponse update(Long id, GuestUpdateRequest request) {
        Guest guest = guestRepository.findByIdWithQrCodes(id)
                .orElseThrow(() -> new EntityNotFoundException("Гость с ID " + id + " не найден"));
        GuestMapper.updateEntity(guest, request);
        Guest updatedGuest = guestRepository.save(guest);
        return GuestMapper.toResponse(updatedGuest);
    }

    @Transactional
    public void delete(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new EntityNotFoundException("Гость с ID " + id + " не найден");
        }
        guestRepository.deleteById(id);
    }
}
