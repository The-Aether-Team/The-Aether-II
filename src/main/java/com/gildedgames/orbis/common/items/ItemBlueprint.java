package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis_core.data.management.IDataMetadata;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlueprintRenderer;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.items.util.ItemStackInput;
import com.gildedgames.orbis.common.network.packets.PacketCreatePlacingBlueprint;
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

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlueprint extends Item implements ModelRegisterCallback, ItemStackInput
{
	@SideOnly(Side.CLIENT)
	private static TileEntityBlueprintRenderer.BakedModel dummyModel;

	public ItemBlueprint()
	{
		super();

		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBake(final ModelBakeEvent event)
	{
		event.getModelRegistry().putObject(new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/blueprint", "inventory"), dummyModel);
	}

	public static void setBlueprint(final ItemStack stack, final IDataIdentifier id)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final NBTFunnel funnel = OrbisCore.io().createFunnel(stack.getTagCompound());

		funnel.set("blueprint_id", id);
	}

	public static IDataIdentifier getBlueprintId(final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("blueprint_id"))
		{
			return null;
		}

		final NBTFunnel funnel = OrbisCore.io().createFunnel(stack.getTagCompound());

		final IDataIdentifier id = funnel.get("blueprint_id");

		return id;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/blueprint", "inventory"));

		final TileEntityBlueprintRenderer tesr = new TileEntityBlueprintRenderer();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlueprintRenderer.DummyTile.class, tesr);
		dummyModel = tesr.baked;

		ForgeHooksClient.registerTESRItemStack(this, 0, TileEntityBlueprintRenderer.DummyTile.class);
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
			try
			{
				final IDataMetadata data = Orbis.getProjectManager().findMetadata(id);

				return data.getName();
			}
			catch (OrbisMissingDataException | OrbisMissingProjectException e)
			{
				AetherCore.LOGGER.error(e);
			}
		}

		return super.getItemStackDisplayName(stack);
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

				NetworkingAether.sendPacketToServer(new PacketCreatePlacingBlueprint(createPos));
			}
		}
	}

	@Override
	public void onMouseEvent(final MouseEvent event, final PlayerOrbisModule module)
	{
		event.setCanceled(true);
	}
}
