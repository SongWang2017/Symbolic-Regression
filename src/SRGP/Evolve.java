package SRGP;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Evolve.
 *
 * @param <I> the generic type
 * @param <T> the generic type
 */
public class Evolve<I extends Individual<I>, T extends Comparable<T>> {

	/** The comparator. */
	private final IndividualComparator comparator = new IndividualComparator();
	
	/** The fitness function. */
	final Fitness<I, T> fitnessFunction;
	
	/** The population. */
	private Population<I> population;
	
	/**
	 * given a population and a fitness function to calculate individual fitness
	 * a evovler can be implemented.
	 *
	 * @param population the population
	 * @param fitnessF the fitness F
	 */
	public Evolve(Population<I> population, Fitness<I, T> fitnessF) {
		this.population = population;
		this.fitnessFunction = fitnessF;
	}
	
	// for each generation we need to sort the fitness of the individual 
	// for a list with a class type we need to calculate its fitness first
	/**
	 * The Class IndividualComparator.
	 */
	// then sort the objects, so construct a comparator here.
	private class IndividualComparator implements Comparator<I> {
		
		/** The hm. */
		private final Map<I, T> hm = new HashMap<I, T>();

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(I individual1, I individual2) {
			T fit1 = this.fitness(individual1);
			T fit2 = this.fitness(individual2);
			return fit1.compareTo(fit2);
		}

		/**
		 * Fitness.
		 *
		 * @param individual the individual
		 * @return the t
		 */
		public T fitness(I individual) {
			T fit = this.hm.get(individual);
			if (fit == null) {
				fit = Evolve.this.fitnessFunction.calculate(individual);
				this.hm.put(individual, fit);
			}
			return fit;
		};
	}
	
	/**
	 * Gets the fittest.
	 *
	 * @return the fittest
	 */
	public I getFittest() {
		I result = this.population.getIndividual(0);	
		T min = Evolve.this.fitnessFunction.calculate(this.population.getIndividual(0));
		for (int i = 0; i < this.population.getSize(); i ++) {
			T fit = Evolve.this.fitnessFunction.calculate(this.population.getIndividual(i));
			if (fit.compareTo(min) < 0) {
				min = fit;
				result = this.population.getIndividual(i); 
			}
		}
		return result;
	}

	/**
	 * every generation we get the fittest parent individual, and copy it into the next 
	 * generation,.
	 *
	 * @author wangsong
	 */
	public void evolve() {
		int parentSize = this.population.getSize();
		Population<I> newPopulation = new Population<I>();
		// select the best parent and move to new population
		newPopulation.addIndividual(Evolve.this.getFittest());	
		for (int i = 0; i < parentSize; i++) {
			I individual = this.population.getIndividual(i);
			I mutated = individual.mutate();
			I otherIndividual = this.population.getIndividual();
			List<I> crossed = individual.cross(otherIndividual);
			newPopulation.addIndividual(mutated);
			for (I ind : crossed) {
				newPopulation.addIndividual(ind);
			}
		}
		// this sorting evaluates the fitness function a lot
		newPopulation.sortPopulation(this.comparator);
		newPopulation.trim(parentSize);
		this.population = newPopulation;
	}

	/**
	 * Evolve.
	 *
	 * @param count the count
	 */
	public void evolve(int count) {
		for (int i = 1; i <= count; i++) {
			this.evolve();
		}
	}
}
