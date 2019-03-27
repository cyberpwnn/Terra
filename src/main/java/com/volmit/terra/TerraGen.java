package com.volmit.terra;

import com.volmit.terra.generator.BiomeTerraGenerator;
import com.volmit.terra.generator.biome.BiomeDesert;
import com.volmit.terra.generator.biome.BiomeMountains;
import com.volmit.terra.generator.biome.BiomePlains;
import com.volmit.terra.generator.biome.BiomePlainsHills;

public class TerraGen extends BiomeTerraGenerator
{
	@Override
	public void init(long seed)
	{
		super.init(seed);
	}

	@Override
	public void initBiomes()
	{
		registerBiome(BiomePlains.class);
		registerBiome(BiomePlainsHills.class);
		registerBiome(BiomeDesert.class);
		registerBiome(BiomeMountains.class);
	}
}
