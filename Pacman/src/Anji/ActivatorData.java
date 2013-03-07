package Anji;

import com.anji.util.Configurable;
import com.anji.util.Properties;

public class ActivatorData implements Configurable {
	
	public ActivatorData(){
		//Moet van de Interface. Alle initializatie however moet in init() gebeuren.
	}

	@Override
	public void init(Properties properties) throws Exception {
		try{
			
		}catch(Exception e){
			throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString() + ": "
					+ e.getMessage() );
		}
	}

}
