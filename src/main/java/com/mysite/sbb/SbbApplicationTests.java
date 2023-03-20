package com.mysite.sbb;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("SpringTest 어노테이션이 안됩니다.");
        q1.setContent("gradle에 추가했는데도 왜 그럴까요?");
        q1.setCreateTime(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("SpringBoot Model 관련 질문합니다.");
        q2.setContent("ID는 자동 생성인건가요?");
        q2.setCreateTime(LocalDateTime.now());
        this.questionRepository.save(q2);
    }

}
