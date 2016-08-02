package de.egym.recruiting.codingtask.jpa.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.User;
import de.egym.recruiting.codingtask.rest.UserServiceImpl;
import exceptions.ExerciseConfilctException;

@Transactional
public class ExerciseDaoImpl extends AbstractBaseDao<Exercise> implements
		ExerciseDao {

	private static final Logger log = LoggerFactory
			.getLogger(UserServiceImpl.class);

	UserDao userDao;

	@Inject
	protected ExerciseDaoImpl(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider, Exercise.class);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Inject
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Exercise> getAllExercises(User user) {

		if (user.getId() == null) {
			return new ArrayList<Exercise>();
		}

		try {
			return (Collection<Exercise>) getEntityManager()
					.createQuery(
							"SELECT e FROM Exercise e WHERE e.user.id = :uId")
					.setParameter("uId", user.getId()).getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			return new ArrayList<Exercise>();
		}
	}

	@Override
	public Exercise insertExercise(Exercise exercise)
			throws ExerciseConfilctException {
		User user = userDao.findById(exercise.getUser().getId());

		// check if there is other exercises intersects with the supposed new
		// one

		
		// Calendar cal = Calendar.getInstance();
		// Collection<Exercise> exercisesTemp = getAllExercises(user);
		// for (Exercise exTemp : exercisesTemp) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		// Date endTime = new Date();
		// try {
		// endTime = sdf.parse(exTemp.getStart().toString());
		//
		// cal.setTime(endTime);
		// cal.add(Calendar.SECOND, (int) exTemp.getDuration());
		// // //
		// endTime = sdf.parse(cal.getTime().toString());
		// exercise.setStart(sdf.parse(exercise.getStart().toString()));
		// // /
		// log.debug("start from database is : " + exTemp.getStart());
		// log.debug("endTime is : " + sdf.parse(cal.getTime().toString()));
		// log.debug("new Exercise start date  is : " + exercise.getStart());
		//
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		// check the interval
		// if ((exTemp.getStart().before(exercise.getStart()) || exTemp
		// .getStart().equals(exercise.getStart()))
		// && (endTime.after(exercise.getStart()) || endTime
		// .equals(exercise.getStart()))) {
		// throw new ExerciseConfilctException();
		// }
		// }
		// if the user is not found in the database
		if (user == null)
			return null;

		exercise.setUser(user);
		exercise = create(exercise);
		return exercise;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Exercise> getAllExercisesFilteredByType(User user,
			Exercise.Type type) {
		if (user.getId() == null) {
			return new ArrayList<Exercise>();
		}

		try {
			return (Collection<Exercise>) getEntityManager()
					.createQuery(
							"SELECT e FROM Exercise e WHERE e.user.id = :uId and e.excerciseType = :type")
					.setParameter("uId", user.getId())
					.setParameter("type", type).getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			return new ArrayList<Exercise>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Exercise> getAllExercisesFilteredByDate(User user,
			Date startDate) {
		if (user.getId() == null) {
			return new ArrayList<Exercise>();
		}

		try {
			return (Collection<Exercise>) getEntityManager()
					.createQuery(
							"SELECT e FROM Exercise e WHERE e.user.id = :uId And e.start >= :startDate")
					.setParameter("uId", user.getId())
					.setParameter("startDate", startDate).getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			return new ArrayList<Exercise>();
		}
	}

	@Override
	public Exercise getExerciseByExerciseIDAndUserID(User user, Long exerciseID) {
		// if the IDs are not appilcable
		if (user.getId() < 0 || exerciseID < 0) {
			return null;
		}

		try {
			return (Exercise) getEntityManager()
					.createQuery(
							"SELECT e FROM Exercise e WHERE e.user.id = :uId and e.id = :eId")
					.setParameter("uId", user.getId())
					.setParameter("eId", exerciseID).getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}

	@Override
	public long getTheUserPoints(User user) {
		if (user.getId() == null) {
			return 0;
		}
		long result = -1;
		try {
			Object[] values = (Object[]) getEntityManager()
					.createQuery(
							"SELECT SUM(e.duration) as duration , SUM(e.calories) as calories FROM Exercise e WHERE e.user.id = :uId")
					.setParameter("uId", user.getId()).getSingleResult();
			result = (((Long) values[0]) / 60) + (Long) values[1];
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public long getTheUserPointsForWeeksAgo(User user, int weeksCount) {
		if (user.getId() == null) {
			return 0;
		}

		try {

			// get the date four weeks before
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -(7 * weeksCount)); // 4 weeks * 7 days
			Date startDate = calendar.getTime();

			Object[] values = (Object[]) getEntityManager()
					.createQuery(
							"SELECT SUM(e.duration) as duration , SUM(e.calories) as calories FROM Exercise e WHERE e.user.id = :uId And e.start >= :sDate")
					.setParameter("uId", user.getId())
					.setParameter("sDate",
							new java.sql.Date(startDate.getTime()))
					.getSingleResult();

			long result = (((Long) values[0]) / 60) + (Long) values[1];
			return result;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public long getCountOfExercisesForUserWithinLastWeekAndDurationMoreThan30Minutes(
			User user) {
		if (user.getId() == null || user.getId() < 0) {
			return 0;
		}

		try {

			// get the date four weeks before
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -7); // 4 weeks * 7 days
			Date startDate = calendar.getTime();

			Long result = (Long) getEntityManager()
					.createQuery(
							"SELECT COUNT(e) FROM Exercise e WHERE e.user.id = :uId And e.start >= :sDate And e.duration >= 30*60")
					.setParameter("uId", user.getId())
					.setParameter("sDate", startDate).getSingleResult();
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

}
