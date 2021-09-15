package ca.sfu.cmpt213.a2.textui;

import ca.sfu.cmpt213.a2.model.Game;

import java.util.Scanner;

import static ca.sfu.cmpt213.a2.textui.Print.printHelp;

/*
Handles player input
 */
public class Input {
    public static void readInput(Game game) {
        Print.askMove();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        input = input.toLowerCase();
        while (!input.equals("m") && !input.equals("a") && !input.equals("w") && !input.equals("s") && !input.equals("d") && !input.equals("?") && !input.equals("c")) {
            System.out.println("INCORRECT MOVE, TRY AGAIN: ");
            input = scanner.next();
            input = input.toLowerCase();
        }
        switch (input) {
            case "w", "a", "s", "d" -> {
                game.playerMovement(input);
                game.checkExplored();
            }
            case "?" -> printHelp();
            case "c" -> game.setRelics(1);
            case "m" -> game.revealMap();
        }
    }
}
