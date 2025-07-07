package airhacks.zmcpli.boundary;

import java.util.Map;
import java.util.function.Function;

import airhacks.zmcpli.entity.ToolResponse;
/**
 * An example zmcp tool that returns the server's runtime metrics.
 * It is a starting point for creating your own implementations.
 * To implement your own zmcp tool, you have to:
 * <ul>
 * <li>implement the <code>Function<String, Map<String, String>></code> interface</li>
 * <li>return a <code>Map<String, String></code> with "content" and "error" keys, the ToolResponse class provides a utility method to create a tool response.</li>
 * <li>have a <code>TOOL_SPEC</code> attribute that describes the tool's input parameters and its behavior.
 *     The <code>ToolSpec</code> class provides a utility method to create a tool spec.</li>
 * <li>put the fully qualified class name in the <code>META-INF/services/java.util.function.Function</code> file</li>
 * </ul>
 */
public class RuntimeInfoTool implements Function<String, Map<String, String>> {

    /**
     * A TOOL_SPEC attribute has to exist in every tool implementation.
     * It is used to describe the tool's input parameters and its behavior and is fetched by zmcp via reflection.
     */
    static Map<String, String> TOOL_SPEC = ToolSpec.singleRequiredParameter("RuntimeInfoTool", "returns server's runtime metrics");

    /**
     * The apply method is the entry point for the tool.
     * It is called by zmcp when the tool is invoked.
     * The parameter is the input parameter and is usually in JSON format.
     * The return value is a map of key-value pairs with "content" and "error" keys.
     * The "content" key is the tool's response and the "error" key is a boolean value indicating if the tool failed.
     */
    @Override
    public Map<String, String> apply(String parameter) {
        var memoryStatus = """
                Memory Status:
                Free Memory: %s
                Total Memory: %s
                Max Memory: %s
                """.formatted(freeMemory(), totalMemory(), maxMemory());
        return ToolResponse.success(memoryStatus).toMap();
    }

    /**
     * Returns the free memory available to the JVM in MB
     * @return
     */
    String freeMemory(){
        return toMB(Runtime.getRuntime().freeMemory());
    }

    /**
     * Returns the total memory available to the JVM in MB
     * @return
     */
    String totalMemory(){
        return toMB(Runtime.getRuntime().totalMemory());
    }

    /**
     * Returns the maximum memory available to the JVM in MB
     * @return
     */
    String maxMemory(){
        return toMB(Runtime.getRuntime().maxMemory());
    }

    String toMB(long bytes){
        return String.valueOf(bytes / 1024 / 1024 + " MB");
    }
}
