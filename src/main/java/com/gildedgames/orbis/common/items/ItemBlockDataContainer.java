package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

		final NBTFunnel data = OrbisCore.io().createFunnel(stack.getTagCompound());

		data.set("data", container);
	}

	public static BlockDataContainer getDatacontainer(final World world, final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("data"))
		{
			return null;
		}

		final NBTTagCompound tag = stack.getTagCompound();
		final NBTFunnel data = OrbisCore.io().createFunnel(tag);

		return data.get(world, "data");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand handIn)
	{
		final ItemStack stack = player.getHeldItem(handIn);

		final PlayerOrbisModule module = PlayerOrbisModule.get(player);

		if (module.inDeveloperMode())
		{
			if (!world.isRemote)
			{
				final BlockDataContainer container = getDatacontainer(world, stack);

				final BlockPos selection = module.raytraceNoSnapping();

				final Rotation rotation = Rotation.NONE;

				final IRegion region = RotationHelp.regionFromCenter(selection, container, rotation);

				final DataPrimer primer = new DataPrimer(new BlockAccessExtendedWrapper(world));
				primer.create(container, new CreationData(world, player).set(region.getMin()).set(rotation));
			}

			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

}
