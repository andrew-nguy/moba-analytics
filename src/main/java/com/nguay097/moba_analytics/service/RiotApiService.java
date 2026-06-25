package com.nguay097.moba_analytics.service;

import com.nguay097.moba_analytics.client.RiotApiClient;
import com.nguay097.moba_analytics.dto.AccountDto;
import com.nguay097.moba_analytics.dto.LeagueEntryDto;
import com.nguay097.moba_analytics.dto.MatchDto;
import com.nguay097.moba_analytics.dto.SummonerDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service layer that orchestrates Riot API calls and handles business logic.
 * 
 * Responsible for translating platform codes to regional endpoints required by
 * the Riot Games API. This layer acts as an intermediary between the controller
 * and client layers, handling platform-to-region mapping and coordinating API calls.
 * It encapsulates mapping logic and ensures proper endpoint selection for different
 * regions (Americas, Europe, Asia, and SEA).
 */
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

    private static final Map<String, String> PLATFORM_TO_MATCH_REGION = Map.ofEntries(
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
            Map.entry("oc1", "sea")
    );

    /**
     * Constructs a RiotApiService with the provided RiotApiClient.
     *
     * @param riotApiClient the HTTP client for making Riot API requests
     */
    public RiotApiService(RiotApiClient riotApiClient) {
        this.riotApiClient = riotApiClient;
    }

    /**
     * Converts a platform code to its corresponding regional endpoint.
     *
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return the regional endpoint (e.g., "americas", "europe", "asia")
     */
    private String getRegion(String platform) {
        return PLATFORM_TO_REGION.getOrDefault(platform.toLowerCase(), "americas");
    }

    /**
     * Converts a platform code to its corresponding regional endpoint for match queries.
     * 
     * Note: The match API endpoint uses different region mappings than other endpoints,
     * particularly for OC1 which maps to "sea" instead of "asia".
     *
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return the regional endpoint for match queries (e.g., "americas", "europe", "sea")
     */
    private String getMatchRegion(String platform) {
        return PLATFORM_TO_MATCH_REGION.getOrDefault(platform.toLowerCase(), "americas");
    }

    /**
     * Retrieves account information by Riot ID (name and tag line).
     *
     * @param name the summoner name
     * @param tagLine the account tag line
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return an AccountDto containing the account information
     */
    @Cacheable(value = "accounts", key = "#name + ':' + #tagLine + ':' + #platform")
    public AccountDto getAccountsByRiotId(String name, String tagLine, String platform) {
        String region = getRegion(platform);
        return riotApiClient.getAccountByRiotId(name, tagLine, region);
    }

    /**
     * Retrieves summoner information by PUUID.
     *
     * @param puuid the player's unique identifier
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return a SummonerDto containing the summoner's game information
     */
    @Cacheable(value = "summoners", key = "#puuid + ':' + #platform")
    public SummonerDto getSummonerByPuuid(String puuid, String platform) {
        return riotApiClient.getSummonerByPuuid(puuid, platform);
    }

    /**
     * Retrieves match IDs for a player.
     *
     * @param puuid the player's unique identifier
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return an array of match IDs as strings
     */
    @Cacheable(value = "matches", key = "#puuid + ':' + #platform")
    public String[] getMatchIdsByPuuid(String puuid, String platform) {
        String region = getMatchRegion(platform);
        return riotApiClient.getMatchIdsByPuuid(puuid, region);
    }

    /**
     * Retrieves detailed information for a specific match.
     *
     * @param matchId the unique identifier for the match
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return a MatchDto containing complete match details and player statistics
     */
    @Cacheable(value = "match", key = "#matchId")
    public MatchDto getMatchById(String matchId, String platform) {
        String region = getMatchRegion(platform);
        return riotApiClient.getMatchById(matchId, region);
    }

    /**
     * Retrieves ranked league entries for a player.
     *
     * @param puuid the player's unique identifier
     * @param platform the platform code (e.g., "na1", "euw1", "kr")
     * @return an array of LeagueEntryDto containing the player's ranked information for each queue
     */

    @Cacheable(value = "ranked", key = "#puuid + ':' + #platform")
    public LeagueEntryDto[] getRankedByPuuid(String puuid, String platform) {
        return riotApiClient.getRankedByPuuid(puuid, platform);
    }
}