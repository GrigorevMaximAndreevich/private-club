package com.github.grigorevmaximandreevich.privateclub.mapper;

import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestBriefDto;
import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestDto;
import com.github.grigorevmaximandreevich.privateclub.model.dto.QRCodeDto;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;

import java.util.List;

public class GuestMapper {

    public static Guest toEntity(GuestDto guestDto) {
        Guest guest = new Guest();
        guest.setFirstName(guestDto.firstName());
        guest.setLastName(guestDto.lastName());
        guest.setPatronymic(guestDto.patronymic());
        return guest;
    }

    public static GuestDto toDto(Guest guest) {
        List<QRCodeDto> qrCodeDtos = QRCodeMapper.toDtoList(guest.getQrCodes());
        GuestDto guestDto = new GuestDto(
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getPatronymic(),
                qrCodeDtos
        );
        return guestDto;
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
