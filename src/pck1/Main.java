package pck1;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int SIZE = 5;
    static final int DOTS_TO_WIN = 4;
    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    public static void main(String[] args) {
        // write your code here
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (whoWins() == DOT_X) {
                System.out.println("Вы выиграли!!!");
                break;
            }
            if (whoWins() ==  DOT_O) {
                System.out.printf("Победил ИИ!!!");
                break;
            }

            smartAiTurn();
            printMap();
            if (whoWins() == DOT_X) {
//                System.out.println("Ничья!");
                System.out.println("Вы выиграли!!!");
                break;
            }
            if (whoWins() ==  DOT_O) {
                System.out.printf("Победил ИИ!!!");
                break;
            }

        }
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <SIZE; j++) {
                map[i][j] = DOT_EMPTY;

            }

        }
    }
    public static void printMap() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 +" ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 +" ");
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%s ", map[i][j]);
            }
            System.out.println();

        }
    }

    public static void humanTurn() {
        int x;
        int y;
        System.out.println("Введите X Y ");

        do {
            x = sc.nextInt() - 1;  //j //Строка
            y = sc.nextInt() - 1; //i //Столбец
        } while (!isCellValid(y, x));
        map[y][x] = DOT_X;
    }
    public static boolean isCellValid (int y, int x) {
        if (x < 0 || x > SIZE - 1 || y < 0 || y > SIZE - 1) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static void aiTurn () {
        int x;
        int y;
        do {
            y = random.nextInt(SIZE);
            x = random.nextInt(SIZE);
        } while (!isCellValid(y, x));
        map[y][x] = DOT_O;
    }

    public static char whoWins() {

        for (int i = 0; i <= SIZE - 1 ; i++) {
            for (int j = 0; j <= SIZE - 1; j++) {
//                boolean isRight = j + 3 <= SIZE - 1;
//                boolean isDown = i + 3 <= SIZE -1;
//                boolean rightAndDown = (i + 3 <= SIZE -1 && j + 3 <= SIZE - 1);
//                boolean leftAndDown = (i - 3 >= 0 && j + 3 <= SIZE - 1);
                int winRight = 0;
                int winDown = 0;
                int winRightAndDown = 0;
                int winRightAndUp = 0;

                char sign = map[i][j];
                for (int ii = 0; ii <= SIZE - 1; ii++) {

                    if (ii + i <= SIZE-1) {
                        if (map[ii + i][j] == sign) {
                            winRight++;
                        } else {
                            winRight = 0;
                        }
                    }
                    if (winRight >= DOTS_TO_WIN) {
                        return sign;

                    }
                    if (ii+j <= SIZE - 1) {
                        if (map[i][ii+j] == sign) {
                            winDown++;
                        } else {
                            winDown = 0;
                        }
                    }
                    if (winDown >= DOTS_TO_WIN ) {
                        return sign;
                    }
                    if (ii + i <= SIZE-1 && ii+j <= SIZE - 1) {
                        if (map[i+ii][j+ii] == sign) {
                            winRightAndDown++;
                        } else {
                            winRightAndDown = 0;
                        }
                    }
                    if (winRightAndDown >= DOTS_TO_WIN) {
                        return sign;
                    }
                    if (i - ii >= 0 && j + ii <= SIZE - 1) {
                        if (map[i - ii][j + ii] == sign) {
                            winRightAndUp++;
                        } else {
                            winRightAndUp = 0;
                        }

                    }
                    if (winRightAndUp >= DOTS_TO_WIN) {
                        return sign;
                    }
                }

            }

        }
        return '!';
    }
    public static void smartAiTurn() {
        boolean isOut = false;
        //Блокировка хода игрока
        for (int i = 0; i <= SIZE - 1 ; i++) {
            int way = 0;
            for (int j = 0; j <= SIZE - 1; j++) {
                if (j > 0 && map[i][j-1] == DOT_X && map[i][j] == DOT_X){
                    if (isBlockFound(i, j, "right")) {
                        return;
                    }
                }
                if (i > 0 && map[i-1][j]==DOT_X && map[i][j]==DOT_X){
                    if (isBlockFound(i, j, "down")) {
                        return;
                    }
                }
                if (i > 0 && j > 0 && map[i-1][j-1]==DOT_X && map[i][j]==DOT_X){
                    if (isBlockFound(i, j, "rightDown")) {
                        return;
                    }
                }
                if (i > 0 && j < SIZE -1 && map[i-1][j+1]==DOT_X && map[i][j]==DOT_X){
                    if (isBlockFound(i, j, "rightUp")) {
                        return;
                    }
                }
            }
        }

        if (!isOut) {
            aiTurn();
        }
    }

    public static boolean isBlockFound(int i, int j, String inTurn){
        int j1 = 0;
        int j2 = 0;
        int i1 = 0;
        int i2 = 0;
        int dir = 1;
        int dirAdd = 0;
        if (inTurn.equals("right")) {
            j1 = 1;
            j2 = 2;
            dir = j;
        }
        if (inTurn.equals("down")) {
            i1 = 1;
            i2 = 2;
            dir = i;
        }

        if (inTurn.equals("rightDown")) {
            i1 = 1;
            i2 = 2;
            j1 = 1;
            j2 = 2;
            dir = i;
            dirAdd = j;
        }
        if (dir + 1 <= SIZE - 1 && dirAdd + 2 <= SIZE - 1) {
            if (map[i + i1][j + j1] == DOT_X) {  //3 подряд, 4я пустая
                //ход
                if (dir + 2 <= SIZE - 1 && dirAdd + 2 <= SIZE -1) {
                    if (map[i + i2][j + j2] == DOT_EMPTY) { //3 подряд, 4я пустая
                        map[i + i2][j + j2] = DOT_O;
                        return true;
                    }
                }
                if (dir -2 >= 0 && dirAdd -2 >= 0 && map[i+i1][j+j1] == DOT_X) {
                    map[i - i2][j - j2] = DOT_O; // 3 подряд, перед ними пусто
                    return true;
                }
            }
            if (dir + 2 <= SIZE - 1 && dirAdd + 2 <= SIZE - 1 && map[i + i1][j + j1] == DOT_EMPTY && map[i + i2][j + j2] == DOT_X) { // 2 полные, след пустая, за ней занято
                //ход
                map[i + i1][j + j1] = DOT_O;
                return true;
            }
        }
        return false;
    }
}
