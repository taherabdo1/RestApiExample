package de.egym.recruiting.codingtask.jpa.dao;

import java.text.ParseException;
import java.util.Collection;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import de.egym.recruiting.codingtask.AbstractIntegrationTest;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.User;
import de.egym.recruiting.codingtask.rest.UserServiceImpl;
import exceptions.ExerciseConfilctException;

public class ExerciseDaoIntegrationTest extends AbstractIntegrationTest {

	@Inject
	private ExerciseDao exerciseDao;

	private static final Logger log = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Test
	public void testFindExcerciseUsingUserIDExerciseID() throws Exception {
		log.debug("test Find Excercise Using UserID ExerciseID: ");
		User user = new User();
		user.setId(3L);
		final Exercise exercise = exerciseDao.getExerciseByExerciseIDAndUserID(
				user, 1L);
		Assert.assertNotNull(exercise);
	}

	// test exercise insertion in the database
	@Test
	public void testCreateNewExercise() throws Exception {
		log.debug("test insertion of new Exercise in the DataBase: ");
		User user = new User();
		user.setId(3L);
		Exercise exercise = new Exercise();
		exercise.setDistance(120);
		exercise.setCalories(300);
		exercise.setDuration(30 * 60);
		exercise.setExcerciseType(Exercise.Type.ROWING);
		exercise.setUser(user);
		exercise.setStart(DateUtils.parseDate("2016-07-15:15:30:00",
				"yyyy-MM-dd:hh:mm:ss"));
		try {
			exerciseDao.insertExercise(exercise);
		} catch (ExerciseConfilctException e) {
			log.error(e.getMessage());
		}
		final Collection<Exercise> exercisesAfterInsertion = exerciseDao
				.getAllExercises(user);
		Assert.assertTrue(exercisesAfterInsertion.contains(exercise));
	}

	// test exercise filttered by type happy scienatio
	@Test
	public void testFiltterExercisesForUserByType() throws Exception {
		log.debug("test filtter by Type");
		User user = new User();
		user.setId(3L);
		Exercise exercise = new Exercise();
		exercise.setDistance(120);
		exercise.setCalories(300);
		exercise.setDuration(30 * 60);
		exercise.setExcerciseType(Exercise.Type.ROWING);
		exercise.setUser(user);
		exercise.setStart(DateUtils.parseDate("2016-07-15:15:30:00",
				"yyyy-MM-dd:hh:mm:ss"));
		try {
			exerciseDao.insertExercise(exercise);
		} catch (ExerciseConfilctException e) {
			log.error(e.getMessage());
		}
		final Collection<Exercise> typeFiltteredExercises = exerciseDao
				.getAllExercisesFilteredByType(user, Exercise.Type.ROWING);
		Assert.assertTrue(typeFiltteredExercises.contains(exercise));
	}

	// test exercises filtered by type if the new exercise is not of this type
	@Test
	public void testFiltterExercisesForUserByTypeNewExerciseFilteredOut()
			throws Exception {
		log.debug("test filtter by Type if the type doesn't: ");
		User user = new User();
		user.setId(3L);
		Exercise exercise = new Exercise();
		exercise.setDistance(120);
		exercise.setCalories(300);
		exercise.setDuration(30 * 60);
		exercise.setExcerciseType(Exercise.Type.ROWING);
		exercise.setUser(user);
		exercise.setStart(DateUtils.parseDate("2016-07-15:15:30:00",
				"yyyy-MM-dd:hh:mm:ss"));
		try {
			exerciseDao.insertExercise(exercise);
		} catch (ExerciseConfilctException e) {
			log.error(e.getMessage());
		}
		final Collection<Exercise> typeFiltteredExercises = exerciseDao
				.getAllExercisesFilteredByType(user, Exercise.Type.CYCLING);
		Assert.assertTrue(!typeFiltteredExercises.contains(exercise));
	}

	// test exercises filtered by Date
	@Test
	public void testFiltterExercisesForUserByDate() throws Exception {
		log.debug("test filtter by Date: ");
		User user = new User();
		user.setId(3L);
		Exercise exercise = new Exercise();
		exercise.setDistance(120);
		exercise.setCalories(300);
		exercise.setDuration(30 * 60);
		exercise.setExcerciseType(Exercise.Type.ROWING);
		exercise.setUser(user);
		exercise.setStart(DateUtils.parseDate("2016-07-15:15:30:00",
				"yyyy-MM-dd:hh:mm:ss"));
		try {
			exerciseDao.insertExercise(exercise);
		} catch (ExerciseConfilctException e) {
			log.error(e.getMessage());
		}
		final Collection<Exercise> dateFiltteredExercises = exerciseDao
				.getAllExercisesFilteredByDate(user, DateUtils.parseDate(
						"2016-07-14:15:30:00", "yyyy-MM-dd:hh:mm:ss"));
		Assert.assertTrue(dateFiltteredExercises.contains(exercise));
	}

	// test exercises filtered by Date , check that the exercise that begin
	// before the date will not be selected
	@Test
	public void testFiltterExercisesForUserByDateNotSelected() throws Exception {
		log.debug("test filtter by Date Exercise not selected: ");
		User user = new User();
		user.setId(3L);
		Exercise exercise = new Exercise();
		exercise.setDistance(120);
		exercise.setCalories(300);
		exercise.setDuration(30 * 60);
		exercise.setExcerciseType(Exercise.Type.ROWING);
		exercise.setUser(user);
		exercise.setStart(DateUtils.parseDate("2016-07-14:15:30:00",
				"yyyy-MM-dd:hh:mm:ss"));
		try {
			exerciseDao.insertExercise(exercise);
		} catch (ExerciseConfilctException e) {
			log.error(e.getMessage());
		}
		final Collection<Exercise> dateFiltteredExercises = exerciseDao
				.getAllExercisesFilteredByDate(user, DateUtils.parseDate(
						"2016-07-15:15:30:00", "yyyy-MM-dd:hh:mm:ss"));
		Assert.assertTrue(!dateFiltteredExercises.contains(exercise));
	}

	@Test
	public void testGetExerciseByExerciseIDAndUserID() {
		log.debug("test filtter by Date Exercise not selected: ");
		User user = new User();
		user.setId(3L);
		Exercise exercise = new Exercise();
		exercise.setDistance(120);
		exercise.setCalories(300);
		exercise.setDuration(30 * 60);
		exercise.setExcerciseType(Exercise.Type.SPORTS);
		exercise.setUser(user);
		try {
			exercise.setStart(DateUtils.parseDate("2016-07-14:15:30:00",
					"yyyy-MM-dd:hh:mm:ss"));
			exerciseDao.insertExercise(exercise);
		} catch (ExerciseConfilctException e) {
			log.error(e.getMessage());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final Exercise exerciseFromMethodCall = exerciseDao.getExerciseByExerciseIDAndUserID(user, exercise.getId());
		Assert.assertEquals(exercise , exerciseFromMethodCall);
	}
	//test exerciseDao.getTheUserPoints
	@Test
	public void testGetTheUserPoints(){
		log.debug("test filtter by Date Exercise not selected: ");
		User user = new User();
		user.setId(3L);
		//get the exercises of this user
		Collection<Exercise> userExercises = exerciseDao.getAllExercises(user);
		long userPoints = 0L;
		for(Exercise e : userExercises){
			userPoints += ((e.getDuration()/60) + e.getCalories());
		}
		Assert.assertEquals(userPoints, exerciseDao.getTheUserPoints(user));
		
	}
}
	