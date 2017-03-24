package com.almundo.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.control.Dispatcher;
import com.almundo.model.Call;

@RestController
public class WebCallController {
	int callCounter = 0;

	@Autowired
	Dispatcher dispatcher = new Dispatcher();

	@RequestMapping("/")
	public String process() {

		dispatcher.init();
		return "Open Lines waiting for connection";
	}

	@RequestMapping("/calls/{id}")
	public String makeCalls(@PathVariable("id") int calls) {
		String message = "";
		for (int i = 0; i < calls; i++) {
			message = message + addCall() + "<br />";
		}
		return message;
	}

	private String addCall() {
		Call call = new Call();
		callCounter++;
		call.setId(callCounter);
		call.setTiempo(randTime());
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