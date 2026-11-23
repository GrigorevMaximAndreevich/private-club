package com.github.grigorevmaximandreevich.privateclub.model.dto;

public record GuestUpdateRequest(
        String firstName,
        String lastName,
        String patronymic
) {
}
