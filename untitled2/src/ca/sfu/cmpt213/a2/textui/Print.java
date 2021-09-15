package ca.sfu.cmpt213.a2.textui;

import ca.sfu.cmpt213.a2.model.Game;

/*
Class handles the different prints that are needed in the console
 */
public class Print {
    public static void printHelp() {
        System.out.println("DIRECTIONS: COLLECT 3 RELICS");
        System.out.println("LEGEND:");
        System.out.println("#: WALL");
        System.out.println("@: YOU(TREASURE HUNTER)");
        System.out.println("!: GUARDIAN");
        System.out.println("^: RELIC");
        System.out.println(".: UNEXPLORED");
        System.out.println("MOVES:");
        System.out.println("USE W(UP), A(LEFT), S(DOWN), D(RIGHT) TO MOVE. (PRESS ENTER AFTER MOVE)");
    }

    public static void askMove() {
        System.out.println("ENTER YOUR MOVE [WASD]");
    }

    public static void printRelics(Game game) {
        System.out.println("TOTAL NUMBER OF RELICS TO BE COLLECTED: " + game.getRelics());
        System.out.println("NUMBER OF RELICS IN YOUR POSSESSION: " + (game.getCollectedRelics()));
    }

    public static void printEnd() {
        System.out.println("OH NO! THE HUNTER HAS BEEN KILLED!");
    }

    public static void printWin() {
        System.out.println("CONGRATULATIONS! YOU WON!");
    }
}
