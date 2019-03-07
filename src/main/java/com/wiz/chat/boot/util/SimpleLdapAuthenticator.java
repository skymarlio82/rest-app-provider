
package com.wiz.chat.boot.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class SimpleLdapAuthenticator {

	private Hashtable<String, String> env = null;
	
	private final static String LDAP_PROVIDER_URL            = ResourceBundle.getBundle("application").getString("LDAP_PROVIDER_URL");
	private final static String LDAP_SECURITY_AUTHENTICATION = ResourceBundle.getBundle("application").getString("LDAP_SECURITY_AUTHENTICATION");
	private final static String LDAP_SECURITY_PRINCIPAL      = ResourceBundle.getBundle("application").getString("LDAP_SECURITY_PRINCIPAL");
	private final static String LDAP_SECURITY_CREDENTIALS    = ResourceBundle.getBundle("application").getString("LDAP_SECURITY_CREDENTIALS");
	private final static String LDAP_SEARCH_TIME_OUT         = ResourceBundle.getBundle("application").getString("LDAP_SEARCH_TIME_OUT");

	public SimpleLdapAuthenticator() {
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, LDAP_PROVIDER_URL);
		env.put(Context.SECURITY_AUTHENTICATION, LDAP_SECURITY_AUTHENTICATION);
		if (LDAP_SECURITY_AUTHENTICATION.equals("simple")) {
			env.put(Context.SECURITY_PRINCIPAL, LDAP_SECURITY_PRINCIPAL);
			env.put(Context.SECURITY_CREDENTIALS, LDAP_SECURITY_CREDENTIALS);
		}
	}

	public String fetchPassword(String username) {
		String password = null;
		LdapContext ctx = null;
		NamingEnumeration<?> results = null;
		try {
			ctx = new InitialLdapContext(env, null);
			ctx.setRequestControls(null);
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setTimeLimit(Integer.parseInt(LDAP_SEARCH_TIME_OUT));
			String uid = "(uid=" + username + ")";
			results = ctx.search("ou=people", uid, searchControls);
			while (results.hasMore()) {
				SearchResult result = (SearchResult)results.next();
				Attributes attrs = result.getAttributes();
				Attribute pwd = attrs.get("userPassword");
				password = new String((byte[])pwd.get());
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (Exception e) {
					
				}
			}
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					
				}
			}
		}
		return password;
	}
	
	public boolean verify(String username, String password) {
		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, LDAP_PROVIDER_URL);
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, "uid=" + username + ",ou=people,dc=springframework,dc=org");  
		environment.put(Context.SECURITY_CREDENTIALS, password);
        boolean result = false;
		InitialDirContext ctx = null;
		try {
			ctx = new InitialDirContext(environment);
			result = true;
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("====> user : " + username + " verification failed.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					
				}
			}
		}
		return result;
	}
}