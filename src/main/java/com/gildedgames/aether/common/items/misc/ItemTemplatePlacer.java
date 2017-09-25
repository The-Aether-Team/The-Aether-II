package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.common.world.templates.TemplatePrimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

import java.util.function.Supplier;

public class ItemTemplatePlacer extends Item
{

	private final Supplier<TemplateDefinition> definitions;

	public ItemTemplatePlacer(final Supplier<TemplateDefinition> template)
	{
		this.maxStackSize = 1;
		this.definitions = template;
	}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, BlockPos pos, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		final ItemStack stack = player.getHeldItem(hand);

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
				final Rotation rotation;

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

				final PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false)
						.setChunk(null).setReplacedBlock(Blocks.AIR).setIgnoreStructureBlock(false);

				final TemplateDefinition def = this.definitions.get();

				final BlockPos size = def.getTemplate().transformedSize(rotation);

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

				TemplatePrimer.generateTemplate(new BlockAccessExtendedWrapper(world), def, new TemplateLoc().set(pos).set(placementsettings));

				stack.shrink(1);

				return EnumActionResult.SUCCESS;
			}
		}

		//return EnumActionResult.FAIL;
	}
}
