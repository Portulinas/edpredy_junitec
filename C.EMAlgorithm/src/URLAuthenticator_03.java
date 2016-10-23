import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class URLAuthenticator_03  extends TimerTask {
	
	static HygroSample higroPred = new HygroSample();
	
    public void run() {
    	System.out.println("Hello World! - C.Prediction");
    	
        Date date1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1S = sdf1.format(date1);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String date1N = sdf2.format(date1);
        
        //Prediction Vector and Fill
        double[] timeP=new double[24];
		double[] tempP=new double[24];
		double[] humiP=new double[24];
        
		for (int i=0;i<timeP.length;i++){
        	timeP[i]=i*1.0;
        }
        
        if (higroPred.first==null){
        	System.out.println("First Day - C.Prediction");
            // Create data sample if inexisting
    		double[] timeS=new double[288];
    		double[] tempS=new double[288];
    		double[] humiS=new double[288];
    		String[] stateS=new String[288];    		
    		higroPred.add(date1,timeS,tempS,humiS,stateS);
        }
        
        //Access website and feed vector
        try {
            // Sets the authenticator that will be used by the networking code
            // when a proxy or an HTTP server asks for authentication.
            Authenticator.setDefault(new CustomAuthenticator());
            URL urlT = new URL("http://meteo.tecnico.ulisboa.pt/service/junitec/PrevByDateAndMetric_sup?lat=38.74&lon=-9.14&date=" + date1N + "&metric=temp");
            URL urlH = new URL("http://meteo.tecnico.ulisboa.pt/service/junitec/PrevByDateAndMetric_sup?lat=38.74&lon=-9.14&date=" + date1N + "&metric=hum");


            // read text returned by server
            BufferedReader inT = new BufferedReader(new InputStreamReader(urlT.openStream()));
            String lineT;
            String[] dataAuxT;
            String[] dataT;

            BufferedReader inH = new BufferedReader(new InputStreamReader(urlH.openStream()));
            String lineH;
            String[] dataAuxH;
            String[] dataH;
            
            // Create data sample if inexisting
			Date date = new Date();
			double[] timeS=new double[288];
			double[] tempS=new double[288];
			double[] humiS=new double[288];
			String[] stateS=new String[288];

			//Date
			//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			date1 = higroPred.last.day;
			Date date2 = new Date();

			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			System.out.println(sdf3.format(date2));
			
			// Create new node if day changes
			if (sdf1.format(date2).equals(sdf1.format(date1))!=true){
				System.out.println(sdf1.format(date2)+ " vs " + sdf1.format(date1));
	        	// or
	    		// Verify if date is different of date of last node
	    		higroPred.add(date2,timeS,tempS,humiS,stateS);
	            System.out.println("New Day");
			}
			
            //Read Data for Temperature Prevision
            while ((lineT = inT.readLine()) != null) {
                System.out.println(lineT);	// Print line read

                dataAuxT=lineT.split(":");
                dataT=dataAuxT[2].split(",");
                
				//System.out.println(data[1].substring(12, 22) + "  " + data[1].substring(23,28) + "  " + data[2].substring(7) + "  " + data[4].substring(5));
				for (int i=0;i<dataT.length;i++){
					if (i==0){
						//System.out.println(dataT.length);
						//System.out.println(dataT[i].substring(2, dataT[i].length()));
						tempP[i]=Double.parseDouble(dataT[i].substring(2, dataT[i].length()));
						
					} else if (i<dataT.length-1){
						//System.out.println(dataT[i].substring(1, dataT[i].length()));
						tempP[i]=Double.parseDouble(dataT[i].substring(1, dataT[i].length()));
					} else {
						//System.out.println(dataT[i].substring(1, dataT[i].length()-4));
						tempP[i]=Double.parseDouble(dataT[i].substring(1, dataT[i].length()-4));
					}
					
				}

				//Read Data for Humidity Prevision
	            while ((lineH = inH.readLine()) != null) {

	                System.out.println(lineH);	// Print line read

	                dataAuxH=lineH.split(":");
	                dataH=dataAuxH[2].split(",");
	                
					//System.out.println(data[1].substring(12, 22) + "  " + data[1].substring(23,28) + "  " + data[2].substring(7) + "  " + data[4].substring(5));
					for (int i=0;i<dataH.length;i++){
						if (i==0){
							//System.out.println(dataH.length);
							//System.out.println(dataH[i].substring(2, dataH[i].length()));
							humiP[i]=Double.parseDouble(dataH[i].substring(2, dataH[i].length()));
							
						} else if (i<dataH.length-1){
							//System.out.println(dataH[i].substring(1, dataH[i].length()));
							humiP[i]=Double.parseDouble(dataH[i].substring(1, dataH[i].length()));
						} else {
							//System.out.println(dataH[i].substring(1, dataH[i].length()-4));
							humiP[i]=Double.parseDouble(dataH[i].substring(1, dataH[i].length()-4));
						}
						
					}
	            }
	            
				
				// ---> Interpolation from hour to minute
				for (int i=0;i<timeS.length;i++){
					
					//Variable Declaration
					double time=i*1.0/12;
					int aux1=i/12;
					int aux2=i-aux1*12;
					double temp;
					double humi;
					String state = "NA";
					
					if (aux1<23){
						temp=tempP[aux1]*(1-aux2*1.0/12)+tempP[aux1+1]*(aux2*1.0/12);
						humi=humiP[aux1]*(1-aux2*1.0/12)+humiP[aux1+1]*(aux2*1.0/12);
					} else {
						temp=tempP[aux1]*(1-aux2*1.0/12)+tempP[0]*(aux2*1.0/12);
						humi=humiP[aux1]*(1-aux2*1.0/12)+humiP[0]*(aux2*1.0/12);
					}
					
					higroPred.feed(higroPred.last, time, temp, humi, state);
					//System.out.println(time + " " + aux1 + " " + aux2 + " " + temp + " " + humi);
				}
            }

            //Close Bufferer Readers
            inT.close();
            inH.close();
            
            // Print for debug purposes
            higroPred.printL();
            higroPred.toCSV(higroPred.last, "Pre");
            // System.out.println(" Goodbye World!");
        }
        
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
        
        
      }
    
    public static void main(String[] args){
    	
        Timer timer = new Timer();
        timer.schedule(new URLAuthenticator_03(), 0,1000*60*4);
        
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
            String password = "lat_lon_app";

            // Return the information (a data holder that is used by Authenticator)
            return new PasswordAuthentication(username, password.toCharArray());

        }

    }

}