package NeuralNetwork.interfaces.nodes;

import NeuralNetwork.interfaces.NeuralNodeInterface;

/**
 * The NeuralInputNode can be assigned a value and a label. It will be connected to
 * nodes NeuralHiddenNodes or NeuralOutputNodes. This node itself does not hold the
 * weights, these are stored in the node which is pointed to.
 * 
 * @author Eric de Kruijf
 * @version 0.1 
 */
public interface NeuralInputNodeInterface extends NeuralNodeInterface {
	
	/**
	 * Input nodes will be assigned a value before evaluating the neural network.
	 * @params value
	 * 		This is the value the node will contain.
	 */
	public void set(double value);
	
	/**
	 * UnsupportedOperationException linkFrom
	 */

}
