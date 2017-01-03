package dk.lbloft.service;

import java.util.List;
import java.util.logging.Logger;


public class TestModuleConfig extends ModuleConfig {
	private static final Logger logger = Logger.getLogger(TestModuleConfig.class.getName());

	@Override
	public void getClasses(List<Class<? extends BaseCo>> classes) {
		classes.add(TimerServiceCo.class);
	}
}