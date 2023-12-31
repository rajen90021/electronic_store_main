package elctric.com.Controller;


import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.Userdto;
import elctric.com.dtos.apiResponse;
import elctric.com.dtos.imageresponse;
import elctric.com.dtos.pagableResponse;
import elctric.com.service.UserService;
import elctric.com.service.implement.fileserviceimp;

@RestController
@RequestMapping("/users")

public class UserController {

	
	@Autowired
	private UserService service;
	
	@Autowired
	private fileserviceimp fileserviceimp;
	
	
//	  @Value("${image/user/}")
	  @Value("${user.profile.image.path:image/user/}")
	    private String uploadImagePath;
	//create
	
	@PostMapping
	  public ResponseEntity<Userdto> createuser(@Valid @RequestBody Userdto userdto){
		  
		
		Userdto createUser = this.service.createUser(userdto);
		
		return new ResponseEntity<Userdto>(createUser,HttpStatus.CREATED);
		  
	  }
	     
	
	
	//delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	  public ResponseEntity<apiResponse>  deleteuser(@PathVariable String userid){
		  
		 apiResponse apiResponse = new apiResponse("user delete sucessfully ", true, HttpStatus.OK);
		
		
		this.service.deleteUserByid(userid);
		
		return new ResponseEntity<apiResponse>(apiResponse,HttpStatus.OK);
	  }
	  
	//get single user by id
	  @PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/{userid}")
	public ResponseEntity<Userdto> getsinguserbyid(@PathVariable String userid){
		
		Userdto userUserByid = this.service.getUserUserByid(userid);
		
		return new ResponseEntity<Userdto>(userUserByid,HttpStatus.OK);
		
		
	}
	
	
	//update
	
	@PutMapping("/{userid}")
	public ResponseEntity<Userdto> updateuser(@Valid @PathVariable String userid,@RequestBody Userdto userdto){
		
		   Userdto updateUser = this.service.updateUser(userdto, userid);
		   
		   return new ResponseEntity<Userdto>(updateUser,HttpStatus.CREATED);
		
	}
	
	//get all user
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<pagableResponse<Userdto>> getalluser(@RequestParam(value = "pagenumber",defaultValue = "0",required = false)int pagenumber
			,@RequestParam( value = "pagesize",defaultValue = "10",required = false) int pagesize,
			@RequestParam(value = "sortby" ,defaultValue = "name",required = false) String sortby,
			@RequestParam(value ="dir" ,defaultValue = "desc",required = false )String dir
			
			
			
			){
		 pagableResponse<Userdto> gellAlluser = this.service.gellAlluser(pagenumber ,pagesize,sortby,dir);
		
		return new ResponseEntity<pagableResponse<Userdto>>(gellAlluser,HttpStatus.OK);
	}
	
	
	// get by email
	
	@GetMapping("/email/{email}")
	public ResponseEntity<Userdto> getbyemail(@PathVariable String email){
		
		return new ResponseEntity<Userdto>(this.service.getUserByEmail(email),HttpStatus.OK);
		
	}
	
	
	// search user
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<Userdto>> searchuser(@PathVariable String keywords){
		
		return new ResponseEntity<List<Userdto>>(this.service.searchUser(keywords),HttpStatus.OK);
		
		
	}
	
	@PostMapping("/image/{userid}")
	public ResponseEntity<imageresponse> uploadimage(@RequestParam("image") MultipartFile image,@PathVariable("userid") String userid ) throws IOException{
		
//		String uploadimagename = fileserviceimp.uploadimage(image, uploadImagePath);
		
	  String uploaduserimage = this.service.uploaduserimage(image, uploadImagePath);

		Userdto userUserByid = this.service.getUserUserByid(userid);
		
		userUserByid.setImageName(uploaduserimage);
		
		Userdto updateUser = service.updateUser(userUserByid, userid);
		
		
	imageresponse imageresponse= new imageresponse();
		
		imageresponse.setFilename(uploaduserimage);
		
		imageresponse.setMessage("file uploaded sucessfully ");
		
		imageresponse.setStatus(HttpStatus.CREATED);
		imageresponse.setSucess(true);
		
		
		return new ResponseEntity<imageresponse>(imageresponse,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/image/{userid}")
	public void serveimage(@PathVariable("userid") String userid,HttpServletResponse response) throws IOException {
		
		Userdto userUserByid = this.service.getUserUserByid(userid);	
		
		String imageName = userUserByid.getImageName();
//		InputStream serveimage = this.fileserviceimp.serveimage(uploadImagePath, imageName);
		  InputStream serveimage = this.service.serveimage(uploadImagePath, imageName);
		
		  response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	        StreamUtils.copy(serveimage, response.getOutputStream());
	
	}
	
	
}
