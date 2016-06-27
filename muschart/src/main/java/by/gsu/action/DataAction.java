package by.gsu.action;

import javax.servlet.http.HttpServletRequest;

import by.gsu.constants.ActionConstants;
import by.gsu.constants.PropertyConstants;
import by.gsu.exception.IllegalDataException;

public abstract class DataAction {

    public static String getPath(final HttpServletRequest request) {
        ActionConstants action = (ActionConstants) request.getAttribute(PropertyConstants.ACTION);
        return action.getPath();
    }

    public static String getLocation(final HttpServletRequest request) {
        ActionConstants action = (ActionConstants) request.getAttribute(PropertyConstants.ACTION);
        return action.getLocation();
    }

    public static ActionConstants getAction(final String uri) {
        for (ActionConstants action : ActionConstants.values()) {
            if (action.getUri().equals(uri)) {
                return action;
            }
        }
        return null;
    }

    public static void checkError(final HttpServletRequest request) throws IllegalDataException {
        if (request.getAttribute(PropertyConstants.ERROR) != null) {
            String error = (String) request.getAttribute(PropertyConstants.ERROR);
            throw new IllegalDataException(error);
        }
    }

}
