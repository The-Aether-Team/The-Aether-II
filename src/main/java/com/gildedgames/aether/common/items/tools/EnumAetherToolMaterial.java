package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public enum EnumAetherToolMaterial
{
	// TODO: The tool classes are terrible.
	// They seriously need to be fixed.

	SKYROOT(ToolMaterial.WOOD)
			{
				@Override
				public void onBlockDestroyed(BlockPos pos, World world)
				{
					// TODO: Chunk hooks!
				}
			},
	HOLYSTONE(ToolMaterial.STONE)
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
			},
	ZANITE(ToolMaterial.IRON)
			{
				@Override
				public float getDigSpeed(ItemStack stack, IBlockState state, float baseSpeed)
				{
					float modifiedSpeed = baseSpeed;

					if (stack != null)
					{
						float additionalSpeed = (2.0F * stack.getItemDamage() / stack.getItem().getMaxDamage() + 0.5F);
						modifiedSpeed = baseSpeed * additionalSpeed;
					}

					return modifiedSpeed;
				}
			},
	GRAVITITE(ToolMaterial.EMERALD)
			{
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

	private final ToolMaterial material;

	EnumAetherToolMaterial(ToolMaterial material)
	{
		this.material = material;
	}

	public void onBlockDestroyed(BlockPos pos, World world)
	{
	}

	public void onBlockActivated(BlockPos pos, World world)
	{
	}

	public void onEntityAttacked(ItemStack stack, EntityLivingBase prey, EntityLivingBase predator)
	{
	}

	public float getDigSpeed(ItemStack stack, IBlockState state, float baseSpeed)
	{
		return baseSpeed;
	}

	public ToolMaterial getToolMaterial()
	{
		return this.material;
	}
}
