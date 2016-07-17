package de.egym.recruiting.codingtask.rest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.egym.recruiting.codingtask.jpa.dao.UserDao;
import de.egym.recruiting.codingtask.jpa.domain.User;

@Singleton
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserDao userDao;

	@Inject
	UserServiceImpl(final UserDao userDao) {
		this.userDao = userDao;
	}


	@Nonnull
	@Override
	public List<User> indexUsers() {
		log.debug("-------------------******************Get all users.");
		return userDao.findAll();
	}

	@Nullable
	@Override
	public User getUserById(@Nonnull final Long userId) {
		log.debug("Get user by id.");
		final User user = userDao.findById(userId);
		if (user == null) {
			throw new NotFoundException("User with such id not found");
		}
		return user;
	}
	
	@Override
	public List<User> getUsersByLatName(String userLastName) {
		log.debug("Get users by last name.");
		final List<User> users = userDao.getUsersByLastName(userLastName);
		return users;

	}
	@Override
	public Response createNewUser(User userData) {

		final String EmailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EmailPattern);
		Matcher matcher = pattern.matcher(userData.getEmail());

		// check mandatory fields
		if (StringUtils.isEmpty(userData.getFirstName())
				|| StringUtils.isEmpty(userData.getLastName())
				|| StringUtils.isEmpty(userData.getEmail())
				|| userData.getBirthday() == null) {
			log.debug("client attempting to insert user with incomplete data.");
			String message = "all user data is required, please add (firstName, lastName, email, and birthdate)";
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		// check valid mail
		else if (!matcher.matches()) {
			log.debug("client attempting to insert user with invalide Email");
			String message = "please, insert a valid mail";
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		// check if the email is found before
		else if (userDao.findByEmail(userData.getEmail()) != null) {
			log.debug("client attempting to insert user with a pre-inserted Email");
			String message = "this email already has an account";
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		// check if the user data is sent containing the user ID
		else if (userData.getId() != null) {
			log.debug("client attempting to insert user with a ID");
			String message = "sorry, But you can't insert the user ID, It is system internal property";
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}

		// happy sceinario
		userData = userDao.create(userData);
		log.debug("new user crested");
		String returnContent = "user : " + userData.getFirstName()
				+ " created with Id : " + userData.getId();
		return Response.status(201).entity(returnContent).build();

	}
}
