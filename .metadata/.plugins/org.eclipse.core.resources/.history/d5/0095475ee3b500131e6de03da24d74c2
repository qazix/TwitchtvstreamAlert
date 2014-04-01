import java.io.*;
import java.text.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertiesService
{
	private static Properties mProp = new Properties();
	
	static
	{
		try
		{
			InputStream is = PropertiesService.class.getClassLoader().getResourceAsStream("properties.prop");
			mProp.load(is);
			System.out.println("Prop size: " + mProp.size());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public PropertiesService()
	{
	}
	
	public static String get(String pKey)
	{
		return mProp.getProperty(pKey);
	}
	
	/**
	 * Uses Jackson to convert a JSON string to an object
	 * @param pJSON STring in a JSON format
	 * @return Map of the same JSON object
	 * @throws Exception propagates the exceptions thrown by Jackson's functions
	 */
	public static Map<String, Object> JSONtoMap(String pJSON) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();  

	    @SuppressWarnings("unchecked")
		Map<String,Object> map = mapper.readValue(pJSON.getBytes(), Map.class); 
//	    System.out.println("Got " + map); 
	    
	    return map;
	}
}