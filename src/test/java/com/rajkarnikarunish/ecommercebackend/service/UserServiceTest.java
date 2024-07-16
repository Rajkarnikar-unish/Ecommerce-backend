package com.rajkarnikarunish.ecommercebackend.service;

import com.rajkarnikarunish.ecommercebackend.api.models.RegistrationBody;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.VerificationToken;
import com.rajkarnikarunish.ecommercebackend.models.dao.LocalUserDao;
import jakarta.mail.MessagingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
//@AutoConfigureMockMvc
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    EncryptionService encryptionService;
    @Mock
    EmailService emailService;
    @Mock
    LocalUserDao localUserDao;
    @Mock
    JWTService jwtService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() throws MessagingException {
        try {
            RegistrationBody body = new RegistrationBody();
            body.setUsername("UserAxfghfxcjh");
            body.setEmail("UserServiceTest$testRegisterUser@junit.com");
            body.setFirstName("FirstName");
            body.setLastName("LastName");
            body.setPassword("MySecretPassword123");

            String encryptedPassword = "a0b1c2d3";
            String jwtAuthToken = "jwtToken01";

            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setId(12345L);
            verificationToken.setToken("AccessToken");

            LocalUser localUser = new LocalUser();
            localUser.setEmail(body.getEmail());
            localUser.setUsername(body.getUsername());
            localUser.setFirstName(body.getFirstName());
            localUser.setLastName(body.getLastName());
            localUser.setId(123L);
            localUser.setPassword(body.getPassword());

            when(encryptionService.encryptPassword(anyString())).thenReturn(encryptedPassword);
            doNothing().when(emailService).sendVerificationEmail(any(VerificationToken.class));
            when(localUserDao.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
            when(localUserDao.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());
            when(localUserDao.save(any(LocalUser.class))).thenReturn(localUser);
            when(jwtService.generateVerificationJWT(any(LocalUser.class))).thenReturn(jwtAuthToken);

            System.out.println("LOCALUSER");
            System.out.println(localUser.toString());

            LocalUser testResult = userService.registerUser(body);
            System.out.println("TESTRESULT");
            System.out.println(testResult.toString());a

            Assert.assertEquals(testResult, localUser);
        } catch (Exception ex) {
            System.out.println("Error during test registration user: Error: " + ex);
        }
    }

//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    private VerificationTokenDao verificationTokenDao;
//    @Autowired
//    private LocalUserDao localUserDao;
//
//    @Autowired
//    private EncryptionService encryptionService;
//
//    @RegisterExtension
//    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
//            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
//            .withPerMethodLifecycle(true);
//
//    @Test
//    @Transactional //When springboot runs the test and starts the session and knows this is a sql transaction
//    public void testRegisterUser() throws MessagingException {
//        RegistrationBody body = new RegistrationBody();
//        body.setUsername("UserA");
//        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
//        body.setFirstName("FirstName");
//        body.setLastName("LastName");
//        body.setPassword("MySecretPassword123");
//        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(body), "Username should already be in use.");
//        body.setUsername("UserServiceTest$testRegisterUser");
//        body.setEmail("UserA@junit.com");
//        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(body), "Email should already be in use.");
//        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
//        Assertions.assertDoesNotThrow(() -> userService.registerUser(body), "User should register successfully.");
//        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)[0].toString());
//    }
//
//    @Test
//    @Transactional
//    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
//        LoginBody body = new LoginBody();
//        body.setUsername("UserA-NotExists");
//        body.setPassword("PasswordA123-BadPassword");
//        Assertions.assertNull(userService.loginUser(body), "The user should not exist.");
//        body.setUsername("UserA");
//        Assertions.assertNull(userService.loginUser(body), "The password should be incorrect.");
//        body.setPassword("PasswordA123");
//        Assertions.assertNotNull(userService.loginUser(body), "The user should login successfully.");
//        body.setUsername("UserB");
//        body.setPassword("PasswordB123");
//        try {
//            userService.loginUser(body);
//            Assertions.assertTrue(false, "User should not have email verified");
//        } catch (UserNotVerifiedException e){
//            Assertions.assertTrue(e.isNewEmailSent(), "Email verification should be sent");
//            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
//        }
//        try {
//            userService.loginUser(body);
//            Assertions.assertTrue(false, "User should not have email verified");
//        } catch (UserNotVerifiedException e){
//            Assertions.assertFalse(e.isNewEmailSent(), "Email verification should not be resent");
//            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testVerifyUser() throws EmailFailureException {
//        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token is bad or does not exist should return false");
//        LoginBody body = new LoginBody();
//        body.setUsername("UserB");
//        body.setPassword("PasswordB123");
//        try {
//            userService.loginUser(body);
//            Assertions.assertTrue(false, "User should not have email verified");
//        } catch (UserNotVerifiedException e){
//            List<VerificationToken> tokens = verificationTokenDao.findByUser_IdOrderByIdDesc(2L);
//            String token = tokens.get(0).getToken();
//            Assertions.assertTrue(userService.verifyUser(token), "Token should be valid.");
//            Assertions.assertNotNull(body, "The user should now be verified.");
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testForgotPassword() throws MessagingException {
//        Assertions.assertThrows(EmailNotFoundException.class,
//                () -> userService.forgotPassword("UserNotExist@junit.com"));
//        Assertions.assertDoesNotThrow(() -> userService
//                .forgotPassword("UserA@junit.com"),
//                "Non existing email should be rejected.");
//        Assertions.assertEquals("UserA@junit.com",
//                greenMailExtension.getReceivedMessages()[0]
//                        .getRecipients(Message.RecipientType.TO)[0].toString(),
//                "Password reset email should be sent.");
//    }
//
//    @Test
//    public void testResetPassword() {
//        LocalUser user = localUserDao.findByUsernameIgnoreCase("UserA").get();
//        String token = jwtService.generatePasswordResetJWT(user);
//        PasswordResetBody body = new PasswordResetBody();
//        body.setToken(token);
//        body.setPassword("Password123456");
//        userService.resetPassword(body);
//        user = localUserDao.findByUsernameIgnoreCase("UserA").get();
//        Assertions.assertTrue(encryptionService.verifyPassword("Password123456", user.getPassword()),
//                "Password change should be written to DB.");
//    }
}
