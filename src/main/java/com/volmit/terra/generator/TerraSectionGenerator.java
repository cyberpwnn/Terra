package com.volmit.terra.generator;

import java.util.Random;

import org.bukkit.World;

import com.volmit.terra.decor.TypeColumn;

import mortar.compute.noise.NoiseGenerator;
import mortar.compute.noise.OctaveGenerator;
import mortar.lang.collection.GMap;

public abstract class TerraSectionGenerator extends TerraGeneratorBase implements TerraGenerator
{
	private int chunkX;
	private int chunkZ;
	private int minX;
	private int minZ;
	private int maxX;
	private int maxZ;
	private Long seed;
	private GMap<String, FilteredNoiseGenerator> genLayers = new GMap<>();

	@Override
	public void generate(TypeColumn c, int cx, int cz, int xf, int xt, int zf, int zt, World world, Random random, BiomeGrid biomes)
	{
		if(seed == null)
		{
			init(seed = world.getSeed());
		}

		chunkX = cx;
		chunkZ = cz;
		minX = xf;
		minZ = zf;
		maxX = xt;
		maxZ = zt;
		generate(c, world, random, biomes);
	}

	public String[] getAllLayers()
	{
		return genLayers.k().toArray(new String[genLayers.size()]);
	}

	public void genLayer(String layer, NoiseGenerator generator, double zoom)
	{
		genLayers.put(layer, new FilteredNoiseGenerator(generator, zoom));
	}

	public void genLayer(String layer, OctaveGenerator generator, double zoom, double amp, double freq)
	{
		genLayers.put(layer, new FilteredNoiseGenerator(generator, zoom, amp, freq));
	}

	public double noise(double x, double z, String... layers)
	{
		double f = 0;

		for(String i : layers)
		{
			f += genLayers.get(i).noise(x, z);
		}

		return (f / 2D) + 0.5;
	}

	public double normalNoise(double x, double z, String... layers)
	{
		double f = 0;

		for(String i : layers)
		{
			f += genLayers.get(i).noise(x, z);
		}

		return ((f / (double) layers.length) / 2D) + 0.5;
	}

	public abstract void init(long seed);

	public abstract void generate(TypeColumn c, World world, Random random, BiomeGrid grid);

	public int minX()
	{
		return minX;
	}

	public int minZ()
	{
		return minZ;
	}

	public int maxX()
	{
		return maxX;
	}

	public int maxZ()
	{
		return maxZ;
	}

	public int getWorldX(int value)
	{
		return (chunkX << 4) + (value);
	}

	public int getWorldZ(int value)
	{
		return (chunkZ << 4) + (value);
	}

	public double getWorldX(int value, double multiplier)
	{
		return (double) getWorldX(value) * multiplier;
	}

	public double getWorldZ(int value, double multiplier)
	{
		return (double) getWorldZ(value) * multiplier;
	}
}
