import java.util.Scanner;

class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение из двух чисел от 1 до 10 или от I до X, оператор +, -, :, *: ");
        String input = scanner.nextLine();

        try {
            String[] tokens = input.split(" ");
            if (tokens.length != 3) {
                throw new IllegalArgumentException("Для расчета выражения необходимо ввести два числа от 1 до 10 или от I до Х и необходимый оператор, введенное Вами выражение не соответствует форме ввода.");
            }

            boolean isRoman = isRomanNumber(tokens[0]) && isRomanNumber(tokens[2]);
            boolean isArabic = isArabicNumber(tokens[0]) && isArabicNumber(tokens[2]);

            if (!isRoman && !isArabic) {
                throw new IllegalArgumentException("Вводимые числа должны быть из одной системы счисления");
            }

            int a, b;

            if (isRoman) {
                if (!isValidRoman(tokens[0]) || !isValidRoman(tokens[2])) {
                    throw new IllegalArgumentException("Римские числа должны быть от I до X");
                }
                a = RomanConverter.romanToArabic(tokens[0]);
                b = RomanConverter.romanToArabic(tokens[2]);
            } else {
                a = Integer.parseInt(tokens[0]);
                b = Integer.parseInt(tokens[2]);
            }

            char operation = tokens[1].charAt(0);
            int result;

            switch (operation) {
                case '+':
                    result = a + b;
                    break;
                case '-':
                    result = a - b;
                    break;
                case '*':
                    result = a * b;
                    break;
                case '/':
                    if (b == 0) {
                        throw new ArithmeticException("Деление на ноль");
                    }
                    result = a / b;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция");
            }

            if (isRoman) {
                System.out.println(RomanConverter.arabicToRoman(result));
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    static boolean isRomanNumber(String input) {
        String romanNumerals = "IVXLCDM";
        for (char c : input.toCharArray()) {
            if (romanNumerals.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    static boolean isArabicNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isValidRoman(String input) {
        String[] validRomanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String romanNumeral : validRomanNumerals) {
            if (romanNumeral.equals(input)) {
                return true;
            }
        }
        return false;
    }
}

class RomanConverter {
    static int romanToArabic(String roman) {
        int arabic = 0;
        int lastValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            char ch = roman.charAt(i);
            int value;
            switch (ch) {
                case 'I':
                    value = 1;
                    break;
                case 'V':
                    value = 5;
                    break;
                case 'X':
                    value = 10;
                    break;
                case 'L':
                    value = 50;
                    break;
                case 'C':
                    value = 100;
                    break;
                default:
                    throw new IllegalArgumentException("Неверный символ в римском числе");
            }
            if (value < lastValue) {
                arabic -= value;
            } else {
                arabic += value;
            }
            lastValue = value;
        }
        return arabic;
    }

    static String arabicToRoman(int arabic) {
        if (arabic < 1) {
            throw new IllegalArgumentException("Число должно быть больше 0");
        }

        String[] romanSymbols = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
                "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
                "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};
        if (arabic > romanSymbols.length) {
            throw new IllegalArgumentException("Число должно быть не больше " + romanSymbols.length);
        }
        return romanSymbols[arabic - 1];
    }
}