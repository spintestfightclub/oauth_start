package io.levvel.app.registration.totp;

import io.levvel.app.registration.user.CustomUser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@Service
public class TOTPService {
    private final String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    private final String APP_NAME = "SpringRegistration";

    public String generateQRUrl(CustomUser user) throws IOException {
        String qrUrl = QR_PREFIX + URLEncoder.encode(String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                APP_NAME, user.getEmail(), user.getSecret(), APP_NAME),
                "UTF-8");
        return qrUrl;
    }
}
