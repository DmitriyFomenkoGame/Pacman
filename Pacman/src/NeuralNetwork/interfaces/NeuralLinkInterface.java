package NeuralNetwork.interfaces;

import NeuralNetwork.NeuralNode;

/**
 * The NeuralLink class will represent a link between two nodes. It will store points
 * to both nodes and it will hold its own weight.
 * 
 * @author Eric de Kruijf
 */

public interface NeuralLinkInterface {
	/**
	 * The constructor will get two nodes and the weight which the link should be.
	 * Also just two nodes could be given, then the weight is initialized randomly.
	 */
	
	/**
	 * To get the weight from the link, the following method is used
	 * @return
	 * 		The links weight
	 */
	public double get();
	
	/**
	 * To set the weight from the link, the following method is used
	 * @param weight
	 * 		The links new weight
	 */
	public void set(double weight);
	
	/**
	 * To get the already calculated weight from the node at one side of the link
	 * through the link's weight to the other side of the link.
	 * @return
	 * 		The calculated value
	 */
	public double getCalculated();

	/**
	 * This method gives the possibility to get the side from the link which it is
	 * pointing from
	 * @return
	 * 		The node which is at the starting point of the link
	 */
	public NeuralNode getFrom();
	
	/**
	 * This method gives the possibility to get the side from the link which it is
	 * pointing at
	 * @return
	 * 		The node which the link is pointing at.
	 */
	public NeuralNode getTo();
}
