package com.github.grigorevmaximandreevich.privateclub.model.dto;

import java.util.List;

public record GuestResponse(
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        List<QRCodeDto> qrCodes
) {
}
