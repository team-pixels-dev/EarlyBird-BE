package earlybird.earlybird.email.address.check;

import org.springframework.stereotype.Service;

@Service
public class CheckEmailAddressService {
    public Boolean checkEmailRegex(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
