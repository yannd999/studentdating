package com.studentdating.studentdating.model.db;

import com.studentdating.studentdating.model.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, Long> {
}
