package dk.lbloft.service.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import dk.lbloft.config.converter.URLConverter;
import dk.lbloft.service.BaseCo;
import dk.lbloft.service.Builder;

@Builder(builder=ConfigurationBuilder.class)
@XStreamAlias("configuration")
public class ConfigurationCo extends BaseCo {
	@XStreamImplicit(itemFieldName="url")
	private List<String> urls;
	
	public List<URL> getUrls() {
		URLConverter converter = new URLConverter();
		ArrayList<URL> res = new ArrayList<URL>();
		for (String url : urls) {
			res.add(converter.convert(url));
		}
		return res;
	}
}
