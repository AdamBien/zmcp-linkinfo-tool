package airhacks.zmcpli.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ToolSpecTest {

    @Test
    void singleRequiredParameterCreatesValidMCPToolSpec() {
        var toolName = "TestTool";
        var parameterName = "url";
        var description = "Test tool description";
        
        var spec = ToolSpec.singleRequiredParameter(toolName, parameterName, description);
        
        assertThat(spec)
                .containsEntry("name", toolName)
                .containsEntry("description", description)
                .containsKey("inputSchema");
        
        var inputSchema = spec.get("inputSchema");
        assertThat(inputSchema)
                .contains("\"type\": \"object\"")
                .contains("\"properties\"")
                .contains("\"" + parameterName + "\"")
                .contains("\"type\": \"string\"")
                .contains("\"required\": [\"" + parameterName + "\"]");
    }


}