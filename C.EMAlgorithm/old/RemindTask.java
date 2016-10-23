import java.util.Timer;
import java.util.TimerTask;

public class RemindTask extends TimerTask {
    public void run() {
      System.out.println(" Hello World!");
    }
    
    public static void main(String[] args){
       Timer timer = new Timer();
       timer.schedule(new RemindTask(), 3000,3000);
    }
  }