package com.ecole.service;

import com.ecole.domain.Resultat;
import org.springframework.stereotype.Service;

@Service
public interface ResultatService {
    public Resultat saveResultat(Resultat resultat);

}
