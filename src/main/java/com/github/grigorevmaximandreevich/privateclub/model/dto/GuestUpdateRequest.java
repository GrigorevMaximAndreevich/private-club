package com.github.grigorevmaximandreevich.privateclub.model.dto;

import jakarta.validation.constraints.Size;

public record GuestUpdateRequest(

        @Size(min = 1, max = 255, message = "Имя не должно быть меньше 1 и больше 255 символов")
        String firstName,

        @Size(min = 1, max = 255, message = "Фамилия не должно быть меньше 1 и больше 255 символов")
        String lastName,

        @Size(max = 255, message = "Отчество не должно быть больше 255 символов")
        String patronymic
) {
}
