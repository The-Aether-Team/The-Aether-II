package com.gildedgames.aether.common.items.consumables;

import java.util.HashMap;
import java.util.List;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwetJelly extends ItemFood
{
	private static final HashMap<Block, IBlockState> growables = new HashMap<>();

	static
	{
		ItemSwetJelly.growables.put(Blocks.dirt, Blocks.grass.getDefaultState());
		ItemSwetJelly.growables.put(BlocksAether.aether_dirt, BlocksAether.aether_grass.getDefaultState());
	}

	public enum JellyType
	{
		BLUE("blue"), GOLDEN("golden"), DARK("dark");

		public final String name;

		JellyType(String name)
		{
			this.name = name;
		}

		public static JellyType fromOrdinal(int ordinal)
		{
			JellyType[] jelly = values();

			return jelly[ordinal > jelly.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	public ItemSwetJelly()
	{
		super(5, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
	{
		for (JellyType types : JellyType.values())
		{
			subItems.add(new ItemStack(item, 1, types.ordinal()));
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
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

					if (world.getBlockState(nPos).getBlock() == state.getBlock() && !world.getBlockState(nPos.up()).getBlock().isNormalCube())
					{
						world.setBlockState(nPos, nState);
					}
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.swet_jelly." + JellyType.fromOrdinal(stack.getMetadata()).name;
	}
}
