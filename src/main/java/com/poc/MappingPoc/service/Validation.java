package com.poc.MappingPoc.service;

import java.util.ArrayList;
import java.util.List;

import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

@Service
public class Validation {
	public JsonObject validate(String msg, JsonObject valSchema) {
		var targetJson = new JSONObject(msg);
		var schemaJson = new JSONObject(valSchema.toString());
		List<String> err = new ArrayList<>();
		var validationError = new JsonObject();
		try {
			var schemaValidator = SchemaLoader.load(schemaJson);
			schemaValidator.validate(targetJson);
		} catch (ValidationException ex) {
			err = ex.getAllMessages();
		}
		err.forEach(e -> {
			if (e.contains("required key")) {
				int splitterIndstrt = e.indexOf("[");
				int splitterIndend = e.indexOf("]");
				var errKey = e.substring(splitterIndstrt + 1, splitterIndend);
				var errVal = "Required Key not found";
				validationError.addProperty(errKey, errVal);

			} else {
				String[] splitter = e.split("#/");
				int splitterInd = splitter[1].indexOf(":");
				var errKey = splitter[1].substring(0, splitterInd);
				var errVal = splitter[1].substring(splitterInd + 1);
				validationError.addProperty(errKey, errVal);
			}
		});
		var validationResult=new JsonObject();
		if(validationError.entrySet().isEmpty()) {
			validationResult.addProperty("Status", "Validation Success");
		}
		else {
			validationResult.addProperty("Status", "Validation Failed");
			validationResult.add("Issue", validationError);
		}

		return validationResult;
	}
}
