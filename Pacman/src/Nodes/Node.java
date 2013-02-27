package Nodes;

import java.util.ArrayList;
import Link.Link;

public class Node {

	private double value;
	private boolean isInputNode;
	private boolean isHiddenNode;
	private boolean isOutputNode;
	private ArrayList<Link> incommingLinks;
	private ArrayList<Link> outgoingLinks;
	
	public Node(boolean isInput, boolean isHidden, boolean isOutput){
		value = 0;
		incommingLinks = new ArrayList<Link>();
		outgoingLinks = new ArrayList<Link>();
		if ((isInput && isHidden) || (isHidden && isOutput) || (isInput && isOutput)){
			throw new Error("A node can only be one kind of node at a time");
		}
		if (!isInput && !isHidden && !isOutput){
			throw new Error("A node has to have a type");
		}
		else{
			isInputNode = isInput;
			isHiddenNode = isHidden;
			isOutputNode = isOutput;
		}
	}
	
	public String getNodeType(){
		if (isInputNode){
			return ("input");
		}
		else if (isOutputNode){
			return ("output");
		}
		else if (isHiddenNode){
			return ("hidden");
		}
		else{
			throw new Error("This node has no type, which should not happen");
		}
	}
	
	public void setValue(double i){
		value = i;
	}

	public double getValue(){
		return value;
	}
	
	public void addInLink(Link l){
		if (isInputNode){
			throw new Error("An input node has no incomming links");
		}
		incommingLinks.add(l);
	}
	
	public boolean removeInLink(Link l){
		if (isInputNode){
			throw new Error("An input node has no incomming links");
		}
		boolean isRemoved = false;
		while(incommingLinks.indexOf(l) != -1){
			isRemoved = incommingLinks.remove(l);
		}
		return isRemoved;
	}
	
	public void addOutLink(Link l){
		if (isOutputNode){
			throw new Error("An output node has no incomming links");
		}
		outgoingLinks.add(l);
	}
	
	public boolean removeOutLink(Link l){
		if (isOutputNode){
			throw new Error("An output node has no incomming links");
		}
		boolean isRemoved = false;
		while(outgoingLinks.indexOf(l) != -1){
			isRemoved = outgoingLinks.remove(l);
		}
		return isRemoved;
	}
	
	public double calculate(){
		if (isInputNode){
			throw new Error("An input node has a set value and cannot be calculated");
		}
		double d = 0;
		for (int i = 0; i < incommingLinks.size(); i++){
			Link l = incommingLinks.get(i); 
			d += l.getWeight() * l.getInputNode().getValue();
		}
		setValue(d);
		return d;
	}
}