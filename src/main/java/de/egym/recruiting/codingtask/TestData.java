package de.egym.recruiting.codingtask;

import java.text.ParseException;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import de.egym.recruiting.codingtask.jpa.dao.ExerciseDao;
import de.egym.recruiting.codingtask.jpa.dao.UserDao;
import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.User;

@Transactional
public class TestData {

	private static final Logger log = LoggerFactory.getLogger(TestData.class);

	private final UserDao userDao;
	private final ExerciseDao exerciseDao;

	@Inject
	public TestData(final UserDao userDao, ExerciseDao dao) {
		this.userDao = userDao;
		this.exerciseDao = dao;

		insertTestData();
	}

	public void insertTestData() {
		// For convenience set the default time zone to UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		// Create the test user accounts
		insertTestUsers();
	}

	private void insertTestUsers() {
		log.debug("Inserting test users");
		if (userDao.findByEmail("heinz@egym.de") == null) {
			User testUser1 = new User();
			testUser1.setEmail("heinz@egym.de");
			testUser1.setFirstName("Heinz");
			testUser1.setLastName("Mueller");
			try {
				testUser1.setBirthday(DateUtils.parseDate("1983-02-01",
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				// ignoring
			}

			userDao.create(testUser1);
		}

		if (userDao.findByEmail("007@mi6.co.uk") == null) {
			User testUser2 = new User();
			testUser2.setEmail("007@mi6.co.uk");
			testUser2.setFirstName("James");
			testUser2.setLastName("Bond");
			try {
				testUser2.setBirthday(DateUtils.parseDate("1968-03-02",
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				// ignoring
			}

			userDao.create(testUser2);
		}

		if (userDao.findByEmail("taher@mi6.co.uk") == null) {
			User testUser3 = new User();
			testUser3.setEmail("taher@mi6.co.uk");
			testUser3.setFirstName("James");
			testUser3.setLastName("BondTaher");
			try {
				testUser3.setBirthday(DateUtils.parseDate("1968-03-02",
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				// ignoring
			}
			userDao.create(testUser3);
			Exercise ex1 = new Exercise();
			ex1.setCalories(150);
			ex1.setDistance(12D);
			ex1.setDuration(123L);
			ex1.setExcerciseType(Exercise.Type.FITNESS_COURSE);
			 ex1.setUser(testUser3);
			try {
				ex1.setStart(DateUtils.parseDate("1987-06-15:15:30:00", "yyyy-MM-dd:hh:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			exerciseDao.create(ex1);
		}
		
	}

}
