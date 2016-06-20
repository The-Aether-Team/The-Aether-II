package com.gildedgames.aether.client.sound.objects;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LabyrinthTotemSound implements ITickableSound
{
	private final World world;

	private final BlockPos pos;

	public LabyrinthTotemSound(World world, BlockPos pos)
	{
		this.world = world;
		this.pos = pos;
	}

	@Override
	public ResourceLocation getSoundLocation()
	{
		return AetherCore.getResource("aeblock.labyrinth_totem.drone");
	}

	@Override
	public boolean canRepeat()
	{
		return true;
	}

	@Override
	public int getRepeatDelay()
	{
		return 0;
	}

	@Override
	public float getVolume()
	{
		return 0.5f;
	}

	@Override
	public float getPitch()
	{
		return 1.0f;
	}

	@Override
	public float getXPosF()
	{
		return this.pos.getX();
	}

	@Override
	public float getYPosF()
	{
		return this.pos.getY();
	}

	@Override
	public float getZPosF()
	{
		return this.pos.getZ();
	}

	@Override
	public AttenuationType getAttenuationType()
	{
		return AttenuationType.LINEAR;
	}

	@Override
	public boolean isDonePlaying()
	{
		boolean missing = world.getBlockState(this.pos).getBlock() != BlocksAether.labyrinth_totem;

		return missing;
	}

	@Override
	public void update()
	{

	}
}
