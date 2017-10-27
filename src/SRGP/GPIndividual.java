package SRGP;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GPIndividual.
 */
class GPIndividual implements Individual<GPIndividual> {

	/** The random. */
	private Random random = new Random();
	
	/** The b tree. */
	private Node bTree;
	
	/** The fitness function. */
	private Fitness<GPIndividual, Double> fitnessFunction;

	/**
	 * Instantiates a new GP individual.
	 *
	 * @param fitnessFunction the fitness function
	 * @param tree the tree
	 */
	public GPIndividual(Fitness<GPIndividual, Double> fitnessFunction, Node tree) {
		this.fitnessFunction = fitnessFunction;
		this.bTree = tree;
	}

	/* (non-Javadoc)
	 * @see SRGP.Individual#mutate()
	 */
	@Override
	public GPIndividual mutate() {
		GPIndividual result = new GPIndividual(this.fitnessFunction, this.bTree.clone());
		int num = this.random.nextInt(4);
		if (num == 0) result.replaceSubTree();
		if (num == 1) result.changeChild();
		if (num == 2) result.increaseDepth();
		if (num == 3) result.bTree = createTree(3);
		result.optimize(20);
		return result;
	}

	/**
	 * replace the whole tree with any subtree.
	 */
	private void replaceSubTree() {
		this.bTree = this.getRandomNode(this.bTree);
	}

	/**
	 * obtain a non-terminal operator from the vocabulary we built
	 * since all the non-terminal operator we used are binary
	 * so just add the original tree as one of the child of the 
	 * operator we choose, then randomly add another child.
	 */
	private void increaseDepth() {
		Operator o = new Vocab().getOneNonTerminalOperator();
		Node newRoot = new Node(o);
		newRoot.addChild(this.bTree);
		newRoot.addChild(createTree(0));
		newRoot.addCoeff(random.nextDouble() * 20 - 10);
		this.bTree = newRoot;
	}

	/**
	 * choose a random node, then always choose its first child, 
	 * and set this node as a new tree
	 */
	private void changeChild() {
		Node node = this.getRandomNode(this.bTree);
		if (!node.getChildren().isEmpty()) {
			node.getChildren().set(0, createTree(1));
		}
	}
	
	/* (non-Javadoc)
	 * @see SRGP.Individual#cross(SRGP.Individual)
	 */
	@Override
	public List<GPIndividual> cross(GPIndividual aIndividual) {
		ArrayList<GPIndividual> result = new ArrayList<GPIndividual>(2);
		GPIndividual aClone = new GPIndividual(this.fitnessFunction, this.bTree.clone());
		GPIndividual bClone = new GPIndividual(aIndividual.fitnessFunction, aIndividual.bTree.clone());
		this.swapNode(this.getRandomNode(aClone.bTree),aIndividual.getRandomNode(bClone.bTree).clone());
		this.swapNode(aIndividual.getRandomNode(bClone.bTree), this.getRandomNode(aClone.bTree).clone());		
		aClone.optimize(20);
		result.add(aClone);
		bClone.optimize(20);
		result.add(bClone);
		return result;
	}

	/**
	 * get a random node from all the nodes that are in a
	 * subtree with a root as Node tree.
	 *
	 * @param tree the tree
	 * @return a random node
	 */
	private Node getRandomNode(Node tree) {
		ArrayList<Node> allNodesOfTree = tree.getAllNodes();
		return allNodesOfTree.get(this.random.nextInt(allNodesOfTree.size()));
	}

	/**
	 * in a tree, change a node to a new node(to be a root of 
	 * of a new subtree) can simply implemented by change the 
	 * operator of this node, and set its children to the new 
	 * node's children.
	 *
	 * @param oldNode the old node
	 * @param newNode the new node
	 */
	private void swapNode(Node oldNode,Node newNode) {
		oldNode.setChildren(newNode.getChildren());
		oldNode.setOperator(newNode.getOperator());
		oldNode.setCoeffOfNode(newNode.getCoeffOfNode());
	}

	/**
	 * for simplify or optimize the tree, we first cut the tree to be a depth of 
	 * at most 5; then simply the tree by replacing all the subtree who is made up of 
	 * only constants with the evaluated value of the subtree;.
	 *
	 * @param iterations the iterations
	 */
	public void optimize(int iterations) {
		cutTree(this.bTree, 5);
		simplifyTree(this.bTree);
		List<Double> coeffOfTree = this.bTree.getCoeffOfTree();

		if (coeffOfTree.size() > 0) {
			CoeffIndividual initialIndividual = new CoeffIndividual(coeffOfTree);
			Population<CoeffIndividual> population = new Population<CoeffIndividual>();
			for (int i = 0; i < 3; i++) {
				population.addIndividual(initialIndividual.mutate());
			}
			population.addIndividual(initialIndividual);
			Fitness<CoeffIndividual, Double> fit = new CoeffFitness();
			Evolve<CoeffIndividual, Double> evolver = new Evolve<CoeffIndividual, Double>(population, fit);
			evolver.evolve(iterations);
			List<Double> optimizedCoeff = evolver.getFittest().getCoeff();
			this.bTree.setCoeffOfTree(optimizedCoeff);
		}
	}
	
	/**
	 * Gets the tree.
	 *
	 * @return the tree
	 */
	public Node getTree() {
		return this.bTree;
	}

	/**
	 * The Class CoeffIndividual.
	 */
	private class CoeffIndividual implements Individual<CoeffIndividual>, Cloneable {
		/** The coeff. */
		private List<Double> coeff;

		/**
		 * just create a individual where the coefficients is a tree.
		 *
		 * @param coeff the coeff
		 */
		public CoeffIndividual(List<Double> coeff) {
			this.coeff = coeff;
		}

		/**
		 * construct two Coefficient individuals which are coefficient of constants of a subtree
		 * make two backup of these two coeffIndividuals.
		 *
		 * @param bIndividual the b individual
		 * @return the list
		 */
		@Override
		public List<CoeffIndividual> cross(CoeffIndividual bIndividual) {
			List<CoeffIndividual> result = new ArrayList<CoeffIndividual>(2);

			CoeffIndividual aClone = this.clone();
			CoeffIndividual bClone =bIndividual.clone();

			for (int i = 0; i < aClone.coeff.size(); i++) {
				if ( Math.random() > 0.3) {
					aClone.coeff.set(i, bIndividual.coeff.get(i));
					bClone.coeff.set(i, this.coeff.get(i));
				}
			}
			result.add(aClone);
			result.add(bClone);

			return result;
		}

		/* (non-Javadoc)
		 * @see SRGP.Individual#mutate()
		 */
		@Override
		public CoeffIndividual mutate() {
			CoeffIndividual result = this.clone();
			for (int i = 0; i < result.coeff.size(); i++) {
				if (Math.random() > 0.5) {
					double coeff = result.coeff.get(i);
					coeff = coeff + Math.random() * 10 - 5;
					result.coeff.set(i, coeff);
				}
			}
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#clone()
		 */
		@Override
		protected CoeffIndividual clone() {
			List<Double> result = new ArrayList<Double>(this.coeff.size());
			for (double d : this.coeff) {
				result.add(d);
			}
			return new CoeffIndividual(result);
		}

		/**
		 * Gets the coeff.
		 *
		 * @return the coeff
		 */
		public List<Double> getCoeff() {
			return this.coeff;
		}

	}

	/**
	 * The Class CoeffFitness.
	 *
	 * @author wangsong
	 */
	private class CoeffFitness implements Fitness<CoeffIndividual, Double> {

		/* (non-Javadoc)
		 * @see SRGP.Fitness#calculate(SRGP.Individual)
		 */
		@Override
		public Double calculate(CoeffIndividual individual) {
			GPIndividual.this.bTree.setCoeffOfTree(individual.getCoeff());
			return GPIndividual.this.fitnessFunction.calculate(GPIndividual.this);
		}

	}
	
	/**
	 * Creates the tree.
	 *
	 * @param depth the depth
	 * @return the node
	 */
	public static Node createTree(int depth) {
		Vocab vocab = new Vocab();
		if (depth > 0) {
			Operator o;
			if (Math.random() >= 0.3) {
				o = vocab.getOneNonTerminalOperator();
			} else {
				o = vocab.getOneTerminalOperator();
			}
			Node n = new Node(o);
			if (o.numArg() > 0) {
				for (int i = 0; i < o.numArg(); i++) {
					Node child = createTree(depth - 1);
					n.addChild(child);
				}
			}
			if (o.isConstant()) {
				n.addCoeff(Math.random() * 30 - 15);
			}
			return n;
		} else {
			Operator o = vocab.getOneTerminalOperator();
			Node n = new Node(o);
			if (o.isConstant()) {
				n.addCoeff(Math.random() * 30 - 15);
			}
			return n;
		}
	}
	
	/**
	 * as the tree continue to grow, the tree gets very messy
	 * we create this method to simply the tree; usually if the
	 * subtree is completely made up with constant, just replace
	 * the subtree to a single constant node whose value is equal 
	 * to the subtree evaluated under the vocabulary.
	 *
	 * @author wangsong
	 * @param tree the root of the subtree to be simplified
	 */
	public static void simplifyTree(Node tree) {
		Vocab vocab = new Vocab();
		if (Node.hasVariableNode(tree)) {
			for (Node child : tree.getChildren()) {
				simplifyTree(child);
			}
		} else {
			double value = tree.evaluate(vocab);
			tree.addCoeff(value);
			tree.clear();
			tree.setOperator(vocab.getOperators().get(5));		
		}
	}
	
	/**
	 * given a tree node to cut the subtree rooted at this node to
	 * a subtree with depth of at most 5.
	 *
	 * @author wangsong
	 * @param tree the tree
	 * @param depth the depth
	 */
	public static void cutTree(Node tree,int depth) {
		Vocab vocab = new Vocab();
		if (depth > 0) {
			for (Node child : tree.getChildren()) {
				cutTree(child, depth - 1);
			}
		} else {
			tree.clear();
			tree.clearCoeff();
			Operator o = vocab.getOneTerminalOperator();
			tree.setOperator(o);
			if (o.isConstant()) {
				tree.addCoeff(Math.random() * 30 - 15);
			}
		}
	}
}
