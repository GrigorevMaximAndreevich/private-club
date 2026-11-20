package com.github.grigorevmaximandreevich.privateclub.model.dto;

public record GuestBriefDto(
        Long id,
        String firstName,
        String lastName,
        String patronymic
) {
}
