package com.nguay097.moba_analytics.service;

import com.nguay097.moba_analytics.client.RiotApiClient;
import com.nguay097.moba_analytics.dto.AccountDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RiotApiService {

    private final RiotApiClient riotApiClient;

    private static final Map<String, String> PLATFORM_TO_REGION = Map.ofEntries(
            Map.entry("na1", "americas"),
            Map.entry("br1", "americas"),
            Map.entry("la1", "americas"),
            Map.entry("la2", "americas"),
            Map.entry("euw1", "europe"),
            Map.entry("eun1", "europe"),
            Map.entry("tr1", "europe"),
            Map.entry("ru", "europe"),
            Map.entry("kr", "asia"),
            Map.entry("jp1", "asia"),
            Map.entry("oc1", "asia")
    );

    public RiotApiService(RiotApiClient riotApiClient) {
        this.riotApiClient = riotApiClient;
    }

    private String getRegion(String platform) {
        return PLATFORM_TO_REGION.getOrDefault(platform.toLowerCase(), "americas");
    }

    public AccountDto getAccountsByRiotId(String name, String tagLine, String platform) {
        String region = getRegion(platform);
        return riotApiClient.getAccountByRiotId(name, tagLine, region);
    }
}