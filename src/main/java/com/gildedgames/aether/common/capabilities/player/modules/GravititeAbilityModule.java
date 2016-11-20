package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class GravititeAbilityModule extends PlayerAetherModule
{

	private EntityMovingBlock heldBlock;

	public GravititeAbilityModule(PlayerAetherImpl playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (this.getPlayer().worldObj.isRemote || this.getPlayer().isDead)
		{
			return;
		}

		if (this.heldBlock != null)
		{
			if (this.heldBlock.isDead || this.heldBlock.isFalling())
			{
				this.heldBlock = null;
			}
			else
			{
				ItemStack stack = this.getPlayer().getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

				if (stack != null && stack.getItem() instanceof ItemTool && ((ItemTool) stack.getItem()).getToolMaterial() == MaterialsAether.GRAVITITE_TOOL)
				{
					if (this.heldBlock.ticksExisted % 20 == 0)
					{
						// Does damage 2 damage/sec, increasing the amount of damage by 1 every 3 seconds,
						// for a maximum of 8 damage/sec

						int extra = (int) Math.floor(Math.min(6, this.heldBlock.ticksExisted / 60));

						stack.damageItem(2 + extra, this.getPlayer());
					}
				}
				else
				{
					this.dropHeldBlock();
				}
			}
		}
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		this.dropHeldBlock();
	}

	public boolean pickupBlock(BlockPos pos, World world)
	{
		if (this.heldBlock == null)
		{
			if (world.isBlockModifiable(this.getPlayer(), pos))
			{
				IBlockState state = world.getBlockState(pos);

				EntityMovingBlock movingBlock = new EntityMovingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, state);
				world.spawnEntityInWorld(movingBlock);

				this.holdBlock(movingBlock);

				return true;
			}
		}

		return false;
	}

	private void holdBlock(EntityMovingBlock entity)
	{
		this.dropHeldBlock();

		this.heldBlock = entity;
		this.heldBlock.setHoldingPlayer(this.getPlayer());
	}

	public EntityMovingBlock getHeldBlock()
	{
		return this.heldBlock;
	}

	public void dropHeldBlock()
	{
		if (this.getHeldBlock() != null)
		{
			this.getHeldBlock().setHoldingPlayer(null);
		}
	}

}
