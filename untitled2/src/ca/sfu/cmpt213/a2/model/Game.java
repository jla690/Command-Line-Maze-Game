package ca.cmpt213.a3.maze;

import static ca.cmpt213.a3.maze.Constants.*;

import ca.cmpt213.a3.maze.Board;

import java.util.LinkedList;
import java.util.Random;

/*
Handles maze generation, relic spawning, and movement of entities
 */
public class Game {
    public boolean isFinished = false;
    public boolean wonGame = false;
    private Board board;
    private int[] cPos;
    private int relics;
    private int collectedRelics = 0;
    private int[][] enemyPositions;

    public Game() {
        startGame();
    }

    public int getRelics() {
        return relics;
    }

    public void setRelics(int relics) {
        this.relics = relics;
    }

    public int getCollectedRelics() {
        return collectedRelics;
    }


    private void startGame() {
        relics = 3;
        this.board = new Board();
        cPos = Board.getcPos();
        enemyPositions = board.enemyPositions;
        mazeGeneration();
        checkExplored();
    }


    public void print() {
        for (int i = 0; i < this.board.height; i++) {
            for (int j = 0; j < this.board.width; j++) {
                if (isFinished && !wonGame) {
                    if (Board.map[i][j].getSymbol() == hunter) {
                        System.out.print('X');
                        j++;
                    }

                }
                if (Board.map[i][j].getSymbol() == empty && !Board.map[i][j].isExplored()) {
                    System.out.print(unexplored);
                    j++;
                }
                System.out.print(Board.map[i][j].getSymbol());

            }
            System.out.println();
        }
    }

    // inspiration from this site: https://stackoverflow.com/questions/29739751/implementing-a-randomly-generated-maze-using-prims-algorithm
    private void mazeGeneration() {
        LinkedList<int[]> randomPoints = new LinkedList<>();
        Random random = new Random();
        int row = random.nextInt(board.height);
        int col = random.nextInt(board.width);
        randomPoints.add(new int[]{row, col, row, col});
        while (!randomPoints.isEmpty()) {
            int[] front = randomPoints.remove(random.nextInt(randomPoints.size()));
            row = front[2];
            col = front[3];
            if (Board.map[row][col].getSymbol() == wall) {
                Board.map[front[0]][front[1]].setSymbol(empty);
                Board.map[row][col].setSymbol(empty);
                if (row >= 2 && Board.map[row - 2][col].getSymbol() == wall) {
                    randomPoints.add(new int[]{row - 1, col, row - 2, col});
                }
                if (col >= 2 && Board.map[row][col - 2].getSymbol() == wall) {
                    randomPoints.add(new int[]{row, col - 1, row, col - 2});
                }
                if (row < board.height - 2) {
                    if (Board.map[row + 2][col].getSymbol() == wall) {
                        randomPoints.add(new int[]{row + 1, col, row + 2, col});
                    }
                }
                if (col < board.width - 2 && Board.map[row][col + 2].getSymbol() == wall) {
                    randomPoints.add(new int[]{row, col + 1, row, col + 2});
                }
            }
        }
        Board.map[1][board.width - 2].setSymbol(guardian); //guardian placement
        Board.map[board.height - 2][1].setSymbol(guardian);
        Board.map[board.height - 2][board.width - 2].setSymbol(guardian);
        Board.map[1][1].setSymbol(hunter); //hunter
        int[] firstGuard = new int[]{1, board.width - 2};
        int[] secondGuard = new int[]{board.height - 2, 1};
        int[] thirdGuard = new int[]{board.height - 2, board.width - 2};
        enemyPositions = new int[][]{firstGuard, secondGuard, thirdGuard};
        for (int i = 0; i < board.width; i++) { //generating outer walls
            Board.map[0][i].setSymbol(wall);
            Board.map[0][i].setExplored(true);
        }
        for (int i = 0; i < board.height; i++) {
            Board.map[i][0].setSymbol(wall);
            Board.map[i][0].setExplored(true);
        }
        for (int i = 0; i < board.height; i++) {
            Board.map[i][board.width - 1].setSymbol(wall);
            Board.map[i][board.width - 1].setExplored(true);
        }
        for (int i = 0; i < board.width; i++) {
            Board.map[board.height - 1][i].setSymbol(wall);
            Board.map[board.height - 1][i].setExplored(true);
        }
    }

    public void enemyMovement() {
        int[] possibleMoves = {-1, 1};
        for (int[] enemyPosition : enemyPositions) {
            int row = enemyPosition[0];
            int col = enemyPosition[1];
            Random random = new Random();
            int n = random.nextInt(2);
            int direction = random.nextInt(2);
            int move = possibleMoves[n];
            if (direction == 0) {
                if (checkMovement(row, col + move, guardian)) {
                    Board.map[row][col + move].setSymbol(guardian);
                    enemyPosition[1] += move;
                    Board.map[row][col].setSymbol(empty);
                }
            } else {
                if (checkMovement(row + move, col, guardian)) {
                    enemyPosition[0] += move;
                    Board.map[row + move][col].setSymbol(guardian);
                    Board.map[row][col].setSymbol(empty);
                }
            }
        }

    }

    public void playerMovement(String move) {
        int row = cPos[0];
        int col = cPos[1];
        switch (move) {
            case "d":
                if (checkMovement(row, col + 1, hunter)) {
                    Board.map[row][col + 1].setSymbol(hunter);
                    Board.map[row][col].setSymbol(empty);
                    cPos[1]++;
                }
                break;
            case "a":
                if (checkMovement(row, col - 1, hunter)) {
                    Board.map[row][col - 1].setSymbol(hunter);
                    Board.map[row][col].setSymbol(empty);
                    cPos[1]--;
                }
                break;
            case "w":
                if (checkMovement(row - 1, col, hunter)) {
                    Board.map[row - 1][col].setSymbol(hunter);
                    Board.map[row][col].setSymbol(empty);
                    cPos[0]--;
                }
                break;
            case "s":
                if (checkMovement(row + 1, col, hunter)) {
                    Board.map[row + 1][col].setSymbol(hunter);
                    Board.map[row][col].setSymbol(empty);
                    cPos[0]++;
                }
                break;
        }
        checkExplored();
        enemyMovement();
    }

    private boolean checkMovement(int row, int col, char entity) {
        switch (Board.map[row][col].getSymbol()) {
            case wall:
                return false;
            case relic:
                collectedRelics++;
                relics--; //update relics positions
                if (relics == 0) {
                    isFinished = true;
                    wonGame = true;
                } else if (relics > 0) {
                    generateRelic();
                }
                return true;
            case empty:
            case unexplored:
                return true;
            case guardian: //add endgame state
                if (entity == hunter) {
                    isFinished = true;
                    return false;
                } else {
                    return true;
                }
        }
        return false;
    }

    public void checkExplored() {
        int row = cPos[0];
        int col = cPos[1];
        Board.map[row][col + 1].setExplored(true); //E
        Board.map[row][col - 1].setExplored(true); //W
        Board.map[row + 1][col].setExplored(true); //S
        Board.map[row - 1][col].setExplored(true); //N
        Board.map[row - 1][col - 1].setExplored(true); //NW
    }

    public void generateRelic() {
        Random random = new Random();
        int row = random.nextInt(board.height);
        int col = random.nextInt(board.width);
        while (Board.map[row][col].getSymbol() != empty) {
            row = random.nextInt(board.height);
            col = random.nextInt(board.width);
        }
        if (Board.map[row][col].getSymbol() == empty) {
            Board.map[row][col].setSymbol(relic);
        }
    }

    public void revealMap() {
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                Board.map[i][j].setExplored(true);
            }
        }
    }
}
