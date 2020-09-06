package com.studentdating.studentdating.model.entity;

import com.studentdating.studentdating.model.exception.DomainException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="relation")
public class Relation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="choser")
    private String choser;
    @Column(name="lover_1")
    private String lover_1;
    @Column(name="lover_2")
    private String lover_2;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        if (id < 0) throw new DomainException("Id is invalid");
        this.id = id;
    }

    public String getChoser() {
        return choser;
    }

    public void setChoser(String choser) {
        this.choser = choser;
    }

    public String getLover_1() {
        return lover_1;
    }

    public void setLover_1(String lover_1) {
        this.lover_1 = lover_1;
    }

    public String getLover_2() {
        return lover_2;
    }

    public void setLover_2(String lover_2) {
        this.lover_2 = lover_2;
    }

    @Override
    public boolean equals(Object o) {//todo zelf maken
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equals(choser, relation.choser) &&
                Objects.equals(lover_1, relation.lover_1) &&
                Objects.equals(lover_2, relation.lover_2);
    }

    @Override
    public String toString() {//todo zelf maken
        return "Relation{" +
                "choser='" + choser + '\'' +
                ", lover_1='" + lover_1 + '\'' +
                ", lover_2='" + lover_2 + '\'' +
                '}';
    }
}
