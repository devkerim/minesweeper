import java.util.Scanner;

public class MineSweeper {
    private int[][] board;
    private boolean[][] mines;
    private int size;
    private int numMines;
    private boolean gameover;
    private int uncoveredCount;

    public MineSweeper(int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        this.board = new int[size][size];
        this.mines = new boolean[size][size];
        this.gameover = false;
        this.uncoveredCount = 0;
        initializeBoard();
        placeMines();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = -1;
            }
        }
    }

    private void placeMines() {
        int count = 0;
        while (count < numMines) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (!mines[row][col]) {
                mines[row][col] = true;
                count++;
            }
        }
    }

    private void uncoverCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size || board[row][col] != -1) {
            return;
        }

        int numAdjacentMines = countAdjacentMines(row, col);
        board[row][col] = numAdjacentMines;
        uncoveredCount++;

        if (numAdjacentMines == 0) {
            uncoverCell(row - 1, col - 1);
            uncoverCell(row - 1, col);
            uncoverCell(row - 1, col + 1);
            uncoverCell(row, col - 1);
            uncoverCell(row, col + 1);
            uncoverCell(row + 1, col - 1);
            uncoverCell(row + 1, col);
            uncoverCell(row + 1, col + 1);
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        if (hasMine(row - 1, col - 1)) count++;
        if (hasMine(row - 1, col)) count++;
        if (hasMine(row - 1, col + 1)) count++;
        if (hasMine(row, col - 1)) count++;
        if (hasMine(row, col + 1)) count++;
        if (hasMine(row + 1, col - 1)) count++;
        if (hasMine(row + 1, col)) count++;
        if (hasMine(row + 1, col + 1)) count++;
        return count;
    }

    private boolean hasMine(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && mines[row][col];
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz!");

        while (!gameover && uncoveredCount < size * size - numMines) {
            printBoard();

            System.out.print("Satır Giriniz: ");
            int row = scanner.nextInt();
            System.out.print("Sütun Giriniz: ");
            int col = scanner.nextInt();

            if (row < 0 || row >= size || col < 0 || col >= size) {
                System.out.println("Geçersiz koordinatlar. Lütfen tekrar girin.");
                continue;
            }

            if (mines[row][col]) {
                gameover = true;
                System.out.println("Game Over!!");
            } else {
                uncoverCell(row, col);
            }
        }

        if (!gameover) {
            System.out.println("Oyunu Kazandınız!");
        }

        printBoard();
    }

    private void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == -1) {
                    System.out.print("- ");
                } else if (board[i][j] == 0) {
                    System.out.print("0 ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("===========================");
    }

    public static void main(String[] args) {
        int size = 3;
        int numMines = size * size / 4;
        MineSweeper game = new MineSweeper(size, numMines);
        game.play();
    }
}
