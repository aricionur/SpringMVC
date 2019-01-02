package com.aricionur.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aricionur.springmvc.service.HelloServicePrototype;
import com.aricionur.springmvc.service.HelloServiceSingleton;

@Controller
@RequestMapping("/hello")
//@Scope("session")
public class HelloController {

	@Autowired
	HelloServiceSingleton helloServiceSingleton;
	
	@Autowired
	HelloServicePrototype helloServicePrototype;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doHello(ModelMap model) {

		model.addAttribute("message", "Spring MVC Hello World");
		return "hello";  //InternalResourceViewResolver matches to hello.jsp
	}
	
	@RequestMapping(value = "/services/{param1}", method = RequestMethod.GET)
	public String doHelloServices(ModelMap model, @PathVariable("param1") String param1) {
		helloServiceSingleton.setMessage(param1 + "singletonservice");
		helloServicePrototype.setMessage(param1 + "prototypeservice");
		
		model.addAttribute("message_1", helloServiceSingleton.getMessage());
		model.addAttribute("message_2", helloServicePrototype.getMessage());
		
		return "helloServices";  //InternalResourceViewResolver matches to helloServices.jsp
	}	
	
	@RequestMapping(value = "/serviceTest",method = RequestMethod.GET)
	public String doServiceTest(ModelMap model) {
		model.addAttribute("singletonServiceMessage", helloServiceSingleton.getMessage());
		model.addAttribute("prototypeServiceMessage", helloServicePrototype.getMessage());

		return "testService";
	}
	
	
}
