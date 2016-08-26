package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.enemies.EntitySwet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;

public class ItemSwetJelly extends ItemFood
{
	private static final HashMap<Block, IBlockState> growables = new HashMap<>();

	static
	{
		ItemSwetJelly.growables.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		ItemSwetJelly.growables.put(BlocksAether.aether_dirt, BlocksAether.aether_grass.getDefaultState());
	}

	public ItemSwetJelly()
	{
		super(5, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (EntitySwet.Type types : EntitySwet.Type.values())
		{
			subItems.add(new ItemStack(item, 1, types.ordinal()));
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);

		if (ItemSwetJelly.growables.containsKey(state.getBlock()))
		{
			IBlockState nState = ItemSwetJelly.growables.get(state.getBlock());

			int radius = 1;

			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					BlockPos nPos = new BlockPos(x, pos.getY(), z);

					if (world.getBlockState(nPos).getBlock() == state.getBlock() && !world.getBlockState(nPos.up()).isNormalCube())
					{
						world.setBlockState(nPos, nState);
					}
				}
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.aether.swet_jelly." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
