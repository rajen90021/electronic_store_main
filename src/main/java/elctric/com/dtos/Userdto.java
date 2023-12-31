package elctric.com.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import elctric.com.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Userdto {
	@Id
	private String id;
	
	@NotNull
	@Size(min = 3,max = 20,message = "name is required!!")
	private String name;
	
	
	  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "email is requird ")
	@Column(unique = true)
	private String email;
	
	
	@NotBlank( message="password is required !!")
	@Column(length = 10)
	private String password;
	
	
	@Size(min = 4,max = 6,message = "gender is required!!")
	private String gender;
	
	@Size(min = 3,max = 100,message = " write something about you!")
	@Column(length = 1000)
	private String about;
	
	
	
	private String imageName;
	
	
	private Set<RoleDto> role =new HashSet<>() ;
}
