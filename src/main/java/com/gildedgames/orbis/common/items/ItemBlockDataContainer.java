package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCache;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlockDataContainerRenderer;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.network.packets.PacketSendDataToCache;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlockDataContainer extends Item implements ModelRegisterCallback
{

	@SideOnly(Side.CLIENT)
	private static TileEntityBlockDataContainerRenderer.BakedModel dummyModel;

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

	@SubscribeEvent()
	public static void onModelRegistryReady(final ModelRegistryEvent event)
	{
		for (final Item i : Item.REGISTRY)
		{
			if (i instanceof ModelRegisterCallback)
			{
				((ModelRegisterCallback) i).registerModel();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBake(final ModelBakeEvent event)
	{
		event.getModelRegistry().putObject(new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/block_chunk", "inventory"), dummyModel);
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/block_chunk", "inventory"));

		final TileEntityBlockDataContainerRenderer tesr = new TileEntityBlockDataContainerRenderer();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockDataContainerRenderer.DummyTile.class, tesr);
		dummyModel = tesr.baked;

		ForgeHooksClient.registerTESRItemStack(this, 0, TileEntityBlockDataContainerRenderer.DummyTile.class);
	}

}
