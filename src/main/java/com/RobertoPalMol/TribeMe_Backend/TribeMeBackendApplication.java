package com.RobertoPalMol.TribeMe_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TribeMeBackendApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(TribeMeBackendApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
