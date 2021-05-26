package Domain.Controllers;

import Domain.UserLogged;

public interface IAuthController {

    boolean registerNewUser(String email, String password);
}
