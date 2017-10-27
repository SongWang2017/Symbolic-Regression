package SRGP;

// TODO: Auto-generated Javadoc
/**
 * The Class SRFitness.
 */
class SRFitness implements Fitness <GPIndividual, Double> {

	/** The input. */
	private Double[][] input;
	
	/** The rows. */
	private int rows;

	/**
	 * Instantiates a new SR fitness.
	 *
	 * @param input the input
	 */
	public SRFitness(Double[][] input) {
		this.input = input;
		rows = input.length;
	}

	/** The count. */
	public static long count = 0;
	
	/* (non-Javadoc)
	 * @see SRGP.Fitness#calculate(SRGP.Individual)
	 */
	public Double calculate(GPIndividual individual) {
		count++;
		Node n = individual.getTree();
		return this.fitness(n);
	}
	
	/**
	 * to calculate the fitness for one (binary tree).
	 *
	 * @author wangsong
	 * @param n the n
	 * @return the double
	 * @param: input data, one row per data point, follow the
	 * format "  x  y" separated by two spaces
	 * @return: the total absolute error between calculated y and
	 * the value in the input per data point
	 */
	public double fitness(Node n) {
		double diff = 0;
		Vocab v = new Vocab();
		for (int i = 0; i < rows; i++) {
			double x = input[i][0];
			v.setVariable(x);
			double y = input[i][1];
			double calculated = n.evaluate(v);
			diff  = diff + Math.abs(y - calculated);
		}
		return diff;
	}
	
}

