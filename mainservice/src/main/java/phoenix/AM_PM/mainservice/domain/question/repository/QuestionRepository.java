package phoenix.AM_PM.mainservice.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phoenix.AM_PM.mainservice.domain.question.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public List<Question> findByUserId(String userId);

}
