package com.nguay097.moba_analytics.service;

import com.nguay097.moba_analytics.client.RiotApiClient;
import com.nguay097.moba_analytics.dto.AccountDto;
import com.nguay097.moba_analytics.dto.LeagueEntryDto;
import com.nguay097.moba_analytics.dto.MatchDto;
import com.nguay097.moba_analytics.dto.SummonerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RiotApiServiceTest {

    @Mock
    private RiotApiClient riotApiClient;

    @InjectMocks
    private RiotApiService riotApiService;

    /**
     * Scenario: Retrieve account for OC1 platform (Oceania)
     * Expected: Platform "oc1" should map to region "asia"
     * Mocked: riotApiClient.getAccountByRiotId returns AccountDto for "asia" region
     */
    @Test
    void getAccountsByRiotId_OC1_usesAsiaRegion() {
        AccountDto expected = new AccountDto("puuid123", "zirshiin", "HELLO");
        when(riotApiClient.getAccountByRiotId("zirshiin", "HELLO", "asia")).thenReturn(expected);

        AccountDto result = riotApiService.getAccountsByRiotId("zirshiin", "HELLO", "oc1");

        assertEquals(expected, result);
        verify(riotApiClient).getAccountByRiotId("zirshiin", "HELLO", "asia");
    }

    /**
     * Scenario: Retrieve account for KR platform (Korea)
     * Expected: Platform "kr" should map to region "asia"
     * Mocked: riotApiClient.getAccountByRiotId returns AccountDto for "asia" region
     */
    @Test
    void getAccountsByRiotId_KR_usesAsiaRegion() {
        AccountDto expected = new AccountDto("puuid456", "Faker", "KR897");
        when(riotApiClient.getAccountByRiotId("Faker", "KR897", "asia")).thenReturn(expected);

        AccountDto result = riotApiService.getAccountsByRiotId("Faker", "KR897", "kr");

        assertEquals(expected, result);
        verify(riotApiClient).getAccountByRiotId("Faker", "KR897", "asia");
    }

    /**
     * Scenario: Retrieve account for EUW1 platform (EU West)
     * Expected: Platform "euw1" should map to region "europe"
     * Mocked: riotApiClient.getAccountByRiotId returns AccountDto for "europe" region
     */
    @Test
    void getAccountsByRiotId_EUW1_usesEuropeRegion() {
        AccountDto expected = new AccountDto("puuid789", "Caps", "EUW");
        when(riotApiClient.getAccountByRiotId("Caps", "EUW", "europe")).thenReturn(expected);

        AccountDto result = riotApiService.getAccountsByRiotId("Caps", "EUW", "euw1");

        assertEquals(expected, result);
        verify(riotApiClient).getAccountByRiotId("Caps", "EUW", "europe");
    }

    /**
     * Scenario: Retrieve account for NA1 platform (North America)
     * Expected: Platform "na1" should map to region "americas"
     * Mocked: riotApiClient.getAccountByRiotId returns AccountDto for "americas" region
     */
    @Test
    void getAccountsByRiotId_NA1_usesAmericasRegion() {
        AccountDto expected = new AccountDto("puuid101", "Doublelift", "NA1");
        when(riotApiClient.getAccountByRiotId("Doublelift", "NA1", "americas")).thenReturn(expected);

        AccountDto result = riotApiService.getAccountsByRiotId("Doublelift", "NA1", "na1");

        assertEquals(expected, result);
        verify(riotApiClient).getAccountByRiotId("Doublelift", "NA1", "americas");
    }

    /**
     * Scenario: Retrieve account with unrecognized platform
     * Expected: Unknown platforms should default to "americas" region
     * Mocked: riotApiClient.getAccountByRiotId returns AccountDto for default "americas" region
     */
    @Test
    void getAccountsByRiotId_unknownPlatform_defaultsToAmericas() {
        AccountDto expected = new AccountDto("puuid999", "Unknown", "TAG");
        when(riotApiClient.getAccountByRiotId("Unknown", "TAG", "americas")).thenReturn(expected);

        AccountDto result = riotApiService.getAccountsByRiotId("Unknown", "TAG", "invalidplatform");

        assertEquals(expected, result);
        verify(riotApiClient).getAccountByRiotId("Unknown", "TAG", "americas");
    }

    /**
     * Scenario: Retrieve account with uppercase platform code
     * Expected: Platform code should be case-insensitive; "OC1" treated same as "oc1"
     * Mocked: riotApiClient.getAccountByRiotId returns AccountDto for "asia" region
     */
    @Test
    void getAccountsByRiotId_caseInsensitivePlatform() {
        AccountDto expected = new AccountDto("puuid123", "zirshiin", "HELLO");
        when(riotApiClient.getAccountByRiotId("zirshiin", "HELLO", "asia")).thenReturn(expected);

        AccountDto result = riotApiService.getAccountsByRiotId("zirshiin", "HELLO", "OC1");

        assertEquals(expected, result);
        verify(riotApiClient).getAccountByRiotId("zirshiin", "HELLO", "asia");
    }

    /**
     * Scenario: Retrieve match IDs for OC1 platform using match API
     * Expected: Platform "oc1" maps to "sea" region (different from account API which uses "asia")
     * Mocked: riotApiClient.getMatchIdsByPuuid returns match IDs for "sea" region
     */
    @Test
    void getMatchIdsByPuuid_OC1_usesSEARegion() {
        String[] expected = {"OC1_123", "OC1_456"};
        when(riotApiClient.getMatchIdsByPuuid("puuid123", "sea")).thenReturn(expected);

        String[] result = riotApiService.getMatchIdsByPuuid("puuid123", "oc1");

        assertArrayEquals(expected, result);
        verify(riotApiClient).getMatchIdsByPuuid("puuid123", "sea");
    }

    /**
     * Scenario: Retrieve match IDs for KR platform using match API
     * Expected: Platform "kr" maps to "asia" region for match API
     * Mocked: riotApiClient.getMatchIdsByPuuid returns match IDs for "asia" region
     */
    @Test
    void getMatchIdsByPuuid_KR_usesAsiaRegion() {
        String[] expected = {"KR_123", "KR_456"};
        when(riotApiClient.getMatchIdsByPuuid("puuid456", "asia")).thenReturn(expected);

        String[] result = riotApiService.getMatchIdsByPuuid("puuid456", "kr");

        assertArrayEquals(expected, result);
        verify(riotApiClient).getMatchIdsByPuuid("puuid456", "asia");
    }

    /**
     * Scenario: Retrieve match IDs with unrecognized platform
     * Expected: Unknown platforms should default to "americas" region
     * Mocked: riotApiClient.getMatchIdsByPuuid returns match IDs for default "americas" region
     */
    @Test
    void getMatchIdsByPuuid_unknownPlatform_defaultsToAmericas() {
        String[] expected = {"NA1_123"};
        when(riotApiClient.getMatchIdsByPuuid("puuid999", "americas")).thenReturn(expected);

        String[] result = riotApiService.getMatchIdsByPuuid("puuid999", "invalidplatform");

        assertArrayEquals(expected, result);
        verify(riotApiClient).getMatchIdsByPuuid("puuid999", "americas");
    }

    /**
     * Scenario: Retrieve summoner info (no region mapping required)
     * Expected: Platform passed directly to client without transformation
     * Mocked: riotApiClient.getSummonerByPuuid returns SummonerDto for provided platform
     */
    @Test
    void getSummonerByPuuid_passesPlatformDirectly() {
        SummonerDto expected = new SummonerDto(1234, 1234567890L, "puuid123", 100L);
        when(riotApiClient.getSummonerByPuuid("puuid123", "oc1")).thenReturn(expected);

        SummonerDto result = riotApiService.getSummonerByPuuid("puuid123", "oc1");

        assertEquals(expected, result);
        verify(riotApiClient).getSummonerByPuuid("puuid123", "oc1");
    }

    /**
     * Scenario: Retrieve ranked league entries (no region mapping required)
     * Expected: Platform passed directly to client without transformation
     * Mocked: riotApiClient.getRankedByPuuid returns LeagueEntryDto array for provided platform
     */
    @Test
    void getRankedByPuuid_passesPlatformDirectly() {
        LeagueEntryDto[] expected = {
                new LeagueEntryDto("RANKED_SOLO_5x5", "DIAMOND", "II", "puuid123", 75, 118, 97, false, false, false, false)
        };
        when(riotApiClient.getRankedByPuuid("puuid123", "oc1")).thenReturn(expected);

        LeagueEntryDto[] result = riotApiService.getRankedByPuuid("puuid123", "oc1");

        assertArrayEquals(expected, result);
        verify(riotApiClient).getRankedByPuuid("puuid123", "oc1");
    }

    /**
     * Scenario: Retrieve specific match details for OC1 platform
     * Expected: Platform "oc1" maps to "sea" region (match API uses different mapping)
     * Mocked: riotApiClient.getMatchById returns MatchDto for "sea" region
     */
    @Test
    void getMatchById_OC1_usesSEARegion() {
        MatchDto expected = mock(MatchDto.class);
        when(riotApiClient.getMatchById("OC1_123", "sea")).thenReturn(expected);

        MatchDto result = riotApiService.getMatchById("OC1_123", "oc1");

        assertEquals(expected, result);
        verify(riotApiClient).getMatchById("OC1_123", "sea");
    }

    /**
     * Scenario: Retrieve specific match details for KR platform
     * Expected: Platform "kr" maps to "asia" region for match API
     * Mocked: riotApiClient.getMatchById returns MatchDto for "asia" region
     */
    @Test
    void getMatchById_KR_usesAsiaRegion() {
        MatchDto expected = mock(MatchDto.class);
        when(riotApiClient.getMatchById("KR_123", "asia")).thenReturn(expected);

        MatchDto result = riotApiService.getMatchById("KR_123", "kr");

        assertEquals(expected, result);
        verify(riotApiClient).getMatchById("KR_123", "asia");
    }
}