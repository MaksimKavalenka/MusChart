package by.gsu.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.UserFactory;

@RestController
public class UserRestController {

    @RequestMapping(value = "/user/{login}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getUserById(@PathVariable("login") final String login) {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            boolean exist = userDAO.ifExists(login);
            return new ResponseEntity<Boolean>(exist, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
        }
    }

}
