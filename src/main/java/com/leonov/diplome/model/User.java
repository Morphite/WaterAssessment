package com.leonov.diplome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    @JsonProperty
    private String id;

    @JsonProperty
    private String login;

    @JsonProperty
    private String pass;

    @JsonProperty
    private String name;

    @JsonProperty
    private String surname;

    @JsonProperty
    private String role;

    public User() {
        this.id = UUID.randomUUID().toString();
    }
}
