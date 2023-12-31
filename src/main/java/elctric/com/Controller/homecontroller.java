package elctric.com.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class homecontroller {

	
	
	@RequestMapping("/about")
	public String home() {
		return "hoime";
	}
}
