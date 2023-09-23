package com.jojo.testing.hogwartartfact.sytem;


public class ArtifactNotFoundException extends RuntimeException {
   public ArtifactNotFoundException(String id){
        super("Could not find artifact with Id " + id);

    }
}
