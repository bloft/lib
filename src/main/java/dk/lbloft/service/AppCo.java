package dk.lbloft.service;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("application")
public class AppCo {
	protected String logging;
	
	@XStreamImplicit
	protected List<BaseCo> extensions = new ArrayList<BaseCo>();
	
	public List<BaseCo> getExtensions() {
		return new ArrayList<BaseCo>(extensions);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(AppCo.class).add("extendsions", extensions).toString();
	}
}
