package Pacman;

public class Pacman implements Cloneable {
	
	public Pacman() {
		
	}

	public Object clone(){
		try{
			Pacman cloned = (Pacman) super.clone();
			
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
}
