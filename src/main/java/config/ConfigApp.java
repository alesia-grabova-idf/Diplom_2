package config;

public class ConfigApp {

  public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
  // USER AUTHENTICATION
  public static final String REGISTER_USER = "/api/auth/register";
  public static final String LOGIN_USER = "/api/auth/login";
  public static final String LOGOUT_USER = "/api/auth/logout";
  public static final String UPDATE_TOKEN = "/api/auth/token";
  public static final String USER_INFO = "/api/auth/user";
  // PASSWORD RESET
  public static final String PASSWORD_RESET = "/api/password-reset";
  public static final String PASSWORD_RESET_CONFIRM = "/api/password-reset/reset";
  //ORDER
  public static final String ORDER = "/api/orders";
  public static final String GET_INGREDIENTS = "/api/ingredients";
  public static final String GET_ALL_ORDERS = "/api/orders/all";
}
