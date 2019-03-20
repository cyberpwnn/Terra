package com.volmit.terra.generator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import com.volmit.terra.decor.TypeColumn;

public abstract class TerraGeneratorBase extends ChunkGenerator implements TerraGenerator
{
	private boolean multicore = false;

	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		return !isMulticore() ? generateSingle(world, random, x, z, biomes).getResult() : generateMulti(world, random, x, z, biomes).getResult();
	}

	private TypeColumn generateMulti(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		TypeColumn tc = new TypeColumn();
		TypeColumn[] q = new TypeColumn[4];
		// TODO actually make multicore
		generate(q[0] = new TypeColumn(), x, z, 0, 8, 0, 8, world, random, biomes);
		generate(q[1] = new TypeColumn(), x, z, 0, 8, 8, 16, world, random, biomes);
		generate(q[2] = new TypeColumn(), x, z, 8, 16, 0, 8, world, random, biomes);
		generate(q[3] = new TypeColumn(), x, z, 8, 16, 8, 16, world, random, biomes);
		tc.addBlocks(q[0], 0, 8, 0, 8);
		tc.addBlocks(q[1], 0, 8, 8, 16);
		tc.addBlocks(q[2], 8, 16, 0, 8);
		tc.addBlocks(q[3], 8, 16, 8, 16);

		return tc;
	}

	private TypeColumn generateSingle(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		TypeColumn tc = new TypeColumn();

		generate(tc, x, z, 0, 16, 0, 16, world, random, biomes);

		return tc;
	}

	public boolean isMulticore()
	{
		return multicore;
	}

	public void setMulticore(boolean multicore)
	{
		this.multicore = multicore;
	}
}