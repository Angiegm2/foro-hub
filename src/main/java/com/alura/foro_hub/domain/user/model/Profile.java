package com.alura.foro_hub.domain.user.model;


import com.alura.foro_hub.domain.user.dtos.CreateProfileDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String country;

    @OneToOne
    private User user;

    public Profile(CreateProfileDto profile, User user){
        this.name = profile.name();
        this.user = user;
        this.lastName = profile.lastName();
        this.country = profile.country();
    }


}
