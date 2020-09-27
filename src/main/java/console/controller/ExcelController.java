package console.controller;

import console.domain.ExcelVo;
import console.view.ExcelReadingView;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.CommonsConstant.ROOT_DIRECTORY;

public class ExcelController {

    private static final String EXCEL_FILENAME = ROOT_DIRECTORY + "bookList.xls";
    private static final int SHEET_INDEX = 0;
    public static final int EXCEL_COLUMN_COUNT = 5;

    private List<ExcelVo> data;

    public void run() {
        try {
            data = new ArrayList<>();
            FileInputStream fileInputStream = new FileInputStream(EXCEL_FILENAME);

            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(SHEET_INDEX);
            Iterator<Row> rows = sheet.rowIterator();
            rows.next();

            while (rows.hasNext()) {
                Row row = rows.next(); // 열부터 읽어오기
                Iterator<Cell> cells = row.cellIterator();
                String[] cellArr = new String[EXCEL_COLUMN_COUNT];
                int i = 0;
                ExcelReadingView.readCell(cells, cellArr, i);

                ExcelVo print = ExcelVo.print(cellArr);
                data.add(print);
            }
            ExcelReadingView.showExcelData(data);
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e" + e.getMessage());
        }
    }
}
