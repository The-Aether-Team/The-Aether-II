package com.gildedgames.aether.common.entities.living.npc;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityNPC extends EntityCreature
{

	public EntityNPC(final World worldIn)
	{
		super(worldIn);

		this.isImmuneToFire = true;
	}

	@Override
	public boolean isEntityInvulnerable(final DamageSource source)
	{
		return source != DamageSource.OUT_OF_WORLD || super.isEntityInvulnerable(source);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean canBeLeashedTo(final EntityPlayer player)
	{
		return false;
	}
}
