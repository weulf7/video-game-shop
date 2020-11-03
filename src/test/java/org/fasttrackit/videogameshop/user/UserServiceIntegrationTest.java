package org.fasttrackit.videogameshop.user;

import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.service.UserService;
import org.fasttrackit.videogameshop.steps.UserTestSteps;
import org.fasttrackit.videogameshop.transfer.user.SaveUserRequest;
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

    @Autowired
    private UserTestSteps userTestSteps;


    @Test
    public void createUser_whenValidRequest_thenSaveCreatedUser(){
        userTestSteps.createUser();

    }



    @Test
    public void getUser_whenExistingUser_thenReturnUser(){
        User createdUser = userTestSteps.createUser();
        User userResponse = userService.getUser(createdUser.getId());

        assertThat(userResponse,notNullValue());
        assertThat(userResponse.getId(),is(createdUser.getId()));
        assertThat(userResponse.getFirstName(),is(createdUser.getFirstName()));
        assertThat(userResponse.getLastName(),is(createdUser.getLastName()));
    }

    @Test
    public void updateUser_whenExistingUser_thenSaveUpdatedUser(){
        User existingUser = userTestSteps.createUser();
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
        User user = userTestSteps.createUser();
        userService.deleteUser(user.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->userService.getUser(user.getId()));
    }
}
