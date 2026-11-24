package com.github.grigorevmaximandreevich.privateclub.controller;

import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestCreateRequest;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestResponse;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestUpdateRequest;
import com.github.grigorevmaximandreevich.privateclub.service.GuestService;
import jakarta.validation.Valid;
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
    public List<GuestResponse> getAllGuests() {
        return guestService.findAll();
    }

    @GetMapping("/{id}")
    public GuestResponse getGuestById(@PathVariable Long id) {
        return guestService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestResponse createGuest(@Valid @RequestBody GuestCreateRequest request) {
        return guestService.create(request);
    }

    @PutMapping("/{id}")
    public GuestResponse updateGuest(@PathVariable Long id, @Valid @RequestBody GuestUpdateRequest request) {
        return guestService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
    }
}
