package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import com.google.common.base.Supplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public class ItemTemplatePlacer extends Item
{

	private Supplier<WorldGenTemplate> template;
	
	public ItemTemplatePlacer(Supplier<WorldGenTemplate> template)
	{
		this.maxStackSize = 1;
		this.template = template;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (facing == EnumFacing.DOWN)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			pos = pos.offset(facing);

			if (!player.canPlayerEdit(pos, facing, stack))
			{
				return EnumActionResult.FAIL;
			}
			else if (world.isRemote)
			{
				return EnumActionResult.PASS;
			}
			else
			{
				Rotation rotation;

				if (player.getHorizontalFacing() == EnumFacing.NORTH)
				{
					rotation = Rotation.CLOCKWISE_90;
				}
				else if (player.getHorizontalFacing() == EnumFacing.EAST)
				{
					rotation = Rotation.CLOCKWISE_180;
				}
				else if (player.getHorizontalFacing() == EnumFacing.SOUTH)
				{
					rotation = Rotation.COUNTERCLOCKWISE_90;
				}
				else
				{
					rotation = Rotation.NONE;
				}

				PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null).setReplacedBlock(Blocks.AIR).setIgnoreStructureBlock(false);

				final WorldGenTemplate template = this.template.get();

				BlockPos size = template.getTemplate().transformedSize(rotation);

				switch (rotation)
				{
					case NONE:
						pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);
						break;
					default:
						break;
					case CLOCKWISE_90:
						pos = pos.add(size.getX() / 2, 0, -size.getZ() / 2);
						break;
					case COUNTERCLOCKWISE_90:
						pos = pos.add(-size.getX() / 2, 0, size.getZ() / 2);
						break;
					case CLOCKWISE_180:
						pos = pos.add(size.getX() / 2, 0, size.getZ() / 2);
						break;
				}

				template.placeTemplateWithoutCheck(world, pos, placementsettings);

				--stack.stackSize;

				return EnumActionResult.SUCCESS;
			}
		}

		//return EnumActionResult.FAIL;
	}
}
