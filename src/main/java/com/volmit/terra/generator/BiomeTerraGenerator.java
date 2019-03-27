package com.volmit.terra.generator;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;

import com.volmit.terra.decor.TypeColumn;
import com.volmit.terra.generator.biome.BiomeBase;

import mortar.compute.math.M;
import mortar.lang.collection.GList;

public abstract class BiomeTerraGenerator extends TerraSectionGenerator
{
	private GList<BiomeGenerator> biomeGenerators;
	private long seed;

	@Override
	public void init(long seed)
	{
		this.seed = seed;
		biomeGenerators = new GList<>();
		registerBiome(BiomeBase.class);
		initBiomes();
	}

	public abstract void initBiomes();

	protected void registerBiome(Class<? extends BiomeGenerator> gen)
	{
		try
		{
			biomeGenerators.add(gen.getConstructor(long.class).newInstance((long) (seed + biomeGenerators.size())));
		}

		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void generate(TypeColumn c, World world, Random random, BiomeGrid grid)
	{
		for(int x = minX(); x < maxX(); x++)
		{
			for(int z = minZ(); z < maxZ(); z++)
			{
				double max = 0;
				double noise = 0;
				int biome = 0;
				int wx = getWorldX(x);
				int wz = getWorldZ(z);
				int bs = 0;

				for(int i = 0; i < biomeGenerators.size(); i++)
				{
					double n = biomeGenerators.get(i).getEarthHeight(wx, wz);

					if(n > 0)
					{
						noise += biomeGenerators.get(i).noise(wx, wz);
						bs++;

						if(n > max)
						{
							max = n;
							biome = i;
						}
					}
				}

				noise /= bs;
				noise = M.clip(noise, 0, 1);
				BiomeGenerator generator = biomeGenerators.get(biome);
				int height = 63 + (int) (noise * (255D - 63D));

				for(int y = 0; y < height; y++)
				{
					if(y < 3)
					{
						c.setBlock(x, y, z, (short) Material.BEDROCK.getId());
					}

					else if(y < height - generator.getEarthHeight(wx, wz) - 1)
					{
						c.setBlock(x, y, z, (short) generator.getRock(wx, wz));
					}

					else if(y < height - 2)
					{
						c.setBlock(x, y, z, (short) generator.getEarth(wx, wz));
					}

					else if(y < height - 1)
					{
						c.setBlock(x, y, z, (short) generator.getUnderSurface(wx, wz));
					}

					else
					{
						c.setBlock(x, y, z, (short) generator.getSurface(wx, wz));
					}
				}
			}
		}
	}
}
