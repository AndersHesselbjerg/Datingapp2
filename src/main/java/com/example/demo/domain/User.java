package com.example.demo.domain;


public class User {
       private int userid;
       private String userName;
       private String password;
       private String firstName;
       private String lastName;
       private String creditInfo;
       private String phone;
       private String mail;
       private String description;
       private String tags;
       private String timeOfRegistry;
       private int score;

       public User(int userid, String userName, String password, String firstName, String lastName, String creditInfo, String phone, String mail, String description, String tags, String timeOfRegistry, int score) {
              this.userid = userid;
              this.userName = userName;
              this.password = password;
              this.firstName = firstName;
              this.lastName = lastName;
              this.creditInfo = creditInfo;
              this.phone = phone;
              this.mail = mail;
              this.description = description;
              this.tags = tags;
              this.timeOfRegistry = timeOfRegistry;
              this.score = score;
       }

       public String getTimeOfRegistry() {
              return timeOfRegistry;
       }

       public void setTimeOfRegistry(String timeOfRegistry) {
              this.timeOfRegistry = timeOfRegistry;
       }

       public int getScore() {
              return score;
       }

       public void setScore(int score) {
              this.score = score;
       }

       public String getCreditInfo() {
              return creditInfo;
       }

       public void setCreditInfo(String credit_info) {
              this.creditInfo = credit_info;
       }

       public String getPhone() {
              return phone;
       }

       public void setPhone(String phone) {
              this.phone = phone;
       }

       public String getMail() {
              return mail;
       }

       public void setMail(String mail) {
              this.mail = mail;
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

       public int getUserid() {
              return userid;
       }

       public void setUserid(int userid) {
              this.userid = userid;
       }

       public String getUserName() {
              return userName;
       }

       public void setUserName(String userName) {
              this.userName = userName;
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
