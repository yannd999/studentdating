package com.studentdating.studentdating.model.service;

import com.studentdating.studentdating.model.dto.RelationDTO;

import java.util.List;

public interface RelationService {
    void makeRelation(String choser, String lover_1, String lover_2);
    List<RelationDTO> getRelations();
    List<RelationDTO> getRelations(String name);
}
