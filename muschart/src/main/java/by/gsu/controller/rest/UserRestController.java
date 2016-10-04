package by.gsu.controller.rest;

import static by.gsu.constants.MessageConstants.PASSWORDS_ERROR;
import static by.gsu.constants.MessageConstants.VALIDATION_ERROR;
import static by.gsu.constants.UrlConstants.Rest.USERS_URL;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

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
import by.gsu.constants.ModelStructureConstants.Entities;
import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.TrackDAO;
import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;
import by.gsu.entity.UserEntity;
import by.gsu.constants.RoleConstants;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.service.dao.RoleServiceDAO;
import by.gsu.jpa.service.dao.UserServiceDAO;
import by.gsu.utility.SecureData;
import by.gsu.utility.Validator;

@RestController
@RequestMapping(USERS_URL)
public class UserRestController extends by.gsu.controller.rest.RestController {

    @Autowired
    private ArtistDAO      artistDAO;
    @Autowired
    private GenreDAO       genreDAO;
    @Autowired
    private RoleServiceDAO roleService;
    @Autowired
    private TrackDAO       trackDAO;
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

            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(roleService.getRoleByName(RoleConstants.ROLE_USER.name()));
            userService.createUser(login, SecureData.secureBySha(password, login), roles);
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
                ArtistEntity artist = artistDAO.getArtistById(entityId);
                userService.updateUserArtists(getLoggedUser(), artist);
                break;
            case Entities.GENRE:
                GenreEntity genre = genreDAO.getGenreById(entityId);
                userService.updateUserGenres(getLoggedUser(), genre);
                break;
            case Entities.TRACK:
                TrackEntity track = trackDAO.getTrackById(entityId);
                userService.updateUserTracks(getLoggedUser(), track);
                break;
            default:
                break;
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/check_login/{login}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkLogin(@PathVariable("login") final String login) {
        boolean exists = userService.checkLogin(login);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
