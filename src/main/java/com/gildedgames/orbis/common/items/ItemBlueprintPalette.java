package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlueprintPaletteRenderer;
import com.gildedgames.orbis.common.OrbisServerCaches;
import com.gildedgames.orbis.common.data.BlueprintPalette;
import com.gildedgames.orbis.common.items.util.ItemStackInput;
import com.gildedgames.orbis.common.network.packets.PacketCreatePlacingBlueprintPalette;
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

import java.util.List;
import java.util.concurrent.ExecutionException;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlueprintPalette extends Item implements ModelRegisterCallback, ItemStackInput
{
	@SideOnly(Side.CLIENT)
	private static TileEntityBlueprintPaletteRenderer.BakedModel dummyModel;

	public ItemBlueprintPalette()
	{
		super();

		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBake(final ModelBakeEvent event)
	{
		event.getModelRegistry().putObject(new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/blueprint_palette", "inventory"), dummyModel);
	}

	public static void setBlueprintPalette(final ItemStack stack, final BlueprintPalette palette)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final NBTFunnel funnel = OrbisCore.io().createFunnel(stack.getTagCompound());

		funnel.set("palette", palette);
	}

	public static BlueprintPalette getBlueprintPalette(final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("palette"))
		{
			return null;
		}

		try
		{
			return OrbisServerCaches.getBlueprintPalettes().get(stack.getTagCompound()).orNull();
		}
		catch (final ExecutionException e)
		{
			OrbisCore.LOGGER.error(e);
		}

		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/blueprint_palette", "inventory"));

		final TileEntityBlueprintPaletteRenderer tesr = new TileEntityBlueprintPaletteRenderer();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlueprintPaletteRenderer.DummyTile.class, tesr);
		dummyModel = tesr.baked;

		ForgeHooksClient.registerTESRItemStack(this, 0, TileEntityBlueprintPaletteRenderer.DummyTile.class);
	}

	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer par2EntityPlayer, final List<String> creativeList, final boolean par4)
	{

	}

	@Override
	public boolean getShareTag()
	{
		return true;
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

				NetworkingAether.sendPacketToServer(new PacketCreatePlacingBlueprintPalette(createPos));
			}
		}
	}

	@Override
	public void onMouseEvent(final MouseEvent event, final PlayerOrbisModule module)
	{
		event.setCanceled(true);
	}
}
