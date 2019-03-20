package com.volmit.terra.generator;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;

import com.volmit.terra.decor.TypeColumn;

import mortar.compute.noise.SimplexOctaveGenerator;

public class SimplexTerraGenerator extends TerraSectionGenerator
{
	@Override
	public void init(long seed)
	{
		genLayer("base", new SimplexOctaveGenerator(seed + 1000, 16), 2255D, 0.56, 0.25);
		genLayer("baseBump", new SimplexOctaveGenerator(seed + 2000, 4), 88D, 0.35, 0.45);
		genLayer("baseDip", new SimplexOctaveGenerator(seed + 3000, 8), 250D, 0.35, 0.45);
		genLayer("baseShiftX", new SimplexOctaveGenerator(seed + 1001, 7), 1366D, 0.25, 0.25);
		genLayer("baseShiftZ", new SimplexOctaveGenerator(seed + 1002, 7), 1366D, 0.25, 0.25);
		genLayer("baseBendX", new SimplexOctaveGenerator(seed + 3001, 3), 466D, 0.75, 0.75);
		genLayer("baseBendZ", new SimplexOctaveGenerator(seed + 3002, 3), 466D, 0.75, 0.75);
		genLayer("baseMuffleX", new SimplexOctaveGenerator(seed + 3001, 2), 2366D, 0.25, 0.25);
		genLayer("baseMuffleZ", new SimplexOctaveGenerator(seed + 3002, 2), 2366D, 0.25, 0.25);
		genLayer("baseScratcherX", new SimplexOctaveGenerator(seed + 2001, 4), 8D, 0.35, 0.85);
		genLayer("baseScratcherZ", new SimplexOctaveGenerator(seed + 2002, 4), 8D, 0.35, 0.85);
		genLayer("baseMelt", new SimplexOctaveGenerator(seed + 1003, 8), 30D, 0.9, 0.25);
		genLayer("baseShuffle", new SimplexOctaveGenerator(seed + 1004, 16), 8D, 0.25, 0.25);
		genLayer("baseBuffer", new SimplexOctaveGenerator(seed + 1005, 2), 45D, 0.25, 0.25);
		genLayer("baseScratcher", new SimplexOctaveGenerator(seed + 1006, 8), 5D, 0.5, 0.75);
		genLayer("baseFracture", new SimplexOctaveGenerator(seed + 1007, 8), 16D, 0.8, 0.025);
		genLayer("baseFractureClip", new SimplexOctaveGenerator(seed + 2007, 2), 9D, 0.32, 0.025);
		genLayer("baseFractureForce", new SimplexOctaveGenerator(seed + 3007, 5), 16D, 0.42, 0.325);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void generate(TypeColumn c, World world, Random random, BiomeGrid grid)
	{
		for(int x = minX(); x < maxX(); x++)
		{
			for(int z = minZ(); z < maxZ(); z++)
			{
				double noise = 0;
				double xMuffle = noise(getWorldX(x), getWorldZ(z), "baseMuffleX") / 11;
				double zMuffle = noise(getWorldX(x), getWorldZ(z), "baseMuffleZ") / 11;
				double xNoise = (xMuffle * noise(Math.sin(getWorldX(x) / 100D), Math.sin(getWorldZ(z) / 100D), "baseShiftX")) * getWorldX(x);
				double zNoise = (zMuffle * noise(Math.sin(getWorldX(x) / 100D), Math.sin(getWorldZ(z) / 100D), "baseShiftZ")) * getWorldZ(z);
				xNoise += noise(getWorldX(x), getWorldZ(z), "baseScratcherX") * 8D;
				zNoise += noise(getWorldX(x), getWorldZ(z), "baseScratcherZ") * 8D;
				xNoise *= noise(getWorldX(x), getWorldZ(z), "baseBendX");
				zNoise *= noise(getWorldX(x), getWorldZ(z), "baseBendZ");
				noise += noise(xNoise, zNoise, "base");
				noise += noise(xNoise, zNoise, "baseShuffle") * ((1 / 128D) * noise(xNoise, zNoise, "baseBuffer"));
				noise -= noise(xNoise, zNoise, "baseScratcher") * ((1 / (32D) * noise(xNoise, zNoise, "baseBuffer")));
				noise /= 1.125;
				noise += noise(xNoise, zNoise, "baseBump");
				noise -= Math.pow(noise(xNoise, zNoise, "baseDip"), 3);
				noise -= 0.35;
				noise -= noise(xNoise, zNoise, "baseMelt") * 0.58;

				double fracture = noise(xNoise, zNoise, "baseFracture");

				if(fracture > noise(xNoise, zNoise, "baseFractureClip"))
				{
					noise += fracture * ((1 / 32D) * noise(xNoise, zNoise, "baseFractureForce"));
				}

				int max = (int) (16 + (noise * (230D)));
				max = max > 256 ? 256 : max;
				max = max < 0 ? 0 : max;
				Material mat = Material.STONE;

				for(int y = 0; y < max; y++)
				{
					if(y == max - 1)
					{
						mat = max > 67 ? Material.GRASS : max > 62 ? Material.SAND : Material.DIRT;
					}

					else if(y > max - 8)
					{
						mat = Material.DIRT;
					}

					else if(y > 2)
					{
						mat = Material.STONE;
					}

					else
					{
						mat = Material.BEDROCK;
					}

					c.setBlock(x, y, z, (short) mat.getId());
				}

				if(max < 63)
				{
					for(int y = max; y < 63; y++)
					{
						c.setBlock(x, y, z, (short) Material.STATIONARY_WATER.getId());
					}
				}
			}
		}
	}
}
