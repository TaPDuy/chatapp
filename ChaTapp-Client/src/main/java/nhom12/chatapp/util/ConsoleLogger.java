package nhom12.chatapp.util;

public class ConsoleLogger {
    
    public static final int INFO = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;
    
    public static void log(String msg, int level) {
	log(msg, null, level);
    }
    
    public static void log(String msg, String source, int level) {
	
	String line = "[";
	
	switch(level) {
	    case WARNING:
		line += "WARNING";
		break;
	    case ERROR:
		line += "ERROR";
		break;
	    case INFO:
	    default:
		line += "INFO";
		break;
	}
	
	if (source != null) {
	    line += " (" + source + ")";
	}
	
	line += "]: " + msg;
	
	System.out.println(line);
    }
}
