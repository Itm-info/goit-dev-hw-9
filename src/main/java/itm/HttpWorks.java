package itm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpWorks {
    public static int httpGetResponseCode(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection.getResponseCode();
    }
}
