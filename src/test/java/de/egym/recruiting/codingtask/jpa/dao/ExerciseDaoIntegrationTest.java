package de.egym.recruiting.codingtask.jpa.dao;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import de.egym.recruiting.codingtask.AbstractIntegrationTest;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.User;
import de.egym.recruiting.codingtask.rest.UserServiceImpl;

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
	
	
}
