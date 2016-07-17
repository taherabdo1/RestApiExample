package de.egym.recruiting.codingtask.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

import de.egym.recruiting.codingtask.jpa.dao.ExerciseDao;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.Exercise.Type;
import de.egym.recruiting.codingtask.jpa.domain.User;
import exceptions.ExerciseConfilctException;

@Singleton
public class ExerciseServiceImpl implements ExerciseService {

	private static final Logger log = LoggerFactory
			.getLogger(UserServiceImpl.class);

	private ExerciseDao exerciseDao;

	@Inject
	public ExerciseServiceImpl(ExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

	@Override
	public List<Exercise> indexAllExcercisesForUser(final Long id) {
		User user = new User();
		user.setId(id);
		log.debug("Get all exercises for user.");
		return (List<Exercise>) exerciseDao.getAllExercises(user);
	}

	@Override
	public Response createNewExercise(Exercise exercise) {
		String returnContent = "";
		log.debug("create new Excercise.");
		if (exercise.getCalories() < 0 || exercise.getDistance() < 0.0
				|| exercise.getDuration() < 0
				|| exercise.getExcerciseType() == null
				|| exercise.getStart() == null
				|| exercise.getUser().getId() == null
				|| exercise.getUser().getId() < 0) {
			returnContent = "please check the data you sent, there is somethiing wrong, please notice that all the attributes are required except distance";
			return Response.status(Status.BAD_REQUEST).entity(returnContent)
					.build();
		}
		try{
			exercise = exerciseDao.insertExercise(exercise);
			if (exercise == null) {
				returnContent = "this user is not found in the system, please check the user Id";
				return Response.status(Status.NOT_FOUND).entity(returnContent)
						.build();

			}
			returnContent = "new execise created with Id : " + exercise.getId();
			return Response.status(Status.CREATED).entity(returnContent).build();			
		}catch(ExerciseConfilctException e){
			return Response.status(Status.CONFLICT).entity("this Exercise can't be created, because this is another exercise for this user conflicts")
					.build();
		}

	}

	@Override
	public List<Exercise> indexAllExcercisesForUserFilteredByType(Long id,
			Type type) {
		User user = new User();
		user.setId(id);
		log.debug("Get all exercises for user filtered by Type.");
		return (List<Exercise>) exerciseDao.getAllExercisesFilteredByType(user,
				type);

	}

	@Override
	public Exercise getExerciseUsingExerciseIDAndUserID(Long id, Long exerciseId) {
		User user_ = new User();
		user_.setId(id);
		log.debug("Get exercise for user using user ID and exercise ID.");
		return exerciseDao.getExerciseByExerciseIDAndUserID(user_, exerciseId);

	}

	@Override
	public Response getUserPointsForLastFourWeeks(Long id) {
		User user = new User();
		user.setId(id);
		String returnContent = "";

		log.debug("Get User Points.");
		log.debug("total points is : "
				+ exerciseDao.getTheUserPointsForWeeksAgo(user, 4));
		returnContent = exerciseDao.getTheUserPointsForWeeksAgo(user, 4) + "";
		return Response.status(Status.OK).entity(returnContent).build();
	}

	@Override
	public Response getUserPoints(Long id) {
		User user = new User();
		user.setId(id);
		String returnContent = "";
		log.debug("Get User Points.");
		log.debug("total points is : " + exerciseDao.getTheUserPoints(user));
		returnContent = exerciseDao.getTheUserPoints(user) + "";
		return Response.status(Status.OK).entity(returnContent).build();
	}

	@Override
	public List<Exercise> indexAllExcercisesForUserFilteredByDate(Long id,

	String startDate) {

		// if the properties are not well supported
		if (id < 0 || StringUtils.isEmpty(startDate)) {
			return new ArrayList();
		}

		User user = new User();
		user.setId(id);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");

		// date handling
		Date sDate = null;
		try {
			sDate = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		log.debug("Get all exercises for user filtered by Type."
				+ sDate.getTime());
		return (List<Exercise>) exerciseDao.getAllExercisesFilteredByDate(user,
				sDate);

	}

	@Override
	public Response isUserHasTRAINING_ADDICTAchievmentBadge(Long id) {
		User user = new User();
		user.setId(id);

		log.debug("is User has TRAINING_ADDICT Badge");
		long daysCount = exerciseDao
				.getCountOfExercisesForUserWithinLastWeekAndDurationMoreThan30Minutes(user);
		log.debug("total points is : " + daysCount);
		if (daysCount >= 4)
			return Response.status(Status.OK)
					.entity("the user has TRAINING_ADDICT Bagde").build();
		else
			return Response.status(Status.OK)
					.entity("the user doesn't has TRAINING_ADDICT Bagde")
					.build();

	}

}
