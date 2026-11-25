package com.github.grigorevmaximandreevich.privateclub.model.dto;

import jakarta.validation.constraints.NotBlank;

public record GuestCreateRequest(

        @NotBlank(message = "Имя обязательно")
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        String lastName,

        String patronymic
) {
}
