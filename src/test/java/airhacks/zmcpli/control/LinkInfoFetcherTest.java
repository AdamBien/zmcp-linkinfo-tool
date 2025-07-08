package airhacks.zmcpli.control;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LinkInfoFetcherTest {

    @Test
    void urlWithSchemeRemainsUnchanged() {
        var httpsUrl = "https://example.com";
        var httpUrl = "http://example.com";
        
        assertThat(LinkInfoFetcher.normalizeUrl(httpsUrl)).isEqualTo(httpsUrl);
        assertThat(LinkInfoFetcher.normalizeUrl(httpUrl)).isEqualTo(httpUrl);
    }
    
    @Test
    void urlWithoutSchemeGetsHttpsPrefix() {
        var result = LinkInfoFetcher.normalizeUrl("example.com");
        
        assertThat(result).isEqualTo("https://example.com");
    }
    
    @Test
    void urlWithoutSchemeAndPath() {
        var result = LinkInfoFetcher.normalizeUrl("example.com/path");
        
        assertThat(result).isEqualTo("https://example.com/path");
    }
    
    @Test
    void hasNoSchemeDetectsCorrectly() {
        assertThat(LinkInfoFetcher.hasNoScheme("example.com")).isTrue();
        assertThat(LinkInfoFetcher.hasNoScheme("example.com/path")).isTrue();
        assertThat(LinkInfoFetcher.hasNoScheme("https://example.com")).isFalse();
        assertThat(LinkInfoFetcher.hasNoScheme("http://example.com")).isFalse();
        assertThat(LinkInfoFetcher.hasNoScheme("ftp://example.com")).isFalse();
    }
    
    @Test
    void extractTitleFromValidHtml() {
        var html = "<html><head><title>Test Title</title></head><body></body></html>";
        var result = LinkInfoFetcher.extractTitle(html);
        
        assertThat(result).isEqualTo("Test Title");
    }
    
    @Test
    void extractTitleFromHtmlWithoutTitle() {
        var html = "<html><head></head><body></body></html>";
        var result = LinkInfoFetcher.extractTitle(html);
        
        assertThat(result).isNull();
    }
    
    @Test
    void extractDescriptionFromValidHtml() {
        var html = """
            <html>
                <head>
                    <meta name="description" content="This is a test description">
                </head>
                <body></body>
            </html>
            """;
        var result = LinkInfoFetcher.extractDescription(html);
        
        assertThat(result).isEqualTo("This is a test description");
    }
    
    @Test
    void extractDescriptionFromHtmlWithoutDescription() {
        var html = "<html><head></head><body></body></html>";
        var result = LinkInfoFetcher.extractDescription(html);
        
        assertThat(result).isNull();
    }
}