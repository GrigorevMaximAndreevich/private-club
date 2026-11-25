package com.github.grigorevmaximandreevich.privateclub.mapper;

import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestBriefDto;
import com.github.grigorevmaximandreevich.privateclub.model.dto.QRCodeDto;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;
import com.github.grigorevmaximandreevich.privateclub.model.entity.QRCode;

import java.util.Collections;
import java.util.List;

public class QRCodeMapper {

    public static QRCodeDto toDto(QRCode qrCode) {
        if (qrCode == null) {
            return null;
        }
        GuestBriefDto guestBriefDto = GuestMapper.toBriefDto(qrCode.getGuest());
        return new QRCodeDto(
                qrCode.getId(),
                qrCode.getCode(),
                qrCode.getCreatedTime(),
                qrCode.isActive(),
                guestBriefDto
        );
    }

    public static List<QRCodeDto> toDtoList(List<QRCode> qrCodes) {
        if (qrCodes == null || qrCodes.isEmpty()) {
            return Collections.emptyList();
        }
        return qrCodes.stream()
                .map(QRCodeMapper::toDto)
                .toList();
    }

}
