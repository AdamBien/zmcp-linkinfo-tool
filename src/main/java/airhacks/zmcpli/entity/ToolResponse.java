package airhacks.zmcpli.entity;

import java.util.Map;

public record ToolResponse(String content,boolean error) {

    public Map<String, String> toMap(){
        return Map.of("content", content, "error", String.valueOf(error));
    }

    public static ToolResponse success(String content){
        return new ToolResponse(content, false);
    }

    public static ToolResponse error(String content){
        return new ToolResponse(content, true);
    }
    
}
