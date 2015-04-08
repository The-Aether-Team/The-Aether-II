package com.gildedgames.aether.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.aether.Aether;

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
			// 5% Chance
			if (world.rand.nextInt(100) <= 5)
			{
				EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ());
				entity.setEntityItemStack(new ItemStack(Aether.getItems().ambrosium_shard, 1));

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

		// TODO: Floating blocks!
	};

	public void onBlockDestroyed(BlockPos pos, World world)
	{
		return;
	}

	public void onBlockActivated(BlockPos pos, World world)
	{
		return;
	}

	public float getDigSpeed(ItemStack stack, Block block, float baseSpeed)
	{
		return baseSpeed;
	}

	public abstract ToolMaterial getToolMaterial();
}
