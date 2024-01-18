package org.max.home.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.max.seminar.accu.GetLocationTest;
import org.max.seminar.accu.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetLocationHomeTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(GetLocationTest.class);

    @Test
    @DisplayName("Test 1. Status code '200'- in get request!")
    void get_shouldReturn200StatusSaintP() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodySaintP = new Location();
        bodySaintP.setKey("Success");

        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Saint-Petersburg"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodySaintP))));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");
        //when
        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/autocomplete");
        URI uriSP = new URIBuilder(request.getURI())
                .addParameter("q", "Saint-Petersburg")
                .build();
        request.setURI(uriSP);
        HttpResponse responseSP = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(200, responseSP
                .getStatusLine()
                .getStatusCode());
    }

    @Test
    @DisplayName("Test 2. Check Key in request throw mapper ")
    void get_shouldReturn200StatusMoscowThrowMapper() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyMoscow = new Location();
        bodyMoscow.setKey("GOOD");
        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Moscow"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyMoscow))));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");
        //when
        HttpGet requestMW = new HttpGet(getBaseUrl() + "/locations/v1/cities/autocomplete");
        URI uriMW = new URIBuilder(requestMW.getURI())
                .addParameter("q", "Moscow")
                .build();
        requestMW.setURI(uriMW);
        HttpResponse responseMW = httpClient.execute(requestMW);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals("GOOD", mapper
                .readValue(responseMW
                        .getEntity()
                        .getContent(), Location.class)
                .getKey());
    }

    @Test
    @DisplayName("Test 3. Check method DELETE ")
    void get_shouldNotReturnObject() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyRostov = new Location();
        bodyRostov.setKey("Wonderful");

        logger.debug("Формирование мока для DELETE /locations/v1/cities/autocomplete");

        stubFor(delete(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Rostov-on-Don"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyRostov))));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");
        //when
        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/autocomplete");
        URI uriRD = new URIBuilder(request.getURI())
                .addParameter("q", "Rostov-on-Don")
                .build();
        request.setURI(uriRD);
        HttpResponse responseRD = httpClient.execute(request);
        logger.debug("Rostov-on-Don параметр удален");
        //then
        assertNotNull(responseRD);

    }


    @Test
    @DisplayName("Test 3. Check method 404 status PUT method ")
    void get_shouldReturnObjectWhichModified() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");

        //given
        ObjectMapper mapper = new ObjectMapper();

        Location bodyVladivostok = new Location();
        bodyVladivostok.setKey("Success");

        logger.debug("Формирование мока для PUT /locations/v1/cities/autocomplete");

        stubFor(put(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withHeader("HeaderMistake", equalTo("application/x-www-form-urlencoded"))
                .willReturn(aResponse().withStatus(404).withBody("Mistake")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");
        //when
        HttpGet requestVV = new HttpGet(getBaseUrl() + "/locations/v1/cities/autocomplete");
        URI uriVV = new URIBuilder(requestVV.getURI())
                .addParameter("q", "Vladivostok")
                .build();
        requestVV.setURI(uriVV);
        HttpResponse responseVV = httpClient.execute(requestVV);
        assertEquals(404, responseVV.getStatusLine().getStatusCode());
        assertNotEquals("NO Mistake",convertResponseToString(responseVV));
    }
}
