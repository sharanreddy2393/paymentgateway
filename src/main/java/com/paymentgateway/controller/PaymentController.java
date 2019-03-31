package com.paymentgateway.controller;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.View;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import com.paymentgateway.model.Amount;

@Controller
public class PaymentController {
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public void makePayment(@RequestParam String amount, HttpServletResponse response) throws IOException {
		String payableMoney = amount;// amount.getAmount();
		/*
		 * Get a reference to the instamojo api
		 */
		ApiContext context = ApiContext.create("test_tqw1WjtqVYjFbYAk8eZibOGaT8WliZDLJV2",
				"test_mA6hzEUrsbf7BYsMwpzNCPIJr0AIZUSZCGSeADJ3gE3EInHV47UIyyp44RRdZO07qfxfZvs1ADv4NdBmr6pxUV1tN6PwqIZcpuqfE4O8mwVhOLsTg9lCakROoFz",
				ApiContext.Mode.TEST);
		Instamojo api = new InstamojoImpl(context);

		/*
		 * Create a new payment order
		 */
		PaymentOrder order = new PaymentOrder();
		order.setName("Sharan");
		order.setEmail("sharan@gmail.com");
		order.setPhone("9008420384");
		order.setCurrency("INR");
		order.setAmount(Double.parseDouble(payableMoney));
		order.setDescription("This is a test transaction.");
		order.setRedirectUrl("http://localhost:8083/success.html");
		order.setWebhookUrl("http://www.google.com/");
		order.setTransactionId("dxg2389");
		String url = "";
		PaymentOrderResponse paymentOrderResponse = null;
		try {
			paymentOrderResponse = api.createPaymentOrder(order);
			url = paymentOrderResponse.getPaymentOptions().getPaymentUrl();
			System.out.println(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
			System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());

		} catch (HTTPException e) {
			System.out.println(e.getStatusCode());
			System.out.println(e.getMessage());
			System.out.println(e.getJsonPayload());

		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
		response.sendRedirect(url);
		
		System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());
		
		
	}
}
