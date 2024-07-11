package com.woody.firstService.controller;

import com.woody.firstService.model.User;
import com.woody.firstService.repository.UserRepository;
import com.woody.firstService.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service userS3Service;

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> users = new ArrayList<>(userRepository.findAll());

            if (users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping(path = "/getUserPhotoById/{id}")
    public ResponseEntity<byte[]> getUserPhotoById(@PathVariable Long id){
        try {
            byte[] userPhotoBytes = userS3Service.downloadUserPhoto(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(userPhotoBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User userObj = userRepository.save(user);

        return new ResponseEntity<>(userObj, HttpStatus.OK);
    }

    @PatchMapping(path = "/addUserPhoto/{id}")
    public ResponseEntity<HttpStatus> addUserPhotoById(@PathVariable Long id, @RequestParam("userPhoto") MultipartFile userPhoto){
        if (userPhoto.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            userS3Service.uploadUserPhoto(id, userPhoto);
            String photoUrl = userS3Service.getPhotoUrl(id);
            User userWithPhoto = user.get();
            userWithPhoto.setUserPhotoUrl(photoUrl);
            userRepository.save(userWithPhoto);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/updateUserById/{userID}")
    public ResponseEntity<User> updateUserById(@PathVariable Long userID, @RequestBody User newUser){

        Optional<User> oldUser = userRepository.findById(userID);

        if (oldUser.isPresent()){
            User updateUser = oldUser.get();
            updateUser.setName(newUser.getName());
            updateUser.setEmail(newUser.getEmail());

            User resultUser = userRepository.save(updateUser);
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(path = "/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id){

        userRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
