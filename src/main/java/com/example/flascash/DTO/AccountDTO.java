package com.example.flascash.DTO;

import com.example.flascash.Validation.ValidIban;

public class AccountDTO {
    @ValidIban
    private String iban;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
