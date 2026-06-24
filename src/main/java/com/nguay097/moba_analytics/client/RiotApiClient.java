package com.nguay097.moba_analytics.client;

import com.nguay097.moba_analytics.dto.AccountDto;
import com.nguay097.moba_analytics.dto.LeagueEntryDto;
import com.nguay097.moba_analytics.dto.MatchDto;
import com.nguay097.moba_analytics.dto.SummonerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RiotApiClient {

    private final WebClient webClient;

    @Value("${riot.api.key}")
    private String apiKey;

    public RiotApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public AccountDto getAccountByRiotId(String name, String tagLine, String region) {
        String url = "https://" + region + ".api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + name + "/" + tagLine;
        return get(url, AccountDto.class);
    }

    public SummonerDto getSummonerByPuuid(String puuid, String platform) {
        String url = "https://" + platform + ".api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return get(url, SummonerDto.class);
    }

    public String[] getMatchIdsByPuuid(String puuid, String region) {
        String url = "https://" + region + ".api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids";
        return get(url, String[].class);
    }

    public MatchDto getMatchById(String matchId, String region) {
        String url = "https://" + region + ".api.riotgames.com/lol/match/v5/matches/" + matchId;
        return get(url, MatchDto.class);
    }

    public LeagueEntryDto[] getRankedByPuuid(String puuid, String platform) {
        String url = "https://" + platform + ".api.riotgames.com/lol/league/v4/entries/by-puuid/" + puuid;
        return get(url, LeagueEntryDto[].class);
    }

    private <T> T get(String url, Class<T> responseType) {
        return webClient.get()
                .uri(url)
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}