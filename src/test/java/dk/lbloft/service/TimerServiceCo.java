package dk.lbloft.service;

import java.util.logging.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Builder(TimerService.class)
@XStreamAlias("timer")
public class TimerServiceCo extends BaseCo {
	private static final Logger logger = Logger.getLogger(TimerServiceCo.class.getName());

	public String getText() {
		return getConfig().get("text", "wee");
	}

	public long getInterval() {
		return getConfig().getTime("interval", "1s");
	}
}