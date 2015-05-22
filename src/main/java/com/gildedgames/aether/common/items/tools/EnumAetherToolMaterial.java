package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public enum EnumAetherToolMaterial
{
	SKYROOT
			{
				@Override
				public void onBlockDestroyed(BlockPos pos, World world)
				{
					// TODO: Chunk hooks!
				}

				@Override
				public ToolMaterial getToolMaterial()
				{
					return ToolMaterial.WOOD;
				}
			},
	HOLYSTONE
			{
				@Override
				public void onBlockDestroyed(BlockPos pos, World world)
				{
					this.dropAmbrosium(pos, world);
				}

				@Override
				public void onEntityAttacked(ItemStack stack, EntityLivingBase prey, EntityLivingBase predator)
				{
					this.dropAmbrosium(prey.getPosition(), prey.getEntityWorld());
				}

				private void dropAmbrosium(BlockPos pos, World world)
				{
					// 5% Chance
					if (world.rand.nextInt(100) <= 5)
					{
						EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ());
						entity.setEntityItemStack(new ItemStack(ItemsAether.ambrosium_shard, 1));

						world.spawnEntityInWorld(entity);
					}
				}

				@Override
				public ToolMaterial getToolMaterial()
				{
					return ToolMaterial.STONE;
				}
			},
	ZANITE
			{
				@Override
				public float getDigSpeed(ItemStack stack, Block block, float baseSpeed)
				{
					if (stack != null)
					{
						return baseSpeed + ((stack.getItemDamage() / stack.getItem().getMaxDamage()) + 0.5F);
					}

					return baseSpeed;
				}

				@Override
				public ToolMaterial getToolMaterial()
				{
					return ToolMaterial.IRON;
				}
			},
	GRAVITITE
			{
				@Override
				public ToolMaterial getToolMaterial()
				{
					return ToolMaterial.EMERALD;
				}

				@Override
				public void onEntityAttacked(ItemStack stack, EntityLivingBase prey, EntityLivingBase predator)
				{
					if (predator instanceof EntityPlayer && predator.isSneaking())
					{
						if (prey.hurtTime > 0 || prey.deathTime > 0)
						{
							prey.addVelocity(0.0D, 1.0D, 0.0D);
						}
					}
				}

				// TODO: Floating blocks!
			};

	public void onBlockDestroyed(BlockPos pos, World world)
	{
	}

	public void onBlockActivated(BlockPos pos, World world)
	{
	}

	public void onEntityAttacked(ItemStack stack, EntityLivingBase prey, EntityLivingBase predator)
	{
	}

	public float getDigSpeed(ItemStack stack, Block block, float baseSpeed)
	{
		return baseSpeed;
	}

	abstract ToolMaterial getToolMaterial();
}
