package com.binas.tachographcms.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ADMIN")
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
