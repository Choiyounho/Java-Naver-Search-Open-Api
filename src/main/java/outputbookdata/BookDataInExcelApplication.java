package outputbookdata;

import outputbookdata.domain.ExcelDao;
import outputbookdata.view.InputView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 검색한 책의 내용은 엑셀에 입력
 */
public class BookDataInExcelApplication {
    public static void main(String[] args) {
        ExcelDao dao = new ExcelDao();
        try {
            String inputStatus = InputView.inputStart();
            if (inputStatus.equals("I")) {
                dao.excelInput();
            }
            if (inputStatus.equals("E")) {
                System.out.println("프로그램종료");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}

