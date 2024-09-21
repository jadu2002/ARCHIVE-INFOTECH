import java.util.Scanner;

public class NumberToWords {

    private static final String[] ones = {
        "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] tens = {
        "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static String twoDigitToWords(int n) {
        if (n < 20) {
            return ones[n];
        } else {
            return tens[n / 10] + (n % 10 == 0 ? "" : " " + ones[n % 10]);
        }
    }

    private static String threeDigitToWords(int n) {
        String hundredPart = (n / 100 > 0) ? ones[n / 100] + " hundred" : "";
        int remainder = n % 100;
        if (remainder > 0) {
            return hundredPart + (hundredPart.isEmpty() ? "" : " ") + twoDigitToWords(remainder);
        }
        return hundredPart;
    }

    private static String groupToWords(int n, String scale) {
        if (n == 0) return "";
        String result = threeDigitToWords(n);
        return result.isEmpty() ? "" : result + " " + scale;
    }

    public static String numberToWords(int num) {
        if (num == 0) return "zero";
        
        String[] scales = {"billion", "million", "thousand", ""};
        int[] units = {1000000000, 1000000, 1000, 1};
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < units.length; i++) {
            int part = num / units[i];
            if (part > 0) {
                result.append(groupToWords(part, scales[i]).trim()).append(" ");
            }
            num %= units[i];
        }
        
        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number between 1 and 999,999,999: ");
        int number = scanner.nextInt();
        
        if (number < 1 || number > 999999999) {
            System.out.println("Please enter a number between 1 and 999,999,999.");
        } else {
            System.out.println("In words: " + numberToWords(number));
        }
        
        scanner.close();
    }
}
