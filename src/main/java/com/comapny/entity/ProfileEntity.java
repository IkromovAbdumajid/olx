package com.comapny.entity;

import com.comapny.enums.ProfileRole;
import com.comapny.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String login;
    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProfileRole profileRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;


}
