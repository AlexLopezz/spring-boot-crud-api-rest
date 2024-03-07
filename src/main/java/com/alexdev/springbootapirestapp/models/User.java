package com.alexdev.springbootapirestapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "db_user")
public class User {
    @Id
    private Long id;

    private String name;
    private String lastName;
    private String email;
}
