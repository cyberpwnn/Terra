package com.volmit.terra.generator;

import mortar.compute.noise.NoiseGenerator;
import mortar.compute.noise.OctaveGenerator;

public class FilteredNoiseGenerator
{
	private NoiseGenerator generator;
	private OctaveGenerator octaveGenerator;
	private double zoom;
	private double amp;
	private double freq;

	public FilteredNoiseGenerator(NoiseGenerator generator, double zoom)
	{
		this.generator = generator;
		this.zoom = zoom;
	}

	public FilteredNoiseGenerator(OctaveGenerator octaveGenerator, double zoom, double amp, double freq)
	{
		this.octaveGenerator = octaveGenerator;
		this.amp = amp;
		this.freq = freq;
		this.zoom = zoom;
	}

	public double noise(double x, double z)
	{
		return generator != null ? generator.noise((double) x / zoom, (double) z / zoom) : octaveGenerator.noise((double) x / zoom, (double) z / zoom, amp, freq, true);
	}
}
