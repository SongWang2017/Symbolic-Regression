package SRGP;

// TODO: Auto-generated Javadoc
/**
 * The Class Main_GP.
 */
public class Main_GP {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//String fileName = "C:/Users/wangsong/Documents/Symbolic Regression/SR_line.txt";
		String fileName = "C:/Users/wangsong/Documents/Symbolic Regression/SR_div_noise.txt";
		Double[][] input = new ReadData(fileName).read();
    	Driver driver = new Driver(input);  
		driver.evolve(20);
		double fitness = driver.fitness(driver.getBestTree());
		System.out.println(String.format("TotalError = %s   f(x) = %s", fitness, driver.getBestTree().print()));
		System.out.println("number of evaluations:  " + SRFitness.count);
	}
}
