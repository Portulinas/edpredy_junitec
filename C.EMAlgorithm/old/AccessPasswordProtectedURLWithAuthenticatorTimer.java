import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AccessPasswordProtectedURLWithAuthenticatorTimer  extends TimerTask {
	
	public HygroSample higro = new HygroSample();	
	
    public void run() {
    	System.out.println(" Hello World!");
        Date date = new Date();
        
        if (higro.first==null){
        	System.out.println("First Day");
            // Create data sample if inexisting
    		double[] timeS=new double[288];
    		double[] tempS=new double[288];
    		double[] humiS=new double[288];
    		String[] stateS=new String[288];    		
    		higro.add(date,timeS,tempS,humiS,stateS);
        }
        
        //Access website and feed vector
        try {
            // Sets the authenticator that will be used by the networking code
            // when a proxy or an HTTP server asks for authentication.
            Authenticator.setDefault(new CustomAuthenticator());
            URL url = new URL("http://meteo.tecnico.ulisboa.pt/obs/livejson");
            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String[] data;

            while ((line = in.readLine()) != null) {
                // System.out.println(line);	// Print line read
				data=line.split(",");
				System.out.println(data[1].substring(12, 22) + "  " + data[1].substring(23,28) + "  " + data[2].substring(7) + "  " + data[4].substring(5));

				
				//Date
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String dateInString1 = data[1].substring(12, 22);
				String dateInString2 = data[1].substring(12, 28);
				
				Date date1 = sdf1.parse(dateInString1);
				Date date2 = sdf2.parse(dateInString2);
				// Convert Date date2 to Calendar --> Avoid Deprecation!
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(date2);
//				int hours = calendar.get(Calendar.HOUR_OF_DAY);
//				int minutes = calendar.get(Calendar.MINUTE);
//				int seconds = calendar.get(Calendar.SECOND);
				
				// System.out.println(date1); //Tue Aug 31 10:20:56 SGT 1982
				//Time
				@SuppressWarnings("deprecation")
				// Use Calendar.HOUR_OF_DAY instead of date2.getHours()
				//double time = calendar.get(Calendar.HOUR_OF_DAY)*1.0+calendar.get(Calendar.MINUTE)*1.0/60;
				double time = date2.getHours()*1.0+date2.getMinutes()*1.0/60;

				double temp = Double.parseDouble(data[2].substring(7));
				double humi = Double.parseDouble(data[2].substring(7));
				String state = "NA";
				
				// Create new node if day changes
				if (higro.last.day != date1 && higro.length()>1){
		        	// or
		    		// Verify if date is different of date of last node
		    		double[] timeS=new double[288];
		    		double[] tempS=new double[288];
		    		double[] humiS=new double[288];
		    		String[] stateS=new String[288];    		
		    		higro.add(date,timeS,tempS,humiS,stateS);
		            System.out.println("New Day");
				}
				// Update last instant data
				higro.feed(higro.last, time, temp, humi, state);
            }
            in.close();
            
            // Print for debug purposes
            higro.printL();
            System.out.println(" Goodbye World!");
        }
        
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
    
    public static void main(String[] args){
    	
        Timer timer = new Timer();
        timer.schedule(new AccessPasswordProtectedURLWithAuthenticatorTimer(), 0,1000*60*5);
                    
    }
    
    // Authenticator
    public static class CustomAuthenticator extends Authenticator {
        // Called when password authorization is needed
        protected PasswordAuthentication getPasswordAuthentication() {
            // Get information about the request
            String prompt = getRequestingPrompt();
            String hostname = getRequestingHost();
            InetAddress ipaddr = getRequestingSite();
            int port = getRequestingPort();
            String username = "junitec";
            String password = "ema.ist";

            // Return the information (a data holder that is used by Authenticator)
            return new PasswordAuthentication(username, password.toCharArray());

        }

    }

}

