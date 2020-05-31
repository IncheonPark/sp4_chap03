package spring;

import java.util.Date;

public class Member {
	
	private Long id;
	private String email;
	private String name;
	private String password;
	private Date registerDate;
	
	public Member(String email, String name, String password, Date registerDate) {
		
		this.email = email;
		this.name = name;
		this.password = password;
		this.registerDate = registerDate;
	}
	
	void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if (!password.equals(oldPassword))
			throw new IdPasswordNotMatchingException();
		this.password = newPassword;
	}
	
	
}
