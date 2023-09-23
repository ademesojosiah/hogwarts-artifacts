package com.jojo.testing.hogwartartfact.artifacts;

import com.jojo.testing.hogwartartfact.sytem.ArtifactNotFoundException;
import com.jojo.testing.hogwartartfact.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    ArtifactService artifactService;

    List<Artifact> artifacts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dum");
        a1.setImageUrl("ImageUrl");

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer");
        a2.setImageUrl("ImageUrl");

        this.artifacts.add(a1);
        this.artifacts.add(a2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() {

        Artifact a = new Artifact();
        a.setId("1234r");
        a.setName("Invisible cloak");
        a.setDescription("An Invisible cloak is used to make the wearer invisible");
        a.setImageUrl("ImageUrl");

        Wizard w = new Wizard();
        w.setId("1");
        w.setName("Harry Potter");
        a.setOwner(w);

        //Given
        given(artifactRepository.findById("1234r")).willReturn(Optional.of(a));


        //When
        Artifact returnArtifact = artifactService.findArtifactById("1234r");

        //Then
        assertEquals(returnArtifact.getId(),a.getId());
        assertEquals(returnArtifact.getName(),a.getName());
        assertEquals(returnArtifact.getDescription(),a.getDescription());
        assertEquals(returnArtifact.getImageUrl(),a.getImageUrl());

        verify(artifactRepository,times(1)).findById("1234r");

    }
    @Test
    void testFindByIdNotfound(){
        //Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());


        //Whem
        Throwable thrown = catchThrowable(()->{
            Artifact returnedArtifact = artifactService.findArtifactById("1234r");
        });

        //then
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class).hasMessage("Could not find artifact with Id 1234r");
    }

    @Test
    void testFindAllSuccess(){
        //Given
        given(artifactRepository.findAll()).willReturn(this.artifacts);

        //when
        List<Artifact> foundArtifacts = this.artifactService.findAllArtifacts();

        //Then
        assertThat(foundArtifacts.size()).isEqualTo(this.artifacts.size());
        verify(artifactRepository,times(1)).findAll();
    }

    @Test
    void testSaveSuccess(){
        //Given
        Artifact newArtifact = new Artifact();
        newArtifact.setId("12345");
        newArtifact.setName("artifact..");
        newArtifact.setDescription("description...");
        newArtifact.setImageUrl("imageUrl...");

        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        //When
        Artifact savedArtifact = artifactService.save(newArtifact);

        //Then
        assertThat(savedArtifact.getId()).isEqualTo(newArtifact.getId());
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        verify(artifactRepository,times(1)).save(newArtifact);
    }

    @Test
    void  testUpdateSuccess(){

        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("123r");
        oldArtifact.setDescription("description .......");
        oldArtifact.setName("artifact....");
        oldArtifact.setImageUrl("imageUrl");

        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setImageUrl("oldArtifact.getImageUrl()");
        updatedArtifact.setName("yeeee");
        updatedArtifact.setId("123r");
        updatedArtifact.setDescription("updated version");

        //Given
        given(this.artifactRepository.findById("123r")).willReturn(Optional.of(oldArtifact));
        given(this.artifactRepository.save(oldArtifact)).willReturn(oldArtifact);

        //When
        Artifact update = this.artifactService.updateArtifact("123r",updatedArtifact);


        //then
        assertThat(update.getId()).isEqualTo("123r");
        assertThat(update.getDescription()).isEqualTo(updatedArtifact.getDescription());

        verify(artifactRepository,times(1)).findById("123r");
        verify(artifactRepository,times(1)).save(oldArtifact);

    }

    @Test
    void testUpdateNotFound(){

        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setImageUrl("oldArtifact.getImageUrl()");
        updatedArtifact.setName("yeeee");
        updatedArtifact.setDescription("updated version");

        //Given

        given(artifactRepository.findById("123r")).willReturn(Optional.empty());

        //then

        assertThrows(ArtifactNotFoundException.class,()->{
            artifactService.updateArtifact("123r",updatedArtifact);
        });

        verify(artifactRepository,times(1)).findById("123r");

    }

    @Test
    void testDeleteSuccess()throws Exception{
        //Given
        Artifact artifact = new Artifact();
        artifact.setId("4b7391a0-3dbc-49a6-845b-54ad4c0c515");
        artifact.setImageUrl("oldArtifact.getImageUrl()");
        artifact.setName("yeeee");
        artifact.setDescription("updated version");

        given(artifactRepository.findById("4b7391a0-3dbc-49a6-845b-54ad4c0c515")).willReturn(Optional.of(artifact));
        doNothing().when(artifactRepository).deleteById("4b7391a0-3dbc-49a6-845b-54ad4c0c515");

        //When

        artifactService.deleteArtifact("4b7391a0-3dbc-49a6-845b-54ad4c0c515");

            //Then
        verify(artifactRepository,times(1)).deleteById("4b7391a0-3dbc-49a6-845b-54ad4c0c515");

    }

    @Test
    void testDeleteFailure()throws Exception{


        given(artifactRepository.findById("4b7391a0-3dbc-49a6-845b-54ad4c0c515")).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(()->{
            artifactService.deleteArtifact("4b7391a0-3dbc-49a6-845b-54ad4c0c515");
        });

        //Then
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class).hasMessage("Could not find artifact with Id 4b7391a0-3dbc-49a6-845b-54ad4c0c515");
        verify(artifactRepository,times(1)).findById("4b7391a0-3dbc-49a6-845b-54ad4c0c515");

    }
}