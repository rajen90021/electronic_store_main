package elctric.com.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import elctric.com.dtos.apiResponse;

@RestControllerAdvice
public class GlobalExceptionhandler {

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<apiResponse>   resourcenotfoundexception(ResourceNotFoundException exception){
		
		
		
		     apiResponse apiResponse = new apiResponse();
		     apiResponse.setMessage(exception.getMessage());
		     apiResponse.setStatus(HttpStatus.NOT_FOUND);
		     apiResponse.setSucess(false);
		
		     
		     return new ResponseEntity<apiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
		
	}
	
	
	@ExceptionHandler(badApiRequestException.class)
	public ResponseEntity<apiResponse>  badapiResquestionexception(badApiRequestException exception){
		
		
		
		     apiResponse apiResponse = new apiResponse();
		     apiResponse.setMessage(exception.getMessage());
		     apiResponse.setStatus(HttpStatus.NOT_FOUND);
		     apiResponse.setSucess(false);
		
		     
		     return new ResponseEntity<apiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
		
	}
	

	   @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> validationErrors = new HashMap<>();

	        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

	        for (ObjectError error : errors) {
	            if (error instanceof FieldError) {
	                FieldError fieldError = (FieldError) error;
	                validationErrors.put(fieldError.getField(), error.getDefaultMessage());
	            }
	        }

	        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	    }
	   
		
		@ExceptionHandler(filenotfoundexpection.class)
		public ResponseEntity<apiResponse>  filenotfoundexception(filenotfoundexpection exception){
			
			
			
			     apiResponse apiResponse = new apiResponse();
			     
			     apiResponse.setMessage(exception.getMessage());
			     apiResponse.setStatus(HttpStatus.NOT_FOUND);
			     apiResponse.setSucess(false);
			
			     
			     return new ResponseEntity<apiResponse>(apiResponse,HttpStatus.NOT_FOUND);
			
			
		}
	   
	
}
