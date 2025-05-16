package itmo.deniill.service.services.interfaces;

import itmo.deniill.dao.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {
    User registerUser(OAuth2User principal);
}
