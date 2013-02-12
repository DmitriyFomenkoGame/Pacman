package NeuralNetwork.interfaces;


import java.util.ArrayList;

/**
 * The NeuralLayer implementation groups the NeuralInputLayer, NeuralHiddenLayer and
 * NeuralOutputLayer implementations.
 * 
 * @author Eric de Kruijf
 * @version 0.1
 */
 
//kan jij deze comment lezen in eclipse eric ?

public interface NeuralLayerInterface {
	/**
	 * TODO
	 */
	
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
	public void connectTo(NeuralLayerInterface layer, int method);
	
	/**
	 * This method will give insight in the NeuralNodes contained in the instance.
	 * These will be used mainly to connect layers together.
	 * @return
	 */
	public ArrayList<NeuralNodeInterface> getNodes();
}
