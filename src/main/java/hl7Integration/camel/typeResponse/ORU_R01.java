package hl7Integration.camel.typeResponse;

import org.springframework.stereotype.Component;
import ca.uhn.hl7v2.model.Message;

@Component
public class ORU_R01 {

	public void process(Message in) throws Exception {
		System.out.println("ORU_R01 Messsage");
	}

}
