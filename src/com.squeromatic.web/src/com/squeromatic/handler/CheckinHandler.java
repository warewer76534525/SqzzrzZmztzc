package com.squeromatic.handler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Service;

import com.squeromatic.model.CheckinCommand;
import com.squeromatic.service.FourSquareService;

@Service
public class CheckinHandler implements MessageListener {
	protected final static Log log = LogFactory.getLog(CheckinHandler.class);
	
	@Autowired
	FourSquareService _fourSquareService;
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage mapMessage = (ObjectMessage) message;
		
		try {
			CheckinCommand command = (CheckinCommand) mapMessage.getObject();
			_fourSquareService.checkinTo(command.getToken(), command.getVenueId());
			log.info("checkin success");
		} catch (JMSException e) {
			throw JmsUtils.convertJmsAccessException(e);
		}
	}

} 


