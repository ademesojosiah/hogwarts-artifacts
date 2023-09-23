package com.jojo.testing.hogwartartfact.artifacts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojo.testing.hogwartartfact.sytem.ArtifactNotFoundException;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtifactService artifactService;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();
        Artifact a = new Artifact();
        a.setId("123r");
        a.setName("illuminator");
        a.setDescription("illuminator is used by harry potter");
        a.setImageUrl("imageUrl");
        this.artifacts.add(a);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() throws Exception {
        //Given
        given(this.artifactService.findArtifactById("123r")).willReturn(this.artifacts.get(0));

        //When and then
        this.mockMvc.perform(get("/api/v1/artifacts/123r").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("123r"));
    }


    @Test
    void testFindArtifactByIdNotfound() throws Exception {
        //Given
        given(this.artifactService.findArtifactById("123r")).willThrow(new ArtifactNotFoundException("123r"));

        //When and then
        this.mockMvc.perform(get("/api/v1/artifacts/123r").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllSuccess() throws Exception {
        //Given
        given(this.artifactService.findAllArtifacts()).willReturn(this.artifacts);

        //when and then
        this.mockMvc.perform(get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())));
    }

    @Test
    void testSaveSuccess() throws Exception {
        //Given
        ArtifactDto newArtifact = new ArtifactDto(null,
                "illuminator",
                "illuminator is used by harry potter",
                "imageUrl",null);

        String json = objectMapper.writeValueAsString(newArtifact);

        Artifact a = new Artifact();
        a.setId("123r");
        a.setName("illuminator");
        a.setDescription("illuminator is used by harry potter");
        a.setImageUrl("imageUrl");

        given(artifactService.save(Mockito.any(Artifact.class))).willReturn(a);

        this.mockMvc.perform(post("/api/v1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty());


    }

    @Test
    void testUpdateSuccess() throws Exception {
        ArtifactDto artifactDto = new ArtifactDto("123r","Remembrall","Remembrall Description", "imageUrl", null);

        Artifact updateArtifact = new Artifact();
        updateArtifact.setImageUrl("imageUrl");
        updateArtifact.setId("123r");
        updateArtifact.setDescription("Remembrall Description");
        updateArtifact.setName("Remembrall");

        String json = this.objectMapper.writeValueAsString(artifactDto);

        given(artifactService.updateArtifact(eq("123r"), Mockito.any(Artifact.class))).willReturn(updateArtifact);

        this.mockMvc.perform(put("/api/v1/artifacts/123r").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(updateArtifact.getId()))
                .andExpect(jsonPath("$.data.description").value(updateArtifact.getDescription()));

    }

    @Test
    void testUpdateFailure() throws Exception {
            ArtifactDto artifactDto = new ArtifactDto("123r","Remembrall","An instrument for .....","imageUrl",null);
            String json = this.objectMapper.writeValueAsString(artifactDto);

            //given
            given(artifactService.updateArtifact(eq("123r"),Mockito.any(Artifact.class))).willThrow(new ArtifactNotFoundException("123r"));

                    // when and them
            this.mockMvc.perform(put("/api/v1/artifacts/123r").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(false))
                    .andExpect(jsonPath("$.code").value(404))
                    .andExpect(jsonPath("$.message").value("Could not find artifact with Id " + "123r" ))
                    .andExpect(jsonPath("$.message").exists())
                    .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactSuccess() throws  Exception{

        //Given
        doNothing().when(this.artifactService).deleteArtifact("14b7391a0-3dbc-49a6-845b-54ad4c0c515");


        this.mockMvc.perform(delete("/api/v1/artifacts/14b7391a0-3dbc-49a6-845b-54ad4c0c515").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Delete Success" ))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactErrorWithNonExistenceId()throws Exception{

        //Given
        doThrow(new ArtifactNotFoundException("14b7391a0-3dbc-49a6-845b-54ad4c0c515")).when(artifactService).deleteArtifact("14b7391a0-3dbc-49a6-845b-54ad4c0c515");

        //When and Then

        this.mockMvc.perform(delete("/api/v1/artifacts/14b7391a0-3dbc-49a6-845b-54ad4c0c515").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id " + "14b7391a0-3dbc-49a6-845b-54ad4c0c515" ));
    }

}