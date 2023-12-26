package phoenix.AM_PM.mainservice.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
	private String userId;
	private String password;
}
