package hl7Integration.camel.routes.in;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.component.hl7.HL7DataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v26.segment.MSH;
import hl7Integration.camel.processors.FilterProcessor;

@Component
public class InboundRouteBuilder extends SpringRouteBuilder{
    private static final Logger log = LoggerFactory.getLogger(InboundRouteBuilder.class);
DataFormat hl7 = new HL7DataFormat();
    
    final Predicate A01 = header("bean:respondACK?method=process").isEqualTo("A01");
    final Predicate A03 = header("bean:respondACK?method=process").isEqualTo("A03");
    final Predicate R01 = header("bean:respondACK?method=process").isEqualTo("R01");
    
    @Override
    public void configure() throws Exception {

    		from("hl7listener").routeId("route_hl7listener").startupOrder(997).unmarshal().hl7(Boolean.FALSE).choice()
				.when(new Predicate() {
					public boolean matches(Exchange exchange) {
						try {
							if (msgType(exchange).equals("A01"))
								return true;
						} catch (HL7Exception e) {
							e.printStackTrace();
						}
						return false;
					}
				}).to("bean:ADT_A01?method=process").when(new Predicate() {
					public boolean matches(Exchange exchange) {
						try {
							if (msgType(exchange).equals("A03"))
								return true;
						} catch (HL7Exception e) {
							e.printStackTrace();
						}
						return false;
					}
				}).to("bean:ADT_A03?method=process").when(new Predicate() {
					public boolean matches(Exchange exchange) {
						try {
							if (msgType(exchange).equals("R01"))
								return true;
						} catch (HL7Exception e) {
							e.printStackTrace();
						}
						return false;
					}
				}).process(new FilterProcessor()).to("bean:ORU_R01?method=process").end();

	}

	public String msgType(Exchange exchange) throws HL7Exception {
		Message msg = exchange.getIn().getBody(Message.class);
		MSH aa;
		aa = (MSH) msg.get("MSH");
		return aa.getMsh9_MessageType().getMsg2_TriggerEvent().toString();
	}
}
