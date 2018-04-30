package com.h1b4.www.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HBExceptionHandler {
	
		@ExceptionHandler(Exception.class)
		public String errorHandler(Exception e){
			
			e.printStackTrace();
			
			return "/error";
		}
	

}