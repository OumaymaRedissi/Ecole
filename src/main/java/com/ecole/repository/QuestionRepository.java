package com.ecole.repository;

import com.ecole.domain.Examen;
import com.ecole.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Set<Question> findByExamen(Examen examen);
    Question findByIdQuest(Long idQuest);

}
