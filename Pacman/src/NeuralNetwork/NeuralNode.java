package NeuralNetwork;

import java.util.ArrayList;

import NeuralNetwork.interfaces.NeuralNodeInterface;

public class NeuralNode implements NeuralNodeInterface {
	private int node_type;
	
	public NeuralNode(int type) {
		node_type = type;
	}

	@Override
	public void set(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double get() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Double> getWeights() {
		// TODO Auto-generated method stub
		return null;
	}

}
