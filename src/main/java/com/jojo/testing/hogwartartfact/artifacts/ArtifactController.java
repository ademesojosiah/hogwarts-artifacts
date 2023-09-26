package com.jojo.testing.hogwartartfact.artifacts;

import com.jojo.testing.hogwartartfact.artifacts.converter.ArtifactDtoToArtifact;
import com.jojo.testing.hogwartartfact.artifacts.converter.ArtifactToArtifactDtoConverter;
import com.jojo.testing.hogwartartfact.sytem.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class   ArtifactController {
    private final ArtifactService artifactService;
    public final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    public final ArtifactDtoToArtifact artifactDtoToArtifact;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifact artifactDtoToArtifact) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifact = artifactDtoToArtifact;
    }

    @GetMapping("{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
        Artifact returnedArtifact = this.artifactService.findArtifactById(artifactId);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(returnedArtifact);
        return  new Result(true,200,"find one success",artifactDto);
    }

    @GetMapping
    public Result findAllArtifacts(){
        List<Artifact> artifacts = this.artifactService.findAllArtifacts();
        List<ArtifactDto> artifactDtos = new ArrayList<>();
        artifacts.forEach(artifact -> {
            artifactDtos.add(this.artifactToArtifactDtoConverter.convert(artifact));
        });

        return new Result(true, 200,"find all success",artifactDtos);
    }
    @PostMapping
    public Result createArtifact(@Valid @RequestBody ArtifactDto artifactDto){
        Artifact newArtifact = this.artifactDtoToArtifact.convert(artifactDto);
        Artifact savedArtifact = this.artifactService.save(newArtifact);
        ArtifactDto artifactDto1 = this.artifactToArtifactDtoConverter.convert(savedArtifact);
        return new Result(true,200,"Add Success", artifactDto1);
    }

    @PutMapping("{artifactId}")
    public  Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto){
        Artifact artifact = this.artifactDtoToArtifact.convert(artifactDto);
        Artifact artifact1 = this.artifactService.updateArtifact(artifactId,artifact);
        ArtifactDto artifactDto1 = this.artifactToArtifactDtoConverter.convert(artifact1);

        return new Result(true,200,"Update Success",artifactDto1);
    }

    @DeleteMapping(value = "{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId){
        this.artifactService.deleteArtifact(artifactId);

        return  new Result(true,200,"Delete Success");
    }


}
