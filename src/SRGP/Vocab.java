package SRGP;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * this class stores all the operators or variables that will be used
 * and can be viewed as the material to be used for building the binary tree
 * or lets say the vocabulary for the binary tree.
 *
 * @author wangsong
 */

public class Vocab {
	
	/** The x. */
	// x holds the value for the variable
	private double x = 0;
	
	/** The random. */
	// a random object to randomly choose operators
	Random random = new Random();
	
	/** The non terminal operators. */
	private ArrayList<Operator> nonTerminalOperators = new ArrayList<Operator>();
	
	/** The terminal operators. */
	private ArrayList<Operator> terminalOperators = new ArrayList<Operator>();
	
	/** The operators. */
	private ArrayList<Operator> operators = new ArrayList<Operator>();
	
	/**
	 * Instantiates a new vocab.
	 */
	public Vocab() {
		operators.add(Operators.ADD);
		operators.add(Operators.SUB);
		operators.add(Operators.MUL);
		operators.add(Operators.DIV);
		operators.add(Operators.VARIABLE);
		operators.add(Operators.CONSTANT);
		for (Operator o : operators) {
			if (o.numArg() == 0) {
				this.terminalOperators.add(o);
			} else {
				this.nonTerminalOperators.add(o);
			}
		}
	}

	/**
	 * Gets the variable value.
	 *
	 * @return the variable value
	 */
	public double getVariableValue() {
		return x;
	}

	/**
	 * Sets the variable.
	 *
	 * @param value the new variable
	 */
	public void setVariable(double value) {
		x =  value;
	}

	/**
	 * Gets the operators.
	 *
	 * @return the operators
	 */
	public List<Operator> getOperators() {
		return this.operators;
	}
	
	/**
	 * Gets the one non terminal operator.
	 *
	 * @return the one non terminal operator
	 */
	public Operator getOneNonTerminalOperator() {
		return this.nonTerminalOperators.get(this.random.nextInt(this.nonTerminalOperators.size()));
	}

	/**
	 * Gets the one terminal operator.
	 *
	 * @return the one terminal operator
	 */
	public Operator getOneTerminalOperator() {
		return this.terminalOperators.get(this.random.nextInt(this.terminalOperators.size()));
	}
}
