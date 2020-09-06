package com.studentdating.studentdating.model.entity;

import com.studentdating.studentdating.model.exception.DomainException;

import javax.persistence.*;

@Entity
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="message")
    private String bericht;
    @Column(name="sender")
    private String zender;
    @Column(name="receiver")
    private String ontvanger;

    public Message() {}

    public Message(String zender, String ontvanger, String bericht) {
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
