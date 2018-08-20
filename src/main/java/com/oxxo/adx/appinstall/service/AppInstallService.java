/**
 * 
 */
package com.oxxo.adx.appinstall.service;

/**
 * @author Administrator
 *
 */
public interface AppInstallService {
	<T> T deserializeMsg(String msg, Class<T> cls);
}
