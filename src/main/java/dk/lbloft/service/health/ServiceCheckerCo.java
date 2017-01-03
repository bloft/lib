package dk.lbloft.service.health;

import java.util.logging.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import dk.lbloft.service.BaseCo;
import dk.lbloft.service.Builder;


@Builder(ServiceChecker.class)
@XStreamAlias("service-checker")
public class ServiceCheckerCo extends BaseCo {
	private static final Logger logger = Logger.getLogger(ServiceCheckerCo.class.getName());

	public long getInterval() {
		return getConfig().getTime("interval", "1m");
	}
}