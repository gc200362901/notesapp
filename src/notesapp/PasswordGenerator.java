package notesapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Sean Noddin
 */
public class PasswordGenerator {

    /**
     * this will create a random salt consisting of 16 bytes
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException {

        SecureRandom rng = SecureRandom.getInstanceStrong();

        byte[] salt = new byte[16];

        rng.nextBytes(salt);

        return salt;

    }

    /**
     * this will hash a password with a given salt and return it as a string
     */
    public static String getPW(String pw, byte[] salt) throws NoSuchAlgorithmException {

        String hashedPW = null;

        try {

            //configure hashing algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(salt);

            byte[] hashed = md.digest(pw.getBytes());

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < hashed.length; i++) {
                sb.append(Integer.toString((hashed[i] & 0xff) + 0x100, 16).substring(1));
            }

            hashedPW = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        }

        return hashedPW;
    }
}
