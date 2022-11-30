package Homeworks2_2;

import java.util.*;

public class Main {

    public static void main(String[] args) throws UserNotFoundException, AccessDeniedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите логин");
        String login = scanner.nextLine();

        System.out.println("Введите пароль");
        String password = scanner.nextLine();

        //Проверить логин и пароль
        User user = getUserByLoginAndPassword(login,password);
        //Вызвать методы валидации пользователя
        validateUser(user);

        System.out.println("Доступ предоставлен");
    }

    public static User[] getUsers() {
        User user1 = new User("John", "jhon@gmail.com", "pass", 24);
        User user2 = new User("Donald", "don@gmail.com", "pass", 19);
        User user3 = new User("Mike", "mike@gmail.com", "pass", 10);
        User user4 = new User("Niki", "niki@gmail.com", "pass", 17);

        return new User[]{user1, user2,user3,user4};
    }

    public static boolean validateUser(User user) throws AccessDeniedException{

        if(user.getAge() < 18){
            throw new AccessDeniedException("Доступ запрещен");
        }
        return true;

    }

    public static User getUserByLoginAndPassword(String login, String password) throws UserNotFoundException {
        User[] users = getUsers();
        for (User user : users) {
            if(user.getLogin().equals(login) && user.getPass().equals(password)){
                return user;
            }
        }
        throw new UserNotFoundException("Пользователь не найден");

    }
}