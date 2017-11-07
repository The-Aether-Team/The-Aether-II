package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCache;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.network.packets.PacketSendDataToCache;
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

	public static void setDataContainer(final EntityPlayer player, final ItemStack stack, final BlockDataContainer container)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final IDataCache cache = Orbis.getDataCache().findCache(Orbis.BLOCK_DATA_CONTAINERS_CACHE);

		final boolean shouldSend = container.getMetadata().getIdentifier() == null;
		final int id = cache.addData(container);

		stack.getTagCompound().setInteger("dataId", id);

		if (!player.world.isRemote && player.getServer() != null && player.getServer().isDedicatedServer() && shouldSend)
		{
			NetworkingAether.sendPacketToAllPlayers(new PacketSendDataToCache(Orbis.BLOCK_DATA_CONTAINERS_CACHE, container));
		}
	}

	public static BlockDataContainer getDataContainer(final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("dataId"))
		{
			return null;
		}

		final NBTTagCompound tag = stack.getTagCompound();

		final IDataCache cache = Orbis.getDataCache().findCache(Orbis.BLOCK_DATA_CONTAINERS_CACHE);

		return cache.getData(tag.getInteger("dataId"));
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
				final BlockDataContainer container = getDataContainer(stack);

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
