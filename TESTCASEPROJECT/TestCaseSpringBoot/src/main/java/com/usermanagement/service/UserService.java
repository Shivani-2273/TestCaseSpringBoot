package com.usermanagement.service;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;

import com.usermanagement.model.User;

public interface UserService {

	/**
	 * this method save & update user details
	 * @param user
	 */
	void saveUser(User user);
	
	/**
	 * this method helps user to login into system
	 * @param user
	 * @return
	 */
	User userLogin(User user);
	
	/**
	 * this method will return all user of system 
	 * @return
	 */
	List<User> getAllUser();
		
	/**
	 * this method will delete user details and address
	 * @param userId
	 */
	void deleteUser(int userId);
	
	/**
	 * this method will return object of particular user
	 * @param userId
	 * @return
	 */
	User displayUser(int userId);
			
	/**
	 * this method will check email exists or not
	 * @param email
	 * @return
	 */
	boolean checkEmail(String email);
	
	/**
	 * this method will update password of user
	 * @param user
	 */
	void resetPassword(User user);

	/**
	 * this method will generate csv file
	 * @param startDate
	 * @param endDate
	 * @throws FileNotFoundException
	 */
	void generateCSV(Date startDate, Date endDate) throws FileNotFoundException;
	
}
