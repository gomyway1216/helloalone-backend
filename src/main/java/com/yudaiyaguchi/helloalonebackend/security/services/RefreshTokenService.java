package com.yudaiyaguchi.helloalonebackend.security.services;


import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yudaiyaguchi.helloalonebackend.exception.TokenRefreshException;
import com.yudaiyaguchi.helloalonebackend.models.RefreshToken;
import com.yudaiyaguchi.helloalonebackend.models.User;
import com.yudaiyaguchi.helloalonebackend.repository.RefreshTokenRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository2;


@Service
public class RefreshTokenService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenService.class);
	
  @Value("${jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserRepository2 userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken createRefreshToken(String userId) {
	 // search user by userId and get the reference to the User.
	  
    RefreshToken refreshToken = new RefreshToken();
    try {
        User user = this.userRepository.findById(userId);

        refreshToken.setUser(user);
//        Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("last_login_date");
//        Timestamp
        Instant now = Instant.now();
//        Timestamp timestamp = Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build();
//        refreshToken.setExpiryTime(timestamp);
        Date d = new Date(System.currentTimeMillis() + this.refreshTokenDurationMs);
        LOGGER.info("date a : {}", d);
        refreshToken.setExpiryTime(d);
        refreshToken.setToken(UUID.randomUUID().toString());

//        refreshToken = refreshTokenRepository.save(refreshToken);
        boolean isInserted = refreshTokenRepository.insert(refreshToken);
        LOGGER.info("isInserted : {}", isInserted);
        return refreshToken;
    } catch (Exception e) {
    	LOGGER.error(e.getMessage());
    }
    	return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
	  LOGGER.info("token.getExpiryDate() : {}, Instant.now() : {}, new Date() : {}", token.getExpiryTime(), Instant.now(), new Date());
	  if(token.getExpiryTime().before(new Date())) {
	      refreshTokenRepository.delete(token);
	      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
	  }
    return token;
  }

  @Transactional
  public int deleteByUserId(String userId) {
    try {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId));
	} catch (Exception e) {
		LOGGER.error(e.getMessage());
	}
    return 0;
  }
}