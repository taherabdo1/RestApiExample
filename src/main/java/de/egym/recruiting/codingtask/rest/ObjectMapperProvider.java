package de.egym.recruiting.codingtask.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Singleton;

@Provider
@Singleton
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	static ObjectMapper newObjectMapper() {
		final ObjectMapper result = new ObjectMapper();
		result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		result.configure(SerializationFeature.INDENT_OUTPUT, true);
		result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return result;
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return newObjectMapper();
	}
}
