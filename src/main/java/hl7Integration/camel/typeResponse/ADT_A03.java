package hl7Integration.camel.typeResponse;

import org.springframework.stereotype.Component;
import ca.uhn.hl7v2.model.Message;

@Component
public class ADT_A03 {

	public void process(Message in) throws Exception {
		System.out.println("ADT_A03 Messsage");
	}

}
