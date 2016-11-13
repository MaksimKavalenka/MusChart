package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.USERS_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.AUTH_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.CHECK_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.CREATE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.LIKE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.LOGOUT_OPERATION;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.bean.ErrorMessage;
import com.muschart.constants.RoleConstants;
import com.muschart.constants.EntityConstants.Structure.Entities;
import com.muschart.dto.RegisterDTO;
import com.muschart.dto.UserDTO;
import com.muschart.entity.UserEntity;
import com.muschart.exception.ValidationException;
import com.muschart.service.dao.RoleServiceDAO;
import com.muschart.service.dao.UserServiceDAO;
import com.muschart.utility.Parser;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(USERS_URL)
public class UserRestController {

    @Autowired
    private RoleServiceDAO roleService;
    @Autowired
    private UserServiceDAO userService;

    @RequestMapping(value = AUTH_OPERATION, method = RequestMethod.GET)
    public ResponseEntity<UserDTO> authentication(Principal principal) {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                UserEntity user = (UserEntity) ((AbstractAuthenticationToken) principal)
                        .getPrincipal();

                UserDTO userDto = null;
                if (user != null) {
                    userDto = new UserDTO(user);
                }
                return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserDTO>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = LOGOUT_OPERATION, method = RequestMethod.POST)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @RequestMapping(value = CREATE_OPERATION, method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody @Valid RegisterDTO user, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<Object>(
                    new ErrorMessage(Parser.getErrorsMessagesFromObjectError(errors)),
                    HttpStatus.BAD_REQUEST);
        }

        try {
            List<GrantedAuthority> roles = new ArrayList<>(1);
            roles.add(roleService.getRoleByName(RoleConstants.ROLE_USER.name()));
            userService.createUser(user.getLogin(),
                    Secure.secureBySha(user.getPassword(), user.getLogin()), roles);
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = LIKE_OPERATION + "/{entity}/{entityId}", method = RequestMethod.POST)
    public ResponseEntity<Object> setUserLike(@PathVariable("entity") String entity,
            @PathVariable("entityId") long entityId) {
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

    @RequestMapping(value = CHECK_OPERATION + "/login/{login}", method = RequestMethod.POST)
    public ResponseEntity<Object> checkLogin(@PathVariable("login") String login) {
        boolean exists = userService.checkLogin(login);
        return new ResponseEntity<Object>(exists, HttpStatus.OK);
    }

}
