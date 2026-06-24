package com.nguay097.moba_analytics.dto;

public record LeagueEntryDto(
        String queueType,
        String tier,
        String rank,
        String puuid,
        int leaguePoints,
        int wins,
        int losses,
        boolean veteran,
        boolean inactive,
        boolean freshBlood,
        boolean hotStreak
) {}