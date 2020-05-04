package drunkBot.util.Logging;

public class Logger {

    public Logger(){}

    public void log(Level level, String msg){
        System.out.println("[" + level + "] : " + msg);
    }
}
