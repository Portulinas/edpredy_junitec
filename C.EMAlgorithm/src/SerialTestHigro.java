import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;


public class SerialTestHigro implements SerialPortEventListener {
	
static HygroSample higroInt = new HygroSample();
	
SerialPort serialPort;
    /** The port we're normally going to use. */
private static final String PORT_NAMES[] = {"/dev/tty.usbserial-A9007UX1", // Mac OS X
        									"/dev/ttyUSB0", // Linux
        									"COM5", // Windows
        									};

private BufferedReader input;
private OutputStream output;
private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 9600;

public void initialize() {

	// Higro variables Date + Hour + Temp + HR
	System.out.println("Hello World! - A.Interior");
    Date date1 = new Date();
    
    if (higroInt.first==null){
    	System.out.println("First Day - A.Interior");
        // Create data sample if inexisting
		double[] timeS=new double[288];
		double[] tempS=new double[288];
		double[] humiS=new double[288];
		String[] stateS=new String[288];    		
		higroInt.add(date1,timeS,tempS,humiS,stateS);
    }

	
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

    //First, Find an instance of serial port as set in PORT_NAMES.
    while (portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if (currPortId.getName().equals(portName)) {
                portId = currPortId;
                break;
            }
        }
    }
    if (portId == null) {
        System.out.println("Could not find COM port.");
        return;
    }

    try {
        serialPort = (SerialPort) portId.open(this.getClass().getName(),
                TIME_OUT);
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // open the streams
        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        output = serialPort.getOutputStream();

        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    } catch (Exception e) {
        System.err.println(e.toString());
    }
}


public synchronized void close() {
    if (serialPort != null) {
        serialPort.removeEventListener();
        serialPort.close();
    }
}

public synchronized void serialEvent(SerialPortEvent oEvent) {
	//Date
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	Date date1 = higroInt.last.day;
	Date date2 = new Date();
	//String instante = sdf1.format(date2);
	//System.out.println(instante);
	
	
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
        try {
            String inputLine=null;
            if (input.ready()) {
                inputLine = input.readLine();
                
                String [] chunks = inputLine.split(",");
                
                // System.out.println(inputLine);
                System.out.println(chunks[0] + "\t" + chunks[1] + "\t");
                
                // Evaluate Data
				double time = date2.getHours()*1.0+date2.getMinutes()*1.0/60;

				double temp = Double.parseDouble(chunks[0]);
				double humi = Double.parseDouble(chunks[1]);
				String state = "NA";
				
				// Create new node if day changes
				if (sdf1.format(date2).equals(sdf1.format(date1))!=true){
					System.out.println(sdf1.format(date2)+ " vs " + sdf1.format(date1));
		        	// or
		    		// Verify if date is different of date of last node
		    		double[] timeS=new double[288];
		    		double[] tempS=new double[288];
		    		double[] humiS=new double[288];
		    		String[] stateS=new String[288];    		
		    		higroInt.add(date2,timeS,tempS,humiS,stateS);
		            System.out.println("New Day");
		            higroInt.toCSV(higroInt.last, "Int");
		            //higroInt.toHTML(higroInt.last, "Int");
				}
				// Update last instant data
				higroInt.feed(higroInt.last, time, temp, humi, state);

            }
            
            // Print for debug purposes
            higroInt.printL();
			higroInt.toCSV(higroInt.last, "Int");
			//higroInt.toHTML(higroInt.last, "Int");


        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    // Ignore all the other eventTypes, but you should consider the other ones.
}

public void start(Stage stage) {
	double[] timeS=new double[288];
	for(int i=0;i<timeS.length;i++){
		timeS[i]=24/(5/60)*i;
	}
	double[] tempS=new double[288];
	
	stage.setTitle("Hygrometric Curve");
    //defining the axes
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Hour");
    //creating the chart
    final LineChart<Number,Number> lineChart = 
            new LineChart<Number,Number>(xAxis,yAxis);
            
    lineChart.setTitle("Temperature");

    //defining a series
    XYChart.Series series = new XYChart.Series();
    series.setName("My portfolio");

    //populating the series with data
    //populating the series with data
    for (int i=0; i<timeS.length; i++){
    	series.getData().add(new XYChart.Data(timeS[i],tempS[i]));
    }
    
    Scene scene  = new Scene(lineChart,800,600);
    lineChart.getData().add(series);
   
    stage.setScene(scene);
    stage.show();
}


public static void main(String[] args) throws Exception {
	System.out.println("Started");
	SerialTestHigro main = new SerialTestHigro();
    main.initialize();
    Thread t=new Thread() {
        public void run() {
            //the following line will keep this app alive for 1000    seconds,
            //waiting for events to occur and responding to them    (printing incoming messages to console).
            try {Thread.sleep(1000000);} catch (InterruptedException    ie) {}
        }
    };
    t.start();
    
    Timer timer = new Timer();
    timer.schedule(new PasswordURLAuthenticatorTimerArduino(), 0,1000*60*4);
    

}
}