package com.volmit.terra.generator;

import org.bukkit.block.Biome;

import mortar.compute.math.M;
import mortar.compute.noise.SimplexOctaveGenerator;

public abstract class BiomeGenerator
{
	public static final double scale = 0.15;
	private FilteredNoiseGenerator weightBase;
	private FilteredNoiseGenerator weightSub;
	private FilteredNoiseGenerator weightSub2;
	private FilteredNoiseGenerator weightSub3;

	public BiomeGenerator(long seed)
	{
		weightBase = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1000, 2), scale * 1000D, 1D, 0.525D);
		weightSub = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1001, 2), scale * 1200D, 1D, 0.325D);
		weightSub2 = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1002, 2), scale * 1300D, 1D, 0.625D);
		weightSub3 = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1003, 2), scale * 500D, 1D, 0.225D);
	}

	public double getBiomeWeight(int worldX, int worldZ)
	{
		return weightBase.noise(worldX, worldZ) - (weightSub.noise(worldX, worldZ)) - (weightSub2.noise(worldX, worldZ)) - ((weightSub3.noise(worldX, worldZ)) / 15D);
	}

	public abstract int getEarthHeight(int wx, int wz);

	public abstract int getSurface(int wx, int wz);

	public abstract int getUnderSurface(int wx, int wz);

	public abstract int getEarth(int wx, int wz);

	public abstract int getRock(int wx, int wz);

	public abstract Biome getBiome(int wx, int wz);

	public abstract double getBiomeNoise(int worldX, int worldZ);

	public double noise(int x, int z)
	{
		return M.clip(getBiomeNoise(x, z), 0, 1) * Math.pow(M.clip(getBiomeWeight(x, z), 0, 1), 3);
	}
}
