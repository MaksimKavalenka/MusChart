package by.gsu.filter;

import static by.gsu.constants.ExceptionConstants.CHECK_PASSWORD_ERROR;
import static by.gsu.constants.ExceptionConstants.EMPTY_FIELD_ERROR;

import java.io.IOException;

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
                TrackEditFilter.editAddTrack(request);
                break;
            case LOGIN:
                UserEditFilter.editLogin(request);
                break;
            case REGISTRATION:
                UserEditFilter.editRegistration(request);
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

        private static void editAddTrack(final ServletRequest request) {
            String name = request.getParameter(TrackColumns.NAME.toString());

            if ((name.trim().isEmpty())) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else {
                request.setAttribute(TrackColumns.NAME.toString(), name);
            }
        }

    }

    private static class UserEditFilter {

        private static void editLogin(final ServletRequest request) {
            String login = request.getParameter(UserColumns.LOGIN.toString());
            String password = request.getParameter(UserColumns.PASSWORD.toString());

            if ((login.trim().isEmpty()) || (password.trim().isEmpty())) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else {
                request.setAttribute(UserColumns.LOGIN.toString(), login);
                request.setAttribute(UserColumns.PASSWORD.toString(), password);
            }
        }

        private static void editRegistration(final ServletRequest request) {
            String login = request.getParameter(UserColumns.LOGIN.toString());
            String password = request.getParameter(UserColumns.PASSWORD.toString());
            String checkPassword = request.getParameter(PropertyConstants.CHECK_PASSWORD);

            if ((login.trim().isEmpty()) || (password.trim().isEmpty())
                    || (checkPassword.trim().isEmpty())) {
                request.setAttribute(PropertyConstants.ERROR, EMPTY_FIELD_ERROR);
            } else if (!password.equals(checkPassword)) {
                request.setAttribute(PropertyConstants.ERROR, CHECK_PASSWORD_ERROR);
            } else {
                request.setAttribute(UserColumns.LOGIN.toString(), login);
                request.setAttribute(UserColumns.PASSWORD.toString(), password);
            }
        }

    }

}
