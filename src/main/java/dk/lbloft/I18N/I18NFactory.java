package dk.lbloft.I18N;

public class I18NFactory {
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> clazz) {
		return (T) java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), new java.lang.Class[] { clazz }, new I18NInvocationHandler(clazz.getSimpleName()));
	}
}
