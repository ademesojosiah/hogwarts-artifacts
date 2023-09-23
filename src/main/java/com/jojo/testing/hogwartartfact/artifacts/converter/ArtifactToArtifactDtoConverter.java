package com.jojo.testing.hogwartartfact.artifacts.converter;

import com.jojo.testing.hogwartartfact.artifacts.Artifact;
import com.jojo.testing.hogwartartfact.artifacts.ArtifactDto;
import com.jojo.testing.hogwartartfact.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {

    public WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        ArtifactDto artifactDto = new ArtifactDto(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null ? this.wizardToWizardDtoConverter.convert(source.getOwner()) : null );
        return artifactDto;
    }
}
