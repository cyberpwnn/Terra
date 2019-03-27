package com.volmit.terra.generator.biome;

import com.volmit.terra.generator.FilteredNoiseGenerator;

import mortar.compute.noise.SimplexOctaveGenerator;

public class BiomePlainsHills extends BiomePlains
{
	private FilteredNoiseGenerator plainNoise;

	public BiomePlainsHills(long seed)
	{
		super(seed);
		plainNoise = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1000, 4), 99, 0.5, 0.5);
	}

	@Override
	public double getBiomeNoise(int worldX, int worldZ)
	{
		return 0.29 + (plainNoise.noise(worldX, worldZ) * 0.195);
	}
}
