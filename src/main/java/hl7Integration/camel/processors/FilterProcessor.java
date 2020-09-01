package hl7Integration.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.model.v26.segment.MSH;
import ca.uhn.hl7v2.model.v26.segment.OBR;
import ca.uhn.hl7v2.model.v26.segment.OBX;

public class FilterProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		System.out.println("AAAAA");
		Message msg = exchange.getIn().getBody(Message.class);
		
		ORU_R01 oruMessage = (ORU_R01) msg;
		
		MSH msh = oruMessage.getMSH();
		
		int indis =1;
		for (ORU_R01_PATIENT_RESULT response : oruMessage.getPATIENT_RESULTAll()) 
		{			
			for (ORU_R01_ORDER_OBSERVATION orderObservation : response.getORDER_OBSERVATIONAll())
			{			
				  OBR obr = orderObservation.getOBR();
				  String fillerOrderNumber = obr.getObr3_FillerOrderNumber().encode();
				  
				  for (ORU_R01_OBSERVATION observation : orderObservation.getOBSERVATIONAll()) {
					      OBX obx = observation.getOBX();
					      String type = obx.getObx3_ObservationIdentifier().getCwe2_Text().getValue();
					      String status = obx.getObservationResultStatus().getValue();
					      Varies[] variesArr = obx.getObservationValue();
					      if(variesArr != null && variesArr.length > 0)
					      {
					    	  Varies vari0 = variesArr[0];
					    	  Type typVarr = vari0.getData();
					    	  System.out.println(indis++ +" "+ type + " "+ typVarr.toString());
					      }
				  }
			}
		}
		
	}

}
