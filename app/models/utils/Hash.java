package models.utils;

import org.mindrot.jbcrypt.BCrypt;


public class Hash {

    /**
     * Create an encrypted password from a clear string.
     *
     * @param clearString the clear string
     * @return an encrypted password of the clear string
     * @throws AppException APP Exception, from NoSuchAlgorithmException
     */
    public static String createPassword(String clearString) {
        if (clearString == null) {
            throw new IllegalArgumentException("No password defined!");
        }
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    /**
     * @param candidate         the clear text
     * @param encryptedPassword the encrypted password string to check.
     * @return true if the candidate matches, false otherwise.
     */
    public static boolean checkPassword(String candidate, String encryptedPassword) {
        if (candidate == null) {
            return false;
        }
        if (encryptedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encryptedPassword);
    }
}

