package de.egym.recruiting.codingtask.jpa.dao;

import java.util.Collection;
import java.util.Date;

import de.egym.recruiting.codingtask.jpa.domain.Exercise;
import de.egym.recruiting.codingtask.jpa.domain.User;
import exceptions.ExerciseConfilctException;

public interface ExerciseDao extends BaseDao<Exercise> {

	Collection<Exercise> getAllExercises(User user);
	Exercise insertExercise(Exercise exercise)  throws ExerciseConfilctException;
	Collection<Exercise> getAllExercisesFilteredByType(User user , Exercise.Type type);
	Collection<Exercise> getAllExercisesFilteredByDate(User user , Date startDate);
	Exercise getExerciseByExerciseIDAndUserID(User user , Long exerciseID);
	long getTheUserPoints(User user);
	long getTheUserPointsForWeeksAgo(User user , int weeksCount);
	long getCountOfExercisesForUserWithinLastWeekAndDurationMoreThan30Minutes(User user);
}
