package itmo.deniill.service.services;

import itmo.deniill.dao.model.User;
import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.service.services.interfaces.AuthService;
import itmo.deniill.service.services.interfaces.UserProfileService;
import itmo.deniill.service.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired private final UserProfileService userProfileService;
    @Autowired private final UserService userService;

    @Override
    public User registerUser(OAuth2User principal) {
        Map<String, Object> attributes = principal.getAttributes();
        if(userService.findByProviderId(getProviderId(attributes)).isEmpty()){
            UserProfile userProfile = UserProfile
                    .builder()
                    .name(getName(attributes))
                    .photo_url(getPicture(attributes))
                    .build();
            User user = User
                    .builder()
                    .providerId(getProviderId(attributes))
                    .lastLogin(LocalDate.now())
                    .username(getNickname(attributes))
                    .email(getEmail(attributes))
                    .build();
            return userService.saveUser(user, userProfile);
        }
        return userService.findByProviderId(getProviderId(attributes))
                .orElseThrow(() -> new RuntimeException("I cant register this user "));
    }

    private String getProviderId(Map<String, Object> attributes) {
        return (String) attributes.get("sub");
    }

    private String getEmail(Map<String, Object> attributes) {
        return (String) attributes.get("email");
    }

    private String getNickname(Map<String, Object> attributes) {
        return (String) attributes.get("nickname");
    }

    private String getName(Map<String, Object> attributes) {
        return (String) attributes.get("name");
    }

    private String getPicture(Map<String, Object> attributes) {
        return (String) attributes.get("picture");
    }
}
