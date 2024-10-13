import java.util.*;

class TicTacToe {
  public static void main(String[] args) {
    char[][] board = new char[3][3];
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        board[row][col] = ' ';
      }
    }

    char player = 'X';
    boolean gameOver = false;
    Scanner scanner = new Scanner(System.in);

    while (!gameOver) {
      printBoard(board);
      int row = -1, col = -1;
      
      while (true) {
        try {
          System.out.print("Player " + player + " enter row and column (0, 1, or 2): ");
          row = scanner.nextInt();
          col = scanner.nextInt();

          if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            System.out.println("Invalid input! Coordinates must be between 0 and 2.");
          } else if (board[row][col] != ' ') {
            System.out.println("Invalid move. Cell already occupied. Try again!");
          } else {
            break;
          }

        } catch (InputMismatchException e) {
          System.out.println("Invalid input! Please enter integer values.");
          scanner.next();
        }
      }

      board[row][col] = player;
      gameOver = haveWon(board, player);
      
      if (gameOver) {
        System.out.println("Player " + player + " has won!");
      } else {
        player = (player == 'X') ? 'O' : 'X';
      }

      if (isFull(board)) {
        printBoard(board);
        System.out.println("Game Over!! It's a tie.\nNew Game");

        System.out.println("Play Again? (Y/N): ");
        char c = scanner.next().charAt(0);
        if (c == 'N' || c == 'n') {
          gameOver = true;
        } else {
          for (char[] rowArray : board) {
            Arrays.fill(rowArray, ' ');
          }
          player = 'X';
        }
      }
    }

    printBoard(board);
  }

  public static boolean isFull(char[][] board) {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == ' ') {
          return false;
        }
      }
    }
    return true;
  }

  public static boolean haveWon(char[][] board, char player) {
    for (int row = 0; row < board.length; row++) {
      if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
        return true;
      }
    }

    for (int col = 0; col < board[0].length; col++) {
      if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
        return true;
      }
    }

    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
      return true;
    }
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
      return true;
    }

    return false;
  }

  public static void printBoard(char[][] board) {
    System.out.print("-------------\n");
    for (int row = 0; row < board.length; row++) {
      System.out.print("| ");
      for (int col = 0; col < board[row].length; col++) {
        System.out.print(board[row][col] + " | ");
      }
      System.out.print("\n-------------\n");
    }
  }
}
