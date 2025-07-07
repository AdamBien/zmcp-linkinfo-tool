package airhacks.zmcpli.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LinkInfoToolIT {

    @Test
    void airhacksLiveReturnsValidLinkInfo() {
        var tool = new LinkInfoTool();
        var result = tool.apply("https://airhacks.live");
        
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
        var result = tool.apply("https://airhacks.live");
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content)
                .contains("Title:")
                .containsIgnoringCase("airhacks");
    }

    @Test
    void airhacksLiveHandlesRedirects() {
        var tool = new LinkInfoTool();
        var result = tool.apply("https://airhacks.live");
        
        assertThat(result).containsEntry("error", "false");
        
        var content = result.get("content");
        assertThat(content).contains("Status Code: 200");
    }

    @Test
    void nonExistentDomainReturnsError() {
        var tool = new LinkInfoTool();
        var result = tool.apply("https://this-domain-does-not-exist-12345.com");
        
        assertThat(result).containsEntry("error", "true");
        assertThat(result.get("content")).contains("Failed to fetch link info");
    }
}