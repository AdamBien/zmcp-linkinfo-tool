package airhacks.zmcpli.boundary;

import java.util.Map;
import java.util.function.Function;

import airhacks.zmcpli.control.LinkInfoFetcher;
import airhacks.zmcpli.entity.ToolResponse;

public class LinkInfoTool implements Function<String, Map<String, String>> {

    static Map<String, String> TOOL_SPEC = ToolSpec.singleRequiredParameter("LinkInfoTool", 
            "Fetches information about a URL including title, description, and status");

    private final LinkInfoFetcher fetcher;

    public LinkInfoTool() {
        this.fetcher = new LinkInfoFetcher();
    }

    @Override
    public Map<String, String> apply(String url) {
        try {
            if (url == null || url.isBlank()) {
                return ToolResponse.error("URL parameter is required").toMap();
            }

            var linkInfo = fetcher.fetch(url.trim());
            return ToolResponse.success(linkInfo.toString()).toMap();
        } catch (Exception e) {
            return ToolResponse.error("Failed to fetch link info: " + e.getMessage()).toMap();
        }
    }
}