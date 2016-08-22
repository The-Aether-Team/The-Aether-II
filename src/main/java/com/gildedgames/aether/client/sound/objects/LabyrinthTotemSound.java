package com.gildedgames.aether.client.sound.objects;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LabyrinthTotemSound extends PositionedSound implements ITickableSound
{
	private final World world;

	private final BlockPos pos;

	public LabyrinthTotemSound(World world, BlockPos pos)
	{
		super(SoundsAether.labyrinth_totem_drone, SoundCategory.AMBIENT);

		this.world = world;
		this.pos = pos;

		this.xPosF = pos.getX();
		this.yPosF = pos.getY();
		this.zPosF = pos.getZ();

		this.repeat = true;
	}

	@Override
	public boolean isDonePlaying()
	{
		return this.world.getBlockState(this.pos).getBlock() != BlocksAether.labyrinth_totem;
	}

	@Override
	public void update()
	{

	}
}
