package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.aether.api.util.NBTHelper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import com.gildedgames.orbis.common.data.BlueprintData;
import com.gildedgames.orbis.common.data.CreationData;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.processing.DataPrimer;
import com.gildedgames.orbis.common.processing.WorldPrimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;

public class ItemBlockDataContainer extends Item
{
	public ItemBlockDataContainer()
	{
		super();

		this.setHasSubtypes(true);
	}

	public static void setDatacontainer(final ItemStack stack, final BlockDataContainer container)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final NBTFunnel data = AetherCore.io().createFunnel(stack.getTagCompound());

		data.set("data", container);
	}

	public static BlockDataContainer getDatacontainer(World world, final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("data"))
		{
			return null;
		}

		NBTTagCompound tag = stack.getTagCompound();
		final NBTFunnel data = AetherCore.io().createFunnel(tag);

		return data.get(world, "data");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand handIn)
	{
		ItemStack stack = player.getHeldItem(handIn);

		final PlayerOrbisModule module = PlayerOrbisModule.get(player);

		if (module.inDeveloperMode())
		{
			if (!world.isRemote)
			{
				BlockDataContainer container = getDatacontainer(world, stack);

				final BlockPos selection = module.raytraceNoSnapping();

				final OrbisRotation rotation = OrbisRotation.neutral();

				final IRegion region = RotationHelp.regionFromCenter(selection, container, rotation);

				final DataPrimer primer = new DataPrimer(new WorldPrimer(world));
				primer.create(container, region.getMin(), rotation, new CreationData(world, player));
			}

			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

}
