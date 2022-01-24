package com.poc.MappingPoc.controller;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonParser;
import com.poc.MappingPoc.service.Mapping;
import com.poc.MappingPoc.service.Validation;

@RestController
public class MappingController {
	@Autowired
	Validation valObj;
	@Autowired
	Mapping mapObj;

	@PostMapping("/validate")
	public ResponseEntity<String> doValidation(@RequestBody String msg) {
		String schemaString = null;
		BufferedReader reader = null;
		StringBuilder schStr=new StringBuilder();
		try {
			reader = new BufferedReader(
					new FileReader("C:\\Users\\abhi\\eclipse-workspace\\MappingPoc\\schemas\\schema.json"));
			while((schemaString=reader.readLine()) != null){
				schStr.append(schemaString);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		var valResult = valObj.validate(msg, JsonParser.parseString(schStr.toString()).getAsJsonObject());
		return new ResponseEntity<>(valResult.toString(), HttpStatus.OK);
	}

	@PostMapping("/map")
	public ResponseEntity<String> doMapping(@RequestBody String msg) {
		var mapResult = mapObj.doMap(msg);
		return new ResponseEntity<>(mapResult, HttpStatus.OK);
	}
}
