package phoenix.AM_PM.mainservice.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAnswerRequest {
    private String title;
    private String content;
}
