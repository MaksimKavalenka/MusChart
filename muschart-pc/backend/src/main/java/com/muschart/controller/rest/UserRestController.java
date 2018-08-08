package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.USERS_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.AUTH_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.CHECK_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.LIKE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.LOGOUT_OPERATION;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.bean.ErrorMessage;
import com.muschart.constants.RoleConstants;
import com.muschart.constants.EntityConstants.Structure.Entities;
import com.muschart.dto.EntityDTO;
import com.muschart.dto.RegisterDTO;
import com.muschart.dto.output.UserOutputDTO;
import com.muschart.entity.UserEntity;
import com.muschart.exception.ValidationException;
import com.muschart.utility.Parser;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(USERS_URL)
public class UserRestController extends com.muschart.controller.rest.RestController {

    @RequestMapping(value = AUTH_OPERATION, method = RequestMethod.GET)
    public ResponseEntity<UserOutputDTO> authentication(Principal principal) {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                UserEntity user = (UserEntity) ((AbstractAuthenticationToken) principal).getPrincipal();

                UserOutputDTO userOutput = null;
                if (user != null) {
                    userOutput = new UserOutputDTO(user);
                }
                return new ResponseEntity<UserOutputDTO>(userOutput, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserOutputDTO>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody RegisterDTO user, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<Object>(new ErrorMessage(Parser.getErrorsMessagesFromObjectError(errors)), HttpStatus.BAD_REQUEST);
        }

        try {
            String login = new String(Base64.getDecoder().decode(user.getLogin().getBytes()));
            String password = new String(Base64.getDecoder().decode(user.getPassword().getBytes()));

            List<GrantedAuthority> roles = new ArrayList<>(1);
            roles.add(roleDatabaseService.getRoleByName(RoleConstants.ROLE_USER.name()));

            userDatabaseService.createUser(login, Secure.secureBySha(password, login), roles);
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = LOGOUT_OPERATION, method = RequestMethod.POST)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @RequestMapping(value = LIKE_OPERATION, method = RequestMethod.POST)
    public ResponseEntity<Object> setUserLike(@RequestBody @Valid EntityDTO entity) {
        switch (entity.getEntity()) {
            case Entities.ARTIST:
                userDatabaseService.updateUserArtists(Secure.getLoggedUser().getId(), entity.getEntityId());
                break;
            case Entities.GENRE:
                userDatabaseService.updateUserGenres(Secure.getLoggedUser().getId(), entity.getEntityId());
                break;
            case Entities.TRACK:
                userDatabaseService.updateUserTracks(Secure.getLoggedUser().getId(), entity.getEntityId());
                break;
            default:
                break;
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = CHECK_OPERATION + "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> checkLogin(@RequestBody @Valid String login) {
        boolean exists = userDatabaseService.checkLogin(login);
        return new ResponseEntity<Object>(exists, HttpStatus.OK);
    }

}