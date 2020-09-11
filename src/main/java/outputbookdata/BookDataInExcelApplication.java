package outputbookdata;

import outputbookdata.domain.ExcelDao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 검색한 책의 내용은 엑셀에 입력
 */

public class BookDataInExcelApplication {
    public static void main(String[] args) {
        ExcelDao dao = new ExcelDao();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("★대문자로 입력해주세요★");
            System.out.print("입력처리(I)/종료(E):");
            String readLine = bufferedReader.readLine();
            switch (readLine) {
                case "I":
                    dao.excelInput();
                    break;
                case "E":
                    System.out.println("프로그램종료");
                    System.exit(0);
                    break;
                default:
                    System.out.println("I or E input");
            }
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}

