package airhacks.zmcpli.boundary;

import java.util.Map;
import java.util.function.Function;

import airhacks.zmcpli.control.LinkInfoFetcher;
import airhacks.zmcpli.control.Log;
import airhacks.zmcpli.entity.ToolResponse;

public class LinkInfoTool implements Function<String, Map<String, String>> {

    public static final Object VERSION = "LinkInfoTool v2025.07.08.01";
    static Map<String, String> TOOL_SPEC = ToolSpec.singleRequiredParameter("LinkInfoTool", 
            "Fetches information about a URL including title, description, and status");

    @Override
    public Map<String, String> apply(String url) {
        try {
            if (url == null || url.isBlank()) {
                Log.error("LinkInfo request rejected - empty URL parameter");
                return ToolResponse.error("URL parameter is required").toMap();
            }

            var trimmedUrl = url.trim();
            Log.info("Processing LinkInfo request for: " + trimmedUrl);
            
            var linkInfo = LinkInfoFetcher.fetch(trimmedUrl);
            Log.info("LinkInfo retrieved successfully - Status: " + linkInfo.statusCode() + 
                    ", Title length: " + (linkInfo.title() != null ? linkInfo.title().length() : 0) + " chars");
            
            return ToolResponse.success(linkInfo.toString()).toMap();
        } catch (RuntimeException e) {
            Log.error("LinkInfo request failed - URL: " + url, e);
            return ToolResponse.error("Failed to fetch link info: " + e.getMessage()).toMap();
        }
    }
}