package NeuralNetwork.interfaces;

import NeuralNetwork.NeuralNode;

/**
 * The NeuralNode implementation groups the NeuralInputNode, NeuralHiddenNode,
 * NeuralOutputNode and NeuralBiasNode implementations.
 * 
 * @author Eric de Kruijf
 * @version 0.1
 */

public interface NeuralNodeInterface {
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
	 * This method will return the current value of the node in the network. It can be the
	 * case that the network isn't fully evaluated yet. This can result into inaccurate
	 * results.
	 */
	public double get();
}
