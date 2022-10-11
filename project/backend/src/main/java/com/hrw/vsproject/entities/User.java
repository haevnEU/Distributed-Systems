package com.hrw.vsproject.entities;

import lombok.Data;

/**
 * This class is a container class for a twitter user.<br>
 * It utilizes the lombok framework to handle utility methods like getter/setter, equals, etc
 */
@Data
public class User {
     private String id;
     private String name;
     private String username;
}

