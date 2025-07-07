package airhacks.zmcpli.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LinkInfoToolTest {

    @Test
    void nullInputReturnsError() {
        var tool = new LinkInfoTool();
        var result = tool.apply(null);
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("URL parameter is required");
    }

    @Test
    void emptyInputReturnsError() {
        var tool = new LinkInfoTool();
        var result = tool.apply("");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("URL parameter is required");
    }

    @Test
    void blankInputReturnsError() {
        var tool = new LinkInfoTool();
        var result = tool.apply("   ");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("URL parameter is required");
    }

    @Test
    void invalidUrlReturnsError() {
        var tool = new LinkInfoTool();
        var result = tool.apply("not-a-valid-url");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("Failed to fetch link info");
    }

    @Test
    void toolSpecContainsRequiredFields() {
        assertThat(LinkInfoTool.TOOL_SPEC)
                .containsEntry("name", "LinkInfoTool")
                .containsEntry("description", "Fetches information about a URL including title, description, and status")
                .containsKey("inputSchema");
        
        assertThat(LinkInfoTool.TOOL_SPEC.get("inputSchema"))
                .contains("\"required\": [\"input\"]");
    }
}