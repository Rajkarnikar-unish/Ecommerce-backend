package com.rajkarnikarunish.ecommercebackend.api.security;

import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.dao.LocalUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JUnitUserDetailsService implements UserDetailsService {

    @Autowired
    private LocalUserDao localUserDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(username);
        if(opUser.isPresent())
            return opUser.get();
        return null;
    }
}
