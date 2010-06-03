package org.nnio.geocode;

/**
 * Class representing a projection. Not used in this implementation, but
 * potentially useful.
 * 
 */
public class Projection {
	public final String proj4;

	public Projection(String proj4) {
		this.proj4 = proj4;
	}

	public boolean equals(Projection p) {
		return this.proj4.equals(p.proj4);
	}
}
