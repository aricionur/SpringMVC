package com.aricionur.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aricionur.springmvc.service.HelloServicePrototype;
import com.aricionur.springmvc.service.HelloServiceSingleton;

@RequestMapping("test")
@Controller
public class HelloTestController {
	@Autowired
	HelloServiceSingleton helloServiceSingleton;

	@Autowired
	HelloServicePrototype helloServicePrototype;

	@RequestMapping(method = RequestMethod.GET)
	public String doServiceTest(ModelMap model) {
		model.addAttribute("singletonServiceMessage", helloServiceSingleton.getMessage());
		model.addAttribute("prototypeServiceMessage", helloServicePrototype.getMessage());

		return "testService";
	}
}
