package github.rdev.tomcat.password.lib.factory;

import github.rdev.tomcat.password.lib.util.AppUtil;
import github.rdev.tomcat.password.lib.util.EncryptionUtil;
import org.apache.naming.ResourceRef;
import org.apache.naming.factory.BeanFactory;

import javax.naming.*;
import java.util.Hashtable;

/**
 * Created by rdev1991 on 17/10/2018.
 */
public class EncryptedBeanFactory extends BeanFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws NamingException {
        AppUtil.updatePassword((ResourceRef) obj);

        return super.getObjectInstance(obj, name, nameCtx, environment);
    }

}