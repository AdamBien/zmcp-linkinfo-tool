package airhacks.zmcpli.control;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Pattern;

import airhacks.zmcpli.entity.LinkInfo;

public interface LinkInfoFetcher {

    Pattern TITLE_PATTERN = Pattern.compile("<title[^>]*>([^<]+)</title>", 
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    Pattern META_DESCRIPTION_PATTERN = Pattern.compile(
            "<meta\\s+name=[\"']description[\"']\\s+content=[\"']([^\"']+)[\"']", 
            Pattern.CASE_INSENSITIVE);

    static LinkInfo fetch(String urlString) {
        try {
            var httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            
            var uri = URI.create(urlString);
            var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            Log.info("HTTP request initiated - Target: " + uri.getHost() + uri.getPath());
            var startTime = System.currentTimeMillis();
            
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var statusCode = response.statusCode();
            var finalUrl = response.uri().toString();
            var responseTime = System.currentTimeMillis() - startTime;
            
            if (!urlString.equals(finalUrl)) {
                Log.info("URL redirect detected - From: " + urlString + " To: " + finalUrl);
            }
            
            Log.info("HTTP response received - Status: " + statusCode + ", Time: " + responseTime + "ms");
            
            if (statusCode >= 200 && statusCode < 300) {
                var body = response.body();
                var title = extractTitle(body);
                var description = extractDescription(body);
                
                if (title != null || description != null) {
                    Log.info("Metadata extracted - Title found: " + (title != null) + 
                            ", Description found: " + (description != null));
                }
                
                return LinkInfo.withMetadata(urlString, finalUrl, statusCode, title, description);
            } else {
                Log.info("Non-success status code - Skipping metadata extraction");
                return LinkInfo.withoutMetadata(urlString, finalUrl, statusCode);
            }
        } catch (IOException | InterruptedException e) {
            Log.info("HTTP request failed - Exception: " + e.getClass().getSimpleName() + 
                    ", Root cause: " + (e.getCause() != null ? e.getCause().getClass().getSimpleName() : "none"));
            throw new RuntimeException("Failed to fetch URL: " + urlString, e);
        }
    }

    static String extractTitle(String html) {
        var matcher = TITLE_PATTERN.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    static String extractDescription(String html) {
        var matcher = META_DESCRIPTION_PATTERN.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}