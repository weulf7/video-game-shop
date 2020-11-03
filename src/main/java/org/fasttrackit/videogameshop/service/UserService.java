package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.UserRepository;
import org.fasttrackit.videogameshop.transfer.user.GetUsersRequest;
import org.fasttrackit.videogameshop.transfer.user.SaveUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final static Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(SaveUserRequest request){
        LOGGER.info("Creating user: {}",request);
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);


    }

    public User getUser(long id){
        LOGGER.info("Retrieving user {}",id);

        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User with id "+id+" does not exist"));
    }

    public Page<User>getUsers(GetUsersRequest request, Pageable pageable){
        LOGGER.info("Retrieving users");

        return userRepository.findByOptionalCriteria(request.getPartialFirstName(),request.getPartialLastName(),pageable);


    }

    public User updateUser(SaveUserRequest request,long id){
        LOGGER.info("Updating user {}: {}",id,request);

        User user = getUser(id);

        BeanUtils.copyProperties(request,user);

        return userRepository.save(user);
    }

    public void deleteUser(long id){
        LOGGER.info("Deleting user {}",id);

        userRepository.deleteById(id);
    }
}
