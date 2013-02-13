package NeuralNetwork.interfaces.nodes;

import NeuralNetwork.interfaces.NeuralNodeInterface;

/**
 * The NeuralBiasNode cannot be used to point to, but it can point to a
 * NeuralHiddenNode or a NeuralOutputNode. The NeuralBiasNode has a constant
 * value, but the value of the edges leaving the node can change. This way
 * the next layer will always have an input.
 * 
 * @author Eric de Kruijf
 * @version 0.1 
 */

public interface NeuralBiasNodeInterface extends NeuralNodeInterface{
	/**
	 * TODO
	 */
	
	/**
	 * UnsupportedOperationException linkFrom
	 */
}
