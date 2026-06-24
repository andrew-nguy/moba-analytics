package com.nguay097.moba_analytics.dto;

public record ParticipantDto(
        String puuid,
        String championName,
        int kills,
        int deaths,
        int assists,
        boolean win,
        int totalMinionsKilled,
        String teamPosition,
        int champLevel
) {}