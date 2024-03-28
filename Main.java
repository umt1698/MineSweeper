

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Satır Sayısını Giriniz: ");
        int rows = scanner.nextInt();
        System.out.print("Sütun Sayısını Giriniz: ");
        int columns = scanner.nextInt();
        if (rows < 2 || columns < 2) {
            System.out.println("Satır ve sütun sayıları en az 2 olmalıdır!");
            return;
        }

        MineSweeper game = new MineSweeper(rows, columns);
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        game.play();
    }
}
