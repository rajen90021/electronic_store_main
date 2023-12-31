package elctric.com.dtos;

import javax.persistence.Id;

import elctric.com.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleDto {
	
	 private String roleId;
	 
	 private String rolename;
}
