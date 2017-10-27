package SRGP;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Population.
 *
 * @param <I> the generic type
 */
public class Population<I extends Individual<I>> {

	/**  usually after last generation, the population size is the constant I set 	
	 * at the beginning of the next generation we first add the best parent to  	
	 * current population, then each parent is mutated and each pair parents are  	
	 * crossed, so the population size should be set at POPULATION_SIZE * 3 + 1. */
	private List<I> individuals = new ArrayList<I>(Driver.POPULATION_SIZE * 3 + 1);

	/** The random. */
	private final Random random = new Random();

	/**
	 * Adds the individual.
	 *
	 * @param individual the individual
	 */
	public void addIndividual(I individual) {
		this.individuals.add(individual);
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.individuals.size();
	}

	/**
	 * randomly get an individual.
	 *
	 * @return the individual
	 */
	public I getIndividual() {
		return this.individuals.get(this.random.nextInt(this.individuals.size()));
	}

	/**
	 * get the individual with given index.
	 *
	 * @param index the index
	 * @return the individual
	 */
	public I getIndividual(int index) {
		return this.individuals.get(index);
	}
	
	/**
	 * https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html
	 *
	 * @param individualComparator the individual comparator
	 */
	public void sortPopulation(Comparator<I> individualComparator) {
		Collections.sort(this.individuals, individualComparator);
	}
	
	/**
	 * for trim of a list, use sublist to get and return only
	 * a given length list of the original list.
	 *
	 * @param length the length
	 */
	public void trim(int length) {
		this.individuals = this.individuals.subList(0, length);
	}

}
