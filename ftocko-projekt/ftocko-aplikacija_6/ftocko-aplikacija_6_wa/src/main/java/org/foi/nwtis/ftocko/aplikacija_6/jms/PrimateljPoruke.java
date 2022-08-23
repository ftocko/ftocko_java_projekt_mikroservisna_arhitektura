package org.foi.nwtis.ftocko.aplikacija_6.jms;

import org.foi.nwtis.ftocko.jms.SAerodrom;
import org.foi.nwtis.ftocko.jms.SMeteoPodaci;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

@MessageDriven(mappedName = "jms/NWTiS_ftocko", activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue") })
public class PrimateljPoruke implements MessageListener {
	
	@EJB
	RepozitorijJmsPoruka rjp;

	public PrimateljPoruke() {
	}

	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			try {
				ObjectMessage msg = (ObjectMessage) message;
				
				if(msg.getObject() instanceof SMeteoPodaci) {
					SMeteoPodaci sMeteoPodaci = (SMeteoPodaci) msg.getObject();
					StringBuilder sb = new StringBuilder();
					sb.append("Stigla poruka:").append(message.getJMSMessageID()).append(" ")
							.append(new java.util.Date(message.getJMSTimestamp()));
					System.out.println(sb.toString());
					rjp.poruke.add(sMeteoPodaci);
				}
				
				else {
					SAerodrom sAerodrom = (SAerodrom) msg.getObject();
					StringBuilder sb = new StringBuilder();
					sb.append("Stigla poruka:").append(message.getJMSMessageID()).append(" ")
							.append(new java.util.Date(message.getJMSTimestamp()));
					System.out.println(sb.toString());
					rjp.poruke.add(sAerodrom);
				}
				
			} catch (Exception e) {
				System.out.println("Problem kod JMS-a!");
			}

		}
	}
}
