package com.github.grigorevmaximandreevich.privateclub.mapper;

public record GuestBriefDto(
        Long id,
        String firstName,
        String lastName,
        String patronymic
) {
}
