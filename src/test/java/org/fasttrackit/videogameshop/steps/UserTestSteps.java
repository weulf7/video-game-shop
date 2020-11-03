package org.fasttrackit.videogameshop.steps;

import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.service.UserService;
import org.fasttrackit.videogameshop.transfer.user.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class UserTestSteps {

    @Autowired
    private UserService userService;

    public User createUser() {
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
}
