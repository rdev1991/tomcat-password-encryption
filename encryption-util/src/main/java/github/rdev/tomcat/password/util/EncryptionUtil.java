package github.rdev.tomcat.password.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import static java.nio.charset.Charset.defaultCharset;

/**
 * Created by rdev1911 on 17/10/2018.
 */
public class EncryptionUtil {

    private String secretKey;

    private static final String CIPHER = "AES/ECB/PKCS5PADDING";

    public EncryptionUtil(String secretKey) {
        this.secretKey = secretKey;
    }

    private Key getKey() {
        if (isEmpty(secretKey)) {
            throw new RuntimeException("Need secret Key to encrypt the password");
        }

        SecretKeySpec aesKey = null;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(secretKey.getBytes(defaultCharset()));
            aesKey = new SecretKeySpec(Arrays.copyOf(key, 16), "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return aesKey;
    }

    public String getEncryptedData(String plainData) {
        if (isEmpty(plainData)) {
            throw new RuntimeException("Need data to encrypt");
        }

        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            final byte[] byteArray = cipher.doFinal(plainData.getBytes(defaultCharset()));

            return byteArrayToHexString(byteArray);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDecryptedData(String encryptedData) {
        if (isEmpty(encryptedData)) {
            throw new RuntimeException("Need data to decrypt");
        }

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            final byte[] bytes = cipher.doFinal(hexStringToByteArray(encryptedData));
            return new String(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b & 0xff));
        }
        return stringBuilder.toString();
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static void main(String[] args) throws InterruptedException {
        String data = "somedatabasepassword";
        String key = "s3cr3tK3y";
        EncryptionUtil util = new EncryptionUtil(key);
        final String encryptedData = util.getEncryptedData(data);
        System.out.println(encryptedData);

//        final String decryptedData = util.getDecryptedData(encryptedData);
//        System.out.println(decryptedData);
    }
}
