package phoenix.AM_PM.mainservice.domain.answer.dto;

import lombok.*;
import phoenix.AM_PM.mainservice.domain.answer.entity.Answer;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class AddAnswerRequest {

    private String createdDate;
    private String title;
    private String content;
    private Integer questionId;
    private boolean status;

    public Answer toEntity() {
        return Answer.builder()
                .questionId(questionId)
                .createdDate(LocalDateTime.now())
                .title(title)
                .content(content)
                .status(true)
                .build();
    }
}
