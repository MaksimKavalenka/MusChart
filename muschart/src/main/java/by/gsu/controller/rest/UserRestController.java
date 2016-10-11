package by.gsu.controller.rest;

import static by.gsu.constants.MessageConstants.PASSWORDS_ERROR;
import static by.gsu.constants.MessageConstants.VALIDATION_ERROR;
import static by.gsu.constants.UrlConstants.JSON_EXT;
import static by.gsu.constants.UrlConstants.Rest.USERS_URL;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.bean.ErrorMessage;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.UserEntity;
import by.gsu.constants.RoleConstants;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.service.dao.RoleServiceDAO;
import by.gsu.jpa.service.dao.UserServiceDAO;
import by.gsu.utility.Secure;
import by.gsu.utility.Validator;

@RestController
@RequestMapping(USERS_URL)
public class UserRestController {

    @Autowired
    private RoleServiceDAO roleService;
    @Autowired
    private UserServiceDAO userService;

    @RequestMapping(value = "/auth"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> authentication(final Principal principal) {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                UserEntity user = (UserEntity) ((AbstractAuthenticationToken) principal)
                        .getPrincipal();
                return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserEntity>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/logout" + JSON_EXT, method = RequestMethod.POST)
    public void logout(final HttpServletRequest rq, final HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @RequestMapping(value = "/create/{login}/{password}/{confirmPassword}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password,
            @PathVariable("confirmPassword") final String confirmPassword) {
        try {
            if (!Validator.allNotNull(login, password, confirmPassword)) {
                return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                        HttpStatus.CONFLICT);
            }

            if (!password.equals(confirmPassword)) {
                return new ResponseEntity<Object>(new ErrorMessage(PASSWORDS_ERROR),
                        HttpStatus.CONFLICT);
            }

            List<GrantedAuthority> roles = new ArrayList<>(1);
            roles.add(roleService.getRoleByName(RoleConstants.ROLE_USER.name()));
            userService.createUser(login, Secure.secureBySha(password, login), roles);
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/like/{entity}/{entityId}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> setUserLike(@PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        switch (entity) {
            case Entities.ARTIST:
                userService.updateUserArtists(Secure.getLoggedUser().getId(), entityId);
                break;
            case Entities.GENRE:
                userService.updateUserGenres(Secure.getLoggedUser().getId(), entityId);
                break;
            case Entities.TRACK:
                userService.updateUserTracks(Secure.getLoggedUser().getId(), entityId);
                break;
            default:
                break;
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/check_login/{login}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkLogin(@PathVariable("login") final String login) {
        boolean exists = userService.checkLogin(login);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
