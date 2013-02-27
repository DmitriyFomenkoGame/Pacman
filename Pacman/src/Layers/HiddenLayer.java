package Layers;

import java.util.ArrayList;

import Nodes.Node;

public class HiddenLayer {

	private ArrayList<Node> nodes;
	
	HiddenLayer(){
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(){
		nodes.add(new Node(true,false,false));
	}
	
	public Node getNode(int index){
		return nodes.get(index);
	}
	
	public ArrayList<Double> calculate(){
		ArrayList<Double> values = new ArrayList<Double>();
		for (int i =0; i < nodes.size(); i++){
			values.add(nodes.get(i).calculate());
		}
		return values;
	}
	
}