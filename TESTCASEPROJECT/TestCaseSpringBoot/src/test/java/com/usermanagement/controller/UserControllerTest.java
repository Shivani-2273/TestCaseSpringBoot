package com.usermanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.model.Address;
import com.usermanagement.model.User;
import com.usermanagement.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void indexTest() throws Exception {
		mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void forgotPassTest() throws Exception {
		mockMvc.perform(get("/ForgotPassword")).andExpect(status().isOk()).andExpect(view().name("forgotPassword"));
	}

	@Test
	public void adminDashboardTest() throws Exception {
		User user = new User();
		user.setImage("image".getBytes());
		user.setBase64Image("image");
		mockMvc.perform(get("/AdminDashboard").sessionAttr("adminProfile", user))
				.andExpect(view().name("adminDashboard"));
	}

	@Test
	public void userDashboardTest() throws Exception {
		User user = new User();
		user.setImage("image".getBytes());
		user.setBase64Image("image");
		mockMvc.perform(get("/UserDashboard").sessionAttr("userProfile", user)).andExpect(view().name("userDashboard"));
	}

	@Test
	public void addressInfo() throws Exception {
		User user = mock(User.class);

		when(userService.displayUser(3)).thenReturn(user);
		this.mockMvc
				.perform(get("/AddressInfo").param("id", "3").contentType(MediaType.APPLICATION_JSON)
						.content(user.toString()))
				.andExpect(model().attribute("address", user)).andExpect(status().isOk())
				.andExpect(view().name("addressInfo"));

		verify(userService, atLeast(1)).displayUser(3);
	}

	@Test
	public void loginInfoTest() throws Exception {
		mockMvc.perform(get("/LoginInfo")).andExpect(status().isOk()).andExpect(view().name("loginInfo"));

	}

	@Test
	public void viewUserTest() throws Exception {
		List<User> userList = new ArrayList<User>();
		when(userService.getAllUser()).thenReturn(userList);

		this.mockMvc.perform(get("/ViewUsers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(view().name("viewUsers"));

		verify(userService, atLeast(1)).getAllUser();

	}

	@Test
	public void userTest() throws Exception {
		mockMvc.perform(get("/UserRegister").param("userName", "admin")).andExpect(status().isOk())
				.andExpect(view().name("register"));
	}

	@Test
	public void registerTest() throws Exception {
		User user = new User();
		user.setUserId(0);
		user.setFirstName("Shivani");
		user.setLastName("Hemnani");
		user.setEmail("shiv@test.com");
		user.setPassword("123");
		user.setBirthDate("2022-05-11");
		user.setContactNo("7677857857");
		user.setGender("Female");
		user.setLanguages("Gujarati");
		user.setAdmin(false);
		String fileLoc = "src/main/resources/static/img/forgot-password-office.jpeg";

		BufferedImage bImage = ImageIO.read(new File(fileLoc));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bImage, "jpg", bos);
		byte[] data = bos.toByteArray();
		user.setImage(data);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		Address address = new Address();
		List<Address> addressList = new ArrayList<Address>();

		address.setAddressId(1);
		address.setAddressLine("Solaa");
		address.setCity("Ahmedabad");
		address.setState("Gujarat");
		address.setUser(user);
		addressList.add(address);
		user.setAddress(addressList);

		doNothing().when(userService).saveUser(user);

		this.mockMvc.perform(post("/RegisterURL").contentType(MediaType.APPLICATION_JSON).content(user.toString()))
				.andExpect(status().isOk());

		this.mockMvc.perform(post("/RegisterURL").param("myuser", "admin").sessionAttr("adminProfile", user)
				.contentType(MediaType.APPLICATION_JSON).content(user.toString())).andExpect(status().isOk());

		verify(userService, atLeast(1)).saveUser(any());
	}

	@Test
	public void loginTest() throws Exception {

		User user = new User();

		user.setEmail("shiv@test.com");
		user.setPassword("123");
		user.setImage("image".getBytes());
		user.setBase64Image("image");
		user.setAdmin(true);

		when(userService.userLogin(any())).thenReturn(user);
		this.mockMvc.perform(post("/LoginURL").contentType(MediaType.APPLICATION_JSON).content(user.toString()));

		user.setAdmin(false);
		when(userService.userLogin(user)).thenReturn(user);
		this.mockMvc.perform(post("/LoginURL").contentType(MediaType.APPLICATION_JSON).content(user.toString()));

		User userObj = null;
		when(userService.userLogin(userObj)).thenReturn(userObj);
		this.mockMvc.perform(post("/LoginURL"));

		verify(userService, atLeast(1)).userLogin(any());

	}

	@Test
	public void loginExceptionTest() throws Exception {
		User userObj = new User();
		when(userService.userLogin(userObj)).thenReturn(userObj);
		this.mockMvc.perform(post("/LoginURL"));

		verify(userService, atLeast(1)).userLogin(any());
	}

	@Test
	public void forgotPasswordTest() throws Exception {
		User user = new User();
		user.setEmail("shivani@test.com");
		user.setPassword("111");

		doNothing().when(userService).resetPassword(user);

		this.mockMvc.perform(post("/passwordURL")).andExpect(status().isOk()).andExpect(view().name("index"));

		verify(userService, atLeast(1)).resetPassword(any());

	}

	@Test
	public void deleteUserTest() throws Exception {
		User user = new User();
		user.setUserId(1);

		doNothing().when(userService).deleteUser(1);

		this.mockMvc.perform(post("/DeleteUser").param("userId", "1")).andExpect(status().isOk());

		verify(userService, atLeast(1)).deleteUser(1);

	}

	@Test
	public void checkMailTest() throws Exception {

		User user = new User();
		user.setEmail("shivani@test.com");

		when(userService.checkEmail("shivani@test.com")).thenReturn(false);

		this.mockMvc.perform(get("/checkUserEmail").param("email", "shivani@test.com")).andExpect(status().isOk());

		when(userService.checkEmail("shivani@test.com")).thenReturn(true);

		this.mockMvc.perform(get("/checkUserEmail").param("email", "shivani@test.com")).andExpect(status().isOk());

		verify(userService, atLeast(1)).checkEmail(any());

	}

	@Test
	public void generateCSVTest() throws Exception {
		Date startDate = Date.valueOf("2022-05-11");
		Date endDate = Date.valueOf("2022-05-13");

		doNothing().when(userService).generateCSV(startDate, endDate);

		this.mockMvc.perform(post("/getCSV").param("startDate", "2022-05-11").param("endDate", "2022-05-13"))
				.andExpect(view().name("loginInfo")).andExpect(status().isOk());

		verify(userService, atLeast(1)).generateCSV(startDate, endDate);

	}

	@Test
	public void imageEmptyTest() throws IOException, Exception {
		User user = mock(User.class);
		MockMultipartFile file = new MockMultipartFile("data", "".getBytes());
		doNothing().when(userService).saveUser(user);

		when(userService.displayUser(user.getUserId())).thenReturn(user);
		this.mockMvc.perform(multipart("/EditURL")
				.file("file", file.getBytes()).param("user", "userEdit")
				.contentType(MediaType.APPLICATION_JSON).content(user.toString()));

		verify(userService, atLeast(1)).saveUser(any());
	}

	@Test
	public void updateTest() throws Exception {
		User user = mock(User.class);
		user.setUserId(3);
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		doNothing().when(userService).saveUser(user);

		when(userService.displayUser(user.getUserId())).thenReturn(user);
		this.mockMvc.perform(multipart("/EditURL").file("file", file.getBytes()).param("user", "userEdit")
				.contentType(MediaType.APPLICATION_JSON).content(user.toString()));

		verify(userService, atLeast(1)).saveUser(any());

	}

	@Test
	public void updateTest2() throws IOException, Exception {
		User user = spy(User.class);
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		doNothing().when(userService).saveUser(user);

		when(userService.displayUser(3)).thenReturn(user);
		this.mockMvc.perform(multipart("/EditURL").file("file", file.getBytes()).param("user", "adminEdit")
				.contentType(MediaType.APPLICATION_JSON).content(user.toString()));

		verify(userService, atLeast(1)).saveUser(any());

	}

	@Test
	public void editProfileTest() throws Exception {

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
		String fileLoc = "src/main/resources/static/img/forgot-password-office.jpeg";

		BufferedImage bImage = ImageIO.read(new File(fileLoc));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bImage, "jpg", bos);
		byte[] data = bos.toByteArray();
		user.setImage(data);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		Address address = new Address();
		List<Address> addressList = new ArrayList<Address>();

		address.setAddressId(1);
		address.setAddressLine("Solaa");
		address.setCity("Ahmedabad");
		address.setState("Gujarat");
		address.setUser(user);
		addressList.add(address);
		user.setAddress(addressList);

		when(userService.displayUser(3)).thenReturn(user);

		this.mockMvc.perform(get("/editProfile").param("id", "3").param("user", "userEdit")
				.sessionAttr("userProfile", user).sessionAttr("UserImage", user.getImage())).andExpect(status().isOk());

		verify(userService, atLeast(1)).displayUser(3);
	}

	
}
