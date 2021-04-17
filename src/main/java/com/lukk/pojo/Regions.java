package com.lukk.pojo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Regions {

    int regionid;
    String dnoregion;
    String shortname;
    Intensity intensity;
    List<GenerationMix> generationmix;
}
