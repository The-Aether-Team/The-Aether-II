package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCache;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlockDataContainerRenderer;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.items.util.ItemStackInput;
import com.gildedgames.orbis.common.network.packets.PacketCreateItemBlockDataContainer;
import com.gildedgames.orbis.common.network.packets.PacketSendDataToCache;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlockDataContainer extends Item implements ModelRegisterCallback, ItemStackInput
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBake(final ModelBakeEvent event)
	{
		event.getModelRegistry().putObject(new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/block_chunk", "inventory"), dummyModel);
	}

	@Override
	public void onUpdateInHand(final PlayerOrbisModule module)
	{
		final World world = module.getWorld();

		if (!world.isRemote)
		{
			return;
		}

		final BlockPos pos = RaytraceHelp.doOrbisRaytrace(module.getPlayer(), module.raytraceWithRegionSnapping());

		if (!pos.equals(module.powers().getBlueprintPower().getPrevPlacingPos()))
		{
			module.powers().getBlueprintPower().setPrevPlacingPos(pos);

			if ((Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) && module.powers().getBlueprintPower().getPlacingBlueprint() != null)
			{
				final BlockPos createPos = module.raytraceNoSnapping();

				NetworkingAether.sendPacketToServer(new PacketCreateItemBlockDataContainer(module.getEntity().getHeldItemMainhand(), createPos));
			}
		}
	}

	@Override
	public void onMouseEvent(final MouseEvent event, final PlayerOrbisModule module)
	{
		event.setCanceled(true);
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
