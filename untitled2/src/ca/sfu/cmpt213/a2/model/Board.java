package ca.cmpt213.a3.maze;

import static ca.cmpt213.a3.maze.Constants.*;

/*
Creates the 2d array of Blocks
 */
public class Board {
    static Block[][] map;
    static private int[] cPos;
    final int width = 20;
    final int height = 16;
    final int totalLength = 20 * 16;
    int[][] enemyPositions;

    public Board() {
        map = new Block[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = new Block();
                map[i][j].setPosition(i);
                map[i][j].setSymbol(wall);
                map[i][j].setExplored(false);
            }
        }
        cPos = new int[2];
        cPos[0] = 1;
        cPos[1] = 1;
    }

    public static int[] getcPos() {
        return cPos;
    }

    public static void setcPos(int[] cPos) {
        Board.cPos = cPos;
    }


}
