package NeuralNetwork.interfaces;

import java.util.ArrayList;

/**
 * The NeuralNode class will mimic all the types of nodes in the network. They are
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
