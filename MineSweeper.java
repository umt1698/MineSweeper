import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private char[][] map;
    private char[][] mineMap;
    private int rows;
    private int columns;
    private int mines;
    private boolean[][] revealed;
    private boolean gameOver;
    private boolean gameWon;

    public MineSweeper(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.map = new char[rows][columns];
        this.mineMap = new char[rows][columns];
        this.revealed = new boolean[rows][columns];
        this.gameOver = false;
        this.gameWon = false;
        this.mines = rows * columns / 4; // 1/4 of total cells
        initializeMap();
        placeMines();
    }

    private void initializeMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                map[i][j] = '-';
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int count = 0;
        while (count < mines) {
            int randRow = random.nextInt(rows);
            int randCol = random.nextInt(columns);
            if (mineMap[randRow][randCol] != '*') {
                mineMap[randRow][randCol] = '*';
                count++;
            }
        }
    }

    private void calculateValues() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mineMap[i][j] != '*') {
                    int count = countAdjacentMines(i, j);
                    mineMap[i][j] = (char) (count + '0');
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if (mineMap[i][j] == '*') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        calculateValues();

        while (!gameOver && !gameWon) {
            printMap();
            System.out.print("Satır Giriniz: ");
            int row = scanner.nextInt();
            System.out.print("Sütun Giriniz: ");
            int col = scanner.nextInt();

            if (row < 0 || row >= rows || col < 0 || col >= columns) {
                System.out.println("Geçersiz koordinatlar! Lütfen tekrar girin.");
                continue;
            }

            if (revealed[row][col]) {
                System.out.println("Bu koordinat daha önce seçildi, başka bir koordinat girin.");
                continue;
            }

            if (mineMap[row][col] == '*') {
                gameOver = true;
                System.out.println("Game Over!!");
                revealMines();
                printMap();
            } else {
                revealEmptyCells(row, col);
                if (checkWin()) {
                    gameWon = true;
                    System.out.println("Oyunu Kazandınız !");
                    printMap();
                }
            }
        }

        scanner.close();
    }

    private void printMap() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("===========================");
    }

    private void revealMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mineMap[i][j] == '*') {
                    map[i][j] = '*';
                }
            }
        }
    }

    private void revealEmptyCells(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns || revealed[row][col]) {
            return;
        }

        revealed[row][col] = true;
        if (mineMap[row][col] != '0') {
            map[row][col] = mineMap[row][col];
            return;
        }

        map[row][col] = '0';
        revealEmptyCells(row - 1, col);
        revealEmptyCells(row + 1, col);
        revealEmptyCells(row, col - 1);
        revealEmptyCells(row, col + 1);
        revealEmptyCells(row - 1, col - 1);
        revealEmptyCells(row - 1, col + 1);
        revealEmptyCells(row + 1, col - 1);
        revealEmptyCells(row + 1, col + 1);
    }

    private boolean checkWin() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!revealed[i][j] && mineMap[i][j] != '*') {
                    return false;
                }
            }
        }
        return true;
    }


}