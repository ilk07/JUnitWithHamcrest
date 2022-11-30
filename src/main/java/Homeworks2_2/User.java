package Homeworks2_2;

public class User {
    protected String login;
    protected String email;
    protected String pass;
    protected int age;

    public User(String login, String email,String pass, int age){
        this.login = login;
        this.email = email;
        this.pass = pass;
        this.age = age;
    }
    public String getLogin() {
        return login;
    }
    public String getPass() {
        return pass;
    }
    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    } //геттер добавлен в результате тестирования


    @Override
    public String toString() {
        return this.login + ", " + this.email + ", " + this.age;
    }
}
