package de.egym.recruiting.codingtask.jpa.dao;

import java.util.List;

import javax.annotation.Nullable;

import de.egym.recruiting.codingtask.jpa.domain.User;

public interface UserDao extends BaseDao<User> {

	@Nullable
	User findByEmail(@Nullable String email);
	List<User> getUsersByLastName(@Nullable String lastName);
}
