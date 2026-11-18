package com.github.grigorevmaximandreevich.privateclub.model.dto;

import com.github.grigorevmaximandreevich.privateclub.mapper.GuestBriefDto;
import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;

import java.time.LocalDateTime;
import java.util.UUID;

public record QRCodeDto(
        long id,
        UUID code,
        LocalDateTime createdTime,
        boolean isActive,
        GuestBriefDto guest
) {
}
