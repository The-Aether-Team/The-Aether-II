package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.init.MaterialsAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

public class PlayerBlockLevitateModule extends PlayerAetherModule
{

	private EntityMovingBlock heldBlock;

	public PlayerBlockLevitateModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().world.isRemote || this.getEntity().isDead)
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
				final ItemStack stack = this.getEntity().getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

				if (!stack.isEmpty() && stack.getItem() instanceof ItemTool
						&& Objects.equals(((ItemTool) stack.getItem()).getToolMaterialName(), MaterialsAether.GRAVITITE_TOOL.name()))
				{
					if (this.heldBlock.ticksExisted % 20 == 0)
					{
						// Does damage 2 damage/sec, increasing the amount of damage by 1 every 3 seconds,
						// for a maximum of 8 damage/sec

						final int extra = (int) Math.floor(Math.min(6, this.heldBlock.ticksExisted / 60));

						stack.damageItem(2 + extra, this.getEntity());
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
	public void onDeath(final LivingDeathEvent event)
	{
		this.dropHeldBlock();
	}

	public boolean pickupBlock(final BlockPos pos, final World world)
	{
		if (this.heldBlock == null)
		{
			if (world.isBlockModifiable(this.getEntity(), pos))
			{
				final IBlockState state = world.getBlockState(pos);

				final EntityMovingBlock movingBlock = new EntityMovingBlock(world, pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, state);
				world.spawnEntity(movingBlock);

				this.holdBlock(movingBlock);

				return true;
			}
		}

		return false;
	}

	private void holdBlock(final EntityMovingBlock entity)
	{
		this.dropHeldBlock();

		this.heldBlock = entity;
		this.heldBlock.setHoldingPlayer(this.getEntity());
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
