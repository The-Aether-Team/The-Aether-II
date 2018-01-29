package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.items.misc.ItemAetherFood;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

public class ItemSwetJelly extends ItemAetherFood
{
	private static final HashMap<IBlockState, IBlockState> growables = new HashMap<>();

	static
	{
		ItemSwetJelly.growables.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), Blocks.GRASS.getDefaultState());
		ItemSwetJelly.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
				BlocksAether.aether_grass.getDefaultState());
	}

	public ItemSwetJelly()
	{
		super(5, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final EntitySwet.Type types : EntitySwet.Type.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		final IBlockState state = world.getBlockState(pos);

		if (ItemSwetJelly.growables.containsKey(state))
		{
			final IBlockState nState = ItemSwetJelly.growables.get(state);

			final int radius = 1;

			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					final BlockPos nPos = new BlockPos(x, pos.getY(), z);

					if (world.getBlockState(nPos) == state && !world.getBlockState(nPos.up()).isNormalCube())
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
	public String getUnlocalizedName(final ItemStack stack)
	{
		return "item.aether.swet_jelly." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
