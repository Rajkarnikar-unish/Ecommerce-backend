package com.rajkarnikarunish.ecommercebackend.service;

import com.rajkarnikarunish.ecommercebackend.api.models.RegistrationBody;
import com.rajkarnikarunish.ecommercebackend.exception.UserAlreadyExistsException;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.dao.LocalUserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private LocalUserDao localUserDao;

    public UserService(LocalUserDao localUserDao) {
        this.localUserDao = localUserDao;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || localUserDao.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        //TODO: Encrypt passwords!
        user.setPassword(registrationBody.getPassword());

        return localUserDao.save(user);
    }
}
