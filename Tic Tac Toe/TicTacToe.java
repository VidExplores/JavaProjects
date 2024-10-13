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
        char ai = 'O';
        boolean gameOver = false;
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            printBoard(board);
            int row = -1, col = -1;

            if (player == 'X') {
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
            } else {
                int[] move = findBestMove(board, ai);
                board[move[0]][move[1]] = ai;
                System.out.println("AI placed at row " + move[0] + " and column " + move[1]);
            }

            gameOver = haveWon(board, player);

            if (gameOver) {
                printBoard(board);
                System.out.println("Player " + player + " has won!");
            } else {
                if (isFull(board)) {
                    printBoard(board);
                    System.out.println("Game Over!! It's a tie.\nNew Game");
                    break;
                }
                player = (player == 'X') ? 'O' : 'X';
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

    public static int evaluate(char[][] board) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 'O')
                    return 10;
                else if (board[row][0] == 'X')
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 'O')
                    return 10;
                else if (board[0][col] == 'X')
                    return -10;
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O')
                return 10;
            else if (board[0][0] == 'X')
                return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O')
                return 10;
            else if (board[0][2] == 'X')
                return -10;
        }

        return 0;
    }

    public static int minimax(char[][] board, int depth, boolean isMax) {
        int score = evaluate(board);

        if (score == 10 || score == -10)
            return score;

        if (isFull(board))
            return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }

    public static int[] findBestMove(char[][] board, char ai) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = ai;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = ' ';
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
}
