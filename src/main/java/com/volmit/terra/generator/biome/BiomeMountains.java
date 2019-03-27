package com.volmit.terra.generator.biome;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import com.volmit.terra.generator.BiomeGenerator;
import com.volmit.terra.generator.FilteredNoiseGenerator;

import mortar.compute.noise.SimplexOctaveGenerator;

public class BiomeMountains extends BiomeGenerator
{
	protected FilteredNoiseGenerator fractureX;
	protected FilteredNoiseGenerator fractureZ;
	protected FilteredNoiseGenerator fractureXC;
	protected FilteredNoiseGenerator fractureZC;
	protected FilteredNoiseGenerator plainNoise;
	protected FilteredNoiseGenerator plainNoiseBump;

	public BiomeMountains(long seed)
	{
		super(seed);
		plainNoise = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1000, 4), 45, 0.5, 0.5);
		plainNoiseBump = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1006, 6), 125, 0.5, 0.5);
		fractureX = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1001, 4), 250, 0.5, 0.5);
		fractureZ = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1002, 4), 250, 0.5, 0.5);
		fractureXC = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1003, 4), 1025, 0.5, 0.5);
		fractureZC = new FilteredNoiseGenerator(new SimplexOctaveGenerator(seed + 1004, 4), 1025, 0.5, 0.5);
	}

	@Override
	public double getBiomeNoise(int worldX, int worldZ)
	{
		return 0.29 + (plainNoiseBump.noise(worldX, worldZ) * 0.395) + ((plainNoise.noise(worldX + (fractureX.noise(worldX, worldZ) * 1000), worldZ + (fractureZ.noise(worldX, worldZ)) * 1000) * 0.125));
	}

	@Override
	public double getBiomeWeight(int worldX, int worldZ)
	{
		return super.getBiomeWeight((int) (worldX - (fractureXC.noise(worldX, worldZ) * 1000D)), (int) (worldZ - (fractureZC.noise(worldX, worldZ) * 1000D)));
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
		return 3;
	}
}
