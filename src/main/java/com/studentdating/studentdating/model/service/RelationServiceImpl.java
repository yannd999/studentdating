package com.studentdating.studentdating.model.service;

import com.studentdating.studentdating.model.db.RelationRepository;
import com.studentdating.studentdating.model.dto.RelationDTO;
import com.studentdating.studentdating.model.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private final RelationRepository repository;

    public RelationServiceImpl(RelationRepository repository) {
        this.repository = repository;
    }

    public void makeRelation(String choser, String lover_1, String lover_2) {
        //todo geen meerdere relaties met zelfde persoon? ander op profile 5 keer hetzelfde
        Relation relation = new Relation();
        relation.setChoser(choser);
        relation.setLover_1(lover_1);
        relation.setLover_2(lover_2);
        repository.save(relation);
    }

    public List<RelationDTO> getRelations() {
        List<RelationDTO> relations = new ArrayList<>();
        List<Relation> get = repository.findAll();
        for (Relation r: get) {
            relations.add(convert(r));
        }
        return relations;
    }

    @Override
    public List<RelationDTO> getRelations(String name) { // firstname + lastname
        List<RelationDTO> relations = new ArrayList<>();
        List<Relation> get = repository.findAll();
        for (Relation r: get) {
            if (r.getLover_1().equals(name) || r.getLover_2().equals(name)) relations.add(convert(r));
        }
        return relations;
    }


    private RelationDTO convert(Relation relation) {
        RelationDTO relationDTO = new RelationDTO();
        relationDTO.setId(relation.getId());
        relationDTO.setChoser(relation.getChoser());
        relationDTO.setLover_1(relation.getLover_1());
        relationDTO.setLover_2(relation.getLover_2());
        return relationDTO;
    }


    private Relation convertBack(RelationDTO relationDTO) {
        Relation relation = new Relation();
        relation.setChoser(relationDTO.getChoser());
        relation.setLover_1(relationDTO.getLover_1());
        relation.setLover_2(relationDTO.getLover_2());
        return relation;
    }
}
