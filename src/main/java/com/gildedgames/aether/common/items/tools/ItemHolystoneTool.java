package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHolystoneTool extends ItemAetherTool
{
	public ItemHolystoneTool(EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(ToolMaterial.STONE, "holystone", toolType, attackDamage, attackSpeed);

		this.setHarvestLevel(toolType.getToolClass(), 1);
	}

	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity)
	{
		if (!world.isRemote && world.rand.nextInt(100) <= 5)
		{
			EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ());
			entityItem.setEntityItemStack(new ItemStack(ItemsAether.ambrosium_shard, 1));

			world.spawnEntityInWorld(entityItem);
		}

		return super.onBlockDestroyed(stack, world, state, pos, entity);
	}
}
