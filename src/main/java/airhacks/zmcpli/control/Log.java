package airhacks.zmcpli.control;
import static java.lang.System.out;
public interface Log {
    static void info(String message){
        out.println("⚙️ " + message);
    }
}
