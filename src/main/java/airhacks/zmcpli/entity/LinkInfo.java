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
        var sb = new StringBuilder();
        sb.append("Link Information:\n");
        sb.append("Requested URL: ").append(requestedUrl).append("\n");
        if (!requestedUrl.equals(finalUrl)) {
            sb.append("Final URL: ").append(finalUrl).append("\n");
        }
        sb.append("Status Code: ").append(statusCode).append("\n");
        sb.append("Response Time: ").append(responseTime.toMillis()).append("ms\n");
        if (title != null) {
            sb.append("Title: ").append(title).append("\n");
        }
        if (description != null) {
            sb.append("Description: ").append(description).append("\n");
        }
        return sb.toString();
    }
}