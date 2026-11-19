package com.github.grigorevmaximandreevich.privateclub.service;

import com.github.grigorevmaximandreevich.privateclub.exception.EntityNotFoundException;
import com.github.grigorevmaximandreevich.privateclub.mapper.GuestMapper;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestDto;
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
    public List<GuestDto> findAll() {
        return guestRepository.findAll().stream()
                .map(GuestMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GuestDto findById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Гость с ID " + id + " не найден"));
        return GuestMapper.toDto(guest);
    }

    @Transactional
    public GuestDto create(GuestDto guestDto) {
        Guest guest = GuestMapper.toEntity(guestDto);
        Guest savedGuest = guestRepository.save(guest);
        return GuestMapper.toDto(savedGuest);
    }

    @Transactional
    public GuestDto update(Long id, GuestDto guestDto) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Гость с ID " + id + " не найден"));
        guest.setFirstName(guestDto.firstName());
        guest.setLastName(guestDto.lastName());
        guest.setPatronymic(guestDto.patronymic());
        Guest updatedGuest = guestRepository.save(guest);
        return GuestMapper.toDto(updatedGuest);
    }

    @Transactional
    public void delete(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new EntityNotFoundException("Гость с ID " + id + " не найден");
        }
        guestRepository.deleteById(id);
    }
}
