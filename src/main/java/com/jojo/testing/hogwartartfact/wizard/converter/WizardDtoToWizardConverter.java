package com.jojo.testing.hogwartartfact.wizard.converter;

import com.jojo.testing.hogwartartfact.wizard.Wizard;
import com.jojo.testing.hogwartartfact.wizard.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {


    @Override
    public Wizard convert(WizardDto source) {
        Wizard w = new Wizard();
        w.setId(source.id());
        w.setName(source.name());
        w.setArtifacts(null);

        return w;
    }
}
