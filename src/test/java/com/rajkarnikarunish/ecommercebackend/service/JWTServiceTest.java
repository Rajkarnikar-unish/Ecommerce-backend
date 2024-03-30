package com.rajkarnikarunish.ecommercebackend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.dao.LocalUserDao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@AutoConfigureMockMvc
public class JWTServiceTest {

//    @Value("${jwt.algorithm.key}")
//    private String algorithmKey;
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    private LocalUserDao localUserDao;
//
////    @Transactional
//    @Test
//    public void testVerificationTokenNotUsableForLogin() {
//        LocalUser user = localUserDao.findByUsernameIgnoreCase("UserA").get();
//        String token = jwtService.generateVerificationJWT(user);
//        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
//    }
//
//    @Test
//    public void testAuthTokenReturnsUsername() {
//        LocalUser user = localUserDao.findByUsernameIgnoreCase("UserA").get();
//        String token = jwtService.generateJWT(user);
//        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain users username.");
//    }
//
//    @Test
//    public void testLoginJWTNotGeneratedByUs() throws Exception {
//        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256(
//                "NotTheRealSecret"));
//        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getUsername(token));
//    }
//
//    @Test
//    public void testLoginJWTCorrectlySignedNoIssuer() {
//        String token = JWT.create().withClaim("USERNAME", "UserA")
//                .sign(Algorithm.HMAC256(algorithmKey));
//        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getUsername(token));
//    }
//
//    @Test
//    public void testResetPasswordJWTNotGeneratedByUs() throws Exception {
//        String token = JWT.create().withClaim("RESET_PASSWORD_EMAIL_KEY", "UserA@junit.com").sign(Algorithm.HMAC256(
//                "NotTheRealSecret"));
//        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getResetPasswordEmail(token));
//    }
//
//    @Test
//    public void testResetPasswordJWTCorrectlySignedNoIssuer() {
//        String token = JWT.create().withClaim("RESET_PASSWORD_EMAIL_KEY", "UserA@junit.com")
//                .sign(Algorithm.HMAC256(algorithmKey));
//        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getResetPasswordEmail(token));
//    }
//
//    @Test
//    public void testPasswordResetToken() {
//        LocalUser user = localUserDao.findByUsernameIgnoreCase("UserA").get();
//        String token = jwtService.generatePasswordResetJWT(user);
//        Assertions.assertEquals(user.getEmail(),
//                jwtService.getResetPasswordEmail(token), "Email should match inside JWT.");
//    }
}
