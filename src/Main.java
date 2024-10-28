
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StringBuilder sbWord = new StringBuilder();
    private static int countFault;//Счетчики
    private static int countGood;
    private static final StringBuilder sbFaultLetter = new StringBuilder();
    private static final StringBuilder sbLet = new StringBuilder();
    private static char[] encryptWord;//Массив
    private static String randomWord;
    public static final String ANSI_RED = "\u001B[31m";//Константы
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";


    public static void main(String[] args) throws FileNotFoundException {
        picture();
        System.out.println("что бы начать игру введите 1");
        System.out.println("что бы закончить игру введите 2");
        startGame();
    }

    // Ввод для запуска игры и проверка на правильность ввода
    public static void startGame() throws FileNotFoundException {
       while (true) {
           if (scanner.hasNextInt()) {
               int input = scanner.nextInt();

               if (input == 2) {
                   System.out.println("Игра окончена");
                   System.exit(0);
               }

               if (input == 1) {
                   wordDictionary();
               } else {
                   System.out.println("Неверный ввод. Повторите попытку.");
                   startGame();
               }
           } else {
               System.out.println("Неверный ввод. Повторите попытку.");
               scanner.nextLine(); //Очистка буфера ввода, чтобы не читал повторно неверный ввод
           }
       }
    }


    //Словарь и шифрование слова
    static void wordDictionary() throws FileNotFoundException {
        String dictionary = "dictyonary";//Словарь dictyonary лежит в проекте

        File file = new File(dictionary);

        Scanner scanner1 = new Scanner(file);
        if (!scanner1.hasNext()){//Проверка что в словаре есть слово/а
            System.out.println("Слово не найдено в словаре");
            return;
        }

        List<String> words = new ArrayList<>(); //Создание списка

        //считывает все строки из scanner1,убирает лишние пробелы и добавляет их в список words до тех пор,пока не закончатся данные для чтения
        while ((scanner1.hasNext())){
            words.add(scanner1.nextLine().trim());//Метод trim удаляет пробелы в начале и конце считанной строки
        }
            //Случайный выбор слова из словаря и сохраняем в randomWord
            Random random = new Random();
            randomWord = words.get(random.nextInt(words.size()));//Сгенерировано случайное число, представляющее индекс элемента в списке words.
            sbWord.append(randomWord);
        
        //Шифрование слова
            encryptWord = new char[randomWord.length()];//Массив символов длинной = длине randomWord и присвоено переменной enctyptWord
            for (int i = 0; i < randomWord.length(); i++) {
                encryptWord[i] = '*';
            }

            System.out.println();
            System.out.print("Отгадайте слово: ");
            System.out.println(encryptWord);
            game();

        scanner1.close();

    }


    static void game() throws FileNotFoundException {//Цикл игры
        while (countGood < randomWord.length() && countFault < 6) {
            System.out.println();
            System.out.println("Введите букву");

            String let = scanner.next();
            char inputLet = let.charAt(0); //Создание массива
            boolean good = false;


            if (Pattern.matches("[a-zA-Zа-яА-ЯёЁ]", let)) {//Пороверка что введена буква

            } else {
                System.out.println("Неверный ввод. Введите букву");
                continue;
            }

            if (sbLet.indexOf(let) != -1) {//Проверка на повторный ввод буквы
                System.out.println();
                System.out.println("Эту букву уже вводили: " + let);
                continue;

            } else {
                sbLet.append(let);

            }

            for (int i = 0; i <randomWord.length(); i++) {//Замена звездочки на угаданную букву
                if (randomWord.charAt(i) == inputLet) {
                    encryptWord[i] = inputLet;
                    good = true;
                    countGood++;
                }
            }

            if (good) {//Действие -угадали букву
                System.out.println();
                System.out.println("Угадали букву: " + String.valueOf(encryptWord));
            } else {//Действие - не угодали букву
                countFault++;
                sbFaultLetter.append(inputLet + ",");
                System.out.println();
                System.out.println("Не угодали букву: " + sbFaultLetter);
                showPicture();
            }

        }

        if (countGood == randomWord.length()) {//счетчик правильных букв равен длине слова
            System.out.println();
            System.out.println(ANSI_GREEN + "Вы выиграли!!! Загаданное слово: " + sbWord + ANSI_RESET);//Выделяем цветом текст поздравления
            System.out.println();
            continueExitGame();
        }
        if (countFault == 6) {
            System.out.println();
            System.out.println(ANSI_RED + "  Вы проиграли" + ANSI_RESET);//Выделяем цветом текст
            System.out.println();
            continueExitGame();
        }
    }

    static void counterNull() {//Обнуление при повторном запуске игры
        countFault = 0;
        countGood = 0;
        sbWord.setLength(0);
        sbFaultLetter.setLength(0);
        sbLet.setLength(0);
    }

    static void continueExitGame() throws FileNotFoundException {//Продолжение/выход игры
        counterNull();
        System.out.println("Чтобы повторить игру, введите 1");
        System.out.println("Чтобы закончить игру, введите 2");
        picture();
        startGame();
    }

    static void showPicture() {//Показ висилицы при неверном вводе буквы
        if (countFault == 1) {
            pictureOne();
        }
        if (countFault == 2) {
            pictureTwo();
        }
        if (countFault == 3) {
            pictureThree();
        }
        if (countFault == 4) {
            pictureFour();
        }
        if (countFault == 5) {
            pictureFive();
        }
        if (countFault == 6) {
            pictureSix();
        }

    }

    static void picture() {//Изображение висилицы
        System.out.println("      __________");
        System.out.println("       |/      |");
        System.out.println("       |");
        System.out.println("       |");
        System.out.println("       |");
        System.out.println("    ___|___");
    }

    static void pictureOne() {
        System.out.println("      __________");
        System.out.println("       |/      |");
        System.out.println("       |       O");
        System.out.println("       |");
        System.out.println("       |");
        System.out.println("    ___|___");
    }

    static void pictureTwo() {
        System.out.println("      __________");
        System.out.println("       |/      |");
        System.out.println("       |       O");
        System.out.println("       |       |");
        System.out.println("       |");
        System.out.println("    ___|___");
    }

    static void pictureThree() {
        System.out.println("      __________");
        System.out.println("       |/      |");
        System.out.println("       |       O");
        System.out.println("       |      /|");
        System.out.println("       |");
        System.out.println("    ___|___");
    }

    static void pictureFour() {
        System.out.println("      _________");
        System.out.println("       |/     |");
        System.out.println("       |      O");
        System.out.println("       |     /|");
        System.out.println("       |     /");
        System.out.println("    ___|___");
    }

    static void pictureFive() {
        System.out.println("      _________");
        System.out.println("       |/     |");
        System.out.println("       |      O");
        System.out.println("       |     /|\\");
        System.out.println("       |     /");
        System.out.println("    ___|___");
    }

    static void pictureSix() {
        System.out.println("      _________");
        System.out.println("       |/     |");
        System.out.println("       |      O");
        System.out.println("       |     /|\\");
        System.out.println("       |     / \\");
        System.out.println("    ___|___");
    }


}
