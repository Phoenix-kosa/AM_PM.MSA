package phoenix.AM_PM.mainservice.domain.answer.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phoenix.AM_PM.mainservice.domain.answer.dto.AddAnswerRequest;
import phoenix.AM_PM.mainservice.domain.answer.dto.AnswerResponse;
import phoenix.AM_PM.mainservice.domain.answer.dto.UpdateAnswerRequest;
import phoenix.AM_PM.mainservice.domain.answer.entity.Answer;
import phoenix.AM_PM.mainservice.domain.answer.service.AnswerService;
import phoenix.AM_PM.mainservice.domain.question.service.QuestionService;

import java.util.List;


@Slf4j
@RestController //HTTP Response Body에 객체 데이터를 JSON형식으로 반환하는 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {
//    @NonNull
//    UserRepository uR;

    private  final AnswerService answerService;
    private QuestionService questionService;

//    private JwtServiceImpl jwtService;
    //생성
    @PostMapping("/write")
    public ResponseEntity<Answer> create(@RequestBody AddAnswerRequest req) {
        Answer answer = answerService.save(req);
        //성공적으로 생성되었으며 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(answer);
    }

    //전체 조회
    @GetMapping("")
    public ResponseEntity<List<AnswerResponse>> findAll(){
        List<AnswerResponse> answerResponses = answerService.findAll()
                .stream()
                .map(AnswerResponse::new)
                .toList();
        return ResponseEntity.ok().body(answerResponses);
    }

    //조회
    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponse> findAnswer(@PathVariable(name = "id") int id) throws IllegalAccessException {
        Answer answer = answerService.findById(id);
        return ResponseEntity.ok().body(new AnswerResponse(answer));
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") int id) {
        answerService.delete(id);

        return ResponseEntity.ok().build();
    }

    //수정
    @PutMapping("/{id}")
    public ResponseEntity<Answer> update(@PathVariable(name = "id") int id, @RequestBody UpdateAnswerRequest req){
        System.out.println(id);
        System.out.println(req);
        Answer updateAnswer = answerService.update(id, req);
        return ResponseEntity.ok().body(updateAnswer);
    }
}
