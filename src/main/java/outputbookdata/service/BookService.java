package outputbookdata.service;

import console.domain.BookVo;
import inputexcel.domain.PoiApi;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import outputbookdata.domain.ExcelDao;
import outputbookdata.view.InputView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static utils.CommonsConstant.*;

public class BookService {

    private static final String SAVE_ISBN_IMAGE_URL = "ISBN,ImageURL 저장성공";
    private static final String FILENAME = "results/isbn.xls";
    private static final String SHEETNAME = "BOOK SHEET";
    private static final String IMAGENAME = "이미지이름";

    public void bookSearch() {
        try {
            ExcelDao excelDao = ExcelDao.init();
            HSSFWorkbook workbook = excelDao.getWorkbook();
            List<BookVo> excelList = excelDao.getList();
            HSSFSheet firstSheet = workbook.createSheet(SHEETNAME);
            settingsColumn(firstSheet);

            int rowNum = 1;

            while (true) {
                String title = InputView.inputBookName();
                String author = InputView.inputBookAuthor();
                String company = InputView.inputBookCompany();

                BookVo bookInfo = BookVo.create(title, author, company);

                setRowBookData(firstSheet, rowNum, bookInfo);
                rowNum++;

                excelList.add(SearchService.bookSearchApi(bookInfo));

                String progressStatus = InputView.progress();
                if (progressStatus.equalsIgnoreCase("N")) {
                    break;
                }
            }

            System.out.println("데이터 추출중...........");
            saveExcelFile(workbook, excelList);
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

    private void settingsColumn(HSSFSheet firstSheet) {
        HSSFRow rowA = firstSheet.createRow(0);
        HSSFCell cellA = rowA.createCell(0);
        cellA.setCellValue(new HSSFRichTextString(BOOKNAME));
        HSSFCell cellB = rowA.createCell(1);
        cellB.setCellValue(new HSSFRichTextString(AUTHOR));
        HSSFCell cellC = rowA.createCell(2);
        cellC.setCellValue(new HSSFRichTextString(COMPANY));
        HSSFCell cellD = rowA.createCell(3);
        cellD.setCellValue(new HSSFRichTextString(ISBN));
        HSSFCell cellE = rowA.createCell(4);
        cellE.setCellValue(new HSSFRichTextString(IMAGENAME));
        HSSFCell cellF = rowA.createCell(5);
        cellF.setCellValue(new HSSFRichTextString(IMAGE));
    }

    private void setRowBookData(HSSFSheet firstSheet, int rowNum, BookVo bookInfo) {
        HSSFRow row = firstSheet.createRow(rowNum);
        HSSFCell cellTitle = row.createCell(0);
        cellTitle.setCellValue(new HSSFRichTextString(bookInfo.getTitle()));
        HSSFCell cellAuthor = row.createCell(1);
        cellAuthor.setCellValue(new HSSFRichTextString(bookInfo.getAuthor()));
        HSSFCell cellCompany = row.createCell(2);
        cellCompany.setCellValue(new HSSFRichTextString(bookInfo.getCompany()));
    }

    private void saveExcelFile(HSSFWorkbook workbook, List<BookVo> excelList) {
        try {
            HSSFSheet sheet = workbook.getSheetAt(0);
            saveExcelRows(workbook, excelList, sheet);
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

    private void saveExcelRows(HSSFWorkbook workbook, List<BookVo> excelList, HSSFSheet sheet) throws IOException {
        if (sheet != null) {
            Iterator<Row> rows = sheet.rowIterator();
            rows.next();
            int i = 0;

            saveRowData(workbook, excelList, sheet, rows, i);

            PoiApi.excelOutputImage(workbook, FILENAME);
            System.out.println(SAVE_ISBN_IMAGE_URL);
        }
    }

    private void saveRowData(HSSFWorkbook workbook, List<BookVo> excelList, HSSFSheet sheet, Iterator<Row> rows, int i) throws IOException {
        while (rows.hasNext()) {
            HSSFRow row = (HSSFRow) rows.next();
            setCellValue(excelList, i, row);

            int pictureIdx = PoiApi.createImageFile(workbook);

            CreationHelper helper = workbook.getCreationHelper();
            ClientAnchor anchor = createAnchor(i, helper);

            PoiApi.createPicture(sheet, pictureIdx, anchor);

            Cell cellImg = row.createCell(5);
            sheet.setColumnWidth(5, SHEET_WIDTH);
            cellImg.getRow().setHeight(CELL_HEIGHT);
            i++;
        }
    }

    private void setCellValue(List<BookVo> excelList, int i, HSSFRow row) {
        HSSFCell cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(excelList.get(i).getIsbn());

        cell = row.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(excelList.get(i).getImageUrl());
    }

    private ClientAnchor createAnchor(int i, CreationHelper helper) {
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(5);
        anchor.setRow1(i + 1);
        anchor.setCol2(6);
        anchor.setRow2(i + 2);
        return anchor;
    }

}
