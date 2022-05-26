package com.usermanagement.DAO;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.model.User;

@Transactional
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	/**
	 * @param email
	 * @param password
	 * @return
	 */
	User findByEmailAndPassword(String email, String password);

	/**
	 * @param userId
	 * @return
	 */
	User getUserByUserId(int userId);

	
	/**
	 * @param isAdmin
	 * @return
	 */
	List<User> findByIsAdmin(boolean isAdmin);
	
	
	/**
	 * @param email
	 * @return
	 */
	List<User> findByEmail(String email);

	/**
	 * @param email
	 * @param password
	 */
	@Modifying
	@Query("UPDATE User u SET u.password =:password WHERE u.email =:email")
	void updatePassword(@Param("email") String email, @Param("password") String password);

	/**
	 * @param userId
	 * @return
	 */
	@Query("SELECT addressId FROM Address WHERE  user.userId = :userId")
	List<Integer> getAddrId(@Param("userId") int userId);

	/**
	 * 
	 * @param addressId
	 * @param userId
	 */
	@Modifying
	@Query("DELETE FROM Address addr WHERE addr.addressId NOT IN (:addrId) AND addr.user =:user")
	void deleteAddressById(@Param("addrId") List<Integer> addressId, @Param("user") User userId);
	
	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u.isAdmin=0 AND DATE(u.updatedAt) BETWEEN :startDate AND :endDate")
	List<User> getCSV(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	
}