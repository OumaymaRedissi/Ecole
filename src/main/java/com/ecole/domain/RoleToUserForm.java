package com.ecole.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleToUserForm{
    private String username;
    private String rolename;
}