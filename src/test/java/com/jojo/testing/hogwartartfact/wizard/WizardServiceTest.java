package com.jojo.testing.hogwartartfact.wizard;

import com.jojo.testing.hogwartartfact.sytem.WizardNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @InjectMocks
    WizardService wizardService;

    List<Wizard> wizards;

    @BeforeEach
    void setUp() {
        this.wizards = new ArrayList<Wizard>();
        Wizard w = new Wizard();
        w.setId("123456r");
        w.setName("Haary");

        Wizard w2 = new Wizard();
        w2.setId("12345r");
        w2.setName("George");


        this.wizards.add(w);
        this.wizards.add(w2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindWizardById() {
        Wizard w = new Wizard();
        w.setId("123456r");
        w.setName("Haary");

        //Given

        given(wizardRepository.findById("123456r")).willReturn(Optional.of(w));


        //When
        Wizard wizard = wizardService.getWizard("123456r");


        //Then
        assertThat(wizard.getId()).isEqualTo(w.getId());
        assertThat(wizard.getName()).isEqualTo(w.getName());

        verify(wizardRepository,times(1)).findById("123456r");


    }

    @Test
    void testFindWizardByIdFailure() {
        Wizard w = new Wizard();
        w.setId("123456r");
        w.setName("Haary");

        //Given

        given(wizardRepository.findById("123456r")).willReturn(Optional.empty());





        //Then
        assertThrows(WizardNotFoundException.class,()->{
            wizardService.getWizard("123456r");
        });

        verify(wizardRepository,times(1)).findById("123456r");


    }


    @Test
    void testFindAllWizardSuccess() {

        //Given

        given(wizardRepository.findAll()).willReturn(this.wizards);


        //When
        List<Wizard> wizards = wizardService.getAllWizards();


        //Then
        assertThat(wizards.size()).isEqualTo(this.wizards.size());

        verify(wizardRepository,times(1)).findAll();


    }

    @Test
    void testAddWizardSuccess() {
        Wizard w = new Wizard();
        w.setName("Haary");


        //Given

        given(wizardRepository.save(w)).willReturn(this.wizards.get(0));


        //When
        Wizard savedWizard = wizardService.addWizard(w);


        //Then
        assertThat(savedWizard.getName()).isEqualTo(this.wizards.get(0).getName());

        verify(wizardRepository,times(1)).save(w);


    }

    @Test
    void testUpdateWizardSuccess() {

        Wizard oldWizard = new Wizard();
        oldWizard.setId("123456r");
        oldWizard.setName("Haary");

        Wizard updatedWizard = new Wizard();
        updatedWizard.setId("123456r");
        updatedWizard.setName("george");


        //Given

        given(wizardRepository.findById("123456r")).willReturn(Optional.of(oldWizard));
        given(wizardRepository.save(oldWizard)).willReturn(oldWizard);


        //When
        Wizard savedWizard = wizardService.updateWizard("123456r", updatedWizard);


        //Then
        assertThat(savedWizard.getName()).isEqualTo(updatedWizard.getName());
        assertThat(savedWizard.getId()).isNotNull();

        verify(wizardRepository,times(1)).findById("123456r");
        verify(wizardRepository,times(1)).save(oldWizard);


    }

    @Test
    void testUpdateWizardWizardNotFoundException() {


        Wizard updatedWizard = new Wizard();
        updatedWizard.setId("123456r");
        updatedWizard.setName("george");


        //Given

        given(wizardRepository.findById("123456r")).willReturn(Optional.empty());


        //When and Then

        assertThrows(WizardNotFoundException.class,()->{
            wizardService.updateWizard("123456r",updatedWizard);
        });

        verify(wizardRepository,times(1)).findById("123456r");


    }

    @Test
    void testDeleteWizardSuccess() {

        Wizard oldWizard = new Wizard();
        oldWizard.setId("123456r");
        oldWizard.setName("Haary");



        //Given

        given(wizardRepository.findById("123456r")).willReturn(Optional.of(oldWizard));
        doNothing().when(wizardRepository).deleteById("123456r");

        //When
       wizardService.deleteWizard("123456r");


        //Then

        verify(wizardRepository,times(1)).findById("123456r");


    }

    @Test
    void testDeleteWizardNotFoundExceptions() {

        Wizard oldWizard = new Wizard();
        oldWizard.setId("123456r");
        oldWizard.setName("Haary");



        //Given

        given(wizardRepository.findById("123456r")).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(()->{
            wizardService.deleteWizard("123456r");
        });


        //Then

        assertThat(thrown).isInstanceOf(WizardNotFoundException.class).hasMessage("Could not find wizard with Id : 123456r");

        verify(wizardRepository,times(1)).findById("123456r");


    }


}