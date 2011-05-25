package com.squeromatic.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.squeromatic.model.NearestLocation;
import com.squeromatic.service.FourSquareService;

@Controller
public class NearestHistoryController {

	protected Log log = LogFactory.getLog(NearestHistoryController.class);
	
	@Autowired
	private FourSquareService _fourSquareService;
	
	@ResponseBody
	@RequestMapping("/nearestHistory/{token}/{lang}/{lat}")
	public List<NearestLocation> nearest(@PathVariable("token") String token, @PathVariable("lang") String lang, @PathVariable("lat") String lat) {
		String langlat = lang.replace(',', '.') + "," + lat.replace(',', '.');
		log.info("t : " + token);
		log.info("l : " + langlat);
		List<NearestLocation> locations = _fourSquareService.getNearestHistoricalLocation(token, langlat);
		
		return locations;
	}

}
