package elctric.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.Users;

public interface UserRepository extends JpaRepository<Users, String> {

	
	
       Optional<Users> findByEmail(String email);
       
       List<Users> findByNameContaining(String keyword);
}
