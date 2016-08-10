package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.USERS_PATH;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IRoleDAO;
import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.RoleFactory;
import by.gsu.factory.UserFactory;
import by.gsu.model.User;

@RestController
public class UserRestController {

    @RequestMapping(value = USERS_PATH + "/create/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try (IUserDAO userDAO = UserFactory.getEditor();
                IRoleDAO roleDAO = RoleFactory.getEditor()) {
            userDAO.createUser(login, password, roleDAO.getRoleById(1));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUser(login, password);
            if (user == null) {
                return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/{login}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByLogin(@PathVariable("login") final String login) {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUserByLogin(login);
            if (user == null) {
                return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
    }

}
