package elctric.com.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class updatorderrequest {
	
	
	
	   
	   
	  private String billingAddress;
	
	  private String billingPhone;
	  
	  private String billingName;
	  
	  private String paymentStatus;
	  
	  private String orderStatus;
	  private Date deliveredDate;
	

}
