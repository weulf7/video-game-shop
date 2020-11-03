package org.fasttrackit.videogameshop;

import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.service.UserService;
import org.fasttrackit.videogameshop.transfer.SaveUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;


    @Test
    public void createUser_whenValidRequest_thenSaveCreatedUser(){
        createUser();

    }

    private User createUser() {
        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName("Test First Name");
        request.setLastName("Test Last Name");

        User user = userService.createUser(request);

        assertThat(user,notNullValue());
        assertThat(user.getId(),greaterThan(0L));
        assertThat(user.getFirstName(),is(request.getFirstName()));
        assertThat(user.getLastName(),is(request.getLastName()));

        return user;
    }

    @Test
    public void getUser_whenExistingUser_thenReturnUser(){
        User createdUser = createUser();
        User userResponse = userService.getUser(createdUser.getId());

        assertThat(userResponse,notNullValue());
        assertThat(userResponse.getId(),is(createdUser.getId()));
        assertThat(userResponse.getFirstName(),is(createdUser.getFirstName()));
        assertThat(userResponse.getLastName(),is(createdUser.getLastName()));
    }

    @Test
    public void updateUser_whenExistingUser_thenSaveUpdatedUser(){
        User existingUser = createUser();
        SaveUserRequest request = new SaveUserRequest();

        request.setFirstName(existingUser.getFirstName());
        request.setLastName(existingUser.getLastName());

        User updatedUser = userService.updateUser(request, existingUser.getId());

        assertThat(updatedUser,notNullValue());
        assertThat(updatedUser.getId(),is(existingUser.getId()));
        assertThat(updatedUser.getFirstName(),is(request.getFirstName()));
        assertThat(updatedUser.getLastName(),is(request.getLastName()));
    }

    @Test
    public void deleteUser_whenExistingUser_thenDeleteUser(){
        User user = createUser();
        userService.deleteUser(user.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->userService.getUser(user.getId()));
    }
}
