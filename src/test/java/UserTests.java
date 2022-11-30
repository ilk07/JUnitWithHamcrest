import Homeworks2_2.AccessDeniedException;
import Homeworks2_2.Main;
import Homeworks2_2.User;
import Homeworks2_2.UserNotFoundException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserTests {

    User sut; //system under test

    @BeforeAll
    public static void startAllTests(){
        System.out.println("---START UNIT TESTS---");
    }

    @AfterAll
    public static void endAllTests(){
        System.out.println("---UNIT TESTS COMPLETED---");
    }

    @BeforeEach
    public void initOneTest(){
        sut = new User("testUser", "testUser@mail.mail", "test", 18);
    }

    @AfterEach
    void afterTest(TestInfo testInfo) {
        System.out.println("Completed test: " + '"' +  testInfo.getDisplayName() +'"');
    }

    @Test
    @DisplayName("User is Instance of User Object")
    public void testUser(){
        assertThat(sut, instanceOf(User.class));
    }


    @Test
    @DisplayName("User creation")
    public void testUserCreate(){


        //then
        assertThat(sut, allOf(
                hasProperty("login", equalTo("testUser")),
                hasProperty("email", equalTo("testUser@mail.mail")),
                hasProperty("pass", equalTo("test")),
                hasProperty("age", equalTo(18))
        ));
    }

    @Test
    @DisplayName("Getting a login")
    public void testGetLogin(){
        //given
        String expected = "testUser";

        //when
        String actual = sut.getLogin();

        //then
        assertThat(expected, equalTo(actual));
    }

    @Test
    @DisplayName("Getting a password")
    public void testGetPass(){
        //given
        String expected = "test";

        //when
        String actual = sut.getPass();

        //then
        assertThat(expected, equalTo(actual));
    }

    @Test
    @DisplayName("Getting age")
    public void testGetAge(){
        //given
        int expected = 18;

        //when
        int actual = sut.getAge();

        //then
        assertThat(expected, equalTo(actual));
    }

    @Test
    @DisplayName("Getting a list of users")
    public void testGetUsers(){

        //when
        User[] users = Main.getUsers();
        List<User> list = List.of(users);

        //then
        assertThat(list, everyItem(instanceOf(User.class)));
    }

    @Test
    @DisplayName("List of users")
    public void testGetUsersList(){
        //given
        User user1 = new User("John", "jhon@gmail.com", "pass", 24);
        User user2 = new User("Donald", "don@gmail.com", "pass", 19);
        User user3 = new User("Mike", "mike@gmail.com", "pass", 10);
        User user4 = new User("Niki", "niki@gmail.com", "pass", 17);
        User[] expectedList = new User[]{user1, user2,user3,user4};
        User[] actualList = Main.getUsers();

        //when
        String actual = Arrays.asList(actualList).toString();
        String expected = Arrays.asList(expectedList).toString();

        //then
        assertThat(expected, equalTo(actual));

    }

    @Test
    @DisplayName("List of users not empty")
    public void testGetUsers_NOT_NULL() {

        //when
        List<User> actual = List.of(Main.getUsers());

        //then
        assertThat(actual, is(not(empty())));
    }


    @MethodSource("userDataSource")
    @ParameterizedTest(name = "Get user by username and password (user:  {arguments})")
    public void testGetUserByLoginAndPassword(String login, String mail, String pass, int age) throws UserNotFoundException {

        //when
        User user = Main.getUserByLoginAndPassword(login, pass);

        //then
        assertThat(user, allOf(
                hasProperty("login", equalTo(login)),
                hasProperty("pass", equalTo(pass)),
                instanceOf(User.class)
        ));
    }
    public static Stream<Arguments> userDataSource(){
        return Stream.of(
                Arguments.of("John", "jhon@gmail.com", "pass", 24),
                Arguments.of("Donald", "don@gmail.com", "pass", 19),
                Arguments.of("Mike", "mike@gmail.com", "pass", 10),
                Arguments.of("Niki", "niki@gmail.com", "pass", 17)
        );
    }


    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17})
    @ParameterizedTest (name = "User validation by age {arguments}")
    public void testValidateUserUnder18YearsOld(int input) {
        //given
        User user = new User("test", "test", "test", input);

        //when
        Executable executable = () -> Main.validateUser(user);

        //then
        //assertThrows(Exception.class, executable);
        assertThrowsExactly(AccessDeniedException.class, executable);
    }

    @MethodSource("agesOlder18")
    @ParameterizedTest (name = "User validation by age {arguments}")
    public void testValidateUserOver18YearsOld(int age) throws AccessDeniedException {
        //given
        User user = new User("test", "test", "test", age);
        boolean expected = true;

        //when
        boolean actual = Main.validateUser(user);

        //then
        assertThat(expected, equalTo(actual));
    }
    static IntStream agesOlder18() {
        //возрасты в диапазоне от 18 до максимальной продолжительности жизни / Канэ Танака — 118 лет - Япония
        return IntStream.range(18, 119);
    }

    @Test
    @DisplayName("Access denied exception")
    public void testAccessDeniedException(){

        //when
        Executable executable = () -> {
            throw new AccessDeniedException("Exception");
        };

        //then
        //assertThrows(Exception.class, executable);
        assertThrowsExactly(AccessDeniedException.class, executable);

    }

    @Test
    @DisplayName("Access denied message")
    public void testAccessDeniedExceptionMessage() {

        //given
        String expected = "Тестовое сообщение";

        //when
        AccessDeniedException exception = new AccessDeniedException(expected);

        //then
        assertThat(exception.getMessage(), is(equalTo(expected)));
    }

    @Test
    @DisplayName("User not found exception")
    public void testUserNotFoundException(){

        //when
        Executable executable = () -> {
            throw new UserNotFoundException("Exception");
        };

        //then
        //assertThrows(Exception.class, executable);
        assertThrowsExactly(UserNotFoundException.class, executable);

    }

    @Test
    @DisplayName("User not found error message")
    public void testUserNotFoundExceptionMessage() {
        //given
        String expected = "Тестовое сообщение";

        //when
        UserNotFoundException exception = new UserNotFoundException(expected);

        //then
        assertThat(exception.getMessage(), is(equalTo(expected)));
    }


    @Test
    @DisplayName("User object toString method return a specified String")
    public void testUsersToString() {
        //when
        String expected = sut.toString();

        // Then
        assertThat(sut,hasToString(expected));
    }

    @Test
    @DisplayName("Create users with same property values")
    public void testUserPropertyValues() {

        // When
        User expected = new User("testUser", "testUser@mail.mail", "test", 18);

        // Then
        assertThat(expected, samePropertyValuesAs(sut));
    }


}
