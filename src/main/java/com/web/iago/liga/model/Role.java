package com.web.iago.liga.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<Permission> permissions;
}
