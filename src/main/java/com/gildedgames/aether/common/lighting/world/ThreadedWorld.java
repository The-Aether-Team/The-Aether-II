package com.gildedgames.aether.common.lighting.world;

import com.gildedgames.aether.common.lighting.ThreadedLighting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.WorldSettings;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadedWorld extends WorldClient implements Runnable
{

	private class UpdateSkylight
	{
		final BlockPos pos;

		UpdateSkylight(BlockPos pos)
		{
			this.pos = pos;
		}
	}

	public class RelightBlock
	{
		final ThreadedChunk chunk;

		final BlockPos pos;

		RelightBlock(ThreadedChunk chunk, BlockPos pos)
		{
			this.chunk = chunk;
			this.pos = pos;
		}
	}

	private final Thread thread;
	private boolean delayedStart = false;

	public final ArrayList queue = new ArrayList();

	public final ReentrantLock queueLock = new ReentrantLock();

	public ThreadedWorld(NetHandlerPlayClient par1NetClientHandler, WorldSettings par2WorldSettings, int par3, EnumDifficulty par4, Profiler par5Profiler)
	{
		super(par1NetClientHandler, par2WorldSettings, par3, par4, new DummyProfiler(par5Profiler));

		this.thread = new Thread(this);
		if (this.delayedStart)
		{
			this.thread.start();
		}
		((DummyProfiler) this.theProfiler).threadToIgnore = this.thread.getId();
	}

	@Override
	public void addEventListener(IWorldEventListener listener)
	{
		if (this.eventListeners.isEmpty())
		{
			if (this.thread != null)
			{
				this.thread.start();
			}
			else
			{
				this.delayedStart = true;
			}
		}

		super.addEventListener(listener);
	}

	@Override
	public void removeEventListener(IWorldEventListener listener)
	{
		super.removeEventListener(listener);

		if (this.eventListeners.isEmpty())
		{
			this.thread.stop();
		}
	}

	@Override
	public boolean checkLightFor(EnumSkyBlock type, BlockPos pos)
	{
		if (type == EnumSkyBlock.BLOCK)
		{
			return super.checkLightFor(type, pos);
		}

		if (!this.isAreaLoaded(pos, 17))
		{
			return false;
		}

		this.queueLock.lock();
		this.queue.add(new UpdateSkylight(pos));
		this.queueLock.unlock();

		return true;
	}

	@Override
	public void run()
	{
		while (true)
		{
			long start = System.currentTimeMillis();

			this.queueLock.lock();

			if (this.queue.size() > 0)
			{
				Object[] tempQueue = new Object[this.queue.size()];
				this.queue.toArray(tempQueue);
				this.queue.clear();

				this.queueLock.unlock();

				for (Object object : tempQueue)
				{
					if (object instanceof UpdateSkylight)
					{
						UpdateSkylight data = (UpdateSkylight) object;
						super.checkLightFor(EnumSkyBlock.SKY, data.pos);
					}
					else if (object instanceof RelightBlock)
					{
						RelightBlock data = (RelightBlock) object;
						data.chunk.superRelightBlock(data.pos.getX(), data.pos.getY(), data.pos.getZ());
					}
				}
			}
			else
			{
				this.queueLock.unlock();
			}

			long time = System.currentTimeMillis() - start;

			if (time > 100)
			{
				ThreadedLighting.print("prevented lag lasting " + time + " milliseconds.");
			}

			if (time < 10)
			{
				try
				{
					Thread.currentThread().sleep(10 - time);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
