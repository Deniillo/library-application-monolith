package itmo.deniill.service.services.interfaces;


import itmo.deniill.dao.model.User;
import itmo.deniill.dao.model.UserProfile;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User saveUser(User user, UserProfile userProfile);
    void deleteUser(Long id);
    Optional<User> findByProviderId(String providerId);
    Optional<User> findByEmail(String email);
    void updateLastLogin(String providerId);
}
