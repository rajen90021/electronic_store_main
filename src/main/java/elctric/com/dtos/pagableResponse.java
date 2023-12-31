package elctric.com.dtos;

import java.util.List;

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

public class pagableResponse<T> {

	
	private List<T> contect;
	
	
	private int  pagenumber;
	
	
	private int pagesize;
	
	
	private long totalelement;
	
	
	private  long totalpage;
	
	
	private boolean lastpage;
	
}
