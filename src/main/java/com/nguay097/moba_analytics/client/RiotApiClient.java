package com.nguay097.moba_analytics.client;

import com.nguay097.moba_analytics.dto.AccountDto;
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

    private <T> T get(String url, Class<T> responseType) {
        return webClient.get()
                .uri(url)
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}