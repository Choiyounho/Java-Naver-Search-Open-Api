package outputexcel.controller;

import org.apache.poi.ss.usermodel.Row;
import outputexcel.domain.PoiApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static utils.CommonsConstant.ROOT_DIRECTORY;

public class OutputExcelController {

    private static final String EXCEL_FILENAME = "cellDataType.xls";

    public void parseExcelData() {
        try (FileInputStream fileInputStream = new FileInputStream(ROOT_DIRECTORY + EXCEL_FILENAME)) {
            Iterator<Row> rows = PoiApi.readRowIterator(fileInputStream);
            PoiApi.readRow(rows);
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}

