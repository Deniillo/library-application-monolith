package itmo.deniill.service.services;

import itmo.deniill.dao.model.User;
import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.dao.repository.UserProfileRepository;
import itmo.deniill.dao.repository.UserRepository;
import itmo.deniill.service.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);

        UserProfile profile = UserProfile.builder()
                .user(savedUser)
                .name("")
                .photo_url("/default-avatar.jpg")
                .build();

        userProfileRepository.save(profile);
        savedUser.setProfile(profile);
        return userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public User saveUser(User user, UserProfile userProfile) {
        User savedUser = userRepository.save(user);

        UserProfile profile = UserProfile.builder()
                .user(savedUser)
                .name(userProfile.getName())
                .photo_url(userProfile.getPhoto_url())
                .build();

        userProfileRepository.save(profile);
        savedUser.setProfile(profile);
        return userRepository.save(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional()
    public Optional<User> findByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId);
    }

    @Override
    @Transactional()
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updateLastLogin(String providerId) {
        userRepository.findByProviderId(providerId).ifPresent(user -> {
            user.setLastLogin(LocalDate.now());
            userRepository.save(user);
        });
    }
}
