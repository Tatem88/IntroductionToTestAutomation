package org.max.home.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.max.home.accu.weather.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.max.home.accu.AbstractTest.getBaseUrl;


public class GetWeather15DaysTest extends AbstractTest {
    private static final Logger logger
            = LoggerFactory.getLogger(GetWeatherOneDayTest.class);

    @Test
    @DisplayName("Test Get with checks ")
    void get_should() throws Exception {
        logger.info("Тест код ответ 200 запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/15day/55");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/15day/55"))
                .willReturn(aResponse()
                        .withStatus(200).withHeader("Simple Header", "Second Value").withBody("15 days forecast")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/15day/55");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/15day/55")));//
        logger.debug("Operation did - check ok!");
        verify(1, getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/15day/55")));
        logger.debug("Operation did ONCE - check ok!");
        assertEquals("15 days forecast", convertResponseToString(response));

    }

    @Test
    @DisplayName("Test Negative Checks")
    void get_should1() throws Exception {
        logger.info("Тест код ответ 200 запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/15day/55");

        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/15day/55"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("15 days forecast")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/15day/55");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        assertNotNull(response);
        assertNotEquals("20 days in Holiday",convertResponseToString(response));
    }
}
