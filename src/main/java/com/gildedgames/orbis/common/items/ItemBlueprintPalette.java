package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlueprintPaletteRenderer;
import com.gildedgames.orbis.common.OrbisCaches;
import com.gildedgames.orbis.common.data.BlueprintPalette;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlueprintPalette extends Item implements ModelRegisterCallback
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
			return OrbisCaches.getBlueprintPalettes().get(stack.getTagCompound()).orNull();
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
	public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand handIn)
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(player);

		if (module.powers().getBlueprintPower().getPlacingPalette() != null)
		{
			if (!world.isRemote)
			{
				final BlockPos selection = module.raytraceNoSnapping();

				final BlueprintData data = module.powers().getBlueprintPower().getPlacingPalette().fetchRandom(world, world.rand);

				final Rotation rotation = module.powers().getBlueprintPower().getPlacingRotation();

				final IRegion region = RotationHelp.regionFromCenter(selection, data, rotation);

				final DataPrimer primer = new DataPrimer(new BlockAccessExtendedWrapper(world));

				primer.create(data.getBlockDataContainer(), new CreationData(world, player).set(region.getMin()).set(rotation));
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
	public boolean getShareTag()
	{
		return true;
	}

}
