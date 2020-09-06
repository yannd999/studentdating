package com.studentdating.studentdating.model.dto;

import com.studentdating.studentdating.model.exception.DomainException;

public class MessageDTO {
    private Long id;
    private String bericht, zender, ontvanger;

    public MessageDTO() {}

    public MessageDTO(String zender, String ontvanger, String bericht) {
        setZender(zender);
        setOntvanger(ontvanger);
        setBericht(bericht);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        if (id < 0) throw new DomainException("Id is invalid");
        this.id = id;
    }

    public String getZender() {
        return zender;
    }

    public void setZender(String zender) {
        if (zender.trim().isEmpty()) throw new DomainException("Zender is invalid");
        else this.zender = zender;
    }

    public String getOntvanger() {
        return ontvanger;
    }

    public void setOntvanger(String ontvanger) {
        if (ontvanger.trim().isEmpty()) throw new DomainException("Ontvanger is invalid");
        this.ontvanger = ontvanger;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public String toString() {
        return "{\"zender\":\"" + zender + "\",\"ontvanger\":\"" + ontvanger + "\",\"bericht\":\"" + bericht + "\"}";
    }
}
