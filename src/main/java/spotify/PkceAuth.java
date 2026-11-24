/* This class is a refactored version of  PKCE Auth from spotify. To ensure that our keys are not public through jar or any means.
 *
 * */

package spotify;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PkceAuth {
  private static final String POSSIBLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();

  private PkceAuth() {
  }

  // This should only be in the range of 43-128 characters. Based on spotify api
  // docs
  public static String generateRandomString(int length) {
    // allocate space for the stringbuilder
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(POSSIBLE.charAt(RANDOM.nextInt(POSSIBLE.length())));
    }
    return sb.toString();
  }

  public static String generateCodeVerifier() {
    return generateRandomString(64);
  }

  // SHA-256 of the verifier, base64url encoded with no padding
  public static String generateCodeChallenge(String codeVerifier) {
    if (codeVerifier == null) {
      throw new IllegalArgumentException("codeVerifier must not be null");
    }
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
      return base64UrlEncode(hash);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 not available", e);
    }
  }

  private static String base64UrlEncode(byte[] input) {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(input);
  }
}
