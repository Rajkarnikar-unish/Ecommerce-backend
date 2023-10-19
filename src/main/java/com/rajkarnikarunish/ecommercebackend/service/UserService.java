package com.rajkarnikarunish.ecommercebackend.service;

import com.rajkarnikarunish.ecommercebackend.api.models.LoginBody;
import com.rajkarnikarunish.ecommercebackend.api.models.RegistrationBody;
import com.rajkarnikarunish.ecommercebackend.exception.EmailFailureException;
import com.rajkarnikarunish.ecommercebackend.exception.UserAlreadyExistsException;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.VerificationToken;
import com.rajkarnikarunish.ecommercebackend.models.dao.LocalUserDao;
import com.rajkarnikarunish.ecommercebackend.models.dao.VerificationTokenDao;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    private LocalUserDao localUserDao;
    private EncryptionService encryptionService;
    private VerificationTokenDao verificationTokenDao;
    private JWTService jwtService;

    private EmailService emailService;

    public UserService(LocalUserDao localUserDao, EncryptionService encryptionService, VerificationTokenDao verificationTokenDao, JWTService jwtService, EmailService emailService) {
        this.localUserDao = localUserDao;
        this.encryptionService = encryptionService;
        this.verificationTokenDao = verificationTokenDao;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
        if (localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || localUserDao.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        verificationTokenDao.save(verificationToken);
        return localUserDao.save(user);
    }

    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());

        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
