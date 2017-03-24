package com.almundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping("/call/")
	public String addCall() {
		Call call = new Call();
		callCounter++;
		call.setId(callCounter);
		call.setTiempo(5000);
		dispatcher.dispatchCall(call);
		return "Added call "+callCounter+" to hold calls";
	}
}