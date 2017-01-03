package dk.lbloft.service.health;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.util.concurrent.AbstractScheduledService;

import dk.lbloft.locate.Locator;


public class ServiceChecker extends AbstractScheduledService {
	private static final Logger logger = Logger.getLogger(ServiceChecker.class.getName());
	private final ServiceCheckerCo config;

	public ServiceChecker(ServiceCheckerCo config) {
		this.config = config;
	}

	@Override
	protected void runOneIteration() throws Exception {
		for(ServiceTester service : Locator.locateAll(ServiceTester.class)) {
			Status status = service.getStatus();
			if(status.getType() != Status.StatusType.OK) {
				logger.warning(status.toString());
			}
		}
	}

	@Override
	protected Scheduler scheduler() {
		return Scheduler.newFixedDelaySchedule(config.getInterval(), config.getInterval(), TimeUnit.MICROSECONDS);
	}
}