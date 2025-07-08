package airhacks.zmcpli.control;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Pattern;

import airhacks.zmcpli.entity.LinkInfo;

public interface LinkInfoFetcher {

    Pattern TITLE_PATTERN = Pattern.compile("<title[^>]*>([^<]+)</title>", 
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    Pattern META_DESCRIPTION_PATTERN = Pattern.compile(
            "<meta\\s+name=[\"']description[\"']\\s+content=[\"']([^\"']+)[\"']", 
            Pattern.CASE_INSENSITIVE);

    static LinkInfo fetch(String urlString) {
        var normalizedUrl = normalizeUrl(urlString);
        
        try {
            return attemptFetch(normalizedUrl);
        } catch (RuntimeException e) {
            if (hasNoScheme(urlString) && normalizedUrl.startsWith("https://")) {
                Log.info("HTTPS failed, trying HTTP for: %s".formatted(urlString));
                var httpUrl = "http://" + urlString;
                return attemptFetch(httpUrl);
            }
            throw e;
        }
    }
    
    static String normalizeUrl(String urlString) {
        if (hasNoScheme(urlString)) {
            return "https://" + urlString;
        }
        return urlString;
    }
    
    static boolean hasNoScheme(String url) {
        return !url.contains("://");
    }
    
    static LinkInfo attemptFetch(String urlString) {
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

            Log.info("HTTP request initiated - Target: %s%s".formatted(uri.getHost(), uri.getPath()));
            var startTime = Instant.now();
            
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var statusCode = response.statusCode();
            var finalUrl = response.uri().toString();
            var responseTime = Duration.between(startTime, Instant.now());
            
            if (!urlString.equals(finalUrl)) {
                Log.info("URL redirect detected - From: %s To: %s".formatted(urlString, finalUrl));
            }
            
            Log.info("HTTP response received - Status: %d, Time: %dms".formatted(statusCode, responseTime.toMillis()));
            
            if (statusCode >= 200 && statusCode < 300) {
                var body = response.body();
                var title = extractTitle(body);
                var description = extractDescription(body);
                
                if (title != null || description != null) {
                    Log.info("Metadata extracted - Title found: %s, Description found: %s".formatted(title != null, description != null));
                }
                
                return LinkInfo.withMetadata(urlString, finalUrl, statusCode, title, description, responseTime);
            } else {
                Log.error("Non-success HTTP status %d - Skipping metadata extraction".formatted(statusCode));
                return LinkInfo.withoutMetadata(urlString, finalUrl, statusCode, responseTime);
            }
        } catch (IOException | InterruptedException e) {
            Log.error("HTTP request failed for URL: %s".formatted(urlString), e);
            throw new RuntimeException("Failed to fetch URL: %s".formatted(urlString), e);
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