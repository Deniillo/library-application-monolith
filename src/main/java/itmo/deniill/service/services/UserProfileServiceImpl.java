package itmo.deniill.service.services;

import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.dao.repository.UserProfileRepository;
import itmo.deniill.service.services.interfaces.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public UserProfile saveProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    @Override
    @Transactional
    public UserProfile updateProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    @Override
    @Transactional()
    public UserProfile findByUserId(Long userId) {
        return userProfileRepository.findById(userId).orElseThrow(() -> new RuntimeException("user profile is not founded"));
    }
}
