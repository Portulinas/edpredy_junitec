// Arrays
//File Reader
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
//Java FX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


//public class LineChartSample extends Application {
public class Teste extends Application {

	public static double mean(double[] v) {				// Compute vector average
		double avg = 0;
		if (v.length==0){
			return avg;
		} else {
			int i=0;
			
			while (i<v.length){
				avg=avg+v[i];
				i++;
			}
			return (avg/v.length);
		}
			
		}
	
	public static double amplitude(double[] v) {				// Compute amplitude

		if (v.length==0){
			return 0;
		} else {
			int i=0;
			double max=v[i];
			double min=v[i];
			
			while (i<v.length){
				if (v[i]>max){
					max=v[i];
				} else if (v[i]<min){
					min=v[i];
				}
				i++;
			}

			return (max-min)/2;
		}
			
		}
	
	public static double phase(double[] t,double[] v) {				// Compute phase

		if (v.length==0){
			return 0;
		} else {
			int i=0;
			double max=v[i];
			double min=v[i];
			double tmax=t[i];
			double tmin=t[i];
			
			while (i<v.length){
				if (v[i]>max){
					max=v[i];
					tmax=t[i];
				} else if (v[i]<min){
					min=v[i];
					tmin=t[i];
				}
				i++;
			}
			double phasMax = 2*Math.PI*tmax/t[t.length-1];
			double phasMin = 0;
			if (tmin<12){
				phasMin = 2*Math.PI*(tmin+12)/t[t.length-1];	
			} else {
				phasMin = 2*Math.PI*(tmin-12)/t[t.length-1];
			}
			
			return phasMax;
		}
			
		}
		 
	    public void start(Stage stage) {
	    	double t[]={0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,19.5,20,20.5,21,21.5,22,22.5,23,23.5,24};
	    	double v[]={16.46,16.03,15.67,15.38,15.17,15.04,15,15.04,15.17,15.38,15.67,16.03,16.46,16.96,17.5,18.09,18.71,19.35,20,20.65,21.29,21.91,22.5,23.04,23.54,23.97,24.33,24.62,24.83,24.96,25,24.96,24.83,24.62,24.33,23.97,23.54,23.04,22.5,21.91,21.29,20.65,20,19.35,18.71,18.09,17.5,16.96,16.46};
	    	
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
	        for (int i=0; i<t.length; i++){
	        	series.getData().add(new XYChart.Data(t[i],v[i]));
	        }
	        	
	        Scene scene  = new Scene(lineChart,800,600);
	        lineChart.getData().add(series);
	       
	        stage.setScene(scene);
	        stage.show();
	    }
	
	
		public static void main(String[] args) throws IOException{
	//		double tempo[][]={{0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,19.5,20,20.5,21,21.5,22,22.5,23,23.5,24}};
	//		double temperatura[][]={{15,16.46,20,23.54,25,23.54,20,16.46,15,16.46,20,23.54,25,23.54,20,16.46,15,16.46,20,23.54,25,23.54,20,16.46,15,16.46,20,23.54,25,23.54,20,16.46,15,16.46,20,23.54,25,23.54,20,16.46,15,16.46,20,23.54,25,23.54,20,16.46,15}};
	//		double humidade[][]={{55,56.46,60,63.54,65,63.54,60,56.46,55,56.46,60,63.54,65,63.54,60,56.46,55,56.46,60,63.54,65,63.54,60,56.46,55,56.46,60,63.54,65,63.54,60,56.46,55,56.46,60,63.54,65,63.54,60,56.46,55,56.46,60,63.54,65,63.54,60,56.46,55}};
	//		Matrix ti = new Matrix(tempo);
	//		Matrix te = new Matrix(temperatura);
	//		Matrix hu = new Matrix(humidade);
	//		
	//		for (int i=0;i < ti.getNrows();i++){
	//			for (int j=0;j < ti.getNcols();j++){
	//				System.out.println(ti.getValueAt(i, j));// Print
	//			}
	//		}
	//	
	//		Matrix sol= MatrixMathematics.multiply(te,MatrixMathematics.transpose(te));
	//
	//		for (int i=0;i < sol.getNrows();i++){
	//			for (int j=0;j < sol.getNcols();j++){
	//				System.out.println(sol.getValueAt(i, j));// Print
	//			}
	//		}
			
			Date dia = new Date();
			double tempo[]={0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,19.5,20,20.5,21,21.5,22,22.5,23,23.5};
			double temperatura[]={16.46,16.03,15.67,15.38,15.17,15.04,15,15.04,15.17,15.38,15.67,16.03,16.46,16.96,17.5,18.09,18.71,19.35,20,20.65,21.29,21.91,22.5,23.04,23.54,23.97,24.33,24.62,24.83,24.96,25,24.96,24.83,24.62,24.33,23.97,23.54,23.04,22.5,21.91,21.29,20.65,20,19.35,18.71,18.09,17.5,16.96};
			double humidade[]={56.46,56.03,55.67,55.38,55.17,55.04,55,55.04,55.17,55.38,55.67,56.03,56.46,56.96,57.5,58.09,58.71,59.35,60,60.65,61.29,61.91,62.5,63.04,63.54,63.97,64.33,64.62,64.83,64.96,65,64.96,64.83,64.62,64.33,63.97,63.54,63.04,62.5,61.91,61.29,60.65,60,59.35,58.71,58.09,57.5,56.96};
			String estado[]={"S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S"};
			
			HygroSample higro = new HygroSample();
			System.out.println(higro.emptyQ());
			System.out.println(higro.length);
			higro.add(dia,tempo,temperatura,humidade,estado);
	//		System.out.println(higro.emptyQ());
	//		System.out.println(higro.length);
	//		higro.print();
	//		System.out.println(higro.emptyQ());
	  		
			double avg = mean(higro.element(1).temp);
			System.out.println(avg);
			double amp = amplitude(higro.element(1).temp);
			System.out.println(amp);
			double pha = phase(higro.element(1).time,higro.element(1).temp);
			System.out.println(pha);		
			
			// Print date
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
			String dateStr = sdf.format(new Date());
			System.out.println(dateStr); //15/10/2013
			
			// Java FX GUI
			launch(args);
			
			// Read txt data
			//String textPath = "C:/Users/Rui Barros Garcia/Desktop/EDP_ReDy/data/2016-08-22.txt";
			//Path path = Paths.get(textPath);
			Date date = new Date();
			double[] timeS=new double[48];
			double[] tempS=new double[48];
			double[] humiS=new double[48];
			String[] stateS=new String[48];
			HygroSample higro2 = new HygroSample();
			higro2.add(date,timeS,tempS,humiS,stateS);
			
			try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Rui Barros Garcia/Desktop/EDP_ReDy/data/2016-08-22.txt")))
			{

				String sCurrentLine;
				String[] data;
				int i=0;

				while ((sCurrentLine = br.readLine()) != null) {
					System.out.println(sCurrentLine);
					
					if (i!=0){
						data=sCurrentLine.split("\\t");
						higro2.feeda(higro2.first, Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2])," ");

					}
					i++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("a");
			higro.print();
			
		}
	}