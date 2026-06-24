package com.nguay097.moba_analytics.dto;

import java.util.List;

public record MetadataDto(
        String matchId,
        List<String> participants
) {}