package airhacks.zmcpli.boundary;

import org.junit.jupiter.api.Test;

import airhacks.zmcpli.boundary.LinkInfoTool;

import static org.assertj.core.api.Assertions.*;

class LinkInfoToolTest {

    @Test
    void testNullInput() {
        var tool = new LinkInfoTool();
        var result = tool.apply(null);
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("URL parameter is required");
    }

    @Test
    void testEmptyInput() {
        var tool = new LinkInfoTool();
        var result = tool.apply("");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("URL parameter is required");
    }

    @Test
    void testBlankInput() {
        var tool = new LinkInfoTool();
        var result = tool.apply("   ");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("URL parameter is required");
    }

    @Test
    void testInvalidUrl() {
        var tool = new LinkInfoTool();
        var result = tool.apply("not-a-valid-url");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("Failed to fetch link info");
    }

    @Test
    void testToolSpec() {
        assertThat(LinkInfoTool.TOOL_SPEC)
                .containsEntry("name", "LinkInfoTool")
                .containsEntry("description", "Fetches information about a URL including title, description, and status")
                .containsKey("inputSchema");
        
        assertThat(LinkInfoTool.TOOL_SPEC.get("inputSchema"))
                .contains("\"required\": [\"input\"]");
    }
}