package com.iktpreobuka.eDnevnik.services;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class FileHandlerImpl implements FileHandler {

	@Override
	public File getLogs() {
		
		String path = "C:\\Users\\Stefan\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\eDnevnik\\logs\\spring-boot-logging.log";
		
		File log = new File(path);
		
		return log;
	}

}
