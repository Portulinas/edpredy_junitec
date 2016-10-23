import java.util.Date;

public class Node {			//Node constructor class	
	Node next;
	Date day;
	double[] time;
	double[] temp;
	double[] hum;
	String[] state;

	
	 Node (Date sdf, double[] timeTag, double[] temperature, double[] relHumidity, String[] dayState){	//Add data to Node in position
		next=null;
		day=sdf;					// Day of the Node
		time=timeTag;				//vector from a single day with time tag
		temp=temperature;			//vector from a single day with temperature
		hum=relHumidity;			//vector from a single patient with relative humidity
		state=dayState;				//vector with day state (rainy, sunny, etc) or occupied + disoccupied + open + close
		}
}