package de.egym.recruiting.codingtask.rest;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;
import org.junit.Test;

import de.egym.recruiting.codingtask.AbstractRestIntegrationTest;

public class UserServiceRestIntegrationTest extends AbstractRestIntegrationTest {

	@Test
	public void testIndexWithoutFilter() {
		when().get("/api/v1/users").then().statusCode(HttpStatus.SC_OK)
				.body("lastName", hasItems("Bond", "Mueller"));
	}

	@Test
	public void testGetById() {
		when().get("/api/v1/users/1").then().statusCode(HttpStatus.SC_OK)
				.body("lastName", is("Mueller")).body("id", is(1));
	}

	@Test
	public void testGetByIdNotFound() {
		when().get("/api/v1/users/12345").then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void testgetUsersByLastName() {
		when().get("/api/v1/users/filtter/bond").then()
				.statusCode(HttpStatus.SC_OK);
	}

	//problem with sending post request using RestAssured, but the Rest web service is working well
	@Test
	public void testCreateUser() {
		String createExerciseJson = "{\"firstName\": \"taher\", \"lastName\": \"abdo\", \"email\": \"taher@yahoo.com\", \"birthday\": \"2016-07-15\"}";
		given().contentType("application/json").body(createExerciseJson)
				.when().post("/api/v1/users/creatUser").then()
				.statusCode(HttpStatus.SC_OK);
	}
	
}
