package elctric.com.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.Userdto;
import elctric.com.dtos.pagableResponse;
import elctric.com.entity.Role;
import elctric.com.entity.Users;
import elctric.com.exception.ResourceNotFoundException;
import elctric.com.exception.filenotfoundexpection;
import elctric.com.repository.RoleRepository;
import elctric.com.repository.UserRepository;
import elctric.com.service.UserService;



@Service
public class userServiceImp implements UserService {

	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
//	 @Value("${image/user/}")
	  @Value("${user.profile.image.path:image/user/}")
	    private String uploadImagePath;
	  String normalroleid= "sdfgdhtfbxzf";
	  
	  
	  @Autowired
	  private RoleRepository roleRepository;
	  @Autowired
  private PasswordEncoder passwordEncoder;
	//create user
	
	  
	@Override
	public Userdto createUser(Userdto userdto) {
	
		     
		   String userid = UUID.randomUUID().toString();
		   
		   userdto.setId(userid);
		   
   userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));		
		 Users users = this.mapper.map(userdto, Users.class);
		 
		  Optional<Role> roleid = roleRepository.findById(normalroleid);
		  Role role =null;
		  if(roleid.isPresent()) {
			  role = roleid.get();
		  }
		 
		     users.getRole().add(role);		
		 Users save = this.repository.save(users);
		 
		 
		 Userdto userdto2 = this.mapper.map(save, Userdto.class);
		
		
		return userdto2;
	}

	
//	update user
	
	
	
	@Override
	public Userdto updateUser( Userdto userdto, String userid) {
		// TODO Auto-generated method stub
		
		Users users=null;
		    Optional<Users> findById = this.repository.findById(userid);
		    
		    if(findById.isPresent()) {
		    	
		    	 users = findById.get();
		    }else {
		    	
		    	throw new ResourceNotFoundException("user id not found with id ");
		    }
		    users.setName(userdto.getName());
		    
		    users.setAbout(userdto.getAbout());
		    
		    
		    users.setGender(userdto.getGender());
		    
		    users.setImageName(userdto.getImageName());
		    
		    users.setPassword(userdto.getPassword());
		    
		     Users saveuser = this.repository.save(users);
		    
		    Userdto userdto2 = this.mapper.map(saveuser, Userdto.class);
		    
		
		
		return userdto2;
	}

	
	
//	delete user by id
	
	@Override
	public void deleteUserByid(String userid) {
		// TODO Auto-generated method stub
		Users users=null;
	    Optional<Users> findById = this.repository.findById(userid);
	    
	    if(findById.isPresent()) {
	    	
	    	 users = findById.get();
	    }else {
	    	
	    	throw new ResourceNotFoundException("user id not found with id ");
	    }
	    
	    
	    
	    String fullpath= uploadImagePath+users.getImageName();
	    
	    Path path = Paths.get(fullpath);
	    
	    
	    try {
			Files.delete(path);
			
			
		} catch (IOException e) {
			
			
			
			e.printStackTrace();
		}
	    
	    this.repository.deleteById(userid);
		
		
		
	}
	
	
	
//	get user by id

	@Override
	public Userdto getUserUserByid(String userid) {
		
		
		Users users=null;
	    Optional<Users> findById = this.repository.findById(userid);
	    
	    if(findById.isPresent()) {
	    	
	    	 users = findById.get();
	    }else {
	    	
	    	throw new ResourceNotFoundException("user id not found with id :"+userid);
	    }
	    
	    Userdto userdto2 = this.mapper.map(users, Userdto.class);
		
		return userdto2;
	}

	@Override
	
	
//	get user by email
	
	
	
	public Userdto getUserByEmail(String email) {
		
		Users users=null;
	Optional<Users> userByEmail = this.repository.findByEmail(email);
	
	  if(userByEmail.isPresent()) {
	    	
	    	 users = userByEmail.get();
	    }else {
	    	
	    	throw new ResourceNotFoundException("user email not found with email "+email);
	    }
	    
	
		Userdto userdto = this.mapper.map(users, Userdto.class);
		
		return userdto;
	}
	
	
	

	
//	get alll user 
	
	
	@Override
	public pagableResponse<Userdto> gellAlluser(int pagenumber ,int pagesize,String sortby,String dir) {
		// TODO Auto-generated method stub
		
		  Sort sort = Sort.by(sortby);
		    
		    if (dir.equalsIgnoreCase("desc")) {
		        sort = sort.descending();
		    } else {
		        sort = sort.ascending();
		    }
		    
		    
		    PageRequest of = PageRequest.of(pagenumber, pagesize,sort);
		
		 Page<Users> findAll = this.repository.findAll(of);
		List<Users> content = findAll.getContent();
		
		           List<Userdto> userToDto = userToDto(content);
		           pagableResponse<Userdto> response = new pagableResponse<>();
		           
		           response.setContect(userToDto);
		           response.setPagenumber(findAll.getNumber());
		           response.setPagesize(findAll.getSize());
		           
		           response.setTotalelement(findAll.getTotalElements());
		           response.setTotalpage(findAll.getTotalPages());
		           
		           
		           response.setLastpage(findAll.isLast());
		
		return response;
	}

	
		
	
//	search user 
	
	
	
	@Override
	public List<Userdto> searchUser(String keyword) {
		
		     List<Users> findByNameContaining = this.repository.findByNameContaining(keyword);
		          
		         List<Userdto> userToDto = userToDto(findByNameContaining);
		
		return userToDto;
	}

	
	
	
	private List<Userdto> userToDto(List<Users> users) {
	    List<Userdto> userDtos = new ArrayList<>();
	 
	    
	       for(Users user : users) {
	    	   
	    	   Userdto userdto = this.mapper.map(user, Userdto.class);
	    	   userDtos.add(userdto);
	       }
	    
	    return userDtos;
	}


	@Override
	public String uploaduserimage(MultipartFile file, String path) throws IOException {
		System.out.println("1");
		
		  String originalFilename = file.getOriginalFilename();
		  
		  String random = UUID.randomUUID().toString();
		  
		       String extract = originalFilename.substring(originalFilename.lastIndexOf("."));
		       
		       
		          //.png    //534ggg4
		       
//		       image/category/fgrgrg.png
		       String fullimagename=random+extract;
		         
		         String fullpath= path+fullimagename;
		       if(extract.equalsIgnoreCase(".png")||extract.equalsIgnoreCase(".jpg")||extract.equalsIgnoreCase(".jpeg")) {
		    	   
		    	   
		    		System.out.println("2");
		    	   
			         File file2 = new  File(path);
			         
			         if(!file2.exists()) {
			        	 
			        	 file2.mkdirs();
			         }else {
			        	   
			        	Files.copy(file.getInputStream(), Path.of(fullpath));   
			        	 
			         }
			        
			     	System.out.println("3");
		       }else {
		    	   throw new filenotfoundexpection(" file is not supported with extension "+extract);
		       }
		   	System.out.println("4");
		       return fullimagename;
		        
		       
		
	}


	@Override
	public InputStream serveimage(String path, String imagename) throws FileNotFoundException {
	    String fullpath= path +File.separator+imagename;
		
		
			InputStream inputStream= new FileInputStream(fullpath);
			
			return inputStream;
	}


	@Override
	public Optional<Users> findUsersByEmailOptional(String email) {

       return this.repository.findByEmail(email);
	}


}
