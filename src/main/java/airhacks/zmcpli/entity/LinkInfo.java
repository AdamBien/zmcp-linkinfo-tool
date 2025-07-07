package airhacks.zmcpli.entity;

public record LinkInfo(
        String requestedUrl,
        String finalUrl,
        int statusCode,
        String title,
        String description) {

    public static LinkInfo withMetadata(String requestedUrl, String finalUrl, int statusCode, String title, String description) {
        return new LinkInfo(requestedUrl, finalUrl, statusCode, title, description);
    }

    public static LinkInfo withoutMetadata(String requestedUrl, String finalUrl, int statusCode) {
        return new LinkInfo(requestedUrl, finalUrl, statusCode, null, null);
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
        if (title != null) {
            sb.append("Title: ").append(title).append("\n");
        }
        if (description != null) {
            sb.append("Description: ").append(description).append("\n");
        }
        return sb.toString();
    }
}