package dk.lbloft.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Stack;

public class Closer implements AutoCloseable {
	Stack<Closeable> stack = new Stack<Closeable>();

	public <T extends Closeable> T add(T cloneable) {
		stack.push(cloneable);
		return cloneable;
	}

	@Override
	public void close() {
		while(!stack.isEmpty()) {
			Closeable closeable = stack.pop();
			if(closeable != null) {
				try {
					System.out.println("Closing: " + closeable);
					closeable.close();
				} catch (IOException e) {}
			}
		}
	}
}