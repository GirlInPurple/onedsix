package onedsix.loader.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/** @see EnumBuster */
@Slf4j
public class ReflectionHelper {
	public static Class<?>[] getAnonymousClasses(Class<?> clazz) {
		Collection<Class<?>> classes = new ArrayList<>();
		for (int i = 1; ; i++) {
			try {
				classes.add(Class.forName(
					clazz.getName() + "$" + i,
					false, // do not initialize
					Thread.currentThread().getContextClassLoader()));
			} catch (ClassNotFoundException e) {
				break;
			}
		}
		for (Class<?> inner : clazz.getDeclaredClasses()) {
			Collections.addAll(classes, getAnonymousClasses(inner));
		}
		for (Class<?> anon : new ArrayList<>(classes)) {
			Collections.addAll(classes, getAnonymousClasses(anon));
		}
		return classes.toArray(new Class<?>[0]);
	}

	private static final Unsafe UNSAFE;

	static {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			UNSAFE = (Unsafe) theUnsafe.get(null);
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Super dangerous and might have unintended side effects.  Use
	 * only for testing, and then with circumspection.
	 *
	 * @param field
	 * @param value
	 * @throws ReflectiveOperationException
	 */
	public static void setStaticFinalField(Field field, Object value)
		throws ReflectiveOperationException {
		if (!Modifier.isStatic(field.getModifiers())
				|| !Modifier.isFinal(field.getModifiers()))
			throw new IllegalArgumentException(
				"field should be static final");
		Object fieldBase = UNSAFE.staticFieldBase(field);
		long fieldOffset = UNSAFE.staticFieldOffset(field);
		UNSAFE.putObject(fieldBase, fieldOffset, value);
	}

	public static <E extends Enum<E>> E makeEnum(Class<E> enumType, String name)
		throws ReflectiveOperationException {
		E e = (E) UNSAFE.allocateInstance(enumType);
		Field nameField = Enum.class.getDeclaredField("name");
		nameField.setAccessible(true);
		nameField.set(e, name);
		return e;
	}
}
