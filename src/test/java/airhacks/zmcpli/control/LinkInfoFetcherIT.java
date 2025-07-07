package airhacks.zmcpli.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class LinkInfoFetcherIT {

    @Test
    void fetchAirhacksLiveReturnsValidInfo() {
        var linkInfo = LinkInfoFetcher.fetch("https://airhacks.live");
        
        assertThat(linkInfo.statusCode()).isEqualTo(200);
        assertThat(linkInfo.requestedUrl()).isEqualTo("https://airhacks.live");
        assertThat(linkInfo.title()).isNotNull().isNotBlank();
    }

    @Test
    void fetchGithubReturnsValidInfo() {
        var linkInfo = LinkInfoFetcher.fetch("https://github.com");
        
        assertThat(linkInfo.statusCode()).isEqualTo(200);
        assertThat(linkInfo.requestedUrl()).isEqualTo("https://github.com");
        assertThat(linkInfo.title()).isNotNull().containsIgnoringCase("github");
    }

    @Test
    void fetchNonExistentDomainThrowsException() {
        assertThatThrownBy(() -> LinkInfoFetcher.fetch("https://this-domain-does-not-exist-12345.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to fetch URL");
    }

    @Test
    void fetchHttpsRedirectHandledCorrectly() {
        var linkInfo = LinkInfoFetcher.fetch("http://github.com");
        
        assertThat(linkInfo.statusCode()).isEqualTo(200);
        assertThat(linkInfo.finalUrl()).startsWith("https://");
    }

    @Test
    void extractTitleWorksWithSimpleHtml() {
        var html = "<html><head><title>Test Page</title></head></html>";
        var title = LinkInfoFetcher.extractTitle(html);
        
        assertThat(title).isEqualTo("Test Page");
    }

    @Test
    void extractDescriptionWorksWithMetaTag() {
        var html = "<html><head><meta name=\"description\" content=\"Test description\"></head></html>";
        var description = LinkInfoFetcher.extractDescription(html);
        
        assertThat(description).isEqualTo("Test description");
    }

    @Test
    void extractTitleReturnsNullForNoTitle() {
        var html = "<html><head></head></html>";
        var title = LinkInfoFetcher.extractTitle(html);
        
        assertThat(title).isNull();
    }

    @Test
    void extractDescriptionReturnsNullForNoMeta() {
        var html = "<html><head></head></html>";
        var description = LinkInfoFetcher.extractDescription(html);
        
        assertThat(description).isNull();
    }
}