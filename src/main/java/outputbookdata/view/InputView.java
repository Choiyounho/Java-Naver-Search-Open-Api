package outputbookdata.view;

import java.util.Scanner;

import static utils.CommonsConstant.*;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String inputBookName() {
        System.out.print(BOOKNAME + COLON);
        return scanner.nextLine();
    }

    public static String inputBookAuthor() {
        System.out.print(AUTHOR + COLON);
        return scanner.nextLine();
    }

    public static String inputBookCompany() {
        System.out.print(COMPANY + COLON);
        return scanner.nextLine();
    }

    public static String progress() {
        System.out.println("계속입력 하시려면 Y / 입력종료 N" + COLON);
        return scanner.nextLine();
    }

    public static String inputStart() {
        System.out.print("입력처리(I)/종료(E):");
        return scanner.nextLine();
    }

}
