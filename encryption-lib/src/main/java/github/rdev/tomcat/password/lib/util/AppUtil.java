package github.rdev.tomcat.password.lib.util;

import org.apache.naming.ResourceRef;

import javax.naming.RefAddr;
import javax.naming.StringRefAddr;

/**
 * Created by rdev1991 on 17/10/2018.
 */
public class AppUtil {

    private static EncryptionUtil encryptionUtil = new EncryptionUtil("s3cr3tK3y");

    public static void updatePassword(ResourceRef obj) {
        ResourceRef resourceRef = obj;
        for (int i = 0; i < resourceRef.size(); i++) {
            RefAddr addr = resourceRef.get(i);
            String tag = addr.getType();

            if (tag.trim().equals("password")) {
                final String encryptedPassword = (String) addr.getContent();
                final String decrypt = encryptionUtil.getDecryptedData(encryptedPassword);
                RefAddr refAddr = new RefAddr("password") {
                    @Override
                    public Object getContent() {
                        return decrypt;
                    }
                };

                resourceRef.remove(i);
                resourceRef.add(i, new StringRefAddr(tag, decrypt));
                break;
            }
        }
    }
}
