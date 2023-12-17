package Homework.Lesson1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MontyHallParadoxDemo {

    public static void main(String[] args) {

        int totalGames = 1000;
        int switchWins = 0;
        int stayWins = 0;

        for (int i = 0; i < totalGames; i++) {
            List<Integer> doors = new ArrayList<>();
            doors.add(0);
            doors.add(0);
            doors.add(1);
            Collections.shuffle(doors);

            int chosenDoor = (int) (Math.random() * 3);
            int revealedDoor;
            do {
                revealedDoor = (int) (Math.random() * 3);
            } while (revealedDoor == chosenDoor || doors.get(revealedDoor) == 1);

            // игрок меняет выбор двери
            int newChosenDoor;
            do {
                newChosenDoor = (int) (Math.random() * 3);
            } while (newChosenDoor == chosenDoor || newChosenDoor == revealedDoor);

            if (doors.get(newChosenDoor) == 1) {
                switchWins++;
            }

            // игрок не меняет выбор двери
            if (doors.get(chosenDoor) == 1) {
                stayWins++;
            }
        }

        System.out.println("При смене выбора двери: " + switchWins + " выигрышей из " + totalGames + " игр");
        System.out.println("При несменяемости выбора двери: " + stayWins + " выигрышей из " + totalGames + " игр");
    }
}


