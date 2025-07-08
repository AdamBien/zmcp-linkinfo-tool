package airhacks.zmcpli.control;
import static java.lang.System.out;
public interface Log {
    static void info(String message){
        out.println("⚙️ " + message);
    }
    
    static void error(String message){
        out.println("❌ " + message);
    }
    
    static void error(String message, Throwable throwable){
        out.println("❌ " + message);
        if (throwable != null) {
            out.println("   Exception: " + throwable.getClass().getName() + " - " + throwable.getMessage());
            if (throwable.getCause() != null) {
                out.println("   Root cause: " + throwable.getCause().getClass().getName() + " - " + throwable.getCause().getMessage());
            }
        }
    }
}
