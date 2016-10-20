package by.gsu.controller.rest;

import static by.gsu.constants.MessageConstants.PASSWORDS_ERROR;
import static by.gsu.constants.MessageConstants.VALIDATION_ERROR;
import static by.gsu.constants.UrlConstants.Rest.JSON_EXT;
import static by.gsu.constants.UrlConstants.Rest.USER_URL;
import static by.gsu.constants.UrlConstants.Rest.Operation.AUTH_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.CHECK_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.CREATE_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.LIKE_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.LOGOUT_OPERATION;

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
import by.gsu.bean.entity.UserInfoEntity;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.UserEntity;
import by.gsu.constants.RoleConstants;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.service.dao.RoleServiceDAO;
import by.gsu.jpa.service.dao.UserServiceDAO;
import by.gsu.utility.Secure;
import by.gsu.utility.Validator;

@RestController
@RequestMapping(USER_URL)
public class UserRestController {

    @Autowired
    private RoleServiceDAO roleService;
    @Autowired
    private UserServiceDAO userService;

    @RequestMapping(value = AUTH_OPERATION
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoEntity> authentication(final Principal principal) {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                UserEntity user = (UserEntity) ((AbstractAuthenticationToken) principal)
                        .getPrincipal();

                UserInfoEntity userInfo = null;
                if (user != null) {
                    userInfo = new UserInfoEntity(user);
                }
                return new ResponseEntity<UserInfoEntity>(userInfo, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserInfoEntity>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = LOGOUT_OPERATION + JSON_EXT, method = RequestMethod.POST)
    public void logout(final HttpServletRequest rq, final HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @RequestMapping(value = CREATE_OPERATION + "/{login}/{password}/{confirmPassword}"
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

    @RequestMapping(value = LIKE_OPERATION + "/{entity}/{entityId}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> setUserLike(@PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        if (!Validator.allNotNull(entity, entityId)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

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
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = CHECK_OPERATION + "/login/{login}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> checkLogin(@PathVariable("login") final String login) {
        boolean exists = userService.checkLogin(login);
        return new ResponseEntity<Object>(exists, HttpStatus.OK);
    }

}
