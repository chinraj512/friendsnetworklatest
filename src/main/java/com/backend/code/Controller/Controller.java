package com.backend.code.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	 @GetMapping("/")
	 String print()
	 {
		 return "Hello World";
	 }
}
