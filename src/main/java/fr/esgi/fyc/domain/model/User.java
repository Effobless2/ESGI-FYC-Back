package fr.esgi.fyc.domain.model;

public class User {

    private int id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int nbTestLogin;
    private boolean isBlocked;

    public User(int id, String password, String firstName, String lastName, String email, String role, int nbTestLogin, boolean isBlocked) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.nbTestLogin = nbTestLogin;
        this.isBlocked = isBlocked;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNbTestLogin(){
        return nbTestLogin;
    }

    public void setNbTestLogin(int nbTestLogin){
        this.nbTestLogin = nbTestLogin;
    }

    public boolean getIsBlocked(){
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked){
        this.isBlocked = isBlocked;
    }



}
