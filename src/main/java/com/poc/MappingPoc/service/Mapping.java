package com.poc.MappingPoc.service;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class Mapping {
	public String doMap(String msg) {
		JsonObject targetJson=JsonParser.parseString(msg).getAsJsonObject();
		return "";
	}
}
