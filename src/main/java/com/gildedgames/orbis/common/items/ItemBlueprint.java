package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis_core.data.management.IDataMetadata;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlueprintRenderer;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
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

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlueprint extends Item implements ModelRegisterCallback
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
	public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand handIn)
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(player);

		if (module.powers().getBlueprintPower().getPlacingBlueprint() != null)
		{
			if (!world.isRemote)
			{
				final BlockPos selection = module.raytraceNoSnapping();

				final BlueprintData data = module.powers().getBlueprintPower().getPlacingBlueprint();

				final Rotation rotation = module.powers().getBlueprintPower().getPlacingRotation();

				final IRegion region = RotationHelp.regionFromCenter(selection, data, rotation);

				final DataPrimer primer = new DataPrimer(new BlockAccessExtendedWrapper(world));

				primer.create(data.getBlockDataContainer(), new CreationData(world, player).set(region.getMin()).set(rotation));

				System.out.println(data.getMetadata().getIdentifier());
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
