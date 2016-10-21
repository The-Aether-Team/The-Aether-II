package com.gildedgames.aether.common.lighting.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ThreadedChunk extends Chunk
{

	public ThreadedChunk(World par1World, int par2, int par3)
	{
		super(par1World, par2, par3);
	}

	public void relightBlock(int x, int y, int z)
	{
		ThreadedWorld threadedWorld = (ThreadedWorld) this.getWorld();

		threadedWorld.queueLock.lock();
		threadedWorld.queue.add(threadedWorld.new RelightBlock(this, new BlockPos(x, y, z)));
		threadedWorld.queueLock.unlock();
	}

	public void superRelightBlock(int x, int y, int z)
	{
		this.relightBlock(x, y, z); // gets transformed to super.relightBlock(x, y, z)
	}

}
