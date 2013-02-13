package NeuralNetwork.interfaces.nodes;

import NeuralNetwork.interfaces.NeuralNodeInterface;

/**
 * The NeuralOutputNode can be pointed to by a NeuralInputNode, a NeuralBiasNode or
 * a NeuralHiddenNode. When it is pointed to, it will store the weight of that
 * edge, which is used to calculate this nodes value on evaluation.
 * 
 * TODO: Sigmoid function in the calculate method for normalizing the value?
 * 
 * @author Eric de Kruijf
 * @version 0.1 
 */

public interface NeuralOutputNodeInterface extends NeuralNodeInterface {	

	/**
	 * This function will update its value by looking at the values of the pointing
	 * nodes and the weights on the edge,
	 */
	public void calculate();
	
	/**
	 * UnsupportedOperationException linkTo
	 */
}
