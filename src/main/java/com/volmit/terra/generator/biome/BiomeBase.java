package com.volmit.terra.generator.biome;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import com.volmit.terra.generator.BiomeGenerator;

public class BiomeBase extends BiomeGenerator
{
	public BiomeBase(long seed)
	{
		super(seed);
	}

	@Override
	public double getBiomeNoise(int worldX, int worldZ)
	{
		return 0.8;
	}

	@Override
	public double getBiomeWeight(int worldX, int worldZ)
	{
		return 0.3;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSurface(int wx, int wz)
	{
		return Material.STONE.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getUnderSurface(int wx, int wz)
	{
		return Material.STONE.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getEarth(int wx, int wz)
	{
		return Material.STONE.getId();
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
