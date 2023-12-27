package phoenix.AM_PM.mainservice.domain.question.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class QuestionDTO implements Serializable {
    private Integer id;
    private String userId;
    private Integer projectId;
    private String title;
    private String content;
    private String createdDate;
    private boolean status;
}
