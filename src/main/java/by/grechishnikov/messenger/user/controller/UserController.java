package by.grechishnikov.messenger.user.controller;

import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author - Evgeniy Grechishnikov
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestParam(value = "user") String json,
                                     @RequestParam(value = "file", required = false) MultipartFile avatar) {
        try {
            userService.convertAndSave(json, avatar);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable String login) {
        try {
            return new ResponseEntity<>(userService.findByLogin(login), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<Page<User>> search(@RequestParam(required = false) String name,
                                             @RequestParam String currentUserLogin,
                                             Pageable pageable) {
        try {
            return new ResponseEntity<>(userService.search(name, currentUserLogin, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
