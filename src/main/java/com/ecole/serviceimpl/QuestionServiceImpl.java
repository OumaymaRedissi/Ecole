package com.ecole.serviceimpl;

import com.ecole.domain.Examen;
import com.ecole.domain.Question;
import com.ecole.repository.QuestionRepository;
import com.ecole.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl  implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        log.info("Enregistrement nouvelle Question {} à la BD",question.getEnonce());
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        log.info("Mise à jour Question {} ",question.getEnonce());
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        log.info("Toutes les questions");
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Set<Question> questionsOfExamen(Examen examen) {
        log.info("Toutes les questions d'un examen {}",examen.getTitle());
        return new HashSet<>(this.questionRepository.findByExamen( examen));
    }

    @Override
    public void deletequestion(Long idQuest) throws Exception {
        Question question=this.questionRepository.findById(idQuest).get();
        if(question==null) {
            log.error("Id de la question invalide");

            throw new Exception("Id de la question invalide");
        }
        log.info("Suppression de la question {}",question.getEnonce());
        this.questionRepository.deleteById(idQuest);

    }

    @Override
    public Question getQuestionById(Long idQuest) throws Exception {
        Question question=this.questionRepository.findById(idQuest).get();
        if(question==null) {
            log.error("Id de la question invalide");
            throw new Exception("Question inconnue");
        }
        log.info("Extraction question ");

        return question;
    }
}
