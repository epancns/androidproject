package edu.uph.m23si2.pertamaapp.model;

import java.security.PrivateKey;

public class Provinsi {
    private String Code, Name;

    public Provinsi(String code, String name) {
        Code = code;
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
