package com.nguay097.moba_analytics.dto;

import java.util.List;

public record InfoDto(
        long gameDuration,
        String gameMode,
        List<ParticipantDto> participants
) {}