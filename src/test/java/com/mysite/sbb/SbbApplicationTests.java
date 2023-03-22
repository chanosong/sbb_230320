package com.mysite.sbb;

import com.mysite.sbb.Answer.Answer;
import com.mysite.sbb.Answer.AnswerRepository;
import com.mysite.sbb.Question.Question;
import com.mysite.sbb.Question.QuestionRepository;
import com.mysite.sbb.Question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@BeforeEach
	void beforeEach() {

	}

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	@Test
	void testJpaAddQuestion() {
		Question q1 = new Question();
		q1.setSubject("SpringTest 어노테이션이 안됩니다.");
		q1.setContent("Gradle에 추가했는데도 왜 그럴까요?");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("SpringBoot Model 관련 질문합니다.");
		q2.setContent("ID는 자동 생성인건가요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void testJpaShowQuestion() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("SpringTest 어노테이션이 안됩니다.", q.getSubject());
	}

	@Test
	void testJpaFindById() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("SpringTest 어노테이션이 안됩니다.", q.getSubject());
		}
	}

	@Test
	void testJpaFindBySubject() {
		Question q = this.questionRepository.findBySubject("SpringTest 어노테이션이 안됩니다.");
		assertEquals(1, q.getId());
	}

	@Test
	void testJpaFindBySubjectAndContent(){
		Question q = this.questionRepository.findBySubjectAndContent("SpringTest 어노테이션이 안됩니다.", "Gradle에 추가했는데도 왜 그럴까요?");
		assertEquals(1, q.getId());
	}

	@Test
	void testJpaFindBySubjectLike(){
		List<Question> qList = this.questionRepository.findBySubjectLike("Spring%");
		Question q = qList.get(0);
		assertEquals("SpringTest 어노테이션이 안됩니다.", q.getSubject());
	}

	@Test
	void testJpaModifyQuestion() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정본");
		this.questionRepository.save(q);
	}

	@Test
	void testJpaDropQuestion() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void testJpaAddAnswer() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("자동 생성 완료");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	void testJpaShowAnswer() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Transactional
	@Test
	void testJpaGetAnswerList() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("자동 생성 완료", answerList.get(0).getContent());
	}

	@Test
	void testJpaCreateDummyQuestion() {
		for (int i = 0; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "냉무";
			this.questionService.create(subject, content, null);
		}
	}
}
