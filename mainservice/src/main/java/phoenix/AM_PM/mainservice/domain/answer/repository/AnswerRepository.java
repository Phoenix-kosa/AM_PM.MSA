package phoenix.AM_PM.mainservice.domain.answer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.AM_PM.mainservice.domain.answer.entity.Answer;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    public Optional<Answer> findByQuestionId(Integer questionId);
    public void deleteByQuestionId(Integer questionId);
}