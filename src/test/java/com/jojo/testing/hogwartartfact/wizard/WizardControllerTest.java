package com.jojo.testing.hogwartartfact.wizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojo.testing.hogwartartfact.sytem.WizardNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class WizardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WizardService wizardService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

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
    void getWizardById() throws Exception {


        //Given
        given(this.wizardService.getWizard("123456r")).willReturn(this.wizards.get(0));


        //When and then

        this.mockMvc.perform(get(this.baseUrl+"/wizards/123456r").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("123456r"));
    }

    @Test
    void getWizardByIdNotFound() throws Exception {


        //Given
        given(this.wizardService.getWizard("123456r")).willThrow(new WizardNotFoundException("123456r"));


        //When and then

        this.mockMvc.perform(get(this.baseUrl+"/wizards/123456r").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id : 123456r"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getAllWizardSuccess() throws Exception {


        //Given
        given(this.wizardService.getAllWizards()).willReturn(this.wizards);


        //When and then

        this.mockMvc.perform(get(this.baseUrl+"/wizards").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("All wizards retrieved"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.wizards.size())));
    }


    @Test
    void addWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null,"astro", null);

        String json = this.objectMapper.writeValueAsString(wizardDto);

        Wizard savedWizard = new Wizard();
        savedWizard.setId("123456r");
        savedWizard.setName("Haary");



        //Given
        given(this.wizardService.addWizard(Mockito.any(Wizard.class))).willReturn(savedWizard);


        //When and then

        this.mockMvc.perform(post(this.baseUrl+"/wizards").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("successfully added a wizard"))
                .andExpect(jsonPath("$.data.name").value(savedWizard.getName()));
    }



    @Test
    void updateWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto("123456r","astro", null);

        String json = this.objectMapper.writeValueAsString(wizardDto);

        Wizard updatedWizard = new Wizard();
        updatedWizard.setId("123456r");
        updatedWizard.setName("Haary");



        //Given
        given(this.wizardService.updateWizard(eq("123456r"),Mockito.any(Wizard.class))).willReturn(updatedWizard);


        //When and then

        this.mockMvc.perform(put(this.baseUrl+"/wizards/123456r").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("successfully updated wizard"))
                .andExpect(jsonPath("$.data.id").value(updatedWizard.getId()));
    }

    @Test
    void updateWizardFailure() throws Exception {
        WizardDto wizardDto = new WizardDto("123456r","astro", null);

        String json = this.objectMapper.writeValueAsString(wizardDto);

        Wizard savedWizard = new Wizard();
        savedWizard.setId("123456r");
        savedWizard.setName("Haary");



        //Given
        given(this.wizardService.updateWizard(eq("123456r"),Mockito.any(Wizard.class))).willThrow(new WizardNotFoundException(savedWizard.getId()));


        //When and then

        this.mockMvc.perform(put(this.baseUrl+"/wizards/123456r").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id : "+ savedWizard.getId()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void deleteWizardSuccess() throws Exception {


        Wizard updatedWizard = new Wizard();
        updatedWizard.setId("123456r");
        updatedWizard.setName("Haary");



        //Given
       doNothing().when(wizardService).deleteWizard("123456r");


        //When and then

        this.mockMvc.perform(delete(this.baseUrl+"/wizards/123456r").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("successfully deleted wizard with id: 123456r"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void deleteWizardFailure() throws Exception {




        //Given
        doThrow(new WizardNotFoundException("123456r")).when(wizardService).deleteWizard("123456r");

        //When and then

        this.mockMvc.perform(delete(this.baseUrl+"/wizards/123456r").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id : 123456r"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}


