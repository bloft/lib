package dk.lbloft.service;

import java.util.List;

public abstract class ModuleConfig {
	public abstract void getClasses(List<Class<? extends BaseCo>> classes);
}
