package domain.model;

import domain.DomainException;

public class Message {
    private String zender, ontvanger, bericht;

    public Message() {}

    public Message(String zender, String ontvanger, String bericht) {
        setZender(zender);
        setOntvanger(ontvanger);
        setBericht(bericht);
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
