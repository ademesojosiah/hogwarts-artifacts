package com.jojo.testing.hogwartartfact.wizard;

import jakarta.validation.constraints.NotEmpty;

public record WizardDto(
        String id,

        @NotEmpty(message = "name of wizard is required")
        String name,

        Integer numberOfAntifacts) {
}
