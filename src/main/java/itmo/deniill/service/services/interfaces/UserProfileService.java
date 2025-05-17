package itmo.deniill.service.services.interfaces;

import itmo.deniill.dao.model.UserProfile;

import java.util.Optional;

public interface UserProfileService {
    UserProfile saveProfile(UserProfile profile);
    UserProfile updateProfile(UserProfile profile);
    UserProfile findByUserId(Long userId);
    void deleteProfile(Long id);
}
