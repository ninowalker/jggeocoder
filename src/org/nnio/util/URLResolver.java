package org.nnio.util;

import java.io.IOException;
import java.net.URL;

/**
 * Provides a plugin interface for using the URL resolver of your choice.
 * 
 */
public interface URLResolver {
	public String fetch(URL url) throws IOException;

	public String[] fetch(String host, int port, String[] files)
			throws IOException;
}
