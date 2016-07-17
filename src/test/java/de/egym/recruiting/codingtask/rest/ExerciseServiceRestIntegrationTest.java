package de.egym.recruiting.codingtask.rest;

//import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;

import org.apache.http.HttpStatus;
import org.junit.Test;

import de.egym.recruiting.codingtask.AbstractRestIntegrationTest;

public class ExerciseServiceRestIntegrationTest extends
		AbstractRestIntegrationTest {

	@Test
	public void testGetUserExercise() {
		when().get("/api/v1/excercise/2").then().statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void testGetUserExerciseFilteredByDate() {
		when().get(
				"/api/v1/excercise/filerByDate/3?startDate=1986-06-15:15:30:00")
				.then().statusCode(HttpStatus.SC_OK).body("id", hasItems(1));
	}

	@Test
	public void testCreateNewExercise() {
		String createExerciseJson = "{\"user\": {\"id\": 2},\"excerciseType\": \"RUNNING\",\"start\": \"1987-06-15:15:30:00\",\"duration\": 0,\"calories\": 0,\"distance\": 0}";
		given().contentType("application/json").body(createExerciseJson).when()
				.post("/api/v1/excercise/createExercise");
	}

	@Test
	public void testGetUserPoints() {
		when().get("/api/v1/excercise/getUserPoints/2").then()
				.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testIfTheUSerHasTRAINING_ADDICTAchievmentBadge() {
		when().get("/api/v1/excercise/isUserHasTRAINING_ADDICTAchievmentBadge/1").then()
				.statusCode(HttpStatus.SC_OK);
	}
	
}
