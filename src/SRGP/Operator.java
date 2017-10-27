package SRGP;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface Operator.
 */
public interface Operator {
	
	/**
	 *  for each operator the calculation of the tree is 
	 * 		different, so evaluate method is required in the interface.
	 *
	 * @param n the n
	 * @param vocab the vocab
	 * @return the double
	 */
	double evaluate(Node n, Vocab vocab);

	/**
	 *  constants and variable node are terminal nodes, their argument
	 * 	 number should be zero, then for nonterminal nodes add/divide/multiply/subtract,
	 * 	 their argument number is two, in order to differentiate terminal nodes from
	 * 	 nonterminal, a method to return the number of arguments is required in the interface.
	 *
	 * @return the int
	 */
	int numArg();

	/**
	 * if the operator is a constant, since I will optimize the constants of
	 * the syntax tree, and make these constants to form a individual and do cross and mutate, so 
	 * a method to determine whether it's a constant or not is required in the interface.
	 *
	 * @return true if it's a constant
	 */
	boolean isConstant();

	/**
	 * since if I want to recursively print a tree, and print method is different for different node
	 * so a method to require each operator to implement should be set at the interface .
	 *
	 * @param n root of the tree to be printed
	 * @return the string
	 */
	String print(Node n);

	/**
	 * I will optimize the constants of the tree, so a method to return all the constants in the subtree
	 * rooted at node n is required in the interface.
	 *
	 * @param n the n
	 * @return the coeff
	 */
	List<Double> getCoeff(Node n);

	/**
	 * after get the constants of the subtree, and doing cross and mutate for the constants individual,
	 * we need a method to set the mutated constants back into the tree, sefConst method is required 
	 * in the interface.
	 *
	 * @param n the n
	 * @param coeff the coeff
	 * @param index the index
	 */
	void setCoeff(Node n, List<Double> coeff, int index);
}



