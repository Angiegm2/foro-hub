package com.alura.foro_hub.domain.user.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // Define 'name' como atributo

    // Constructor adicional si es necesario
    public Role(String name) {
        this.name = name;
    }
}
