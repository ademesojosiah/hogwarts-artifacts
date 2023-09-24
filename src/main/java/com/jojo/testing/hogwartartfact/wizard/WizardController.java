package com.jojo.testing.hogwartartfact.wizard;

import com.jojo.testing.hogwartartfact.sytem.Result;
import com.jojo.testing.hogwartartfact.wizard.converter.WizardDtoToWizardConverter;
import com.jojo.testing.hogwartartfact.wizard.converter.WizardToWizardDtoConverter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wizards")
public class WizardController {

    WizardService wizardService;
    WizardToWizardDtoConverter wizardToWizardDtoConverter;

    WizardDtoToWizardConverter wizardDtoToWizardConverter;

    public WizardController(WizardService wizardService, WizardToWizardDtoConverter wizardToWizardDtoConverter, WizardDtoToWizardConverter wizardDtoToWizardConverter) {
        this.wizardService = wizardService;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
    }

    @GetMapping("/{wizardId}")
    public Result getWizardById(@PathVariable String wizardId){
        Wizard wizard = this.wizardService.getWizard(wizardId);
        WizardDto wizardDto = this.wizardToWizardDtoConverter.convert(wizard);

        return  new Result(true,200,"found wizard successfully",wizardDto);
    }

    @GetMapping
    public Result getAllWizards(){
        List<WizardDto> wizards = this.wizardService.getAllWizards().stream().map(this.wizardToWizardDtoConverter::convert).collect(Collectors.toList());

        return new Result(true,200,"All wizards retrieved",wizards);
    }

    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto){
        Wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        Wizard savedWizard = this.wizardService.addWizard(newWizard);

        WizardDto wizard = this.wizardToWizardDtoConverter.convert(savedWizard);

        return new Result(true,200,"successfully added a wizard",wizard);
    }

    @PutMapping("/{wizardId}")
    public  Result updateWizard(@Valid @RequestBody WizardDto wizardDto, @PathVariable String wizardId){
        Wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        Wizard updatedWizard = this.wizardService.updateWizard(wizardId,newWizard);

        WizardDto wizard = this.wizardToWizardDtoConverter.convert(updatedWizard);

        return new Result(true,200,"successfully updated wizard",wizard);


    }

    @DeleteMapping("/{wizardId}")
    public  Result deleteWizard( @PathVariable String wizardId){
        this.wizardService.deleteWizard(wizardId);

        return new Result(true,200,"successfully deleted wizard with id: " + wizardId);


    }
}
