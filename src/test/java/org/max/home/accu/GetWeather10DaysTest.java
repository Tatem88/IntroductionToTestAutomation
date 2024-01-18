package org.max.home.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.max.home.accu.weather.DailyForecast;
import org.max.home.accu.weather.Headline;
import org.max.home.accu.weather.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetWeather10DaysTest extends AbstractTest {
    private static final Logger logger
            = LoggerFactory.getLogger(GetWeatherOneDayTest.class);

    @Test
    @DisplayName("Test 1. Get 200 ")
    void get_ShouldReturn200Status() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Weather weather = new Weather();
        Headline headline = new Headline();
        headline.setCategory("Категория");
        headline.setText("Текст");
        weather.setHeadline(headline);
        DailyForecast dailyForecast = new DailyForecast();
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        dailyForecasts.add(dailyForecast);
        weather.setDailyForecasts(dailyForecasts);
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/55");

        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/55"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(weather))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/55");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/55")));
        assertEquals(200, response.getStatusLine().getStatusCode());

        Weather responseBody = mapper.readValue(response.getEntity().getContent(), Weather.class);
        assertEquals("Категория", responseBody.getHeadline().getCategory());
        assertEquals("Текст", responseBody.getHeadline().getText());
        assertEquals(1, responseBody.getDailyForecasts().size());
    }

    @Test
    @DisplayName("Test 2. Get 500")
    void get_shouldReturn500Status() throws Exception {
        logger.info("Тест код ответ 500 запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/55");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/55"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/55");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/55")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }

    @Test
    @DisplayName("Test 3. Get 200 & correct Body")
    void get_shouldReturnBodyPlusStatus() throws Exception {
        logger.info("Тест код ответ 200 запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/55");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/55"))
                .willReturn(aResponse()
                        .withStatus(200).withHeader("Temperature", "-15").withBody("Frosty weather with snow")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/55");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/55")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Frosty weather with snow", convertResponseToString(response));
    }
    @Test
    @DisplayName("Test 3. Get 404 & not matching Body")
    void get_shouldReturn404PlusStatus() throws Exception {
        logger.info("Тест код ответ 404 запущен");
        //given
        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/55");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/2day/55"))
                .willReturn(aResponse()
                        .withStatus(404).withHeader("Temperature", "!!!!").withBody("Sunny!")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/55");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/55")));
        assertNotEquals("Cloudy",convertResponseToString(response));
    }
}
