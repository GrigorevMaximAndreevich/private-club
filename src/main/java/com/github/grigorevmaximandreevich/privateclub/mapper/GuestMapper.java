package com.github.grigorevmaximandreevich.privateclub.mapper;

import com.github.grigorevmaximandreevich.privateclub.model.dto.*;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;

import java.util.Collections;
import java.util.List;

public class GuestMapper {

    public static Guest toEntity(GuestCreateRequest request) {
        Guest guest = new Guest();
        guest.setFirstName(request.firstName());
        guest.setLastName(request.lastName());
        guest.setPatronymic(request.patronymic());
        return guest;
    }

    public static void updateEntity(Guest guest, GuestUpdateRequest request) {
        if (request.firstName() != null) {
            guest.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            guest.setLastName(request.lastName());
        }
        if (request.patronymic() != null) {
            guest.setPatronymic(request.patronymic());
        }
    }

    public static GuestResponse toResponse(Guest guest) {
        List<QRCodeDto> qrCodes = guest.getQrCodes() != null
                ? QRCodeMapper.toDtoList(guest.getQrCodes())
                : Collections.emptyList();

        return new GuestResponse(
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getPatronymic(),
                qrCodes
        );
    }

    public static GuestBriefDto toBriefDto(Guest guest) {
        if (guest == null) {
            return null;
        }
        return new GuestBriefDto(
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getPatronymic()
        );
    }
}
