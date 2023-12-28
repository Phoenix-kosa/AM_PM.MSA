package phoenix.AM_PM.mainservice.domain.answer.dto;

import lombok.*;
import phoenix.AM_PM.mainservice.domain.question.entity.Question;
import phoenix.AM_PM.mainservice.domain.user.entity.User;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private int id;
    private String bulletinId; //작성자 ID
    private String title;
    private String content;
    private String createdDate;
    private Question question;
    private String userId;

    private User user;

}