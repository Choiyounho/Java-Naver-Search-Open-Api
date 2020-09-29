package outputbookdata.controller;

import outputbookdata.service.BookService;
import outputbookdata.view.InputView;

public class BookController {

    public void run() {
        try {
            while (true) {
                inputStatus(InputView.inputStart());
            }
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

    private static void inputStatus(String inputStatus) {
        if (inputStatus.equalsIgnoreCase("I")) {
            new BookService().bookSearch();
        }
        if (inputStatus.equalsIgnoreCase("E")) {
            System.out.println("프로그램종료");
            System.exit(0);
        }
        System.out.println("잘못 입력하였습니다.");
    }

}
