package by.gsu.filter;

import static by.gsu.constants.ExceptionConstants.CHECK_PASSWORD_ERROR;
import static by.gsu.constants.ExceptionConstants.EMPTY_FIELD_ERROR;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import by.gsu.constants.ActionConstants;
import by.gsu.constants.PropertyConstants;
import by.gsu.database.structure.columns.TrackColumns;
import by.gsu.database.structure.columns.UserColumns;
import by.gsu.parser.DateFormatParser;

@WebFilter(servletNames = "edit")
public class EditFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        switch (checkAction(request)) {
            case ADD_TRACK:
                TrackEditFilter.checkName(request);
                TrackEditFilter.checkDate(request);
                break;
            case LOGIN:
                UserEditFilter.checkLogin(request);
                UserEditFilter.checkPassword(request);
                break;
            case REGISTRATION:
                UserEditFilter.checkLogin(request);
                UserEditFilter.checkPassword(request);
                UserEditFilter.checkPasswords(request);
                break;
            default:
                break;
        }
        chain.doFilter(request, response);
    }

    private static ActionConstants checkAction(final ServletRequest request) {
        String value = request.getParameter(PropertyConstants.ACTION);
        ActionConstants action = ActionConstants.valueOf(value.toUpperCase());
        request.setAttribute(PropertyConstants.ACTION, action);
        return action;
    }

    private static class TrackEditFilter {

        private static void checkName(final ServletRequest request) {
            String name = request.getParameter(TrackColumns.NAME.toString());
            if ((name.trim().isEmpty())) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else {
                request.setAttribute(TrackColumns.NAME.toString(), name);
            }
        }

        private static void checkDate(final ServletRequest request) {
            String date = request.getParameter(TrackColumns.DATE.toString());
            try {
                request.setAttribute(TrackColumns.DATE.toString(),
                        DateFormatParser.stringToDate(date));
            } catch (ParseException e) {
                request.setAttribute(TrackColumns.DATE.toString(), date);
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            }
        }

    }

    private static class UserEditFilter {

        private static void checkLogin(final ServletRequest request) {
            String login = request.getParameter(UserColumns.LOGIN.toString());
            if (login.trim().isEmpty()) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else {
                request.setAttribute(UserColumns.LOGIN.toString(), login);
            }
        }

        private static void checkPassword(final ServletRequest request) {
            String password = request.getParameter(UserColumns.PASSWORD.toString());
            if (password.trim().isEmpty()) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else {
                request.setAttribute(UserColumns.PASSWORD.toString(), password);
            }
        }

        private static void checkPasswords(final ServletRequest request) {
            String password = request.getParameter(UserColumns.PASSWORD.toString());
            String checkPassword = request.getParameter(PropertyConstants.CHECK_PASSWORD);
            if (checkPassword.trim().isEmpty()) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else if (!password.equals(checkPassword)) {
                request.setAttribute(PropertyConstants.ERROR, CHECK_PASSWORD_ERROR);
            }
        }

    }

}
