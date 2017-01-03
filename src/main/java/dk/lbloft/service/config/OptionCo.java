package dk.lbloft.service.config;

import java.util.logging.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import dk.lbloft.service.BaseCo;
import dk.lbloft.service.Builder;

@Builder(builder=OptionBuilder.class)
@XStreamAlias("args")
public class OptionCo extends BaseCo {
	private static final Logger logger = Logger.getLogger(OptionCo.class.getName());

}