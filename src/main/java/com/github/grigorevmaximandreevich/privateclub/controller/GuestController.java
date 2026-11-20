package com.github.grigorevmaximandreevich.privateclub.controller;

import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestDto;
import com.github.grigorevmaximandreevich.privateclub.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<GuestDto> getAllGuests() {
        return guestService.findAll();
    }

    @GetMapping("/{id}")
    public GuestDto getGuestById(@PathVariable Long id) {
        return guestService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDto createGuest(@RequestBody GuestDto guestDto) {
        return guestService.create(guestDto);
    }

    @PutMapping("/{id}")
    public GuestDto updateGuest(@PathVariable Long id, @RequestBody GuestDto guestDto) {
        return guestService.update(id, guestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
    }
}
