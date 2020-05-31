package tictactoe;

public abstract class Player {

    private final char mark;

    Player(char mark) {
        this.mark = mark;
    }

    public char getMark() {
        return mark;
    }
}

class TicTacToePlayer extends Player {
    TicTacToePlayer(char mark) {
        super(mark);
    }
}