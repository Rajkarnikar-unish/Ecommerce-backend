package com.rajkarnikarunish.ecommercebackend.api.controller.user;

import com.rajkarnikarunish.ecommercebackend.models.Address;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.dao.AddressDao;
import org.apache.coyote.Response;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private AddressDao addressDao;

    public UserController(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getAddress(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long userId
    ) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(addressDao.findByLocalUser_Id(userId));
    }

    @PutMapping("/{userId}/address")
    public ResponseEntity<Address> putAddress(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long userId,
            @RequestBody Address address
    ) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        address.setId(null);
        LocalUser refUser = new LocalUser();
        refUser.setId(userId);
        address.setLocalUser(refUser);
        return ResponseEntity.ok(addressDao.save(address));
    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress (
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody Address address
    ) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressDao.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getLocalUser();
                if (originalUser.getId() == userId) {
                    address.setLocalUser(originalUser);
                    return ResponseEntity.ok(addressDao.save(address));
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }

    private boolean userHasPermission(LocalUser user, Long id) {
        return user.getId() == id;
    }
}
