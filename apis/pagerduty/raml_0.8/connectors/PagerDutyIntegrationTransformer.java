package prueba;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class PagerDutyIntegrationTransformer extends AbstractMessageTransformer {

	private static final String serviceKey = "{Your service key}";
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		String payload = "{\"event_type\": \"trigger\", \"service_key\": \"" + serviceKey + "\", \"description\": \"Incident triggered by Anypoint Studio connector test.\" }";
		message.setPayload(payload);
		return message;
	}

}
