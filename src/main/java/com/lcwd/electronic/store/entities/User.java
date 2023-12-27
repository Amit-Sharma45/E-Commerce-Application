package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email", unique = true)
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
}
