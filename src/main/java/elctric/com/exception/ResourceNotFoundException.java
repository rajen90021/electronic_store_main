package elctric.com.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException  extends RuntimeException{
		
     
	
	public ResourceNotFoundException() {
		
		super("resource not found");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
		
	}

	
	
	
   

}
