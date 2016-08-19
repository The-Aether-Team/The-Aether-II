package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAerwhale extends EntityFlying
{

	public EntityAerwhale(World world)
	{
		super(world);

		this.setSize(5.0F, 1.0F);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.worldObj.getBlockState(pos.down()).getBlock() == BlocksAether.aether_grass ? 10.0F : this.worldObj.getLightBrightness(pos) - 0.5F;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		IBlockState state = this.worldObj.getBlockState((new BlockPos(this)).down());

		return state.canEntitySpawn(this) && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F;
	}

}
