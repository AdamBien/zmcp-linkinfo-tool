package airhacks.zmcpli.boundary;

import java.util.Map;
import java.util.function.Function;

import airhacks.zmcpli.control.LinkInfoFetcher;
import airhacks.zmcpli.control.Log;
import airhacks.zmcpli.entity.ToolResponse;

public class LinkInfoTool implements Function<Map<String,Object>, Map<String, String>> {

    public static final Object VERSION = "LinkInfoTool v2025.07.08.02";
    static String URL_PARAMETER = "url";
    static Map<String, String> TOOL_SPEC = ToolSpec.singleRequiredParameter("LinkInfoTool", URL_PARAMETER,
            "Fetches information about a URL including title, description, and status");

    @Override
    public Map<String, String> apply(Map<String,Object> input) {
        Log.info("input " + input);
        var url = (String) input.get("url");
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