package me.flezy.setter;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public enum
Progress {
    OPEN(),
    STARTED(),
    CLOSED();

    private boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }
}
