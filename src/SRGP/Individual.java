package SRGP;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * the coefficients of a tree is treated as an individual, also the coefficients will be evolved,
 * so a interface for individual is required.
 *
 * @author wangsong
 * @param <I> the generic type
 */
public interface Individual<I extends Individual<I>> {
	
	/**
	 * Cross.
	 *
	 * @param individual the individual
	 * @return the list
	 */
	List<I> cross( I individual );
	
	/**
	 * Mutate.
	 *
	 * @return the i
	 */
	I mutate();
}
