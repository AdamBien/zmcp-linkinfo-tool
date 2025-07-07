/**
 * Boundary layer components implementing MCP (Model Context Protocol) tools.
 * 
 * <p>This package contains the tool implementations that serve as the entry points
 * for the MCP protocol. Each tool implements {@link java.util.function.Function}
 * and follows the MCP specification for tool responses.</p>
 * 
 * <p>Key components:</p>
 * <ul>
 *   <li>{@link RuntimeInfoTool} - Provides JVM runtime memory metrics</li>
 *   <li>{@link LinkInfoTool} - Fetches and analyzes URL information including title, description, and HTTP status</li>
 *   <li>{@link ToolSpec} - Utility for creating MCP-compliant tool specifications</li>
 * </ul>
 * 
 * <p>Design decisions:</p>
 * <ul>
 *   <li>Tools are stateless functions following the Function interface</li>
 *   <li>Each tool has a static TOOL_SPEC field for MCP discovery</li>
 *   <li>Service provider mechanism is used for tool registration</li>
 * </ul>
 * 
 * @see <a href="https://modelcontextprotocol.io/specification/2025-03-26/server/tools">MCP Tools Specification</a>
 */
package airhacks.zmcpli.boundary;