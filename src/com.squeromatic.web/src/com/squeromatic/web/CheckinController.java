package com.squeromatic.web;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.squeromatic.model.CheckinCommand;

@Controller
@RequestMapping("/checkin")
public class CheckinController {

	protected Log log = LogFactory.getLog(CheckinController.class);
	
	@Autowired
	private JmsTemplate checkinJms;
	
	@RequestMapping(method = POST)
	@ResponseStatus(CREATED)
	public void checkin(@RequestBody CheckinCommand command,
			HttpServletResponse response) {
		log.info("CheckinCommand: " + command);
		
		checkinJms.convertAndSend(command);
	}

	@RequestMapping(method = GET)
	@ResponseBody
	public CheckinCommand checkin() {
		CheckinCommand command = new CheckinCommand("GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041", "4dd5333cfa7645a53ca9ebd1");
		return command;
	}

}
