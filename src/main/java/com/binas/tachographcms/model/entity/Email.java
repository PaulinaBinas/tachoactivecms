package com.binas.tachographcms.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Email {

    @Id
    private Long id;
    private String email;

    public Email(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
