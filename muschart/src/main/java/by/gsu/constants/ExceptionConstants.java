package by.gsu.constants;

public abstract class ExceptionConstants {

    public static final String ERROR_LOAD_DRIVER        = "Driver not found";
    public static final String ERROR_CONNECTION         = "Connection failed";
    public static final String ERROR_CONNECTION_ACCESS  = "Connection access error";
    public static final String ERROR_CONNECTION_RETURN  = "Return connection error";
    public static final String ERROR_CONNECTIONS_LIMIT  = "Connections limit is over";
    public static final String ERROR_CLOSE              = "Resource close failed";
    public static final String ERROR_CORRECT            = "Non correct value (all fields must be filled)";
    public static final String ERROR_DATA               = "Data are not found";
    public static final String ERROR_ACCESS_REQUEST     = "Trying to perform the get request";
    public static final String ERROR_PAGE_NOT_FOUND     = "Page is not found";
    public static final String ERROR_PAGE_NUMBER        = "Non correct page number";
    public static final String ERROR_USER_LOGIN         = "Please, login";
    public static final String SECRET_MESSAGE           = "Hello) Who are you?";

    public static final String AUTHORIZATION_ERROR      = "Login or password are wrong";
    public static final String CHECK_PASSWORD_ERROR     = "Passwords do not match";
    public static final String CLOSE_SESSION_ERROR      = "Session cannot be closed";
    public static final String COMMIT_TRANSACTION_ERROR = "Error while commit transaction";
    public static final String DOUBLE_LOGIN_ERROR       = "User with this login already exists";
    public static final String EMPTY_FIELD_ERROR        = "All fields must be filled";
    public static final String OPEN_SESSION_ERROR       = "Session cannot be opened";
    public static final String PERMISSION_ERROR         = "You do not have permission";
    public static final String UPLOAD_FILE_ERROR        = "You either did not specify a file to upload or are trying to upload a file to a protected or nonexistent location";

}
