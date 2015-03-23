package nuclibooktest.validation_tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

public class TestResponse {

    public final String body;
    public final int status;
    public Map<String, List<String>> headers;

    public TestResponse(int status, String body, Map<String, List<String>> headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    public String getTagValue(){
        Document doc= Jsoup.parse(body);
        Elements element= doc.select("input[name=csrf-token]");
        System.out.print(element.val());
        return element.val();
    }
}
