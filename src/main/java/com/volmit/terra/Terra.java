package com.volmit.terra;

import org.bukkit.generator.ChunkGenerator;

import com.volmit.terra.generator.SimplexTerraGenerator;

import mortar.bukkit.command.Command;
import mortar.bukkit.plugin.MortarPlugin;
import mortar.util.text.C;

public class Terra extends MortarPlugin
{
	@Command
	private CommandTerra terra;

	@Override
	public void start()
	{

	}

	@Override
	public void stop()
	{

	}

	@Override
	public String getTag(String subTag)
	{
		return C.DARK_GRAY + "[" + C.GREEN + "Terra" + C.DARK_GRAY + "]" + C.GRAY + ": ";
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		SimplexTerraGenerator spx = new SimplexTerraGenerator();
		spx.setMulticore(true);

		return spx;
	}
}
