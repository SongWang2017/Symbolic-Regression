package SRGP;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * after the vocabulary, I need to create this class to act as the 
 * basic arithmetic unit which deal with a binary computation in our 
 * case (add, subtract, multiply, divide), and for a single node, its 
 * children (left or right can be a terminal or expressions themselves).
 * In case for the tree can mutate, crossover (which means a node's 
 * children can be changed, so we need to create multiple methods to 
 * get this nodes's children and add a subtree as this node's child.
 * What's more in order to evaluate a Tree, we can do it in a recursive 
 * way, first evaluate it's left subtree, then it's right subtree, lastly
 * combined with the node's operator to do the computation.
 * here I just call it node here, and the node itself or the subtree start
 * from the node are all called node here since it's a standard class for a tree node
 * @author wangsong
 *
 */

public class Node implements Cloneable {

	/** The children. */
	private ArrayList<Node> children = new ArrayList<Node>();

	/** The coeff. */
	// will optimize the coefficients of the tree
	private ArrayList<Double> coeff = new ArrayList<Double>();

	/** The o. */
	private Operator o;

	/**
	 * Instantiates a new node.
	 *
	 * @param o the o
	 */
	public Node (Operator o) {
		this.o = o;
	}

	/**
	 * Evaluate.
	 *
	 * @param vocab the vocab
	 * @return the double
	 */
	public double evaluate(Vocab vocab) {
		return this.o.evaluate(this, vocab);
	}

	/**
	 * Prints the.
	 *
	 * @return the string
	 */
	public String print() {
		return this.o.print(this);
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	// getters and setters for instance variables
	public ArrayList<Node> getChildren() {
		return this.children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children the children
	 * @return the node
	 */
	public Node setChildren(ArrayList<Node> children) {
		this.children = children;
		return this;
	}

	/**
	 * Adds the child.
	 *
	 * @param child the child
	 */
	public void addChild(Node child) {
		this.children.add(child);
	}

	/**
	 * Clear.
	 */
	public void clear() {
		this.children.clear();
	}

	/**
	 * Gets the coeff of node.
	 *
	 * @return the coeff of node
	 */
	public ArrayList<Double> getCoeffOfNode() {
		return this.coeff;
	}

	/**
	 * Sets the coeff of node.
	 *
	 * @param coeff the coeff
	 * @return the node
	 */
	public Node setCoeffOfNode(ArrayList<Double> coeff) {
		this.coeff = coeff;
		return this;
	}

	/**
	 * Adds the coeff.
	 *
	 * @param coeff the coeff
	 */
	public void addCoeff(double coeff) {
		this.coeff.add(coeff);
	}

	/**
	 * Clear coeff.
	 */
	public void clearCoeff() {
		if (this.coeff.size() > 0) {
			this.coeff.clear();
		}
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public Operator getOperator() {
		return this.o;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(Operator operator) {
		this.o = operator;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Node clone() {
		Node cloned = new Node(this.o);

		for (Node c : this.children) {
			cloned.children.add(c.clone());
		}
		for (Double d : this.coeff) {
			cloned.coeff.add(d);
		}
		return cloned;
	}

	/**
	 * Gets the coeff of tree.
	 *
	 * @return the coeff of tree
	 */
	public List<Double> getCoeffOfTree() {
		LinkedList<Double> coeff = new LinkedList<Double>();
		this.getCoeffOfTree(coeff);
		Collections.reverse(coeff);
		return coeff;
	}

	/**
	 * Gets the coeff of tree.
	 *
	 * @param coeff the coeff
	 * @return the coeff of tree
	 */
	private void getCoeffOfTree(Deque<Double> coeff) {
		List<Double> coeffs = this.o.getCoeff(this);
		for (Double d : coeffs) {
			coeff.push(d);
		}
		for (int i = 0; i < this.children.size(); i++) {
			this.children.get(i).getCoeffOfTree(coeff);
		}
	}

	/**
	 * Sets the coeff of tree.
	 *
	 * @param coeff the new coeff of tree
	 */
	public void setCoeffOfTree(List<Double> coeff) {
		this.setCoeffOfTree(coeff, 0);
	}

	/**
	 * Sets the coeff of tree.
	 *
	 * @param coeff the coeff
	 * @param index the index
	 * @return the int
	 */
	private int setCoeffOfTree(List<Double> coeff, int index) {
		this.o.setCoeff(this, coeff, index);
		if (this.o.isConstant()) {
			index += 1;
		}
		if (this.children.size() > 0) {
			for (int i = 0; i < this.children.size(); i++) {
				index = this.children.get(i).setCoeffOfTree(coeff, index);
			}
		}
		return index;
	}

	/**
	 * Gets the all nodes.
	 *
	 * @return the all nodes
	 */
	public ArrayList<Node> getAllNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		int index = 0;
		nodes.add(this);
		while (true) {
			if (index < nodes.size()) {
				Node node = nodes.get(index++);
				for (Node child : node.children) {
					nodes.add(child);
				}
			} else {
				break;
			}
		}	
		return nodes;
	}
	
	/**
	 * traversal all the nodes in the subtree rooted at tree
	 * and return true if any operator is a variable.
	 *
	 * @author wangsong
	 * @param tree the tree
	 * @return true if any operator in the tree is a variable
	 */
	public static boolean hasVariableNode(Node tree) {
		boolean result = false;
		if (!tree.getOperator().isConstant() && tree.getOperator().numArg() == 0) {
			result = true;
		} else {
			for (Node child : tree.getChildren()) {
				result = hasVariableNode(child);
				if (result) {
					break;
				}
			}
		}
		return result;
	}

}
