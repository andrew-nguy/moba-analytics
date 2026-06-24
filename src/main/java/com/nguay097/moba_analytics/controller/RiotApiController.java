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

@RestController
@RequestMapping("/api")
public class RiotApiController {

    private final RiotApiService riotApiService;

    public RiotApiController(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

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

    @GetMapping("/match")
    public MatchDto getMatch(
            @RequestParam String matchId,
            @RequestParam String platform
    ) {
        return riotApiService.getMatchById(matchId, platform);
    }

    @GetMapping("/ranked")
    public LeagueEntryDto[] getRanked(
            @RequestParam String puuid,
            @RequestParam String platform
    ) {
        return riotApiService.getRankedByPuuid(puuid, platform);
    }
}
