package dk.lbloft.service;

import java.lang.reflect.Constructor;

public class GenericBuilder extends RuntimeBuilder<BaseCo> {
	@Override
	public Object buildRuntime(BaseCo configObj) {
		Builder builderAnnotation = configObj.getClass().getAnnotation(Builder.class);
		
		if(builderAnnotation == null) {
			// builder annotation is missing
			System.out.println("builder annotation is missing for " + configObj.getClass().getSimpleName());
		}
		Object o = null;
		Class<?> clazz = builderAnnotation.value();
		try {
			Constructor<?> constructor = clazz.getConstructor(configObj.getClass());
			o = constructor.newInstance(configObj);
		} catch (NoSuchMethodException e) {
			System.out.println("Missing constructor for '" + clazz.getSimpleName() + "' containing only '" + configObj.getClass().getSimpleName() + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}
