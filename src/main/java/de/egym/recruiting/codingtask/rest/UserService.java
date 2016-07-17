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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.persist.Transactional;

import de.egym.recruiting.codingtask.jpa.domain.User;
import io.swagger.annotations.Api;

@Transactional
@Path("/api/v1/users")
@Api(value = "User Service")
public interface UserService {

	/**
	 * This method index users from the database.
	 *
	 * @return a list of user objects.
	 */
	@GET
	@Path("/")
	@Nonnull
	@Produces(MediaType.APPLICATION_JSON)
	List<User> indexUsers();

	/**
	 * This method gets a single user from the database based on the users's id.
	 *
	 * @param userId
	 *            the database user id. If a user with such id is not found in
	 *            the database then an HTTP 404 error is returned.
	 * @return a single user object.
	 */
	@GET
	@Path("/{userId}")
	@Nullable
	@Produces(MediaType.APPLICATION_JSON)
	User getUserById(@Nonnull @PathParam("userId") Long userId);

	/**
	 * This method gets aLL users from the database based on the users's
	 * lasName.
	 *
	 * @param lastName
	 *            the database user last_name. If there is no users with such a
	 *            last name an empty list is returned.
	 * @return a list<user> object.
	 */
	@GET
	@Path("filtter/{userLastName}")
	@Nullable
	@Produces(MediaType.APPLICATION_JSON)
	List<User> getUsersByLatName(@Nonnull @PathParam("userLastName") String userLastName);

	/**
	 * This method gets aLL users from the database based on the users's
	 * lasName.
	 *
	 * @param lastName
	 *            the database user last_name. If there is no users with such a
	 *            last name an empty list is returned.
	 * @return a list<user> object.
	 */
	@POST
	@Path("/creatUser")
	@Nullable
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	Response createNewUser(User userData);

}
