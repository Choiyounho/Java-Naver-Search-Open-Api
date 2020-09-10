package console;

import console.domain.ExcelVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReading {

    private static final String EXCEL_FILENAMEW = "bookList.xls";
    private static final int SHEET_INDEX = 0;

    public static void main(String[] args) {

        List<ExcelVo> data = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(EXCEL_FILENAMEW);

            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(SHEET_INDEX);
            Iterator<Row> rows = sheet.rowIterator();
            rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                String[] cellArr = new String[5];
                int i = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    cellArr[i] = cell.toString();
                    i++;
                }
                ExcelVo vo = new ExcelVo(cellArr[0], cellArr[1], cellArr[2], cellArr[3], cellArr[4]);
                data.add(vo);
            }
            showExcelData(data);
        } catch (IOException e) {
            System.out.println("IOException e" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e" + e.getMessage());
        }

    }

    public static void showExcelData(List<ExcelVo> data) {
        for (ExcelVo vo : data) {
            System.out.println(vo);
        }
    }
}
