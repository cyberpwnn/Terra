package com.volmit.terra.generator;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import com.volmit.terra.Terra;
import com.volmit.terra.decor.TypeColumn;

import mortar.compute.math.Profiler;
import mortar.compute.math.RollingAverage;
import mortar.lang.collection.FinalInteger;
import mortar.logic.format.F;

public abstract class TerraGeneratorBase extends ChunkGenerator implements TerraGenerator
{
	private RollingAverage ra = new RollingAverage(32);
	private int parallelism = 1;
	private TerraGeneratorBase[] microTerra;
	private boolean micro;
	private Profiler px = new Profiler();

	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		px.begin();
		short[][] result = !isMulticore() ? generateSingle(world, random, x, z, biomes).getResult() : generateMulti(world, random, x, z, biomes).getResult();
		px.end();
		ra.put(px.getMilliseconds());

		if(!isMicro())
		{
			System.out.println("MS: " + F.time(ra.get(), 3));
		}

		return result;
	}

	private TypeColumn generateMulti(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		int factor = (int) (16 / Math.sqrt(parallelism));
		FinalInteger fi = new FinalInteger(0);
		TypeColumn tc = new TypeColumn();
		TypeColumn[] q = new TypeColumn[parallelism];
		ExecutorService svc = Terra.pool();

		for(int i = 0; i < parallelism; i++)
		{
			q[i] = new TypeColumn();
		}

		int m = 0;
		for(int i = 0; i < 16; i += factor)
		{
			for(int j = 0; j < 16; j += factor)
			{
				int ii = i;
				int jj = j;
				int d = m;
				svc.execute(() ->
				{
					microTerra[d].generate(q[d], x, z, ii, (ii + factor), jj, (jj + factor), world, random, biomes);

					synchronized(tc)
					{
						tc.addBlocks(q[d], ii, (ii + factor), jj, (jj + factor));
					}

					synchronized(fi)
					{
						fi.add(1);
					}
				});
				m++;
			}
		}

		while(fi.get() < parallelism)
		{
			try
			{
				Thread.sleep(1);
			}

			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		return tc;
	}

	private TypeColumn generateSingle(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		TypeColumn tc = new TypeColumn();

		generate(tc, x, z, 0, 16, 0, 16, world, random, biomes);

		return tc;
	}

	public void setParallelism(int parallelism)
	{
		this.parallelism = parallelism;

		if(parallelism > 1)
		{
			microTerra = new TerraGeneratorBase[parallelism];

			for(int i = 0; i < parallelism; i++)
			{
				try
				{
					microTerra[i] = getClass().getConstructor().newInstance();
					microTerra[i].setMicro();
				}

				catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public int getParallelism()
	{
		return parallelism;
	}

	public boolean isMulticore()
	{
		return parallelism > 1;
	}

	public void setMicro()
	{
		micro = true;
	}

	public boolean isMicro()
	{
		return micro;
	}
}