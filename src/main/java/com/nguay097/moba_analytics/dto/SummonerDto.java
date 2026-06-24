package com.nguay097.moba_analytics.dto;

public record SummonerDto(
        int profileIconId,
        long revisionDate,
        String puuid,
        long summonerLevel
) {}