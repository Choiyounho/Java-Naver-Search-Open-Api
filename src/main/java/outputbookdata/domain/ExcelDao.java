package outputbookdata.domain;

import console.domain.ExcelVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import outputbookdata.view.InputView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.CommonsConstant.*;

public class ExcelDao {

    private static final String SHEETNAME = "BOOK SHEET";
    private static final String IMAGENAME = "이미지이름";
    private static final String SAVE_ISBN_IMAGE_URL = "ISBN,ImageURL 저장성공";
    private static final String ISBN_XLS = "results/isbn.xls";

    private List<ExcelVo> list;
    private HSSFWorkbook workbook;

    public ExcelDao() {
        list = new ArrayList<>();
        workbook = new HSSFWorkbook();
    }

    public void excelInput() {

        try {
            HSSFSheet firstSheet = workbook.createSheet(SHEETNAME);
            settingsColumn(firstSheet);

            int rowNum = 1;

            while (true) {
                String title = InputView.inputBookName();
                String author = InputView.inputBookAuthor();
                String company = InputView.inputBookCompany();

                HSSFRow row = firstSheet.createRow(rowNum);
                HSSFCell cellTitle = row.createCell(0);
                cellTitle.setCellValue(new HSSFRichTextString(title));
                HSSFCell cellAuthor = row.createCell(1);
                cellAuthor.setCellValue(new HSSFRichTextString(author));
                HSSFCell cellCompany = row.createCell(2);
                cellCompany.setCellValue(new HSSFRichTextString(company));
                rowNum++;

                ExcelVo search = ExcelVo.search(title, author, company);
                ExcelVo data = naverSearch(search);
                list.add(data);
                String progressStatus = InputView.inputAddBook();
                if (progressStatus.equals("N")) {
                    break;
                }
            }
            System.out.println("데이터 추출중...........");
            excelSave();
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

    private ExcelVo naverSearch(ExcelVo vo) {
        try {
            String address = NaverSearchApi.getNaverUrl(vo);

            HttpURLConnection httpConnection = NaverSearchApi.requestNaverSearchApi(address);

            StringBuilder response = NaverSearchApi.getNaverSearchResponse(httpConnection);

            Document document = Jsoup.parse(response.toString());
            Element isbn = document.select(ISBN).first();
            System.out.println(ISBN + COLON + BLANK + isbn.text());
            String image = document.toString();
            String imageTag = image.substring(image.indexOf(INDEX_IMG) + 5);
            image = imageTag.substring(0, imageTag.indexOf(QUERY));
            System.out.println(image);
            vo.setIsbn(isbn.text().split(BLANK)[1]);
            String fileName = image.substring(image.lastIndexOf(SLASH) + 1);
            vo.setImageUrl(fileName);

            Runnable downloadBroker = new DownloadBroker(image, fileName);
            Thread thread = new Thread(downloadBroker);
            thread.start();
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        return vo;
    }

    public void excelSave() {
        try {
            HSSFSheet sheet = workbook.getSheetAt(0);
            if (workbook != null && sheet != null) {
                Iterator rows = sheet.rowIterator();
                rows.next();
                int i = 0;
                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    HSSFCell cell = row.createCell(3);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(list.get(i).getIsbn());

                    cell = row.createCell(4);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(list.get(i).getImageUrl());

                    InputStream inputStream = new FileInputStream(list.get(i).getImageUrl());
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
                    inputStream.close();

                    CreationHelper helper = workbook.getCreationHelper();
                    Drawing drawing = sheet.createDrawingPatriarch();
                    ClientAnchor anchor = helper.createClientAnchor();

                    anchor.setCol1(5);
                    anchor.setRow1(i + 1);
                    anchor.setCol2(6);
                    anchor.setRow2(i + 2);

                    Picture picture = drawing.createPicture(anchor, pictureIdx);
                    Cell cellImg = row.createCell(5);
                    sheet.setColumnWidth(5, SHEET_WIDTH);
                    cellImg.getRow().setHeight(CELL_HEIGHT);
                    i++;
                }
                FileOutputStream fileOutputStream = new FileOutputStream(ISBN_XLS);
                workbook.write(fileOutputStream);
                fileOutputStream.close();
                System.out.println(SAVE_ISBN_IMAGE_URL);
            }
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }
}