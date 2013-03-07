package Anji;

import com.anji.util.Configurable;
import com.anji.util.Properties;

public class ActivatorData implements Configurable {

	@Override
	public void init(Properties properties) throws Exception {
		try{
			
		}catch(Exception e){
			throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString() + ": "
					+ e.getMessage() );
		}
	}

}
