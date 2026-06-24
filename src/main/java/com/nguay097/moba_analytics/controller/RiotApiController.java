package com.nguay097.moba_analytics.controller;

import com.nguay097.moba_analytics.dto.AccountDto;
import com.nguay097.moba_analytics.dto.LeagueEntryDto;
import com.nguay097.moba_analytics.dto.MatchDto;
import com.nguay097.moba_analytics.dto.SummonerDto;
import com.nguay097.moba_analytics.service.RiotApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller layer for handling incoming HTTP requests.
 * 
 * Responsible for exposing HTTP endpoints that allow clients to query
 * League of Legends player data. Acts as the presentation layer, receiving
 * user requests and delegating business logic to the RiotApiService.
 * All endpoints follow REST conventions and return JSON responses.
 */
@RestController
@RequestMapping("/api")
public class RiotApiController {

    private final RiotApiService riotApiService;

    /**
     * Constructs a RiotApiController with the provided RiotApiService.
     *
     * @param riotApiService the service that handles business logic for Riot API operations
     */
    public RiotApiController(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    /**
     * Retrieves account information by summoner name and tag line.
     *
     * HTTP GET request to /api/account
     *
     * @param name the summoner name (query parameter)
     * @param tagLine the account tag line (query parameter)
     * @param platform the platform code such as "na1" or "euw1" (query parameter)
     * @return an AccountDto containing the account information
     */
    @GetMapping("/account")
    public AccountDto getAccount(
            @RequestParam String name,
            @RequestParam String tagLine,
            @RequestParam String platform
    ) {
        return riotApiService.getAccountsByRiotId(
                name,
                tagLine,
                platform
        );
    }

    /**
     * Retrieves summoner information by PUUID.
     *
     * HTTP GET request to /api/summoner
     *
     * @param puuid the player's unique identifier (query parameter)
     * @param platform the platform code such as "na1" or "euw1" (query parameter)
     * @return a SummonerDto containing the summoner's game information
     */
    @GetMapping("/summoner")
    public SummonerDto getSummoner(
            @RequestParam String puuid,
            @RequestParam String platform
    ) {
        return riotApiService.getSummonerByPuuid(
                puuid,
                platform
        );
    }

    /**
     * Retrieves match IDs for a player.
     *
     * HTTP GET request to /api/matches
     *
     * @param puuid the player's unique identifier (query parameter)
     * @param platform the platform code such as "na1" or "euw1" (query parameter)
     * @return an array of match IDs as strings
     */
    @GetMapping("/matches")
    public String[] getMatchIds(
            @RequestParam String puuid,
            @RequestParam String platform
    ) {
        return riotApiService.getMatchIdsByPuuid(
                puuid,
                platform
        );
    }

    /**
     * Retrieves detailed information for a specific match.
     *
     * HTTP GET request to /api/match
     *
     * @param matchId the unique identifier for the match (query parameter)
     * @param platform the platform code such as "na1" or "euw1" (query parameter)
     * @return a MatchDto containing complete match details and player statistics
     */
    @GetMapping("/match")
    public MatchDto getMatch(
            @RequestParam String matchId,
            @RequestParam String platform
    ) {
        return riotApiService.getMatchById(matchId, platform);
    }

    /**
     * Retrieves ranked league entries for a player.
     *
     * HTTP GET request to /api/ranked
     *
     * @param puuid the player's unique identifier (query parameter)
     * @param platform the platform code such as "na1" or "euw1" (query parameter)
     * @return an array of LeagueEntryDto containing the player's ranked information for each queue
     */
    @GetMapping("/ranked")
    public LeagueEntryDto[] getRanked(
            @RequestParam String puuid,
            @RequestParam String platform
    ) {
        return riotApiService.getRankedByPuuid(puuid, platform);
    }
}
