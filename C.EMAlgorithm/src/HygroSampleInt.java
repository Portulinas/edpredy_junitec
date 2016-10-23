import java.io.IOException;
import java.util.Date;

public interface HygroSampleInt {
	// Hygrometric Data Sample Interface that includes
	
	void add(Date sdf, double[] time, double[] temp, double[] humi, String[] dayState);
	void feeda(Node n, double time, double temp, double humi, String state);
	void feed(Node n, double time, double temp, double humi, String state);
	boolean emptyQ();
	int length();
	Node element(int i);
	void join(HygroSample a);
	void print();
	void printL();
	//double mean(double[] v);
	//Mix firGuess(int M);
	//Mix EM(Mix firGuessA) throws IllegalDimensionException, NoSquareException;
	void toCSV(Node n, String aux);
	void toHTML(Node n, String tag) throws IOException;

}
