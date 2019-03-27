package com.volmit.terra.generator.biome;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import com.volmit.terra.generator.BiomeGenerator;
import com.volmit.terra.generator.FilteredNoiseGenerator;

import mortar.compute.noise.SimplexOctaveGenerator;

public class BiomePlains extends BiomeGenerator
{
	protected FilteredNoiseGenerator plainNoise;
	protected FilteredNoiseGenerator plainNoiseF;

	public BiomePlains(long seed)
	{
		super(seed);
		plainNoise = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1000, 7), 45, 0.5, 0.5);
		plainNoiseF = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1001, 3), 11, 0.5, 0.5);
	}

	@Override
	public double getBiomeNoise(int worldX, int worldZ)
	{
		return 0.23 + (plainNoise.noise(worldX, worldZ) * 0.095) + (plainNoiseF.noise(worldX, worldZ) * 0.035);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSurface(int wx, int wz)
	{
		return Material.GRASS.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getUnderSurface(int wx, int wz)
	{
		return Material.DIRT.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getEarth(int wx, int wz)
	{
		return Material.DIRT.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getRock(int wx, int wz)
	{
		return Material.STONE.getId();
	}

	@Override
	public Biome getBiome(int wx, int wz)
	{
		return Biome.PLAINS;
	}

	@Override
	public int getEarthHeight(int wx, int wz)
	{
		return 5;
	}
}
