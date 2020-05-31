package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        Player[] players = new Player[]{new TicTacToePlayer('X'), new TicTacToePlayer('O')};
        Field field = new Field();
        BattleField battleField = new BattleField(field, players);

        while (battleField.getState() != State.FINISH) {

            System.out.print("Enter the coordinates: ");
            battleField.nextMove(scanner.nextLine());

        }
    }
}
