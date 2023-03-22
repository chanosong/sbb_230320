package com.mysite.sbb.Answer;

import com.mysite.sbb.Question.Question;
import com.mysite.sbb.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
    @ManyToOne
    private SiteUser author;

    @ManyToOne
    private Question question;
}
