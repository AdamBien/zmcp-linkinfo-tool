/**
 * Control layer implementing procedural business logic.
 * 
 * <p>This package contains the business logic components that perform
 * the actual work requested by the boundary layer. Following the BCE pattern,
 * control classes orchestrate operations and implement algorithms while
 * remaining independent of the presentation layer.</p>
 * 
 * <p>Key components:</p>
 * <ul>
 *   <li>{@link LinkInfoFetcher} - Handles HTTP requests and HTML parsing to extract link metadata</li>
 * </ul>
 * 
 * <p>Design decisions:</p>
 * <ul>
 *   <li>Control classes are stateless and can be safely reused</li>
 *   <li>Business logic is separated from tool protocol concerns</li>
 *   <li>HTTP client configuration is encapsulated within the control layer</li>
 *   <li>HTML parsing logic is kept simple using regex patterns for specific metadata extraction</li>
 * </ul>
 * 
 * <p>The control layer acts as the bridge between boundary (tool interface) and
 * entity (data structures), implementing the core functionality without knowledge
 * of the MCP protocol specifics.</p>
 */
package airhacks.zmcpli.control;