package com.leonov.diplome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "research")
public class Research {

    @Id
    @JsonProperty
    private String id;

    @JsonProperty
    private String objectId;

    @JsonProperty
    private String name;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
    private String notes;

    public Research() {
        this.id = UUID.randomUUID().toString();
    }
}
