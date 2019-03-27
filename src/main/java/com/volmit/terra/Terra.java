package com.volmit.terra;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;

import org.bukkit.generator.ChunkGenerator;

import mortar.bukkit.command.Command;
import mortar.bukkit.plugin.MortarPlugin;
import mortar.util.text.C;

public class Terra extends MortarPlugin
{
	@Command
	private CommandTerra terra;
	private static ExecutorService e;

	@Override
	public void start()
	{
		e = null;
	}

	@Override
	public void stop()
	{
		e = null;
	}

	@Override
	public String getTag(String subTag)
	{
		return C.DARK_GRAY + "[" + C.GREEN + "Terra" + C.DARK_GRAY + "]" + C.GRAY + ": ";
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		TerraGen spx = new TerraGen();
		spx.setParallelism(4);

		return spx;
	}

	public static ExecutorService pool()
	{
		if(e != null)
		{
			return e;
		}

		return e = new ForkJoinPool(16, new ForkJoinWorkerThreadFactory()
		{
			@Override
			public ForkJoinWorkerThread newThread(ForkJoinPool pool)
			{
				final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
				worker.setName("Terra Parallel Generator " + worker.getPoolIndex());
				worker.setPriority(Thread.MAX_PRIORITY);
				return worker;
			}
		}, new UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				e.printStackTrace();
			}
		}, true);
	}
}
