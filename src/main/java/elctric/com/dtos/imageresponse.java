package elctric.com.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
  @Builder
public class imageresponse {
	
	
	private String filename;
	 private String message;
	 private  boolean sucess;
	 
	 private HttpStatus status;

}
