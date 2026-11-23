package com.github.grigorevmaximandreevich.privateclub.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record QRCodeDto(
        Long id,
        UUID code,
        LocalDateTime createdTime,
        boolean isActive,
        GuestBriefDto guest
) {
}
