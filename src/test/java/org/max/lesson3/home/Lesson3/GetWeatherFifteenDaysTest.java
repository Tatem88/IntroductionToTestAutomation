package org.max.lesson3.home.Lesson3;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.lesson3.seminar.accuweather.AccuweatherAbstractTest;
import org.max.lesson3.seminar.accuweather.weather.Weather;

import static io.restassured.RestAssured.given;

public class GetWeatherFifteenDaysTest extends AccuweatherAbstractTest {
    @Test
    void getWeatherFifteenDays_shouldReturn() {
        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/15day/294021")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(5000l))
                .extract()
                .response()
                .body().as(Weather.class);

        Assertions.assertEquals(15,response.getDailyForecasts().size());
    }
}
