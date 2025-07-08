package airhacks.zmcpli.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

class LinkInfoToolIT {

    @Test
    void airhacksLiveReturnsValidLinkInfo() {
        var tool = new LinkInfoTool();
        var result = tool.apply(Map.of("url", "https://airhacks.live"));
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content)
                .contains("Link Information:")
                .contains("Requested URL: https://airhacks.live")
                .contains("Status Code: 200");
    }

    @Test
    void airhacksLiveExtractsTitle() {
        var tool = new LinkInfoTool();
        var result = tool.apply(Map.of("url", "https://airhacks.live"));
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content)
                .contains("Title:")
                .containsIgnoringCase("airhacks");
    }

    @Test
    void airhacksLiveHandlesRedirects() {
        var tool = new LinkInfoTool();
        var result = tool.apply(Map.of("url", "https://airhacks.live"));
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content).contains("Status Code: 200");
    }

    @Test
    void nonExistentDomainReturnsError() {
        var tool = new LinkInfoTool();
        var result = tool.apply(Map.of("url", "https://this-domain-does-not-exist-12345.com"));
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("Failed to fetch link info");
    }

    @Test
    void githubReturnsValidLinkInfo() {
        var tool = new LinkInfoTool();
        var result = tool.apply(Map.of("url", "https://github.com"));
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content)
                .contains("Link Information:")
                .contains("Requested URL: https://github.com")
                .contains("Status Code: 200")
                .contains("Title:");
    }

    @Test
    void wikipediaExtractsTitle() {
        var tool = new LinkInfoTool();
        var result = tool.apply(Map.of("url", "https://en.wikipedia.org/wiki/Java_(programming_language)"));
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content)
                .contains("Title:")
                .containsIgnoringCase("java")
                .contains("Status Code: 200");
    }
}