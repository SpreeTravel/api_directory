package prueba;

import org.mule.module.http.internal.ParameterMap;

public class Bitly {
	
	private static String accessToken;

	public static String getToken(Object payload){
			ParameterMap map = (ParameterMap) payload;
			accessToken = map.get("access_token");				
			return accessToken;
	}
	
	public static String getToken(){
		return accessToken;
	}
}
