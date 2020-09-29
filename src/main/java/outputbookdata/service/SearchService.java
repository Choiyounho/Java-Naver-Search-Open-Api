package outputbookdata.service;

import console.domain.BookVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import outputbookdata.domain.DownloadBroker;
import utils.NaverSearchApiUtils;

import java.net.HttpURLConnection;

import static utils.CommonsConstant.*;

public class SearchService {

    public static BookVo bookSearchApi(BookVo bookVo) {
        try {
            String address = NaverSearchApiUtils.getNaverUrl(bookVo);

            HttpURLConnection httpConnection = NaverSearchApiUtils.requestNaverSearchApi(address);

            StringBuilder response = NaverSearchApiUtils.getNaverSearchResponse(httpConnection);

            Document document = Jsoup.parse(response.toString());
            Element isbn = document.select(ISBN).first();
            System.out.println(ISBN + COLON + BLANK + isbn.text());

            String image = NaverSearchApiUtils.getImageUrl(document);
            System.out.println(image);

            bookVo.setIsbn(isbn.text().split(BLANK)[1]);
            String fileName = image.substring(image.lastIndexOf(SLASH) + 1);
            bookVo.setImageUrl(fileName);

            Runnable downloadBroker = new DownloadBroker(image, fileName);
            Thread thread = new Thread(downloadBroker);
            thread.start();
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        return bookVo;
    }

}
