package com.jojo.testing.hogwartartfact.sytem;

import com.jojo.testing.hogwartartfact.artifacts.Artifact;
import com.jojo.testing.hogwartartfact.artifacts.ArtifactRepository;
import com.jojo.testing.hogwartartfact.wizard.Wizard;
import com.jojo.testing.hogwartartfact.wizard.WizardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DBDataInitializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;
    private final WizardRepository wizardRepository;

    public DBDataInitializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
    }

    @Override
    public void run (String[] args)throws Exception{
        Artifact a1 = new Artifact();
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dum");
        a1.setImageUrl("ImageUrl");

        Artifact a2 = new Artifact();
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer");
        a2.setImageUrl("ImageUrl");

        Artifact a3 = new Artifact();
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the");
        a3.setImageUrl("ImageUrl");

        Artifact a4 = new Artifact();
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts created by Remus Lupin ");
        a4.setImageUrl("ImageUrl");

        Artifact a5 = new Artifact();
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with Large rubies o");
        a5.setImageUrl("ImageUrl");

        Artifact a6 = new Artifact();
        a6.setName ("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to brin ");
        a6.setImageUrl("ImageUrt");

        Wizard w1 = new Wizard();
        w1.setName("Albus Dumbledore");
        w1.addArtifact(a1);
        w1.addArtifact(a3);

        Wizard w2 = new Wizard();
        w2.setName("Harry Potter");
        w2.addArtifact(a2);
        w2.addArtifact(a2);

        Wizard w3 = new Wizard();
        w3.setName("Neville Longbottom");
        w3.addArtifact(a5);

        wizardRepository.saveAll(List.of(w1,w2,w3));
        artifactRepository.save(a6);
    }
}
