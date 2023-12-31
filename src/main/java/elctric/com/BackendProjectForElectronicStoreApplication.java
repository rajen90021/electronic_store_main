package elctric.com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import elctric.com.entity.Role;
import elctric.com.repository.RoleRepository;


@SpringBootApplication
@EnableWebMvc
public class BackendProjectForElectronicStoreApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendProjectForElectronicStoreApplication.class, args);
	}

	
	@Autowired
	 private PasswordEncoder passwordEncoder;

	
	  String normalroleid= "sdfgdhtfbxzf";
	  String adminroleid= "ryykgndvasfjutikgfa";
	  
	  @Autowired
	  private RoleRepository repository;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(passwordEncoder.encode("abc"));
		
		
		
		Role normal= new Role();
		normal.setRoleId(normalroleid);
		normal.setRolename("ROLE_NORMAL");
		
		Role admin= new Role();
		admin.setRoleId(adminroleid);
		admin.setRolename("ROLE_ADMIN");
		
		
		this.repository.save(normal);
		this.repository.save(admin);
		
	}
	
	     


}
