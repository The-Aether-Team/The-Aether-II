package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.util.TemplateUtil;
import com.gildedgames.aether.api.world.generation.PostPlacementTemplate;
import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.orbis.lib.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
import com.google.common.collect.Lists;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class TemplatePlacer
{

	public static final Rotation[] ROTATIONS = Rotation.values();

	/** Filled with block access instances for each world
	 */
	private static final List<IBlockAccessExtended> blockAccessList = Lists.newArrayList();

	public static Rotation getRandomRotation(final Random rand)
	{
		return TemplatePlacer.ROTATIONS[rand.nextInt(TemplatePlacer.ROTATIONS.length)];
	}

	public static boolean place(final World placeWith, final TemplateDefinition def, final TemplateLoc loc, final Random rand)
	{
		IBlockAccessExtended chosen = null;

		for (final IBlockAccessExtended access : TemplatePlacer.blockAccessList)
		{
			if (access.getWorld() == placeWith)
			{
				chosen = access;
				break;
			}
		}

		if (chosen == null)
		{
			chosen = new BlockAccessExtendedWrapper(placeWith);

			TemplatePlacer.blockAccessList.add(chosen);
		}

		return TemplatePlacer.place(chosen, def, loc, rand);
	}

	public static boolean place(final IBlockAccessExtended placeWith, final TemplateDefinition def, final TemplateLoc loc, final Random rand)
	{
		final Rotation rotation = def.hasRandomRotation() ? ROTATIONS[rand.nextInt(ROTATIONS.length)] : ROTATIONS[0];

		loc.getSettings().setRotation(rotation);

		TemplateLoc before = loc.clone();

		if (loc.isCentered())
		{
			loc.set(TemplateUtil.getCenteredPos(def, loc));
		}

		final boolean result = TemplatePrimer.canGenerate(placeWith, def, before);

		if (result)
		{
			TemplatePrimer.generateTemplate(placeWith, def, before);

			if (placeWith.getWorld() != null)
			{
				for (final PostPlacementTemplate post : def.getPostPlacements())
				{
					post.postGenerate(placeWith.getWorld(), rand, loc);
				}
			}
		}

		return result;
	}

	public static boolean canPlace(final IBlockAccessExtended placeWith, final TemplateDefinition def, final TemplateLoc loc, final Random rand)
	{
		final Rotation rotation = def.hasRandomRotation() ? ROTATIONS[rand.nextInt(ROTATIONS.length)] : ROTATIONS[0];

		loc.getSettings().setRotation(rotation);

		if (loc.isCentered())
		{
			loc.set(TemplateUtil.getCenteredPos(def, loc));
		}

		return TemplatePrimer.canGenerate(placeWith, def, loc);
	}

}
