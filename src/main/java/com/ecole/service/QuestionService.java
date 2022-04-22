package com.ecole.service;

import com.ecole.domain.Examen;
import com.ecole.domain.Question;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface QuestionService {
    public Question addQuestion(Question question);

    public Question updateQuestion(Question question);

    public Set<Question> getQuestions();

    public Set<Question> questionsOfExamen(Examen examen);

    public void deletequestion(Long idQuest) throws Exception;

    public Question getQuestionById(Long idQuest) throws Exception;

}
