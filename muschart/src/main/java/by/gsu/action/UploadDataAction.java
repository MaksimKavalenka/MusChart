package by.gsu.action;

import static by.gsu.constants.ExceptionConstants.UPLOAD_FILE_ERROR;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import by.gsu.exception.ValidationException;

public class UploadDataAction {

    public static String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static String uploadFile(final HttpServletRequest request, final String partName,
            final String filePath) throws ValidationException {
        try {
            Part filePart = request.getPart(partName);
            String fileName = getFileName(filePart);
            String fullPath = filePath + "/" + fileName;

            try (OutputStream out = new FileOutputStream(new File(fullPath));
                    InputStream filecontent = filePart.getInputStream()) {
                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                return fullPath;

            } catch (FileNotFoundException e) {
                throw new ValidationException(UPLOAD_FILE_ERROR);
            } catch (IOException e) {
                throw new ValidationException(e.getMessage());
            }
        } catch (IOException | ServletException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
