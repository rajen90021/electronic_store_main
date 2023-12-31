package elctric.com.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.Builder;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
  
import elctric.com.Security.JwtHelper;
import elctric.com.dtos.JwtRequest;
import elctric.com.dtos.JwtResponse;
import elctric.com.dtos.Userdto;
import elctric.com.entity.Users;
import elctric.com.exception.badApiRequestException;
import elctric.com.service.implement.userServiceImp;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class Authcontroller {
	
	@Autowired
	 private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	  @Autowired
	    private AuthenticationManager manager;
	  
	  @Autowired
	    private JwtHelper helper;	
	  @Autowired
	  private userServiceImp imp;
	  
	  
//	  @Value("${googleClientId}")
	  private String googleClientId="118293484707-1l6ofq59tirob8db3694n4592eokipsm.apps.googleusercontent.com";
	  
//	  @Value("${newPassword}")
	  private String newPassword="sfseerhyrdhdfbsvsfefetfaf";
	  
	  
	  
	@GetMapping("/current")
	ResponseEntity<Userdto> currentuser(Principal principal){
		
		String name = principal.getName();
		
		Userdto Userdto = this.modelMapper.map(userDetailsService.loadUserByUsername(name), Userdto.class);
		
		return new ResponseEntity<Userdto>(Userdto,HttpStatus.OK);
	}
	
	
	



	   

	 

	    @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws badApiRequestException {

	        this.doAuthenticate(request.getEmail(), request.getPassword());


	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);

	        
	          Userdto userdto = this.modelMapper.map(userDetails, Userdto.class);
	          
	        
	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .userdto(userdto).build();
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    private void doAuthenticate(String email, String password) throws badApiRequestException {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
//	            throw new BadCredentialsException(" Invalid Username or Passwordd !!");
	        	throw new badApiRequestException("invalid username and password");
	        }

	    }

//login wit goolge api 
	    
	    
	    
	    
	    @PostMapping("/google")
	    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String, Object> data) throws IOException, badApiRequestException {


	        //get the id token from request
	        String idToken = data.get("idToken").toString();

	        NetHttpTransport netHttpTransport = new NetHttpTransport();

	        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

	        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));


	        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);


	        GoogleIdToken.Payload payload = googleIdToken.getPayload();

	       

	        String email = payload.getEmail();

	        Users user = null;

	        user = imp.findUsersByEmailOptional(email).orElse(null);

	        if (user == null) {
	            //create new user
	            user = this.saveUser(email, data.get("name").toString(), data.get("photoUrl").toString());
	        }
	        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
	        return jwtResponseResponseEntity;


	    }

	    private Users saveUser(String email, String name, String photoUrl) {

//	        Userdto newUser = Userdto.builder()
//	                .name(name)
//	                .email(email)
//	                .password(newPassword)
//	                .imageName(photoUrl)
//	                .roles(new HashSet<>())
//	                .build();

	        
	                Userdto newUser= new Userdto();
	                
	                newUser.setName(name);
	                
	                newUser.setEmail(email);
	                newUser.setPassword(newPassword);
	                newUser.setImageName(photoUrl);
	                newUser.setRole(new HashSet<>());
	                
	        Userdto user = imp.createUser(newUser);

	        return this.modelMapper.map(user, Users.class);


	    }

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

}
