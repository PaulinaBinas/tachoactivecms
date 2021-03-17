package com.binas.tachographcms.model.entity;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Nullable
    private String code;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String companyName;

    @Nullable
    private Integer daysReminder;

    @Nullable
    private String serialNumber;
}
