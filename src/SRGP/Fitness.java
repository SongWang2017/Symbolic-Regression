package SRGP;

// TODO: Auto-generated Javadoc
/**
 * the coefficients of a tree is treated as an individual, also the coefficients will be evolved,
 * but the calculation between a tree and the coefficients individual is different, so a interface 
 * for calculate fitness is required .
 *
 * @author wangsong
 * @param <T> the generic type
 */
public interface Fitness<I extends Individual<I>, T extends Comparable<T>> {	
	
	/**
	 * Calculate.
	 *
	 * @param individual the individual
	 * @return the t
	 */
	T calculate(I individual);
}
