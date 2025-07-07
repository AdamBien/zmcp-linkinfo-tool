/**
 * Entity layer components representing domain objects and data structures.
 * 
 * <p>This package contains the domain entities used by the MCP tools to
 * structure and represent data. These are simple data carriers with no
 * business logic, following the Entity pattern in BCE architecture.</p>
 * 
 * <p>Key components:</p>
 * <ul>
 *   <li>{@link ToolResponse} - Standardized response format for MCP tools with content and error status</li>
 *   <li>{@link LinkInfo} - Data structure representing analyzed URL information</li>
 * </ul>
 * 
 * <p>Design decisions:</p>
 * <ul>
 *   <li>All entities are immutable records for thread safety and clarity</li>
 *   <li>Entities provide utility methods for conversion to MCP-required formats</li>
 *   <li>Clear separation between data representation and business logic</li>
 * </ul>
 */
package airhacks.zmcpli.entity;