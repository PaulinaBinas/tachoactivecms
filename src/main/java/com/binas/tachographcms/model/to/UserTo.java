package com.binas.tachographcms.model.to;

import lombok.Data;

@Data
public class UserTo {

    private Long id;

    private Long code;

    private String name;

    private String surname;

    private String phoneNumber;

    private String companyName;

    private Integer daysReminder;

    private String serialNumber;
}
