# zmcp-linkinfo-tool

MCP tool for analyzing web links. Fetches HTTP status, title, and meta description from URLs.

## Features

- HTTP status code checking
- HTML title extraction  
- Meta description parsing
- Redirect handling
- Error reporting for invalid URLs

## Usage

The tool exposes a `LinkInfoTool` function that accepts a URL string and returns:
- Link status (success/error)
- HTTP status code
- Page title (if available)
- Meta description (if available)
- Final URL (after redirects)

## Testing

```bash
# Unit tests
mvn test

# Integration tests  
mvn failsafe:integration-test
```

## Architecture

Follows BCE pattern with web link analysis domain logic:
- `boundary` - MCP tool implementation
- `control` - HTTP fetching and HTML parsing
- `entity` - Link metadata representations
