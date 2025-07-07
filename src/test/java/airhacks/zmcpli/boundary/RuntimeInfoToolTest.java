package airhacks.zmcpli.boundary;

import org.junit.jupiter.api.Test;

import airhacks.zmcpli.boundary.RuntimeInfoTool;

import static org.assertj.core.api.Assertions.assertThat;

public class RuntimeInfoToolTest {
    
    @Test
    void responseContainsKeys() {
        var tool = new RuntimeInfoTool();
        var response = tool.apply(null);
        assertThat(response).isNotNull().as("response should not be null");
        assertThat(response.get("content")).isNotNull().as("content should not be null");
        assertThat(response.get("error")).isNotNull().as("error should not be null");
    }


    @Test
    void toolResponseToolsSpec(){
        assertThat(RuntimeInfoTool.TOOL_SPEC).isNotNull().as("TOOL_SPEC should not be null");
        assertThat(RuntimeInfoTool.TOOL_SPEC.get("name")).isNotNull().as("name should not be null");
        assertThat(RuntimeInfoTool.TOOL_SPEC.get("description")).isNotNull().as("description should not be null");
    }
}
