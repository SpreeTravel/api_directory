package prueba;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenReader2 {
	public static String getToken(Object payload) {
		JSONObject object;
		try {
			object = new org.json.JSONObject(org.apache.commons.io.IOUtils.toString((java.io.InputStream) payload));
			return ((JSONObject) object.get("access_token")).getString("token");
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "lala";
		}

	}
}
