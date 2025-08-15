package com.example.ComputerClub.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String email;
    private String phone;
    private String password;
    @Column(name = "fullName")
    private String fullName;
    private Double wallet;
    @Column(name = "photoPath")
    private String photoPath;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", nullable = false)
    private Roles role;

    public int getId() {return id;}
    public String getLogin() {return login;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getFullName() {return fullName;}
    public Double getWallet() {return wallet;}
    public String getPhotoPath() {return photoPath;}
    public Roles getRole() {return role;}
    public String getPassword() {return password;}

    public void setId(int id) {this.id = id;}
    public void setLogin(String login) {this.login = login;}
    public void setEmail(String email) {this.email = email;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setWallet(Double wallet) {this.wallet = wallet;}
    public void setPhotoPath(String photoPath) {this.photoPath = photoPath;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(Roles role) {this.role = role;}
}
