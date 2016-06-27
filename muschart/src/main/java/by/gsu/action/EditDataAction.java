package by.gsu.action;

import static by.gsu.constants.UploadPathConstants.*;

import javax.servlet.http.HttpServletRequest;

import by.gsu.bean.User;
import by.gsu.constants.ActionConstants;
import by.gsu.constants.PropertyConstants;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.dao.IUserDAO;
import by.gsu.database.structure.Tables;
import by.gsu.database.structure.columns.TrackColumns;
import by.gsu.database.structure.columns.UserColumns;
import by.gsu.exception.IllegalDataException;
import by.gsu.exception.ValidationException;
import by.gsu.factory.TrackFactory;
import by.gsu.factory.UserFactory;

public abstract class EditDataAction {

    public static void edit(final HttpServletRequest request)
            throws ValidationException, IllegalDataException {
        DataAction.checkError(request);
        ActionConstants action = (ActionConstants) request.getAttribute(PropertyConstants.ACTION);

        switch (action) {
            case ADD_TRACK:
                TrackEditDataAction.addTrack(request);
                break;
            case LOGIN:
                UserEditDataAction.login(request);
                break;
            case LOGOUT:
                UserEditDataAction.logout(request);
                break;
            case REGISTRATION:
                UserEditDataAction.registration(request);
                UserEditDataAction.login(request);
                break;
            default:
                break;
        }
    }

    private abstract static class TrackEditDataAction {

        private static void addTrack(final HttpServletRequest request) throws ValidationException {
            try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
                String name = (String) request.getAttribute(TrackColumns.NAME.toString());
                String song = UploadDataAction.uploadFile(request, TrackColumns.SONG.toString(),
                        AUDIO_UPLOAD_PATH);
                String cover = UploadDataAction.uploadFile(request, TrackColumns.COVER.toString(),
                        TRACK_IMAGE_UPLOAD_PATH);
                trackDAO.addTrack(name, song, cover);
            }
        }

    }

    private abstract static class UserEditDataAction {

        private static void login(final HttpServletRequest request) throws ValidationException {
            try (IUserDAO userDAO = UserFactory.getEditor()) {
                String login = (String) request.getAttribute(UserColumns.LOGIN.toString());
                String password = (String) request.getAttribute(UserColumns.PASSWORD.toString());
                User user = userDAO.getUser(login, password);
                request.getSession().setAttribute(Tables.USER.toString(), user);
            }
        }

        private static void logout(final HttpServletRequest request) {
            request.getSession().invalidate();
        }

        private static void registration(final HttpServletRequest request)
                throws ValidationException {
            try (IUserDAO userDAO = UserFactory.getEditor()) {
                String login = (String) request.getAttribute(UserColumns.LOGIN.toString());
                String password = (String) request.getAttribute(UserColumns.PASSWORD.toString());
                userDAO.addUser(login, password);
            }
        }

    }

}
