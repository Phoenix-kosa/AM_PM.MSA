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

    public Answer toEntity() {
        return Answer.builder()
                .createdDate(LocalDateTime.now())
                .title(title)
                .content(content)
                .build();
    }
}
