package com.rajkarnikarunish.ecommercebackend.api.controller.user;

import com.rajkarnikarunish.ecommercebackend.api.models.DataChange;
import com.rajkarnikarunish.ecommercebackend.models.Address;
import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.dao.AddressDao;
import com.rajkarnikarunish.ecommercebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private AddressDao addressDao;
    private SimpMessagingTemplate simpMessagingTemplate;
    private UserService userService;

    public UserController(AddressDao addressDao, SimpMessagingTemplate simpMessagingTemplate, UserService userService) {
        this.addressDao = addressDao;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getAddress(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long userId
    ) {
        if (!userService.userHasPermissionToUser(user, userId)) {
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
        if (!userService.userHasPermissionToUser(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        address.setId(null);
        LocalUser refUser = new LocalUser();
        refUser.setId(userId);
        address.setLocalUser(refUser);
        Address savedAddress = addressDao.save(address);
        simpMessagingTemplate.convertAndSend(
                "/topic/user/" + userId + "/address",
                new DataChange<>(DataChange.ChangeType.INSERT, address)
        );
        return ResponseEntity.ok(savedAddress);
    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress (
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody Address address
    ) {
        if (!userService.userHasPermissionToUser(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressDao.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getLocalUser();
                if (originalUser.getId() == userId) {
                    address.setLocalUser(originalUser);
                    Address savedAddress = addressDao.save(address);
                    simpMessagingTemplate.convertAndSend(
                            "/topic/user/" + userId + "/address",
                            new DataChange<>(DataChange.ChangeType.UPDATE, address)
        );
                    return ResponseEntity.ok(savedAddress);
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
