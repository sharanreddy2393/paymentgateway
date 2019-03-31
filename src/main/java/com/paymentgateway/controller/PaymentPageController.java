package com.paymentgateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class PaymentPageController {

	@GetMapping("/page")
	public String view() {
		System.out.println("am inside");
		
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("paymentpage");
		return "paymentpage.html";
		//return mv;
	}
}
