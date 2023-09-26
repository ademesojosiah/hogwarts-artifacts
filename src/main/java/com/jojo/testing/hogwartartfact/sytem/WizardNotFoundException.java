package com.jojo.testing.hogwartartfact.sytem;

public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException(String wizardId){
        super("Could not find wizard with Id : " + wizardId);
    }
}
