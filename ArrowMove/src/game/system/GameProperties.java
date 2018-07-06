package game.system;

import java.util.Properties;

public class GameProperties extends Properties {
	
	
	public int getPropertyInt(String key) {
		String pr = super.getProperty(key);
		if (pr == null) return -1;
		return Integer.parseInt(pr);
	}
	
	public int getPropertyInt(String key,int defaultValue) {
		String pr = super.getProperty(key);
		if (pr == null) return defaultValue;
		return Integer.parseInt(pr);
	}

}
