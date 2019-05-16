package me.zombie_striker.pixelprinter.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.bukkit.Bukkit;

/**
 * A small help with reflection
 */
public class ReflectionUtil {
	private static final String SERVER_VERSION;
	
	
	static {
		String name = Bukkit.getServer().getClass().getName();
		name = name.substring(name.indexOf("craftbukkit.")
				+ "craftbukkit.".length());
		name = name.substring(0, name.indexOf("."));
		SERVER_VERSION = name;
	}
	public static boolean isVersionHigherThan(int mainVersion,
			int secondVersion) {
		String firstChar = SERVER_VERSION.substring(1, 2);
		int fInt = Integer.parseInt(firstChar);
		if (fInt < mainVersion)
			return false;
		StringBuilder secondChar = new StringBuilder();
		for (int i = 3; i < 10; i++) {
			if (SERVER_VERSION.charAt(i) == '_'
					|| SERVER_VERSION.charAt(i) == '.')
				break;
			secondChar.append(SERVER_VERSION.charAt(i));
		}
		int sInt = Integer.parseInt(secondChar.toString());
		if (sInt < secondVersion)
			return false;
		return true;
	}

	/**
	 * Returns the NMS class.
	 * 
	 * @param name
	 *            The name of the class
	 * 
	 * @return The NMS class or null if an error occurred
	 */
	public static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + SERVER_VERSION
					+ "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the CraftBukkit class.
	 * 
	 * @param name
	 *            The name of the class
	 * 
	 * @return The CraftBukkit class or null if an error occurred
	 */

	public static Class<?> getCraftbukkitClass(String name,
			String packageName) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION
					+ "." + packageName + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the CraftBukkit class.
	 * 
	 * @param name
	 *            The name of the class
	 * 
	 * @return The CraftBukkit class or null if an error occurred
	 */

	public static Class<?> getCraftbukkitClass(String name) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION
					+ "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the mojang.authlib class.
	 * 
	 * @param name
	 *            The name of the class
	 * 
	 * @return The mojang.authlib class or null if an error occurred
	 */

	public static Class<?> getMojangAuthClass(String name) {
		try {
			return Class.forName("com.mojang.authlib." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Invokes the method
	 * 
	 * @param handle
	 *            The handle to invoke it on
	 * @param methodName
	 *            The name of the method
	 * @param parameterClasses
	 *            The parameter types
	 * @param args
	 *            The arguments
	 * 
	 * @return The resulting object or null if an error occurred / the
	 *         method didn't return a thing
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeMethod(Object handle, String methodName,
			Class[] parameterClasses, Object... args) {
		return invokeMethod(handle.getClass(), handle, methodName,
				parameterClasses, args);
	}

	/**
	 * Invokes the method
	 * 
	 * @param clazz
	 *            The class to invoke it from
	 * @param handle
	 *            The handle to invoke it on
	 * @param methodName
	 *            The name of the method
	 * @param parameterClasses
	 *            The parameter types
	 * @param args
	 *            The arguments
	 * 
	 * @return The resulting object or null if an error occurred / the
	 *         method didn't return a thing
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeMethod(Class<?> clazz, Object handle,
			String methodName, Class[] parameterClasses, Object... args) {
		Optional<Method> methodOptional = getMethod(clazz, methodName,
				parameterClasses);

		if (!methodOptional.isPresent()) {
			return null;
		}

		Method method = methodOptional.get();

		try {
			return method.invoke(handle, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the value of an instance field
	 * 
	 * @param handle
	 *            The handle to invoke it on
	 * @param name
	 *            The name of the field
	 * @param value
	 *            The new value of the field
	 */
	@SuppressWarnings("deprecation")
	public static void setInstanceField(Object handle, String name,
			Object value) {
		Class<?> clazz = handle.getClass();
		Optional<Field> fieldOptional = getField(clazz, name);
		if (!fieldOptional.isPresent()) {
			return;
		}

		Field field = fieldOptional.get();
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(handle, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the value of an instance field
	 * 
	 * @param handle
	 *            The handle to invoke it on
	 * @param name
	 *            The name of the field
	 * 
	 * @return The result
	 */
	@SuppressWarnings("deprecation")
	public static Object getInstanceField(Object handle, String name) {
		Class<?> clazz = handle.getClass();
		Optional<Field> fieldOptional = getField(clazz, name);
		if (!fieldOptional.isPresent()) {
			return handle;
		}
		Field field = fieldOptional.get();
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			return field.get(handle);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns an enum constant
	 * 
	 * @param enumClass
	 *            The class of the enum
	 * @param name
	 *            The name of the enum constant
	 * 
	 * @return The enum entry or null
	 */
	public static Object getEnumConstant(Class<?> enumClass, String name) {
		if (!enumClass.isEnum()) {
			return null;
		}
		for (Object o : enumClass.getEnumConstants()) {
			if (name.equals(invokeMethod(o, "name", new Class[0]))) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Returns the constructor
	 * 
	 * @param clazz
	 *            The class
	 * @param params
	 *            The Constructor parameters
	 * 
	 * @return The Constructor or an empty Optional if there is none with
	 *         these parameters
	 */
	public static Optional<?> getConstructor(Class<?> clazz,
			Class<?>... params) {
		try {
			return Optional.of(clazz.getConstructor(params));
		} catch (NoSuchMethodException e) {
			try {
				return Optional.of(clazz.getDeclaredConstructor(params));
			} catch (NoSuchMethodException e2) {
				e2.printStackTrace();
			}
		}
		return Optional.empty();
	}

	/**
	 * Instantiates the class. Will print the errors it gets
	 * 
	 * @param constructor
	 *            The constructor
	 * @param arguments
	 *            The initial arguments
	 * 
	 * @return The resulting object, or null if an error occurred.
	 */
	public static Object instantiate(Constructor<?> constructor,
			Object... arguments) {
		try {
			return constructor.newInstance(arguments);
		} catch (InstantiationException | IllegalAccessException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Optional<Method> getMethod(Class<?> clazz, String name,
			Class<?>... params) {
		try {
			return Optional.of(clazz.getMethod(name, params));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return Optional.of(clazz.getDeclaredMethod(name, params));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	public static Optional<Field> getField(Class<?> clazz, String name) {
		try {
			return Optional.of(clazz.getField(name));
		} catch (NoSuchFieldException e){}

		try {
			return Optional.of(clazz.getDeclaredField(name));
		} catch (NoSuchFieldException e) {}
		return Optional.empty();
	}
}