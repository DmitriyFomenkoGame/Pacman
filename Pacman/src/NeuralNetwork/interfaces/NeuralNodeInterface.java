package NeuralNetwork.interfaces;

import java.util.ArrayList;

import NeuralNetwork.NeuralNode;

/**
 * The NeuralNode class will mimick all the types of nodes in the network. They are
 * distinguishable by an identifier.
 * 
 * @author Eric de Kruijf
 */

public interface NeuralNodeInterface {
	/**
	 * Identifiers:
	 */
	public final static int NODE_INPUT = 0,
							NODE_OUTPUT = 1,
						    NODE_HIDDEN = 2,
						    NODE_BIAS = 3;
	
	/**
	 * @constructor
	 * 		The constructor will prepare some datastructures for storing connections from
	 * 		nodes and bookkeeping of all the weights on the incoming edges.
	 */
	
	/**
	 * This links the input node to another node, which will remember the assigned weight.
	 * If the nodes are already linked, the weight will be overwritten
	 * @param node
	 * 		The node where this node is pointing to
	 * @param weight
	 * 		The weight which that edge should hold
	 */
	public void linkTo(NeuralNode node, double weight);
	
	/**
	 * When another node points to this one, this function is used to store the edge
	 * information in this node. This node therefore needs to remember the assigned
	 * weight, opposed to the linkTo method.
	 * @param node
	 * 		The node from which the edge is coming.
	 * @param weight
	 * 		The weight of the edge.
	 */
	public void linkFrom(NeuralNode node, double weight);
	
	/**
	 * Input nodes will be assigned a value before evaluating the neural network.
	 * @params value
	 * 		This is the value the node will contain.
	 */
	public void set(double value);

	/**
	 * This function will update its value by looking at the values of the pointing
	 * nodes and the weights on the edge.
	 */
	public void calculate();
	
	/**
	 * This method will return the current value of the node in the network. It can be the
	 * case that the network isn't fully evaluated yet. This can result into inaccurate
	 * results.
	 */
	public double get();
	
	/**
	 * To save the neural network to a file, the links with their respective weight need
	 * to be retrieved from the node.
	 * @return
	 * 		A list of doubles which represent the weights.
	 */
	public ArrayList<Double> getWeights();
}
