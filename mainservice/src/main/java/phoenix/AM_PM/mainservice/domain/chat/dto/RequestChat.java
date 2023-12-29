package phoenix.AM_PM.mainservice.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@ToString
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@AllArgsConstructor
@NoArgsConstructor
public class RequestChat {
    private String message;
    private Integer projectId;
    private Integer userId;
}
