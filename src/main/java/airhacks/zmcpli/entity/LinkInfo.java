package airhacks.zmcpli.entity;

import java.time.Duration;

public record LinkInfo(
        String requestedUrl,
        String finalUrl,
        int statusCode,
        String title,
        String description,
        Duration responseTime) {

    public static LinkInfo withMetadata(String requestedUrl, String finalUrl, int statusCode, String title, String description, Duration responseTime) {
        return new LinkInfo(requestedUrl, finalUrl, statusCode, title, description, responseTime);
    }

    public static LinkInfo withoutMetadata(String requestedUrl, String finalUrl, int statusCode, Duration responseTime) {
        return new LinkInfo(requestedUrl, finalUrl, statusCode, null, null, responseTime);
    }

    @Override
    public String toString() {
        var finalUrlSection = !requestedUrl.equals(finalUrl) ? 
            "Final URL: %s\n".formatted(finalUrl) : "";
        
        var titleSection = title != null ? 
            "Title: %s\n".formatted(title) : "";
            
        var descriptionSection = description != null ? 
            "Description: %s\n".formatted(description) : "";
        
        return """
                Link Information:
                Requested URL: %s
                %sStatus Code: %d
                Response Time: %dms
                %s%s""".formatted(
                    requestedUrl, 
                    finalUrlSection, 
                    statusCode, 
                    responseTime.toMillis(),
                    titleSection,
                    descriptionSection);
    }
}