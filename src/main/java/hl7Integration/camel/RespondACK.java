package hl7Integration.camel;

import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v26.datatype.ID;
import ca.uhn.hl7v2.model.v26.segment.MSH;

@Component
public class RespondACK {

	public String process(Message in) throws Exception {
	       
		MSH aa = (MSH) in.get("MSH");
		System.out.println(aa.getMsh9_MessageType().getMsg2_TriggerEvent());
		return aa.getMsh9_MessageType().getMsg2_TriggerEvent().toString();
	}
}
