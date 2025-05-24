package in.abhi8290.helloworld.shared.util;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public class hashUtil {

    private static final int SALT_LENGTH = 16; // 128 bits
    private static final int HASH_LENGTH = 256; // bits
    private static final int ITERATIONS = 65536;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    // Generate a random salt
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    // Hash the password with the given salt
    public static String hashPassword(String password , byte[] salt) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    public static String getHashedPasswordWithSaltAndAlgorithm(String password) throws Exception {
        byte[] salt = generateSalt();

        return ALGORITHM + "$" + ITERATIONS + "$" + encodeSalt(salt) + "$" + hashPassword(password, salt);
    }





    public static boolean verifyPassword(String password, String hash) throws Exception {
        String[] splittedString = hash.split("\\$");
        byte[] salt = Base64.getDecoder().decode(splittedString[2]);
        String savedHash  = splittedString[3];
        return Objects.equals(hashPassword(password, salt), savedHash);
    }


    // Encode salt to store it with the hash
    public static String encodeSalt(byte[] salt) {
        return Base64.getEncoder().encodeToString(salt);
    }

    // Decode salt when verifying
    public static byte[] decodeSalt(String encodedSalt) {
        return Base64.getDecoder().decode(encodedSalt);
    }
}
