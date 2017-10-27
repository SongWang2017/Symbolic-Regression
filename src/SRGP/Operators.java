package SRGP;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Enum Operators.
 */
public enum Operators implements Operator {

	/** The constant. */
	CONSTANT {
		@Override
		public String print(Node n) {
			double coeff = n.getCoeffOfNode().get(0);
			String result = null;
			if (coeff < 0) {
				result = String.format("(%s)", coeff);
			} else {
				result = "" + coeff;
			}
			return result;
		}

		@Override
		public int numArg() {
			return 0;
		}
		
		@Override
		public List<Double> getCoeff(Node n) {
			return n.getCoeffOfNode().subList(0, 1);
		}

		@Override
		public void setCoeff(Node n, List<Double> coeff, int index) {
			n.clearCoeff();
			n.addCoeff(coeff.get(index));
		}

		@Override
		public double evaluate(Node n, Vocab vocab) {
			return n.getCoeffOfNode().get(0);
		}

		@Override
		public boolean isConstant() {
			return true;
		}
	},
	
	/** The variable. */
	VARIABLE {

		@Override
		public List<Double> getCoeff(Node n) {
			return new LinkedList<Double>();
		}

		@Override
		public void setCoeff(Node n, List<Double> coeff, int index) {
			n.clearCoeff();
		}

		@Override
		public int numArg() {
			return 0;
		}

		@Override
		public boolean isConstant() {
			return false;
		}

		@Override
		public double evaluate(Node n, Vocab vocab) {
			return vocab.getVariableValue();
		}

		@Override
		public String print(Node n) {
			return "x";
		}
	},
	
	/** The add. */
	ADD {

		@Override
		public List<Double> getCoeff(Node n) {
			return new LinkedList<Double>();
		}

		@Override
		public void setCoeff(Node n, List<Double> coeff, int index) {
			n.clearCoeff();
		}

		@Override
		public int numArg() {
			return 2;
		}

		@Override
		public boolean isConstant() {
			return false;
		}


		@Override
		public double evaluate(Node n, Vocab vocab) {
			List<Node> children = n.getChildren();
			double left = children.get(0).evaluate(vocab);
			double right = children.get(1).evaluate(vocab);
			return (left + right);
		}

		@Override
		public String print(Node n) {
			List<Node> children = n.getChildren();
			String left = children.get(0).print();
			String right = children.get(1).print();
			return String.format("(%s + %s)", left, right);
		}
	},
	
	/** The sub. */
	SUB {


		@Override
		public List<Double> getCoeff(Node n) {
			return new LinkedList<Double>();
		}

		@Override
		public void setCoeff(Node n, List<Double> coeff, int index) {
			n.clearCoeff();
		}

		@Override
		public int numArg() {
			return 2;
		}

		@Override
		public boolean isConstant() {
			return false;
		}


		@Override
		public double evaluate(Node n, Vocab vocab) {
			List<Node> children = n.getChildren();
			double left = children.get(0).evaluate(vocab);
			double right = children.get(1).evaluate(vocab);
			return (left - right);
		}

		@Override
		public String print(Node n) {
			List<Node> children = n.getChildren();
			String left = children.get(0).print();
			String right = children.get(1).print();
			return String.format("(%s - %s)", left, right);
		}
	},
	
	/** The mul. */
	MUL {

		@Override
		public List<Double> getCoeff(Node n) {
			return new LinkedList<Double>();
		}

		@Override
		public void setCoeff(Node n, List<Double> coeff, int index) {
			n.clearCoeff();
		}

		@Override
		public int numArg() {
			return 2;
		}

		@Override
		public boolean isConstant() {
			return false;
		}

		@Override
		public double evaluate(Node n, Vocab vocab) {
			List<Node> children = n.getChildren();
			double left = children.get(0).evaluate(vocab);
			double right = children.get(1).evaluate(vocab);
			return (left * right);
		}

		@Override
		public String print(Node n) {
			List<Node> children = n.getChildren();
			String left = children.get(0).print();
			String right = children.get(1).print();
			return String.format("(%s * %s)", left, right);
		}
	},
	
	/** The div. */
	DIV {

		@Override
		public List<Double> getCoeff(Node n) {
			return new LinkedList<Double>();
		}

		@Override
		public void setCoeff(Node n, List<Double> coeff, int index) {
			n.clearCoeff();
		}

		@Override
		public int numArg() {
			return 2;
		}

		@Override
		public boolean isConstant() {
			return false;
		}

		
		@Override
		public double evaluate(Node n, Vocab vocab) {
			List<Node> children = n.getChildren();
			double left = children.get(0).evaluate(vocab);
			double right = children.get(1).evaluate(vocab);
			return (left / right);
		}

		@Override
		public String print(Node n) {
			List<Node> children = n.getChildren();
			String left = children.get(0).print();
			String right = children.get(1).print();
			return String.format("(%s / %s)", left, right);
		}
	},

}
