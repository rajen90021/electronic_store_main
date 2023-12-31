package elctric.com.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.Userdto;
import elctric.com.dtos.pagableResponse;
import elctric.com.entity.Users;


@Service
public interface UserService {

	
	////create user 
	    Userdto   createUser(Userdto userdto);
	    
	    
	//update user
	    Userdto   updateUser(Userdto userdto, String userid);
	    
	    
	    
	//delete user
	    void   deleteUserByid(String userid);
	    
	    
	    
//	get single user by id
	    Userdto getUserUserByid(String userid);
	    
	    
//	get user by email
	   Userdto getUserByEmail(String email);
	
	   
	   
//	get all user
	   pagableResponse<Userdto> gellAlluser(int pagenumber ,int pagesize,String sortby,String dir);
	
	
	///search user
	List<Userdto>  searchUser(String keyword);
	
	
//	file upload
	
	public String uploaduserimage(MultipartFile file, String path) throws IOException;
	
//	serve image
	
	public InputStream serveimage(String path,String imagename)throws FileNotFoundException;
	
	
	
	Optional<Users> findUsersByEmailOptional(String email);
	
}
