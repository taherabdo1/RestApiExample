package de.egym.recruiting.codingtask.rest;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.persist.Transactional;

import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import io.swagger.annotations.Api;

@Transactional
@Path("/api/v1/excercise")
@Api(value = "User Exercise Service")
public interface ExerciseService {
	/**
	 * This method index all exercises for a specific user from the database.
	 *
	 * @return a list of exercise objects.
	 */
	@GET
	@Path("/{userId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	List<Exercise> indexAllExcercisesForUser(@PathParam("userId")final Long id);

	/**
	 * This method index all exercises for a specific user filtered by type from the database.
	 *
	 * @return a list of exercise objects.
	 */
	@GET
	@Path("/filerByType/{userId}/{type}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	List<Exercise> indexAllExcercisesForUserFilteredByType(@PathParam("userId")final Long id ,@PathParam("type") Exercise.Type teype);

	/**
	 * This method index all exercises for a specific user starting for the given startDate from the database.
	 *
	 * @return a list of exercise objects.
	 */
	///{startDate}
	@GET
	@Path("/filerByDate/{userId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	List<Exercise> indexAllExcercisesForUserFilteredByDate(@PathParam("userId")final Long id ,@Nonnull @QueryParam("startDate")final String startDate);

	/**
	 * This method get a specific exercise for a specific user from the database.
	 *
	 * @return an object of exercise.
	 */
	@GET
	@Path("getExerciseUsingUserIdAndExerciseId/{userId}/{exerciseId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Exercise getExerciseUsingExerciseIDAndUserID(@PathParam("userId")final Long id ,@PathParam("exerciseId")final Long exerciseId);


	/**
	 * This method gets the point of a user.
	 *
	 * @return integer represents the points.
	 */
	@GET
	@Path("getUserPoints/{userId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_XML)
	Response getUserPoints(@PathParam("userId")final Long id);

	/**
	 * This method gets the point of a user.
	 *
	 * @return integer represents the points.
	 */
	@GET
	@Path("getUserPointsForLastFourWeeks/{userId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_XML)
	Response getUserPointsForLastFourWeeks(@PathParam("userId")final Long id);

	/**
	 * This method gets the point of a user.
	 *
	 * @return integer represents the points.
	 */
	@GET
	@Path("isUserHasTRAINING_ADDICTAchievmentBadge/{userId}")
	@Nonnull
	@Produces(MediaType.APPLICATION_XML)
	Response isUserHasTRAINING_ADDICTAchievmentBadge(@PathParam("userId")final Long id);



	/**
	 * This method create new Exercise for a specific user
	 *
	 * @param Exercise
	 *            
	 * @return a list<Exercise> object.
	 */
	@POST
	@Path("/createExercise")
	@Nullable
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	Response createNewExercise(Exercise exercise);
}
