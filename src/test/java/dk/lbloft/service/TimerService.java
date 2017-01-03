package dk.lbloft.service;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.util.concurrent.AbstractScheduledService;


public class TimerService extends AbstractScheduledService {
	private static final Logger logger = Logger.getLogger(TimerService.class.getName());

	private TimerServiceCo config;

	public TimerService(TimerServiceCo config) {
		this.config = config;
	}

	@Override
	protected void runOneIteration() throws Exception {
		logger.info(config.getText());
	}

	@Override
	protected Scheduler scheduler() {
		return Scheduler.newFixedRateSchedule(0, config.getInterval(), TimeUnit.MILLISECONDS);
	}
}