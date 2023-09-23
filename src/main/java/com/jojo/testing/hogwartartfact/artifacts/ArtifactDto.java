package com.jojo.testing.hogwartartfact.artifacts;

import com.jojo.testing.hogwartartfact.wizard.WizardDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto(String id,

                          @NotEmpty(message = "name is required")
                          String name,

                          @NotEmpty(message = "description is required")
                          String description,

                          @NotEmpty(message = "ImageUrl required")
                          String imageUrl,

                          WizardDto Owner) {
}
