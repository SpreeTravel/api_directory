package prueba;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenReader {

	public static String getToken(Object payload){
			JSONObject object;
			try {
				String string = org.apache.commons.io.IOUtils.toString((java.io.InputStream)payload);
				object = new org.json.JSONObject(string);
				return object.getString("access_token");
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "lala";
			}
			
	}
}
