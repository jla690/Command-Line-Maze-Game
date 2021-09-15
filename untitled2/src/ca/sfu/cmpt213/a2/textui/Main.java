package ca.sfu.cmpt213.a2.textui;

import ca.sfu.cmpt213.a2.model.Game;

import static ca.sfu.cmpt213.a2.textui.Print.printRelics;

/*
Combines all the classes in order to print it to the user
 */
public class Main {
    public static void main(String[] args) {
        Game test = new Game();
        Print.printHelp();
        test.generateRelic();
        test.print();
        while (!test.isFinished) {
            Input.readInput(test);
            printRelics(test);
            if (test.isFinished && test.wonGame) {
                Print.printWin();
            } else if (test.isFinished && !test.wonGame) {
                Print.printEnd();
            }
            test.print();
        }
    }
}
