package outputbookdata.domain;

import console.domain.ExcelVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            HSSFSheet firstSheet = workbook.createSheet(SHEETNAME);
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

            int rowNum = 1;
            while (true) {
                System.out.print(BOOKNAME + COLON);
                String title = bufferedReader.readLine();
                System.out.print(AUTHOR + COLON);
                String author = bufferedReader.readLine();
                System.out.print(COMPANY + COLON);
                String company = bufferedReader.readLine();

                HSSFRow row = firstSheet.createRow(rowNum);
                HSSFCell cellTitle = row.createCell(0);
                cellTitle.setCellValue(new HSSFRichTextString(title));
                HSSFCell cellAuthor = row.createCell(1);
                cellAuthor.setCellValue(new HSSFRichTextString(author));
                HSSFCell cellCompany = row.createCell(2);
                cellCompany.setCellValue(new HSSFRichTextString(company));
                rowNum++;

                ExcelVo vo = new ExcelVo(title, author, company);
                ExcelVo data = naverSearch(vo);
                list.add(data);
                System.out.println("★대문자로 입력해주세요★");
                System.out.println("계속입력 하시려면 Y / 입력종료 N" + COLON);
                String key = bufferedReader.readLine();
                if (key.equals("N")) break;
            }
            System.out.println("데이터 추출중...........");
            excelSave();
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
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


//    public static HttpURLConnection requestNaverSearchApi(String openApi) throws IOException {
//        HttpURLConnection httpConnection;
//        URL url = new URL(openApi);
//        httpConnection = (HttpURLConnection) url.openConnection();
//        httpConnection.setRequestMethod(HTTP_METHOD_GET);
//        httpConnection.setRequestProperty(NAVER_SEARCH_API_KEY_ID, NAVER_SEARCH_CLIENT_ID);
//        httpConnection.setRequestProperty(NAVER_SEARCH_API_KEY_SECRET, NAVER_SEARCH_CLIENT_SECRET);
//        return httpConnection;
//    }
//
//    private static BufferedReader getResponseData(HttpURLConnection httpConnection, int responseCode) throws IOException {
//        if (responseCode == HTTP_STATE_CODE_200_OK) {
//            return new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), UTF_8));
//        }
//        return new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
//    }
//
//    public static StringBuilder getNaverSearchResponse(HttpURLConnection httpConnection) throws IOException {
//        StringBuilder response;
//        int responseCode = httpConnection.getResponseCode();
//        BufferedReader bufferedReader;
//        bufferedReader = getResponseData(httpConnection, responseCode);
//        response = findErrorResponse(bufferedReader);
//        bufferedReader.close();
//        return response;
//    }
//
//    private static StringBuilder findErrorResponse(BufferedReader bufferedReader) throws IOException {
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = bufferedReader.readLine()) != null) {
//            response.append(inputLine);
//        }
//        return response;
//    }

}
