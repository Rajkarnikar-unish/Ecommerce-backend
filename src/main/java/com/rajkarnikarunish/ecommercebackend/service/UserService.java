package com.rajkarnikarunish.ecommercebackend.service;

import com.auth0.jwt.interfaces.Verification;
import com.rajkarnikarunish.ecommercebackend.api.models.LoginBody;
import com.rajkarnikarunish.ecommercebackend.api.models.PasswordResetBody;
import com.rajkarnikarunish.ecommercebackend.api.models.RegistrationBody;
import com.rajkarnikarunish.ecommercebackend.exception.EmailFailureException;
import com.rajkarnikarunish.ecommercebackend.exception.EmailNotFoundException;
import com.rajkarnikarunish.ecommercebackend.exception.UserAlreadyExistsException;
import com.rajkarnikarunish.ecommercebackend.exception.UserNotVerifiedException;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.VerificationToken;
import com.rajkarnikarunish.ecommercebackend.models.dao.LocalUserDao;
import com.rajkarnikarunish.ecommercebackend.models.dao.VerificationTokenDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.List;
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
//        verificationTokenDao.save(verificationToken);
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

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());

        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0
                            || verificationTokens
                            .get(0)
                            .getCreatedTimestamp()
                            .before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000 )));

                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDao.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDao.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();

            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                localUserDao.save(user);
                verificationTokenDao.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDao.findByEmailIgnoreCase(email);
        if(opUser.isPresent()) {
            LocalUser user = opUser.get();
            String token= jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
       } else {
            throw new EmailNotFoundException();
        }
    }

    public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = localUserDao.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            localUserDao.save(user);
        }
    }
}
