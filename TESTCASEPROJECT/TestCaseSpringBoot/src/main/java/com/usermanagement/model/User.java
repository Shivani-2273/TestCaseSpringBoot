package com.usermanagement.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "is_admin")
	private boolean isAdmin;

	@Column(name = "first_name", nullable = false)
	@NotNull(message = "First name can not be null")
	private String firstName;

	@Column(name = "last_name")
	@NotNull(message = "last name can not be null")
	private String lastName;

	@Email(message = "Email should be valid")
	@NotNull(message = "Email can not be null")
	private String email;

	@NotNull(message = "Password can not be null")
	private String password;

	@Column(name = "contact_no")
	@NotNull(message = "Phone number can not be null")
	@Size(min = 10, max = 10, message = "Phone number should be of 10 digit")
	private String contactNo;

	@NotNull(message = "Please select gender")
	private String gender;

	@Column(name = "birth_date")
	@NotNull(message = "Please select birthdate")
	private String birthDate;

	@NotNull(message = "Please select atleast one language")
	private String languages;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Address> address;

	@Column(name = "profile_image")
	@Lob
	private byte[] image;

	@Transient
	String base64Image;

	@Transient
	MultipartFile file;


	public User() {
		super();
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password){
		//byte[] encryptionBytes = Encryption_Decryption.encrypt(password);
		//this.password = new String(encryptionBytes);
		this.password=password;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		System.out.println("in address list");
		this.address = address;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getBase64Image() {

		return Base64.getEncoder().encodeToString(image);
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) throws IOException {
		this.image = file.getBytes();
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", isAdmin=" + isAdmin + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", contactNo=" + contactNo + ", gender=" + gender
				+ ", birthDate=" + birthDate + ", languages=" + languages + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", address=" + address + "]";
	}
	
	

		
}