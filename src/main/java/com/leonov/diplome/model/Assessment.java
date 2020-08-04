package com.leonov.diplome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "assessment")
public class Assessment {

    @Id
    @JsonProperty
    private String id;

    @JsonProperty
    private String objectId;

    @JsonProperty
    private String name;

    @JsonProperty
    private LocalDateTime dateTime;

    @JsonProperty
    private double ph;

    @JsonProperty
    private double hum;

    @JsonProperty
    private double trophy;

    @JsonProperty
    private double oxygen;

    @JsonProperty
    private double phClass;

    @JsonProperty
    private double humClass;

    @JsonProperty
    private double trophyClass;

    @JsonProperty
    private double oxygenClass;

    @JsonProperty
    private double assessmentResult;

    @JsonProperty
    private String notes;

    public Assessment() {
        this.id = UUID.randomUUID().toString();
    }
}
