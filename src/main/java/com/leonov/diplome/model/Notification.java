package com.leonov.diplome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "notification")
public class Notification {

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
    private String notes;

    @JsonProperty
    private String color;

    @JsonProperty
    private boolean read;

    @JsonProperty
    private boolean reported;

    public Notification() {
        this.id = UUID.randomUUID().toString();
    }
}
