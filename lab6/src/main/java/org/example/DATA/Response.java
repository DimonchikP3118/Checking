package org.example.DATA;

import java.io.Serializable;

public class Response implements Serializable {
    private String message;
    private Boolean isBe;

    public Response(String message) {
        this.message = message;
    }

    public Response(Boolean isBe) {
        this.isBe = isBe;
    }

    public Boolean getBe() {
        return isBe;
    }

    public void setBe(Boolean be) {
        isBe = be;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                '}';
    }
}
