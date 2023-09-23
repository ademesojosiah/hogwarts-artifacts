package com.jojo.testing.hogwartartfact.wizard.converter;

import com.jojo.testing.hogwartartfact.wizard.Wizard;
import com.jojo.testing.hogwartartfact.wizard.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {
    @Override
    public WizardDto convert(Wizard source) {
        WizardDto wizardDto = new WizardDto(source.getId(), source.getName(), source.getNumberOfArtifacts());
        return wizardDto;
    }
}
