package dk.lbloft.service;

import java.util.List;

import dk.lbloft.service.config.ConfigurationCo;
import dk.lbloft.service.config.OptionCo;
import dk.lbloft.service.health.ServiceCheckerCo;

public class DefaultModuleConfig extends ModuleConfig {

	@Override
	public void getClasses(List<Class<? extends BaseCo>> classes) {
		classes.add(ConfigurationCo.class);
		classes.add(OptionCo.class);
		classes.add(ServiceCheckerCo.class);
	}

}
