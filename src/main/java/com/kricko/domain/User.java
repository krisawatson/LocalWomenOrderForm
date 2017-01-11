package com.kricko.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.kricko.constants.Role;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String firstname;
    private String lastname;
    @NotNull
    private String email;
    @NotNull
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }
    
    public Role getRole ()
    {
        return role;
    }

    public void setRole (Role role)
    {
        this.role = role;
    }
}
