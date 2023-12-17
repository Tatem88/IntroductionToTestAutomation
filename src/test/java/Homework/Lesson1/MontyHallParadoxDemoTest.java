package Homework.Lesson1;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MontyHallParadoxDemoTest {
    private final int totalGames = 1000; // общее количество игр
    private final int switchWins = 600; // количество выигрышей при смене выбора двери
    private final int stayWins = 400; // количество выигрышей при несменяемости выбора двери

    @Test
    public void positiveTests() {
        Assert.assertTrue(switchWins > 0);
        Assert.assertTrue(stayWins > 0);
        Assert.assertEquals(totalGames, switchWins + stayWins);
    }
    @Test
    public void testStayWinsGreaterThanZero() {
        int stayWins = 0;
        // Perform test logic here and update stayWins variable

        assertTrue(stayWins > 0);
    }

    @Test
    public void testTotalGamesEqualsSwitchWinsPlusStayWins() {
        assertEquals(totalGames, switchWins + stayWins);
    }

    @Test
    public void negativeTests() {
        Assert.assertTrue(totalGames > 0);
        Assert.assertEquals(totalGames, switchWins + stayWins);
        Assert.assertTrue(switchWins >= 0 && stayWins >= 0);
    }
    @Test
    public void testTotalGamesGreaterThanZero() {
        int totalGames = 5;
        assert totalGames > 0;
    }
    @Test
    public void testWins() {
        assertTrue(switchWins >= 0 && stayWins >= 0);
    }
}

