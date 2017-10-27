package SRGP;

import java.io.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadData.
 */
public class ReadData {
	
	/** The dependent. */
	ArrayList<Double> dependent = new ArrayList<Double>();
	
	/** The independent. */
	ArrayList<Double> independent = new ArrayList<Double>();
	
	/** The file name. */
	String fileName;
	
	/**
	 * Instantiates a new read data.
	 *
	 * @param fileName the file name
	 */
	public ReadData(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Read.
	 *
	 * @return the double[][]
	 */
	public Double[][] read() {
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName)));   	    
	    String str;    	    
	    while (( str = br.readLine()) != null) {   	    	
	    	// for sr_line
	    	//String[] xAndY = str.split("  ");
	    	// for circle file, just manually to make the data separated by two spaces
	    	String[] xAndY = str.split("  ");
	    	//System.out.println(xAndY.length + " " + i++);
	    	//TODO
	    	//using a while loop to read the data without manually manipulating the data file
	    	double x = Double.valueOf(xAndY[1]);
	    	this.independent.add(x);
	    	double y = Double.valueOf(xAndY[2]);
	    	this.dependent.add(y);  	    	  	    	    	    	    	    	    	
	    }
	    br.close();
	} catch (IOException e) {
	    System.out.println("ERROR: unable to read file " + fileName);
	    e.printStackTrace();   
	}
	
	int dataPoints = this.dependent.size();
	Double[][] input = new Double[dataPoints][2];
	for (int i = 0; i < dataPoints; i++) {
		input[i][0] = this.independent.get(i);
		input[i][1] = this.dependent.get(i);
	}
	return input;
	}

}
