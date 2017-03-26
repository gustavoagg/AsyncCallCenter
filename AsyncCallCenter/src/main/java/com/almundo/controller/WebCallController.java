package com.almundo.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.bean.LineStatusBean;
import com.almundo.control.Dispatcher;
import com.almundo.model.Call;

@RestController
public class WebCallController {
	int callCounter = 0;

	@Autowired
	Dispatcher dispatcher = new Dispatcher();

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineStatusBean>> process() {

		dispatcher.init();

		return new ResponseEntity<List<LineStatusBean>>(dispatcher.getStatus(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/monitor/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineStatusBean>> monitor() {

		

		return new ResponseEntity<List<LineStatusBean>>(dispatcher.getStatus(), HttpStatus.OK);

	}

	@RequestMapping(value = "/calls/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineStatusBean>> makeCalls(@PathVariable("id") int calls) {
		String message = "";
		for (int i = 0; i < calls; i++) {
			message = message + addCall() + "<br />";
		}
		return new ResponseEntity<List<LineStatusBean>>(dispatcher.getStatus(), HttpStatus.OK);

	}

	private String addCall() {
		Call call = new Call();
		callCounter++;
		call.setId(callCounter);
		call.setTime(randTime());
		dispatcher.dispatchCall(call);
		return "Added call " + callCounter + " to hold calls";
	}

	private long randTime() {

		int min = 5;
		int max = 10;
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum * 1000;
	}
}