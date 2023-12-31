package elctric.com.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import elctric.com.entity.Users;
import elctric.com.repository.UserRepository;


@Service
public class Customuserdetailsservice  implements UserDetailsService{

	
	@Autowired
	  private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		  Users users = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user name not found "));
		  
		return users;
	}

}
