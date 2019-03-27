package com.volmit.terra;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import mortar.api.nms.NMP;
import mortar.api.sched.J;
import mortar.api.world.MaterialBlock;
import mortar.bukkit.command.MortarCommand;
import mortar.bukkit.command.MortarSender;

public class CommandTerra extends MortarCommand
{
	public CommandTerra()
	{
		super("terra");
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		sender.sendMessage("Regenerating View Distance");
		Chunk center = sender.player().getLocation().getChunk();
		Chunk max = center.getWorld().getChunkAt(center.getX() + 10, center.getZ() + 10);
		Chunk min = center.getWorld().getChunkAt(center.getX() - 10, center.getZ() - 10);

		for(int x = min.getX(); x <= max.getX(); x++)
		{
			for(int z = min.getZ(); z <= max.getZ(); z++)
			{
				Chunk m = center.getWorld().getChunkAt(x, z);
				generate(m, sender.player());
			}
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	private void generate(Chunk m, Player p)
	{
		J.a(() ->
		{
			TerraGen gen = new TerraGen();
			gen.setParallelism(4);
			short[][] t = gen.generateExtBlockSections(m.getWorld(), new Random(m.getWorld().getSeed()), m.getX(), m.getZ(), new BiomeGrid()
			{
				@Override
				public void setBiome(int x, int z, Biome bio)
				{
					m.getWorld().setBiome((m.getX() * 16) + x, (m.getZ() * 16) + z, bio);
				}

				@Override
				public Biome getBiome(int x, int z)
				{
					return m.getWorld().getBiome((m.getX() * 16) + x, (m.getZ() * 16) + z);
				}
			});

			for(int i = 0; i < t.length; i++)
			{
				if(t[i] != null)
				{
					for(int x = 0; x < 16; x++)
					{
						for(int y = 0; y < 16; y++)
						{
							for(int z = 0; z < 16; z++)
							{
								NMP.host.setBlock(new Location(m.getWorld(), (m.getX() * 16) + x, (i * 16) + y, (m.getZ() * 16) + z), new MaterialBlock(Material.getMaterial(t[i][((y & 0xF) << 8) | (z << 4) | x])));
							}
						}
					}
				}

				else
				{
					for(int x = 0; x < 16; x++)
					{
						for(int y = 0; y < 16; y++)
						{
							for(int z = 0; z < 16; z++)
							{
								NMP.host.setBlock(new Location(m.getWorld(), (m.getX() * 16) + x, (i * 16) + y, (m.getZ() * 16) + z), new MaterialBlock(Material.AIR));
							}
						}
					}
				}
			}

			J.s(() ->
			{
				NMP.host.relight(m);
				NMP.CHUNK.refresh(p, m);
			});
		});
	}
}
