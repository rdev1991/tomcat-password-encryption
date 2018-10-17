# tomcat-password-encryption
Library to encrypt tomcat resources password.

1. Use encryption-util to generate encrypted password with supplied secret Key
2. Update key in AppUtil of encryption-lib and build it.
3. Use the required Factory class in server/context configuration with encrypted password
4. Copy the library obtained from step 2 to tomcat classpath

Tested with Tomcat DBCP and C3Po based connection pooling.

Please feel free to use and report issue.
