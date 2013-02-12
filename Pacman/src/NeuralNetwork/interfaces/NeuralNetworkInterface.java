package NeuralNetwork.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The implementation of this interface will be used to create a neural network. The
 * neural network will be created based on the amount of nodes requested and can be
 * easily evaluated for results. It provides a mechanism for saving and loading neural
 * network data from the file system together with the weights of the edges. Also the
 * possibility of labeling input and output nodes exists. Meaning that these nodes can
 * be assigned a name for easy value assignment.
 * 
 * @author Eric de Kruijf
 * @version 0.1
 */

public interface NeuralNetworkInterface {
	/**
	 * @constructor
	 * 		The constructor will prepare the underlying layers of nodes and the edges
	 * 		between them. The empty constructor exists to use the file system methods.
	 * @params
	 * 		int inputcount
	 * 			Describes how many input handles the network needs to have
	 * 		int hiddencount
	 * 			Describes how many hidden nodes should be in the network. These will be
	 * 			all in one hidden layer.
	 * 		int outputcont
	 * 			Describes how many output values will be produced by the neural network.
	 *  	ArrayList<String> inputlabels
	 *  		This list will contain the labels for the input nodes, this parameter
	 *  		should not be mandatory
	 *  	ArrayList<String> outputlabels
	 *  		This list will contain the labels for the output nodes, this parameter
	 *  		should not be mandatory
	 */
	
	/**
	 * This will save the neural network to the file system. This includes the structure
	 * as well as the weight on the edges.
	 * @param filename
	 * 		The filename which should be used to store the neural network in. If it already
	 * 		exists, it will be overwritten.
	 */
	public void save(String filename);
	
	/**
	 * To load the neural network into the memory again, the following function is used.
	 * This will generate a fully functioning neural network, so the constructor can be
	 * called without parameters.
	 * @param filename
	 * 		The file which contains the neural network data.
	 * @throws IOException
	 * 		When the file is not found or wrongly formatted.
	 */
	public void load(String filename) throws IOException;

	/**
	 * Setting the input values for the neural network can be done without labeling
	 * using an ArrayList of Double values. All the inputs will be set to zero before
	 * the data from the list is written to them. When they are all inserted, the network
	 * will evaluate itself.
	 * @param values
	 * 		The ArrayList containing the values.
	 */
	public void set(ArrayList<Double> values);
	
	/**
	 * When the neural network has labeled its input nodes, it can be provided with
	 * a Map the double values with the appropriate labels as keys. All the inputs will
	 * be set to zero before the data from the map is written to them. When they are all
	 * inserted, the network will evaluate itself.
	 * @param values
	 * 		The Map of keys and values.
	 */
	public void set(Map<String, Double> values);
	
	/**
	 * When the neural network is not labeled, this method can be used to return the
	 * results from the network calculation.
	 * @return
	 * 		A list containing all the output values.
	 */
	public ArrayList<Double> get();

	/**
	 * When the neural network is labeled, this method will return the output labels
	 * with their respective output value in a map format.
	 * @return
	 * 		A map containing the labels as keys and the output values as values.
	 */
	public Map<String, Double> getLabeled();
	
	/**
	 * This function does the same as get without parameters, but it filters the values
	 * by the given threshold. Values greater than or equal to the threshold will be
	 * passed as one and others as zero.
	 * @param threshold
	 * 		The threshold to determine whether a value is big enough or not
	 */
	public ArrayList<Double> get(double threshold);
	 
	/**
	 * This function does the same as getLabeled without parameters, but it filters
	 * the values by the given threshold. Values greater than or equal to the threshold
	 * will be passed as one and others as zero.
	 * @param threshold
	 * 		The threshold to determine whether a value is big enough or not
	 */
	public Map<String, Double> getLabeled(double threshold);
}
