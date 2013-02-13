package NeuralNetwork.interfaces;


import java.util.ArrayList;
import java.util.Map;

import NeuralNetwork.NeuralLayer;
import NeuralNetwork.NeuralNode;

/**
 * The NeuralLayer implementation will hold nodes and can hold node labels for the input and output nodes.
 * 
 * @author Eric de Kruijf
 */
 
public interface NeuralLayerInterface<N extends NeuralNode> {

	/**
	 * This method will connect the layer to another layer.
	 * @param layer
	 * 		The layer to connect to
	 * @param method
	 * 		The method which should be used to connect the nodes from both layers
	 * 		TODO: Which methods should be supported? Random/One-on-one/One-on-one-mixed/Full
	 */
	public void connectTo(NeuralLayer<NeuralNode> layer, int method);

	/**
	 * This method gives the names for the nodes in a list, if they have any.
	 * @param labels
	 * 		The labels if assigned, null if they do not have labels assigned.
	 */
	public void setLabels(ArrayList<String> labels);
	
	/**
	 * The set method from the NeuralNetwork implementation is forwarded to this
	 * method.
	 * @param values
	 * 		This list contains the values at which the nodes should be set.
	 */
	public void set(ArrayList<Double> values);

	/**
	 * This method will evaluate the full set of nodes contained in this layer.
	 */
	public void evaluate();
	
	/**
	 * This method will give insight in the nodes contained in the instance.
	 * These will be used mainly to connect layers together.
	 * @return
	 * 		The list of NeuralNodes
	 */
	public ArrayList<NeuralNode> getNodes();	
	
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