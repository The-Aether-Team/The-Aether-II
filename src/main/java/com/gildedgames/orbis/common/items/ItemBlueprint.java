package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis.management.IDataMetadata;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.OrbisCore;
import com.gildedgames.orbis.common.data.BlueprintData;
import com.gildedgames.orbis.common.data.CreationData;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.processing.DataPrimer;
import com.gildedgames.orbis.common.processing.WorldPrimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlueprint extends Item
{
	public ItemBlueprint()
	{
		super();

		this.setHasSubtypes(true);
	}

	public static void setBlueprint(final ItemStack stack, final IDataIdentifier id)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final NBTFunnel funnel = AetherCore.io().createFunnel(stack.getTagCompound());

		funnel.set("blueprint_id", id);
	}

	public static IDataIdentifier getBlueprintId(final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("blueprint_id"))
		{
			return null;
		}

		final NBTFunnel funnel = AetherCore.io().createFunnel(stack.getTagCompound());

		final IDataIdentifier id = funnel.get("blueprint_id");

		return id;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand handIn)
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(player);

		if (module.powers().getBlueprintPower().getPlacingBlueprint() != null)
		{
			if (!world.isRemote)
			{
				final BlockPos selection = module.raytraceNoSnapping();

				final BlueprintData data = module.powers().getBlueprintPower().getPlacingBlueprint();

				final OrbisRotation rotation = OrbisRotation.neutral();

				final IRegion region = RotationHelp.regionFromCenter(selection, data, rotation);

				final DataPrimer primer = new DataPrimer(new WorldPrimer(world));

				primer.create(data.getBlockDataContainer(), region.getMin(), rotation, new CreationData(world, player));
			}

			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
		}

		return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
	}

	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer par2EntityPlayer, final List<String> creativeList, final boolean par4)
	{

	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack)
	{
		final IDataIdentifier id = ItemBlueprint.getBlueprintId(stack);

		if (id != null)
		{
			final IDataMetadata data = OrbisCore.getProjectManager().findMetadata(id);

			return data.getName();
		}

		return super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void onUpdate(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected)
	{
		try
		{
			ItemBlueprint.getBlueprintId(stack);
		}
		catch (OrbisMissingProjectException | OrbisMissingDataException e)
		{
			AetherCore.LOGGER.error("Blueprint itemstack could not find its data!", e);

			if (entityIn instanceof EntityPlayer)
			{
				final EntityPlayer player = (EntityPlayer) entityIn;

				player.inventory.setInventorySlotContents(itemSlot, ItemStack.EMPTY);
			}
		}
	}

}
