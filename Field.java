package tictactoe;

import java.nio.CharBuffer;
import java.util.Arrays;

public class Field {

    private char emptySpace;
    private char[][] area;

    Field() {
        this(3, '_');
    }

    Field(int size) {
        this(size, '_');
    }

    Field(int size, char emptySpace) {
        this.area = new char[size][size];
        this.emptySpace = emptySpace;
        fillArea();
        drawArea();
    }

    public void fillArea() {
        for (char[] row : area) {
            Arrays.fill(row, this.emptySpace);
        }
    }

    protected boolean checkWin(int row, int col, char mark) {

        int rowSum = 0;
        int colSum = 0;
        int diagonal = 0;
        int rDiagonal = 0;
        int winResult = area.length * mark;

        for (int i = 0; i < area.length; i++) {
            rowSum += area[row][i];
            colSum += area[i][col];
            diagonal += area[i][i];
            rDiagonal += area[i][area.length - i - 1];
        }

        return rowSum == winResult || colSum == winResult || diagonal == winResult || rDiagonal == winResult;
    }

    protected boolean haveMove() {
        return Arrays.stream(area)
                .map(CharBuffer::wrap)
                .flatMapToInt(CharBuffer::chars)
                .filter(c -> c == emptySpace).count() != 0;
    }

    public boolean updateArea(int row, int coll, char mark) {
        try {
            if (area[row][coll] == emptySpace) {
                area[row][coll] = mark;
                return true;
            }
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Coordinates should be from 1 to " + area.length + "!");
            return false;
        }
    }

    public void drawArea() {
        System.out.println("---------");
        for (char[] row : area) {
            System.out.print("| ");
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.print("|\n");
        }
        System.out.println("---------");
    }
}

class BattleField {

    private final Field field;
    private final Player[] players;
    private State state;
    private int currentPlayerIdx;
    private int currentRow;
    private int currentCol;

    BattleField(Field field, Player[] players) {
        this.field = field;
        this.players = players;
        this.state = State.PROCESS;
        this.currentPlayerIdx = 0;
    }

    private boolean parseString(String data) {
        try {
            currentRow = Integer.parseInt(data.trim().split(" ")[0]) - 1;
            currentCol = Integer.parseInt(data.trim().split(" ")[1]) - 1;
            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("You should enter numbers!");
            return false;
        }
    }

    private boolean move(int row, int col) {
        return field.updateArea(row, col, players[currentPlayerIdx].getMark());
    }

    private boolean checkWin(int row, int col, char mark) {
        return field.checkWin(row, col, mark);
    }

    public boolean haveMove() {
        return field.haveMove();
    }

    public void nextMove(String data) {

        if (parseString(data)) {
            if (move(currentRow, currentCol)) {

                field.drawArea();

                if (checkWin(currentRow, currentCol, players[currentPlayerIdx].getMark())) {
                    System.out.println(players[currentPlayerIdx].getMark() + " wins");
                    state = State.FINISH;
                } else if (haveMove()) {
                    nextPlayer();
                } else {
                    System.out.println("Draw");
                    state = State.FINISH;
                }

            }
        }
    }

    private void nextPlayer() {
        currentPlayerIdx = this.currentPlayerIdx + 1 >= players.length ? 0 : this.currentPlayerIdx + 1;
    }

    public State getState() {
        return state;
    }

}

enum State {
    PROCESS,
    FINISH
}
