package util;

import java.util.Scanner;

//Scanner 클래스를 통해 입력받을 시에 도울이 될만한 static 메소드를 모아둔 클래스
public class ScannerUtil {
    //입력 시 사용자에게 보여줄 메시지 출력을 담당할 메소드
    public static void printMeaages(String message) {
        System.out.println(message);
        System.out.println("> ");
    }

    // 스캐너 버그를 미리 방지하는 nextline()
    public static String nextLine(Scanner scanner, String message) {
        printMeaages(message);
        String temp = scanner.nextLine();
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        return temp;
    }

    public static String nextLine(Scanner scanner) {

        String temp = scanner.nextLine();
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        return temp;
    }

    //사용자의 정수입력을 담당하는 nextInt()
    public static int nextInt(Scanner scanner, String message) {
        String strTemp = nextLine(scanner, message);

        while (!strTemp.matches("\\d+")) {
            System.out.println("잘못입력하셨습니다.");
            strTemp = nextLine(scanner, message);
        }

        int temp = 0;
        try {
            temp = Integer.parseInt(strTemp);
            return temp;
        } catch (NumberFormatException e) {
            printMeaages("잘못입력하셨습니다.");
        }
        return temp;
    }

    // 사용자로 부터 특정 범위의 정수 입력을 담당하는 nextInt()
    public static int nextInt(Scanner scanner, String message, int max, int min) {
        int temp = nextInt(scanner, message);

        while (temp < min || temp > max) {
            System.out.println("잘못입력하셨습니다");
            temp = nextInt(scanner, message);
        }
        return temp;
    }


}
