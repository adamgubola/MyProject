package com.MyProject.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {

	private static final String ERROR_PATH="/error";
	
	private ErrorAttributes errorAttributes;
	
	@Autowired
	public void setErrorAttributes(ErrorAttributes errorAttributes) {
		this.errorAttributes=errorAttributes;
	}
	
	@RequestMapping(ERROR_PATH)
	public String Error(Model model, HttpServletRequest request) {
		ServletWebRequest rA = new ServletWebRequest(request);
		Map<String,Object>error= this.errorAttributes.getErrorAttributes(rA, ErrorAttributeOptions.defaults());
		
	    System.out.println("Error Attributes: " + error); // Logolás a nyomkövetéshez
		
		model.addAttribute("timestamp", error.get("timestamp"));
		model.addAttribute("error", error.get("error"));
		model.addAttribute("message", error.get("message"));
		model.addAttribute("path", error.get("path"));
		model.addAttribute("status", error.get("status"));

		return "detailedError";
	}
	
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
