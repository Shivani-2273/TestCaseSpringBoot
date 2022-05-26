package com.usermanagement.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.usermanagement.DAO.UserDAO;
import com.usermanagement.model.Address;
import com.usermanagement.model.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserDAO userDao;

	@Test
	void saveUserTest() {
		User user = new User();
		user.setFirstName("Shivani");
		user.setLastName("Hemnani");
		user.setEmail("shiv@test.com");
		user.setPassword("123");
		user.setBirthDate("2022-05-11");
		user.setContactNo("7677857857");
		user.setGender("Female");
		user.setLanguages("Gujarati");
		user.setAdmin(false);
		Address address = new Address();

		List<Address> addressList = new ArrayList<Address>();

		address.setAddressId(1);
		address.setAddressLine("Solaa");
		address.setCity("Ahmedabad");
		address.setState("Gujarat");
		address.setUser(user);

		addressList.add(address);
		user.setAddress(addressList);

		final User entity = spy(User.class);
		when(userDao.save(any(User.class))).thenReturn(entity);
		
		// for register
		userService.saveUser(user);

		// for update
		user.setUserId(1);
		userService.saveUser(user);

		// for catch block
		when(userDao.save(any(User.class))).thenThrow(new RuntimeException());
		userService.saveUser(user);

		verify(userDao, atLeast(1)).save(any());

	}

	@Test
	void userLoginTest() {
		User user = new User();
		user.setEmail("shivani@test.com");
		user.setPassword("1111");

		final User entity = spy(User.class);
		when(userDao.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(entity);
		userService.userLogin(user);

		// for catch block
		when(userDao.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenThrow(new RuntimeException());
		userService.userLogin(user);

		verify(userDao, atLeast(1)).findByEmailAndPassword(any(), any());

	}

	@Test
	void getAllUserTest() {
		List<User> userList = new ArrayList<User>();
		when(userDao.findByIsAdmin(false)).thenReturn(userList);
		userService.getAllUser();

		// for catch
		when(userDao.findByIsAdmin(false)).thenThrow(new RuntimeException());
		userService.getAllUser();

		verify(userDao, atLeast(1)).findByIsAdmin(false);

	}

	@Test
	void deleteUserTest() {

		doNothing().when(userDao).deleteById(1);
		userService.deleteUser(1);

		// for catch
		doThrow(new RuntimeException()).when(userDao).deleteById(1);
		userService.deleteUser(1);
		verify(userDao, atLeast(1)).deleteById(1);

	}

	@Test
	void displayUser() {
		final User entity = spy(User.class);
		when(userDao.getUserByUserId(1)).thenReturn(entity);
		userService.displayUser(1);

		// for catch block
		when(userDao.getUserByUserId(1)).thenThrow(new RuntimeException());
		userService.displayUser(1);

		verify(userDao, atLeast(1)).getUserByUserId(1);
	}

	@Test
	void checkEmailTest() {

		List<User> emails = new ArrayList<User>();
		User user = spy(User.class);
		emails.add(user);
		when(userDao.findByEmail("shivani@test.com")).thenReturn(emails);
		userService.checkEmail("shivani@test.com");

		List<User> email = Collections.EMPTY_LIST;
		when(userDao.findByEmail("shivani@test.com")).thenReturn(email);
		userService.checkEmail("shivani@test.com");

		// for catch block
		doThrow(new RuntimeException()).when(userDao).findByEmail("shivani@test.com");
		userService.checkEmail("shivani@test.com");

		verify(userDao,atLeast(1)).findByEmail("shivani@test.com");
	}

	@Test
	void resetPasswordTest() {
		User user = new User();
		user.setEmail("shivani@test.com");
		user.setPassword("2222");

		doNothing().when(userDao).updatePassword(user.getEmail(), user.getPassword());
		userService.resetPassword(user);

		// for catch block
		doThrow(new RuntimeException()).when(userDao).updatePassword(user.getEmail(), user.getPassword());
		userService.resetPassword(user);

		verify(userDao, atLeast(1)).updatePassword(any(), any());
	}

	@Test
	void generateCSVTest() throws ParseException, FileNotFoundException {
		List<User> userList = new ArrayList<User>();
		User user = new User();
		user.setFirstName("Shivani");
		user.setLastName("Hemnani");
		user.setUpdatedAt(LocalDateTime.now());

		userList.add(user);

		Date startDate = Date.valueOf("2022-05-11");
		Date endDate = Date.valueOf("2022-05-13");

		when(userDao.getCSV(startDate, endDate)).thenReturn(userList);

		userService.generateCSV(startDate, endDate);

		// for catch
		doThrow(new RuntimeException()).when(userDao).getCSV(startDate, endDate);
		userService.generateCSV(startDate, endDate);

		verify(userDao, atLeast(1)).getCSV(startDate, endDate);

	}

}
