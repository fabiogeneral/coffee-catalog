package com.personal.coffee_catalog.request;

import com.personal.coffee_catalog.constants.Constants;
import com.personal.coffee_catalog.constants.Constants.Role;
import lombok.Data;

@Data
public class RegisterRequest {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Constants.Role role = Role.USER; // default to USER
}
