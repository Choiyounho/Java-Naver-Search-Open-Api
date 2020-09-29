package outputbookdata.domain;

import console.domain.BookVo;
import org.apache.poi.hssf.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class ExcelDao {

    private List<BookVo> list;
    private HSSFWorkbook workbook;

    private ExcelDao() {
        list = new ArrayList<>();
        workbook = new HSSFWorkbook();
    }

    public static ExcelDao init() {
        return new ExcelDao();
    }

    public List<BookVo> getList() {
        return list;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

}