package com.ecole.serviceimpl;

import com.ecole.domain.Resultat;
import com.ecole.repository.ResultatRepository;
import com.ecole.service.ResultatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResultatServiceImpl implements ResultatService {
    @Autowired
    private ResultatRepository resultatRepository;
    @Override
    public Resultat saveResultat(Resultat resultat) {
        log.info("Ajout resultat de l'etudiant {} dans l'examen:",resultat.getUser_etud().getNomPrenom(),resultat.getExamen().getTitle());
        return this.resultatRepository.save(resultat);
    }
}
