package de.egym.recruiting.codingtask.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import de.egym.recruiting.codingtask.jpa.domain.User;

/**
 * For JPA query reference see:
 * https://docs.oracle.com/html/E13946_03/ejb3_langref.html
 */
@Transactional
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

	@Inject
	public UserDaoImpl(final Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider, User.class);
	}

	@Nullable
	@Override
	public User findByEmail(@Nullable String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}

		email = email.toLowerCase();

		try {
			return (User) getEntityManager()
					.createQuery("SELECT u FROM User u WHERE LOWER(u.email) = :email")
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}
	
	
	@Override
	public List<User> getUsersByLastName(String lastName) {
		if (StringUtils.isEmpty(lastName)) {
			return (List<User>) findAll();
		} else {
			lastName = lastName.toLowerCase();
			try{
			return (List<User>) getEntityManager()
					.createQuery(
							"FROM User u WHERE LOWER(u.lastName) like :lastName ")
					.setParameter("lastName", "%" + lastName + "%").getResultList();
			}catch (NoResultException e){
				return new ArrayList<User>();
			}
		}
	}
}
