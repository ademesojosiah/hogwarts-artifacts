package com.jojo.testing.hogwartartfact.artifacts;

import com.jojo.testing.hogwartartfact.sytem.ArtifactNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArtifactService {
    private final ArtifactRepository artifactRepository;

    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public Artifact findArtifactById(String artifactId){
        return this.artifactRepository.findById(artifactId).orElseThrow(()->{return new ArtifactNotFoundException(artifactId);
        });
    }

    public List<Artifact> findAllArtifacts(){
        return  this.artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact) {
        return  this.artifactRepository.save(newArtifact);
    }

    public Artifact updateArtifact(String artifactId , Artifact artifact){
        return this.artifactRepository.findById(artifactId)
                        .map(oldArtifact -> {
                            oldArtifact.setImageUrl(artifact.getImageUrl());
                            oldArtifact.setName(artifact.getName());
                            oldArtifact.setDescription(artifact.getDescription());
                            return this.artifactRepository.save(oldArtifact);
                        })
                .orElseThrow(()->new  ArtifactNotFoundException(artifactId));

    }

    public void deleteArtifact(String artifactId) {
       this.artifactRepository.findById(artifactId).orElseThrow(()-> new ArtifactNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);

    }
}
