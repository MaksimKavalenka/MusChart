package by.gsu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.action.DataAction;
import by.gsu.action.EditDataAction;
import by.gsu.constants.PropertyConstants;
import by.gsu.exception.IllegalDataException;
import by.gsu.exception.ValidationException;

@WebServlet(name = "edit", urlPatterns = {"/edit"})
@MultipartConfig
public class EditController extends HttpServlet {

    private static final long serialVersionUID = -4678459063410838758L;

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EditDataAction.edit(request);
            response.sendRedirect(DataAction.getLocation(request));
        } catch (IllegalDataException | ValidationException e) {
            request.setAttribute(PropertyConstants.ERROR, e.getMessage());
            request.getRequestDispatcher(DataAction.getPath(request)).forward(request, response);
        }
    }

}
