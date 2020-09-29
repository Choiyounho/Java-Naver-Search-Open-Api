package console.view;

import console.domain.BookVo;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Iterator;
import java.util.List;

public class ExcelReadingView {

    public static void readCell(Iterator<Cell> cells, String[] cellArr, int i) {
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cellArr[i] = cell.toString();
            i++;
        }
    }

    public static void showExcelData(List<BookVo> data) {
        data.forEach(System.out::println);
    }
}
