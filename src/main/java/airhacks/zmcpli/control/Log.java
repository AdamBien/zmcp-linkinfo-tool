package airhacks.zmcpli.control;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import airhacks.zmcpli.boundary.LinkInfoTool;
public interface Log {
    
    static final Path LOG_FILE = Path.of("zmcp-linkinfo-tool.log");

    static void init(){
        try {
            Files.writeString(LOG_FILE,  "🚀⚙️ %s started %n".formatted(LinkInfoTool.VERSION), StandardOpenOption.CREATE);
        } catch (IOException e) {
        }
    }
    
    static void info(String message){
        append("⚙️ %s".formatted(message));
    }
    
    static void error(String message){
        append("❌ %s".formatted(message));
    }
    
    static void error(String message, Throwable throwable){
        append("❌ %s".formatted(message));
        if (throwable != null) {
            append("   Exception: %s - %s".formatted(throwable.getClass().getName(), throwable.getMessage()));
            if (throwable.getCause() != null) {
                append("   Root cause: %s - %s".formatted(throwable.getCause().getClass().getName(), throwable.getCause().getMessage()));
            }
        }
    }

    private static void append(String message){
        try {
            Files.writeString(LOG_FILE, "%s\n".formatted(message), StandardOpenOption.APPEND);
        } catch (IOException e) {
        }
    }
    
}
