package org.example;


import java.util.Random;
import java.util.Scanner;



public class Program {
    private static char[][] field;                                  // поле
    private static final char DOT_HUMAN = 'X';                      // X-хода человека
    private static final char DOT_AI = 'O';                         // ход-компьютера
    private static final char DOT_EMPTY = '*';                      // пустая ячейка
    private static final Scanner SCANNER = new Scanner(System.in);  // считываем данные, фиксируем тип
    private static final Random RANDOM = new Random();              // ссылочный тип не может зафиксировать значение.
    private static int fieldSizeX = 5;                                  // размер поля по оси X (горизонталь)
    private static int fieldSizeY = 5;                                  // размер поля по оси Y (Вертикаль)
    private static int lenWinSize = 4;                                  // условие победы 4-элементов в ряд
    private static int ht_x = 0;                                     // координата последнего хода человека по x
    private static int ht_y = 0;                                     // координата последнего хода человека по y


    public static void main(String[] args) {
        while (true) {                                              // формируем бесконечный игровой цикл
            initField();
            printField();

            while (true) {
                humanTurn();
                printField();
                if (checkGame(DOT_HUMAN, "Вы победили!!!"))
                    break;
                aiTurn();
                printField();
                if (checkGame(DOT_AI, "Победил компьютер!!"))
                    break;
            }
            System.out.println("Сыграем еще?");
            if (!SCANNER.next().equals("y")) {
                SCANNER.close();
                break;
            }
        }

    }

    /**
     * метод инициализации игрового поля
     */
    private static void initField() {

        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * метод вывода визуализации поля в консоль
     */
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }

    /**
     * Ход игрока (человека)
      */
    private static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода X и Y\n(от 1 до 5) через пробел: ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
        ht_x = x; // запоминает последний ход противника."
        ht_y = y; // запоминает последний ход противника."
    }

    /**
     * метод проверки свободна ли ячейка
      */
    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверяет правильность хода,(не ушёл ли пользователь за границы поля)
     * @param x- координата
     * @param y- координата
     * @return- результат проверки
     */
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * метод проверки игры
     * @param dot- фишка игрока
     * @param s- победный слоган
     * @return- состояние игры
     */
    private static boolean checkGame(char dot, String s) {

        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья !!!");
            return true;
        }
        return false;
    }

    /**
     * метод проверки на получение выигрышной комбинации
      */
    private static boolean checkWin(char c) {
        //Проверка наличия победной комбинации по горизонтали
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length - (lenWinSize - 1); x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y][x + z] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация по горизонтали");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }

        /**
         * Проверка наличия победной комбинации по вертикали
         */
        for (int y = 0; y < field.length - (lenWinSize - 1); y++) {
            for (int x = 0; x < field[y].length; x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y + z][x] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация по вертикали");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }

        /**
         * Проверка наличия победной комбинации вдоль главной диагонали (лево верх - право низ)
         */
        for (int y = 0; y < field.length - (lenWinSize - 1); y++) {
            for (int x = 0; x < field[y].length - (lenWinSize - 1); x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y + z][x + z] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация вдоль главной диагонали слева направо");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }

        /**
         * проверка наличия победной комбинации вдоль второстепенной диагонали (право верх - лево низ)
         */
        for (int y = 0; y < field.length - (lenWinSize - 1); y++) {
            for (int x = lenWinSize - 1; x < field[y].length; x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y + z][x - z] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация вдоль второстепенной справа налево");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }
        return false;
    }

    /**
     * Поверка на ничью (все ячейки игрового поля заполнены фишками человека или компьютера)
     * @return
     */
    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }
    /**
     * Ход игрока (компьютера)
     */
    private static void aiTurn() {
        int x;
        int y;
        int decisionIsMade = 0;

        if (decisionIsMade == 0) {

            for (int py = 0; py < field.length && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { //найден ключевой (стартовый элемент, запускается поиск "хвоста"
                        int z = 1;
                        do {
                            if (field[py][px + z] != DOT_AI) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена пред-победная комбинация по горизонтали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + (px + z) + " y = " + py);
                                    if (isCellValid(px + z, py) && isCellEmpty(px + z, py)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"спереди\" --- ставлю в точку х = " + (px + z) + " y = " + py);
                                        field[py][px + z] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px - 1, py) && isCellEmpty(px - 1, py)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"сзади\" --- ставлю в точку х = " + (px - 1) + " y = " + py);
                                        field[py][px - 1] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { // запускаем поиск хвоста вниз
                        int z = 1;
                        do {
                            if (field[py + z][px] != DOT_AI) { //если последовательно сть прервалась
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена пред-победная комбинация по вертикали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + px + " y = " + (py + z));
                                    if (isCellValid(px, py + z) && isCellEmpty(px, py + z)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"снизу\" --- ставлю в точку х = " + px + " y = " + (py + z));
                                        field[py + z][px] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px, py - 1) && isCellEmpty(px, py - 1)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"сверху\" --- ставлю в точку х = " + px + " y = " + (py - 1));
                                        field[py - 1][px] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { // запускаем поиск хвоста вниз вправо
                        int z = 1;
                        do {
                            if (field[py + z][px + z] != DOT_AI) { //если последовательность прервалась
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена ПРЕД-победная комбинация вдоль главной диагонали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + (px + z) + " y = " + (py + z));
                                    if (isCellValid(px + z, py + z) && isCellEmpty(px + z, py + z)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"снизу\" --- ставлю в точку х = " + (px + z) + " y = " + (py + z));
                                        field[py + z][px + z] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px - 1, py - 1) && isCellEmpty(px - 1, py - 1)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"сверху\" --- ставлю в точку х = " + (px - 1) + " y = " + (py - 1));
                                        field[py - 1][px - 1] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = lenWinSize - 2; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { // запускаем поиск хвоста вниз влево
                        int z = 1;
                        do {
                            if (field[py + z][px - z] != DOT_AI) { //если последовательность прервалась
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена ПРЕД-победная комбинация вдоль главной диагонали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + (px - z) + " y = " + (py + z));
                                    if (isCellValid(px - z, py + z) && isCellEmpty(px - z, py + z)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"снизу\" --- ставлю в точку х = " + (px - z) + " y = " + (py + z));
                                        field[py + z][px - z] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px + 1, py - 1) && isCellEmpty(px + 1, py - 1)) {
                                        System.out.println("Решение принял алгоритм \"Победа\" условие \"сверху\" --- ставлю в точку х = " + (px + 1) + " y = " + (py - 1));
                                        field[py - 1][px + 1] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }
        }


        if (decisionIsMade == 0) {

            for (int py = 0; py < field.length && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py][px + z] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px - 1, py) && isCellEmpty(px - 1, py) && isCellValid(px + z, py) && isCellEmpty(px + z, py)) {
                                        System.out.println("Обнаружена пред-проигрышная комбинация по горизонтали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + (px - 1) + " y=" + py + " или х=" + (px + z) + " y=" + py);

                                        if (getBalansOfForses(px - 1, py) > getBalansOfForses(px + z, py)) {
                                            field[py][px - 1] = DOT_AI;
                                            System.out.println("Решение принял алгоритм ставлю в точку х = " + (px - 1) + " y = " + py);
                                        } else {
                                            field[py][px + z] = DOT_AI;
                                            System.out.println("Решение принял алгоритм ставлю в точку х = " + (px + z) + " y = " + py);
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py + z][px] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px, py - 1) && isCellEmpty(px, py - 1) && isCellValid(px, py + z) && isCellEmpty(px, py + z)) {
                                        System.out.println("Обнаружена пред-проигрышная комбинация по вертикали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + px + " y=" + (py - 1) + " или х=" + px + " y=" + (py + z));

                                        if (getBalansOfForses(px, py - 1) > getBalansOfForses(px, py + z)) {
                                            field[py - 1][px] = DOT_AI;
                                            System.out.println("Решение принял алгоритм ставлю в точку х = " + px + " y = " + (py - 1));
                                        } else {
                                            field[py + z][px] = DOT_AI;
                                            System.out.println("Решение принял алгоритм ставлю в точку х = " + px + " y = " + (py + z));
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }


            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py + z][px + z] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px - 1, py - 1) && isCellEmpty(px - 1, py - 1) && isCellValid(px + z, py + z) && isCellEmpty(px + z, py + z)) {
                                        System.out.println("Обнаружена пред-проигрышная комбинация вдоль главной диагонали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + (px - 1) + " y=" + (py - 1) + " или х=" + (px + z) + " y=" + (py + z));
                                        // выбор конкретно куда поставить в начало или в конец пока оставим за дополнительным алгоритмом подсчета соседей
                                        // алгоритм тупой пока.
                                        if (getBalansOfForses(px - 1, py - 1) > getBalansOfForses(px + z, py + z)) {
                                            field[py - 1][px - 1] = DOT_AI;
                                            System.out.println("Решение принял алгоритм, главная диагональ сверху, ставлю в точку х = " + (px - 1) + " y = " + (py - 1));
                                        } else {
                                            field[py + z][px + z] = DOT_AI;
                                            System.out.println("Решение принял алгоритм, условие, главная диагональ снизу, ставлю в точку х = " + (px + z) + " y = " + (py + z));
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = lenWinSize - 2; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py + z][px - z] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px + 1, py - 1) && isCellEmpty(px + 1, py - 1) && isCellValid(px - z, py + z) && isCellEmpty(px - z, py + z)) {
                                        System.out.println("Обнаружена пред-проигрышная комбинация вдоль второстепенной диагонали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + (px + 1) + " y=" + (py - 1) + " или х=" + (px - z) + " y=" + (py + z));

                                        if (getBalansOfForses(px + 1, py - 1) > getBalansOfForses(px - z, py + z)) {
                                            field[py - 1][px + 1] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \" условие \"второстепенная диагональ сверху\"  ставлю в точку х = " + (px - 1) + " y = " + (py - 1));
                                        } else {
                                            field[py + z][px - z] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \" условие \"второстепенная диагональ снизу\"  ставлю в точку х = " + (px - z) + " y = " + (py + z));
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }
        }


        if (decisionIsMade == 0) {

            if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y - 1) && field[ht_y - 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x + 1, ht_y + 1) && isCellEmpty(ht_x + 1, ht_y + 1)) {
                decisionIsMade = 1;
                field[ht_y + 1][ht_x + 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y - 1) && field[ht_y - 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x - 2, ht_y - 2) && isCellEmpty(ht_x - 2, ht_y - 2)) {
                decisionIsMade = 1;
                field[ht_y - 2][ht_x - 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x, ht_y - 1) && field[ht_y - 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y + 1) && isCellEmpty(ht_x, ht_y + 1)) {
                decisionIsMade = 1;
                field[ht_y + 1][ht_x] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x, ht_y - 1) && field[ht_y - 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y - 2) && isCellEmpty(ht_x, ht_y - 2)) {
                decisionIsMade = 1;
                field[ht_y - 2][ht_x] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y - 1) && field[ht_y - 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x - 1, ht_y + 1) && isCellEmpty(ht_x - 1, ht_y + 1)) {
                decisionIsMade = 1;
                field[ht_y + 1][ht_x - 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y - 1) && field[ht_y - 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x + 2, ht_y - 2) && isCellEmpty(ht_x + 2, ht_y - 2)) {
                decisionIsMade = 1;
                field[ht_y - 2][ht_x + 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y) && field[ht_y][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x - 1, ht_y) && isCellEmpty(ht_x - 1, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x - 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y) && field[ht_y][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x + 2, ht_y) && isCellEmpty(ht_x + 2, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x + 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y + 1) && field[ht_y + 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x - 1, ht_y - 1) && isCellEmpty(ht_x - 1, ht_y - 1)) {
                decisionIsMade = 1;
                field[ht_y - 1][ht_x - 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y + 1) && field[ht_y + 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x + 2, ht_y + 2) && isCellEmpty(ht_x + 2, ht_y + 2)) {
                decisionIsMade = 1;
                field[ht_y + 2][ht_x + 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x, ht_y + 1) && field[ht_y + 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y - 1) && isCellEmpty(ht_x, ht_y - 1)) {
                decisionIsMade = 1;
                field[ht_y - 1][ht_x] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x, ht_y + 1) && field[ht_y + 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y + 2) && isCellEmpty(ht_x, ht_y + 2)) {
                decisionIsMade = 1;
                field[ht_y + 2][ht_x] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y + 1) && field[ht_y + 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x + 1, ht_y - 1) && isCellEmpty(ht_x + 1, ht_y - 1)) {
                decisionIsMade = 1;
                field[ht_y - 1][ht_x + 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y + 1) && field[ht_y + 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x - 2, ht_y + 2) && isCellEmpty(ht_x - 2, ht_y + 2)) {
                decisionIsMade = 1;
                field[ht_y + 2][ht_x - 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y) && field[ht_y][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x + 1, ht_y) && isCellEmpty(ht_x + 1, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x + 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y) && field[ht_y][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x - 2, ht_y) && isCellEmpty(ht_x - 2, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x - 2] = DOT_AI;
            }
            if (decisionIsMade != 0) {
                System.out.println("Решение принял алгоритм");
            }
        }


        if (decisionIsMade == 0) {
            do {
                x = RANDOM.nextInt(fieldSizeX);
                y = RANDOM.nextInt(fieldSizeY);
            } while (!isCellEmpty(x, y));
            System.out.println("Решение принял генератор случайного хода, x = " + x + ", y = " + y);
            field[y][x] = DOT_AI; // Записываем ход в массив
        }
    }

    /**
     * Метод проверки баланса вокруг поля
     * @param bx
     * @param by
     * @return
     */
    private static int getBalansOfForses(int bx, int by) {
        int balans = 0;
        //
        if (isCellValid(bx - 1, by - 1) && field[by - 1][bx - 1] == DOT_AI) balans += 3;
        if (isCellValid(bx - 1, by - 1) && field[by - 1][bx - 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx - 1, by - 1) && field[by - 1][bx - 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx, by - 1) && field[by - 1][bx] == DOT_AI) balans += 3;
        if (isCellValid(bx, by - 1) && field[by - 1][bx] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx, by - 1) && field[by - 1][bx] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx + 1, by - 1) && field[by - 1][bx + 1] == DOT_AI) balans += 3;
        if (isCellValid(bx + 1, by - 1) && field[by - 1][bx + 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx + 1, by - 1) && field[by - 1][bx + 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx + 1, by) && field[by][bx + 1] == DOT_AI) balans += 3;
        if (isCellValid(bx + 1, by) && field[by][bx + 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx + 1, by) && field[by][bx + 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx - 1, by + 1) && field[by + 1][bx - 1] == DOT_AI) balans += 3;
        if (isCellValid(bx - 1, by + 1) && field[by + 1][bx - 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx - 1, by + 1) && field[by + 1][bx - 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx, by + 1) && field[by + 1][bx] == DOT_AI) balans += 3;
        if (isCellValid(bx, by + 1) && field[by + 1][bx] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx, by + 1) && field[by + 1][bx] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx + 1, by + 1) && field[by + 1][bx + 1] == DOT_AI) balans += 3;
        if (isCellValid(bx + 1, by + 1) && field[by + 1][bx + 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx + 1, by + 1) && field[by + 1][bx + 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx - 1, by) && field[by][bx - 1] == DOT_AI) balans += 3;
        if (isCellValid(bx - 1, by) && field[by][bx - 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx - 1, by) && field[by][bx - 1] == DOT_EMPTY) balans += 1;
        return balans;
    }
}