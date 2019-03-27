package com.volmit.terra.generator.biome;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import com.volmit.terra.generator.BiomeGenerator;
import com.volmit.terra.generator.FilteredNoiseGenerator;

import mortar.compute.noise.SimplexOctaveGenerator;

public class BiomeDesert extends BiomeGenerator
{
	private FilteredNoiseGenerator plainNoise;

	public BiomeDesert(long seed)
	{
		super(seed);
		plainNoise = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1000, 7), 65, 0.5, 0.5);
	}

	@Override
	public double getBiomeNoise(int worldX, int worldZ)
	{
		return 0.12 + (plainNoise.noise(worldX, worldZ) * 0.095);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSurface(int wx, int wz)
	{
		return Material.SAND.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getUnderSurface(int wx, int wz)
	{
		return Material.SAND.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getEarth(int wx, int wz)
	{
		return Material.SANDSTONE.getId();
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
		return Biome.DESERT;
	}

	@Override
	public int getEarthHeight(int wx, int wz)
	{
		return 9;
	}
}
