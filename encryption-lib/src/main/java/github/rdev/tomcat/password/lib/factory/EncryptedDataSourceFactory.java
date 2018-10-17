package github.rdev.tomcat.password.lib.factory;

import github.rdev.tomcat.password.lib.util.AppUtil;
import github.rdev.tomcat.password.lib.util.EncryptionUtil;
import org.apache.naming.ResourceRef;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;

import javax.naming.Context;
import javax.naming.Name;
import java.util.Hashtable;

/**
 * Created by rdev1991 on 17/10/2018.
 */
public class EncryptedDataSourceFactory extends DataSourceFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        AppUtil.updatePassword((ResourceRef) obj);

        return super.getObjectInstance(obj, name, nameCtx, environment);
    }
}
