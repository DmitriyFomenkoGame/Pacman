package NeuralNetwork.interfaces;


import java.util.ArrayList;
import java.util.Map;

import NeuralNetwork.NeuralLayer;
import NeuralNetwork.NeuralNode;

/**
 * The NeuralLayer implementation groups the NeuralInputLayer, NeuralHiddenLayer and
 * NeuralOutputLayer implementations.
 * 
 * @author Eric de Kruijf
 * @version 0.1
 */
 
public interface NeuralLayerInterface<N extends NeuralNode> {
	/**
	 * TODO
	 */

	/**
	 * This method will evaluate the full set of NeuralHiddenNodes contained in this
	 * layer.
	 */
	public void evaluate();
	
	/**
	 * This method will connect the NeuralInputLayer to another layer. This other layer
	 * can be a NeuralHiddenLayer or a NeuralOutputLayer. Connecting it to another
	 * NeuralInputLayer will result in undetermined behavior.
	 * @param layer
	 * 		The layer to connect to
	 * @param method
	 * 		The method which should be used to connect the nodes from both layers
	 * 		TODO: Which methods should be supported? Random/One-on-one/One-on-one-mixed/Full
	 */
	public void connectTo(NeuralLayer<NeuralNode> layer, int method);
	
	/**
	 * This method will give insight in the NeuralNodes contained in the instance.
	 * These will be used mainly to connect layers together.
	 * @return
	 * 		The list of NeuralNodes
	 */
	public ArrayList<NeuralNode> getNodes();
	
	/**
	 * This method gives the names for the NeuralInputNodes in a list. Used to
	 * assign the values in the next method.
	 * @param labels
	 * 		The labels.
	 */
	public void setLabels(ArrayList<String> labels);
	
	/**
	 * The set method from the NeuralNetwork implementation is forwarded to this
	 * method.
	 * @param values
	 * 		This list contains the values which the InputNodes should have.
	 */
	public void set(ArrayList<Double> values);
	
	
	/**
	 * When the layer is not labeled, this method can be used to return the
	 * results from the network calculation.
	 * @return
	 * 		A list containing all the output values.
	 */
	public ArrayList<Double> get();

	/** 
	 * When the layer is labeled, this method will return the output labels
	 * with their respective output value in a map format.
	 * @return
	 * 		A map containing the labels as keys and the output values as values.
	 */
	public Map<String, Double> getLabeled();

}