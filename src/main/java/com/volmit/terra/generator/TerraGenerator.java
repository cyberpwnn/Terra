package com.volmit.terra.generator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import com.volmit.terra.decor.TypeColumn;

public interface TerraGenerator
{
	public void generate(TypeColumn c, int cx, int cz, int xf, int xt, int zf, int zt, World world, Random random, BiomeGrid biomes);
}
