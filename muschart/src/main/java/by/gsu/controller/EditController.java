package by.gsu.controller;

import static by.gsu.constants.StructureConstants.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.gsu.constants.PageConstants;
import by.gsu.constants.PropertyConstants;
import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.UserFactory;
import by.gsu.model.User;

@Controller
public class EditController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(final HttpServletRequest request,
            @RequestParam(UserColumns.LOGIN) final String login,
            @RequestParam(UserColumns.PASSWORD) final String password) {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUser(login, password);
            request.getSession().setAttribute(Tables.USER, user);
            return PageConstants.UriConstants.MAIN_URI;
        } catch (ValidationException e) {
            request.setAttribute(PropertyConstants.ERROR, e.getMessage());
            return PageConstants.PathConstants.LOGIN_PATH;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(final HttpServletRequest request) {
        request.getSession().invalidate();
        return PageConstants.UriConstants.MAIN_URI;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(final HttpServletRequest request,
            @RequestParam(UserColumns.LOGIN) final String login,
            @RequestParam(UserColumns.PASSWORD) final String password,
            @RequestParam(PropertyConstants.CHECK_PASSWORD) final String checkPassword) {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            userDAO.createUser(login, password);
            login(request, login, password);
            return PageConstants.UriConstants.MAIN_URI;
        } catch (ValidationException e) {
            request.setAttribute(PropertyConstants.ERROR, e.getMessage());
            return PageConstants.PathConstants.REGIGTRATION_PATH;
        }
    }

}
