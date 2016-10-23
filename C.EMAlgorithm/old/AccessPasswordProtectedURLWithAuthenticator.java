import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccessPasswordProtectedURLWithAuthenticator {
    public static void main(String[] args) throws ParseException {
        try {
            // Sets the authenticator that will be used by the networking code
            // when a proxy or an HTTP server asks for authentication.
            Authenticator.setDefault(new CustomAuthenticator());
            URL url = new URL("http://meteo.tecnico.ulisboa.pt/obs/livejson");
            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String[] data;
            // Create data sample if inexisting
			Date date = new Date();
			double[] timeS=new double[288];
			double[] tempS=new double[288];
			double[] humiS=new double[288];
			String[] stateS=new String[288];
			HygroSample higro = new HygroSample();
			higro.add(date,timeS,tempS,humiS,stateS);

			// Verify if date is different of date of last node
			//	Put code here
			
			// If date match last node date
			
            while ((line = in.readLine()) != null) {
                System.out.println(line);
				data=line.split(",");
				System.out.println(data[1].substring(12, 22) + "  " + data[1].substring(23,28) + "  " + data[2].substring(7) + "  " + data[4].substring(5));
				System.out.println(5./60);
				
				//Date
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-M-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-M-dd hh:mm");
				String dateInString1 = data[1].substring(12, 22);
				String dateInString2 = data[1].substring(12, 28);
				Date date1 = sdf1.parse(dateInString1);
				Date date2 = sdf2.parse(dateInString2);
				System.out.println(date1); //Tue Aug 31 10:20:56 SGT 1982
				//Time
				@SuppressWarnings("deprecation")
				double time = date2.getHours()*1.0+date2.getMinutes()*5.0/60;
				
				double temp = Double.parseDouble(data[2].substring(7));
				double humi = Double.parseDouble(data[2].substring(7));
				String state = "NA";
				higro.feed(higro.last, time, temp, humi, state);
            }
            in.close();
            
            // Print for debug purposes
            higro.print();
        }
        
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
            
    }
    
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

