package com.zcore.mabokeserver.user;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date created_at;
    private Date updated_at;
    private String last_login_at;
}
