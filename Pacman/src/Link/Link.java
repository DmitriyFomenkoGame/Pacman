package Link;

import Nodes.Node;

public class Link {

	private Node inputNode;
	private Node outputNode;
	private double weight;
	
	public Link (Node input, Node output){
		this(input,output,0);

	}
	
	public Link (Node input, Node output, double w){
		weight = w;
		inputNode = input;
		outputNode = output;
		inputNode.addOutLink(this);
		outputNode.addInLink(this);
	}

	public Node getInputNode(){
		return inputNode;
	}
	
	public double getInputNodeValue(){
		return inputNode.getValue();
	}
	
	public Node getOutputNode(){
		return outputNode;
	}
	
	public double getOutputNodeValue(){
		return outputNode.getValue();
	}
	
	public double getWeight(){
		return weight;
	}
	
	public void setWeight(double w){
		weight = w;
	}
	
}