package SRGP;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Driver.
 */
public class Driver {
	
	/** The Constant POPULATION_SIZE. */
	static final int POPULATION_SIZE = 20;
	
	/** The evolver. */
	private Evolve<GPIndividual, Double> evolver;

	/** The fitness function. */
	private SRFitness fitnessFunction;

	/**
	 * Instantiates a new driver.
	 *
	 * @param input the input
	 */
	public Driver(Double[][] input) {
		fitnessFunction = new SRFitness(input);
		Population<GPIndividual> population = this.createPopulation( );
		this.evolver = new Evolve<GPIndividual, Double>(population, fitnessFunction);
	}

	/**
	 * initially we always create a tree with a depth of depth from 1 to 5;
	 *
	 * @author wangsong
	 * @return the population
	 */
	private Population<GPIndividual> createPopulation() {
		Random random = new Random();
		Population<GPIndividual> population = new Population<GPIndividual>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			GPIndividual individual = new GPIndividual(this.fitnessFunction, GPIndividual.createTree(random.nextInt(5) + 1));
			population.addIndividual(individual);
		}
		return population;
	}

	/**
	 * Evolve.
	 *
	 * @param iters the iters
	 */
	public void evolve(int iters) {
		this.evolver.evolve(iters);
	}

	/**
	 * Gets the best tree.
	 *
	 * @return the best tree
	 */
	public Node getBestTree() {
		return this.evolver.getFittest().getTree();
	}

	/**
	 * Fitness.
	 *
	 * @param n the n
	 * @return the double
	 */
	public double fitness(Node n) {
		return this.fitnessFunction.fitness(n);
	}
}
