package by.gsu.filter;

import static by.gsu.constants.DefaultConstants.ACTION_DEFAULT;
import static by.gsu.constants.DefaultConstants.PAGE_DEFAULT;
import static by.gsu.constants.ExceptionConstants.PERMISSION_ERROR;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import by.gsu.action.DataAction;
import by.gsu.bean.Role;
import by.gsu.bean.User;
import by.gsu.constants.ActionConstants;
import by.gsu.constants.PropertyConstants;
import by.gsu.database.structure.Tables;

@WebFilter(servletNames = "page")
public class PageFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        checkPage(request);
        switch (checkAction(request)) {
            case ADD_TRACK:
            case ADD_ARTIST:
            case ADD_GENRE:
                checkPermission(request);
                break;
            default:
                break;
        }
        chain.doFilter(request, response);
    }

    private ActionConstants checkAction(final ServletRequest request) {
        String uri = ((HttpServletRequest) request).getRequestURI().toString();
        ActionConstants action = DataAction.getAction(uri);
        if (action == null) {
            action = ACTION_DEFAULT;
        }
        request.setAttribute(PropertyConstants.ACTION, action);
        return action;
    }

    private void checkPage(final ServletRequest request) {
        String page = request.getParameter(PropertyConstants.PAGE);
        if (page == null) {
            page = String.valueOf(PAGE_DEFAULT);
        }
        request.setAttribute(PropertyConstants.PAGE, Integer.valueOf(page));
    }

    private void checkPermission(final ServletRequest request) {
        User user = (User) ((HttpServletRequest) request).getSession()
                .getAttribute(Tables.USER.toString());
        if ((user == null) || (user.getRole() != Role.ADMIN)) {
            request.setAttribute(PropertyConstants.ACTION, ACTION_DEFAULT);
            request.setAttribute(PropertyConstants.ERROR, PERMISSION_ERROR);
        }
    }

}
