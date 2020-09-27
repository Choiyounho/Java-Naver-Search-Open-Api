package outputexcel.domain;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import outputexcel.view.OutputView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class PoiApi {

    private static final int SHEET_INDEX = 0;

    public static Iterator<Row> readRowIterator(FileInputStream fileInputStream) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheetAt(SHEET_INDEX);
        Iterator<Row> rows = sheet.rowIterator();
        return rows;
    }

    public static void readRow(Iterator<Row> rows) {
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            readRowInCell(cells);
        }
    }

    private static void readRowInCell(Iterator<Cell> cells) {
        while (cells.hasNext()) {
            Cell cell = cells.next();
            CellType type = cell.getCellType();
            OutputView.printResult(cell, type);
        }
    }

}
