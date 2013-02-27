package Layers;

import java.util.ArrayList;

import Nodes.Node;

public class InputLayer {

	private ArrayList<Node> nodes;
	
	InputLayer(){
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(){
		nodes.add(new Node(true,false,false));
	}
	
	public Node getNode(int index){
		return nodes.get(index);
	}
	
}