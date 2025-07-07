package airhacks.zmcpli.control;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Pattern;

import airhacks.zmcpli.entity.LinkInfo;

public class LinkInfoFetcher {

    private static final Pattern TITLE_PATTERN = Pattern.compile("<title[^>]*>([^<]+)</title>", 
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern META_DESCRIPTION_PATTERN = Pattern.compile(
            "<meta\\s+name=[\"']description[\"']\\s+content=[\"']([^\"']+)[\"']", 
            Pattern.CASE_INSENSITIVE);
    
    private final HttpClient httpClient;

    public LinkInfoFetcher() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public LinkInfo fetch(String urlString) throws Exception {
        var uri = URI.create(urlString);
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var statusCode = response.statusCode();
        var finalUrl = response.uri().toString();
        
        if (statusCode >= 200 && statusCode < 300) {
            var body = response.body();
            var title = extractTitle(body);
            var description = extractDescription(body);
            return new LinkInfo(urlString, finalUrl, statusCode, title, description);
        } else {
            return new LinkInfo(urlString, finalUrl, statusCode, null, null);
        }
    }

    private String extractTitle(String html) {
        var matcher = TITLE_PATTERN.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    private String extractDescription(String html) {
        var matcher = META_DESCRIPTION_PATTERN.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}