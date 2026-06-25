package com.nguay097.moba_analytics.client;

import com.nguay097.moba_analytics.dto.AccountDto;
import com.nguay097.moba_analytics.dto.LeagueEntryDto;
import com.nguay097.moba_analytics.dto.MatchDto;
import com.nguay097.moba_analytics.dto.SummonerDto;
import com.nguay097.moba_analytics.exception.RiotApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * HTTP client layer for communicating with the Riot Games API.
 * 
 * Responsible for making direct HTTP requests to various Riot API endpoints
 * and deserializing responses into Data Transfer Objects (DTOs).
 * This class encapsulates all API endpoint URLs and handles authentication
 * via the API key. It uses Spring's WebClient for reactive HTTP communication.
 */
@Component
public class RiotApiClient {

    private final WebClient webClient;

    @Value("${riot.api.key}")
    private String apiKey;

    /**
     * Constructs a RiotApiClient with the provided WebClient.
     *
     * @param webClient the Spring WebClient used to make HTTP requests
     */
    public RiotApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Retrieves account information by Riot ID (name and tag line).
     *
     * @param name the summoner name
     * @param tagLine the account tag line
     * @param region the regional endpoint (e.g., "americas", "europe", "asia")
     * @return an AccountDto containing the account information
     */
    public AccountDto getAccountByRiotId(String name, String tagLine, String region) {
        String url = "https://" + region + ".api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + name + "/" + tagLine;
        return get(url, AccountDto.class);
    }

    /**
     * Retrieves summoner information by PUUID (Platform Universal Unique Identifier).
     *
     * @param puuid the player's unique identifier
     * @param platform the platform endpoint (e.g., "na1", "euw1", "kr")
     * @return a SummonerDto containing the summoner's game information
     */
    public SummonerDto getSummonerByPuuid(String puuid, String platform) {
        String url = "https://" + platform + ".api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return get(url, SummonerDto.class);
    }

    /**
     * Retrieves match IDs for a player by PUUID.
     *
     * @param puuid the player's unique identifier
     * @param region the regional endpoint for match history (e.g., "americas", "europe", "asia")
     * @return an array of match IDs as strings
     */
    public String[] getMatchIdsByPuuid(String puuid, String region) {
        String url = "https://" + region + ".api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids";
        return get(url, String[].class);
    }

    /**
     * Retrieves detailed match information by match ID.
     *
     * @param matchId the unique identifier for the match
     * @param region the regional endpoint (e.g., "americas", "europe", "asia")
     * @return a MatchDto containing complete match details and player statistics
     */
    public MatchDto getMatchById(String matchId, String region) {
        String url = "https://" + region + ".api.riotgames.com/lol/match/v5/matches/" + matchId;
        return get(url, MatchDto.class);
    }

    /**
     * Retrieves ranked league entries for a player by PUUID.
     *
     * @param puuid the player's unique identifier
     * @param platform the platform endpoint (e.g., "na1", "euw1", "kr")
     * @return an array of LeagueEntryDto containing the player's ranked information
     */
    public LeagueEntryDto[] getRankedByPuuid(String puuid, String platform) {
        String url = "https://" + platform + ".api.riotgames.com/lol/league/v4/entries/by-puuid/" + puuid;
        return get(url, LeagueEntryDto[].class);
    }

    /**
     * Generic method to execute GET requests to the Riot API and deserialize responses.
     *
     * Catches WebClient exceptions and converts them to RiotApiException with the appropriate
     * HTTP status code from the Riot API response.
     *
     * @param <T> the type of the response object
     * @param url the full URL to the API endpoint
     * @param responseType the class type to deserialize the response into
     * @return an instance of the specified response type containing the API response data
     * @throws RiotApiException if the Riot API returns an error status code
     */
    private <T> T get(String url, Class<T> responseType) {
        try {
            return webClient.get()
                    .uri(url)
                    .header("X-Riot-Token", apiKey)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException ex) {
            throw new RiotApiException(
                    ex.getStatusCode().value(),
                    "Riot API error: " + ex.getStatusCode() + " - " + ex.getStatusText()
            );
        }
    }
}