/**
 * MCP (Model Context Protocol) tool implementations for zmcp.
 * 
 * <p>This package provides tool implementations following the MCP specification,
 * allowing AI models to interact with external systems through well-defined
 * interfaces. The architecture follows the Boundary-Control-Entity (BCE) pattern
 * for clear separation of concerns.</p>
 * 
 * <p>Architecture:</p>
 * <ul>
 *   <li><strong>Boundary</strong> - Tool implementations and protocol interfaces</li>
 *   <li><strong>Entity</strong> - Domain objects and data structures</li>
 *   <li><strong>Control</strong> - (Currently not needed for these simple tools)</li>
 * </ul>
 * 
 * <p>Available tools:</p>
 * <ul>
 *   <li>RuntimeInfoTool - JVM runtime metrics</li>
 *   <li>LinkInfoTool - URL information fetcher</li>
 * </ul>
 * 
 * @see <a href="https://modelcontextprotocol.io">Model Context Protocol</a>
 */
package airhacks.zmcpli;