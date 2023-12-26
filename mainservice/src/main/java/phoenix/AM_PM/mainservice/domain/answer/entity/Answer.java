package phoenix.AM_PM.mainservice.domain.answer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import phoenix.AM_PM.mainservice.domain.question.entity.Question;
import phoenix.AM_PM.mainservice.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Answer {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Integer id; //답변 ID

  private Integer bulletinId; //문의 ID

  @Column (name = "title")
  private String title;

  @Column (name = "content")
  private String content;

  @Column(name = "created_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdDate;

  @Builder
  public Answer(String title, String content){
    this.title = title;
    this.content = content;
  }


  @ManyToOne(fetch = FetchType.LAZY)
  private Question question;    // 댓글이 달린 게시판

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;  // 작성자

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

//  public void changeUserId(User userId){
//    this.user = userId;
//  }
//  public void changePost(Question question){
//    this.question = question;
//  }
}
