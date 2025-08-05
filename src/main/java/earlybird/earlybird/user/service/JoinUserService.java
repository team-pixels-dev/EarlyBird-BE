package earlybird.earlybird.user.service;

import earlybird.earlybird.security.authentication.oauth2.proxy.response.OAuth2ServerResponse;
import earlybird.earlybird.user.User;
import earlybird.earlybird.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinUserService {

    private final UserRepository userRepository;

    public void join(OAuth2ServerResponse userInfo) {
        User user = new User(userInfo);
        userRepository.save(user);
    }
}
