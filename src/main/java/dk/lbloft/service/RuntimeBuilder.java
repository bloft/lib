package dk.lbloft.service;

import java.io.IOException;

public abstract class RuntimeBuilder<T extends BaseCo> {
	public abstract Object buildRuntime(T configObj) throws IOException;
}
