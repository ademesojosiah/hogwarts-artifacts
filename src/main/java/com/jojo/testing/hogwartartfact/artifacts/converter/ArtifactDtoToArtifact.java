package com.jojo.testing.hogwartartfact.artifacts.converter;

import com.jojo.testing.hogwartartfact.artifacts.Artifact;
import com.jojo.testing.hogwartartfact.artifacts.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoToArtifact implements Converter<ArtifactDto, Artifact> {
    @Override
    public Artifact convert(ArtifactDto source) {
       Artifact artifact = new Artifact();
       artifact.setId(source.id());
       artifact.setName(source.name());
       artifact.setDescription(source.description());
       artifact.setImageUrl(source.imageUrl());
       artifact.setOwner(null);
       return artifact;
    }
}
