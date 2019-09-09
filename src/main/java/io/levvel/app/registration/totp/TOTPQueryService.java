package io.levvel.app.registration.totp;

import org.springframework.stereotype.Service;

@Service
public class TOTPQueryService {
/*    private final String ISSUER = "APP_USER_NAME";
    private CustomUserRepository customUserRepo;
    private GoogleAuthenticator googleAuth;
    private TOTPRepoitory totpRepoitory;

    public TOTPQueryService(CustomUserRepository customUserRepo, GoogleAuthenticator googleAuth, TOTPRepoitory totpRepoitory){
        this.customUserRepo = customUserRepo;
        this.googleAuth = googleAuth;
        this.totpRepoitory = totpRepoitory;
    }

    public String generateNewGoogleAuthQrUrl(String username){
        GoogleAuthenticatorKey authKey = googleAuth.createCredentials();
        String secret = authKey.getKey();
        totpRepoitory.deleteById(username);
        totpRepoitory.save(new TOTPDetails(username, secret));
        return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(ISSUER, username, authKey);
    }

    public boolean isTotpEnabled(String username){
        return customUserRepo.findByUsername(username).isTotpEnabled();
    }*/
}
