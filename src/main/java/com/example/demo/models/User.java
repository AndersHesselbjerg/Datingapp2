package com.example.demo.models;


public class User {
       private String userid;
       private String username;
       private String password;
       private String firstName;
       private String lastName;
       private String description;
       private String tags;

       public User(String userid, String username, String password, String firstName, String lastName, String description, String tags) {
              this.userid = userid;
              this.username = username;
              this.password = password;
              this.firstName = firstName;
              this.lastName = lastName;
              this.description = description;
              this.tags = tags;
       }

       public String getDescription() {
              return description;
       }

       public void setDescription(String description) {
              this.description = description;
       }

       public String getTags() {
              return tags;
       }

       public void setTags(String tags) {
              this.tags = tags;
       }

       public String getUserid() {
              return userid;
       }

       public void setUserid(String userid) {
              this.userid = userid;
       }

       public String getUsername() {
              return username;
       }

       public void setUsername(String username) {
              this.username = username;
       }

       public String getPassword() {
              return password;
       }

       public void setPassword(String password) {
              this.password = password;
       }

       public String getFirstName() {
              return firstName;
       }

       public void setFirstName(String firstName) {
              this.firstName = firstName;
       }

       public String getLastName() {
              return lastName;
       }

       public void setLastName(String lastName) {
              this.lastName = lastName;
       }





}
