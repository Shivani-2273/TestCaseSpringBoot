package com.usermanagement.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.usermanagement.model.User;
import com.usermanagement.service.UserService;
import com.usermanagement.utility.StringLiteral;

@Controller
public class UserController{
	private static Logger logger = Logger.getLogger(UserController.class.getName());
	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/index", "/Logout" })
	public String index(HttpSession session) {
		return StringLiteral.INDEX;
	}
	
	@RequestMapping(value = { "/UserRegister", "/addUser" })
	public String userRegister(Model model, HttpSession session,
			@RequestParam(value = "user", required = false) String userName) {
		session.removeAttribute("userProfile");
		model.addAttribute("user", userName);
		return "register";
	}

	@RequestMapping(value = "/ForgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value = "/AdminDashboard")
	public String adminDashboard() {
		return "adminDashboard";

	}

	@RequestMapping(value = "/UserDashboard")
	public String userDashboard() {
		return "userDashboard";

	}

	//to view all user
	@RequestMapping("/ViewUsers")
	public ModelAndView viewUsers() {
		List<User> userList = userService.getAllUser();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("UserList", userList);
		logger.info("UserList" + userList);
		modelAndView.setViewName("viewUsers");
		return modelAndView;
	}

	@RequestMapping(value = "/LoginInfo")
	public String loginInformation() {
		return "loginInfo";
	}

	//get all address of user
	@RequestMapping(value = "/AddressInfo")
	public String addressInformation(Model model, @RequestParam("id") int userId) {
		User addressList = userService.displayUser(userId);
		model.addAttribute("address", addressList);
		logger.info("User Address"+addressList);
		return "addressInfo";
	}

	//get data of user on edit page
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile(HttpSession session, Model model, @RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "user", required = false) String userName) {
	
		model.addAttribute("user", userName);
		if (id != null) {
			User userProfile = userService.displayUser(Integer.parseInt(id));
			byte[] image = userProfile.getImage();
			session.setAttribute("UserImage", image);
			session.setAttribute("userProfile", userProfile);
			logger.info("Profile"+userProfile);
		}
		return "register";

	}

	//to register user
	@RequestMapping(value = { "/RegisterURL", "/addUser" }, method = RequestMethod.POST)
	public String register(Model model, HttpSession session, @ModelAttribute("registerForm") User user,
			@RequestParam(value = "myuser", required = false) String userName,BindingResult result) {
		logger.info("User" + user);
		userService.saveUser(user);
			if (userName != null) {
				User obj = (User) session.getAttribute("adminProfile");
				model.addAttribute(obj);
				logger.info("Admin Profile"+obj);
				return "adminDashboard";
			} else {
				return StringLiteral.INDEX;
			}


	}

	//to get logged in system
	@RequestMapping(value = "/LoginURL", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("LoginForm") User user, HttpSession session) {
		User userObj = userService.userLogin(user);
		logger.info("Login Details"+userObj);
		if (userObj != null) {
			if (userObj.isAdmin()) {
				session.setAttribute("adminProfile", userObj);
				return "adminDashboard";
			} else {
				session.setAttribute("userProfile", userObj);
				return "userDashboard";

			}
		}else {
			model.addAttribute("errorMsg", "Invalid  email id and password");
			return StringLiteral.INDEX;
		}
	}

	//to reset password using email
	@RequestMapping(value = "/passwordURL", method = RequestMethod.POST)
	public String forgotPassword(@ModelAttribute("resetPassword") User user) {
		logger.info("Password Reset"+user);
		userService.resetPassword(user);
		return StringLiteral.INDEX;
	}

	//to delete user
	@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
	public void deleteUser(@RequestParam("userId") int userId, HttpServletResponse response) throws IOException {
		logger.info("Id for delete"+userId);
		userService.deleteUser(userId);
		response.getWriter().write("in success");

	}

	//to check email already exist in database 
	@RequestMapping(value = "/checkUserEmail", method = RequestMethod.GET)
	public void checkUserEmail(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
		logger.info("Check email exist or not"+email);
		boolean isValid = userService.checkEmail(email);
		if (isValid) {
			response.getWriter().write("Matched");
		} else {
			response.getWriter().write("Not Matched");
		}
	}

	//to update user data and address
	@RequestMapping(value = "/EditURL", method = RequestMethod.POST)
	public String update(HttpSession session, Model model, @ModelAttribute("registerForm") User user,
			@RequestParam("file") MultipartFile image,
			@RequestParam(value = "user", required = false) String userName) {
		logger.info("User Update" + user);
		if (image.isEmpty()) {
			byte[] imagebytes = (byte[]) session.getAttribute("UserImage");
			user.setImage(imagebytes);
		}
		userService.saveUser(user);
		if (userName.equals("userEdit")) {
			User userProfile = userService.displayUser(user.getUserId());
			model.addAttribute("userProfile", userProfile);
			return "userDashboard";
		} else {
			return "redirect:/ViewUsers";
		}

	}
		
	
	//to generate CSV file based on login details
	@RequestMapping(value = "/getCSV", method = RequestMethod.POST)
	public String generateCSV(@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate)
			throws FileNotFoundException {
		userService.generateCSV(startDate, endDate);
		return "loginInfo";
	}
}
