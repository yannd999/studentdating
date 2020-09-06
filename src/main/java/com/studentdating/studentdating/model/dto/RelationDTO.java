package com.studentdating.studentdating.model.dto;

import com.studentdating.studentdating.model.exception.DomainException;

import java.util.Objects;

public class RelationDTO {
    private Long id;
    private String choser, lover_1, lover_2;

    public RelationDTO() {
    }

    public RelationDTO(String choser, String lover_1, String lover_2) {
        setChoser(choser);
        setLover_1(lover_1);
        setLover_2(lover_2);
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationDTO relation = (RelationDTO) o;
        return Objects.equals(choser, relation.choser) &&
                Objects.equals(lover_1, relation.lover_1) &&
                Objects.equals(lover_2, relation.lover_2);//todo zelf maken
    }

    @Override
    public String toString() {
        return "RelationDTO{" +//todo zelf maken
                "choser='" + choser + '\'' +
                ", lover_1='" + lover_1 + '\'' +
                ", lover_2='" + lover_2 + '\'' +
                '}';
    }
}
