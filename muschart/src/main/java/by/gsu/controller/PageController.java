package by.gsu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.action.DataAction;
import by.gsu.action.LoadDataAction;
import by.gsu.constants.PropertyConstants;
import by.gsu.exception.IllegalDataException;
import by.gsu.exception.ValidationException;

@WebServlet(name = "page", urlPatterns = {"/tracks", "/track/add", "/track/delete", "/artists",
        "/artist/add", "/artist/delete", "/genres", "/genre/add", "/genre/delete",
        "/track_actors/add", "/track_actors/delete", "/track_genres/add", "/track_genres/delete",
        "/artist_genres/add", "/artist_genres/delete", "/login", "/registration"})
public class PageController extends HttpServlet {

    private static final long serialVersionUID = 6536802898230859089L;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LoadDataAction.load(request);
        } catch (IllegalDataException | ValidationException e) {
            request.setAttribute(PropertyConstants.ERROR, e.getMessage());
        }
        request.getRequestDispatcher(DataAction.getPath(request)).forward(request, response);
    }

}
