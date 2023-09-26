package com.jojo.testing.hogwartartfact.wizard;


import com.jojo.testing.hogwartartfact.sytem.WizardNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WizardService {

    WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }

    public Wizard getWizard(String wizardId){
        return this.wizardRepository.findById(wizardId).orElseThrow(()-> new WizardNotFoundException(wizardId));
    }


    public List<Wizard> getAllWizards() {
        return this.wizardRepository.findAll();
    }

    public Wizard addWizard(Wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    public Wizard updateWizard(String wizardId ,Wizard newWizard) {
        return  this.wizardRepository.findById(wizardId)
                .map((wizard -> {
                    wizard.setName(newWizard.getName());
                    Wizard updatedWizard = this.wizardRepository.save(wizard);
                    return updatedWizard;
                })).orElseThrow(()-> new WizardNotFoundException(wizardId));
    }

    public void deleteWizard(String wizardId) {
        this.wizardRepository.findById(wizardId)
                .orElseThrow(()-> new WizardNotFoundException(wizardId));

        this.wizardRepository.deleteById(wizardId);

    }
}
