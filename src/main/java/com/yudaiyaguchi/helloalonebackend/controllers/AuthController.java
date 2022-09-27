package com.yudaiyaguchi.helloalonebackend.controllers;

import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yudaiyaguchi.helloalonebackend.exception.TokenRefreshException;
//import com.yudaiyaguchi.helloalonebackend.models.ERole;
import com.yudaiyaguchi.helloalonebackend.models.RefreshToken;
//import com.yudaiyaguchi.helloalonebackend.models.Role;
import com.yudaiyaguchi.helloalonebackend.models.User;
import com.yudaiyaguchi.helloalonebackend.payload.request.SignOutRequest;
import com.yudaiyaguchi.helloalonebackend.payload.request.SignInRequest;
import com.yudaiyaguchi.helloalonebackend.payload.request.SignUpRequest;
import com.yudaiyaguchi.helloalonebackend.payload.request.TokenRefreshRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.JwtResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.TokenRefreshResponse;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
//import com.yudaiyaguchi.helloalonebackend.repository.RoleRepository;
//import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;
import com.yudaiyaguchi.helloalonebackend.security.services.RefreshTokenService;
import com.yudaiyaguchi.helloalonebackend.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        LOGGER.info("LoginRequest getUsername: {}", signInRequest.getUsername());
        LOGGER.info("LoginRequest getPassword: {}", signInRequest.getPassword());
        System.out.println(signInRequest.getUsername() + " : " + signInRequest.getPassword());
        // Authentication authentication = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // UsernamePasswordAuthenticationToken authenticationToken = new
        // UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(), signInRequest.getPassword());
        LOGGER.info("authenticationToken : {}", authenticationToken);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            LOGGER.error("authentication exception", e);
        }

        LOGGER.info("the authentication was fine.");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        LOGGER.info("userDetails : {}", userDetails);
        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), userDetails.isAdmin()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
            throws InterruptedException, ExecutionException {
        LOGGER.info("signUpRequest getUsername: {}", signUpRequest.getUsername());
        LOGGER.info("signUpRequest getEmail: {}", signUpRequest.getEmail());
        LOGGER.info("signUpRequest getPassword: {}", signUpRequest.getPassword());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        System.out.println("signUpRequest.getPassword() : " + signUpRequest.getUsername());
        System.out.println("signUpRequest.getPassword() : " + signUpRequest.getPassword());
        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        userRepository.insert(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser).map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody SignOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
    
//    @PostMapping("/check-token-expiry")
//    public ResponseEntity<?> checkTokenExpiry()
}