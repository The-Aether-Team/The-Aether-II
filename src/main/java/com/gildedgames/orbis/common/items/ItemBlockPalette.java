package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.api.ICreationData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataWithConditions;
import com.gildedgames.aether.api.orbis_core.block.BlockFilter;
import com.gildedgames.aether.api.orbis_core.block.BlockFilterLayer;
import com.gildedgames.aether.api.orbis_core.block.BlockFilterType;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.ModelRegisterCallback;
import com.gildedgames.orbis.client.renderers.tiles.TileEntityBlockPaletteRenderer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemBlockPalette extends Item implements ModelRegisterCallback, IShapeSelector
{

	@SideOnly(Side.CLIENT)
	private static TileEntityBlockPaletteRenderer.BakedModel dummyModel;

	public ItemBlockPalette()
	{
		super();

		this.setHasSubtypes(true);
	}

	public static void setFilterLayer(final ItemStack stack, final BlockFilterLayer layer)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final NBTFunnel funnel = OrbisCore.io().createFunnel(stack.getTagCompound());

		funnel.set("layer", layer);
	}

	public static BlockFilterLayer getFilterLayer(final ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("layer"))
		{
			return null;
		}

		final NBTFunnel funnel = OrbisCore.io().createFunnel(stack.getTagCompound());

		return funnel.get("layer");
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBake(final ModelBakeEvent event)
	{
		event.getModelRegistry().putObject(new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/block_palette", "inventory"), dummyModel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(AetherCore.MOD_ID + ":orbis/block_palette", "inventory"));

		final TileEntityBlockPaletteRenderer tesr = new TileEntityBlockPaletteRenderer();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockPaletteRenderer.DummyTile.class, tesr);
		dummyModel = tesr.baked;

		ForgeHooksClient.registerTESRItemStack(this, 0, TileEntityBlockPaletteRenderer.DummyTile.class);
	}

	@Override
	public boolean isSelectorActive(final PlayerOrbisModule module, final World world)
	{
		return true;
	}

	@Override
	public boolean canSelectShape(final PlayerOrbisModule module, final IShape shape, final World world)
	{
		return true;
	}

	@Override
	public void onSelect(final PlayerOrbisModule module, final IShape selectedShape, final World world)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		final ICreationData creationData = new CreationData(world, module.getEntity());

		final BlockFilterLayer layer = ItemBlockPalette.getFilterLayer(held);

		if (module.powers().getCurrentPower() == module.powers().getReplacePower())
		{
			layer.setFilterType(BlockFilterType.ALL_EXCEPT);

			layer.setRequiredBlocks(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0F));
		}
		else if (module.powers().getCurrentPower() == module.powers().getDeletePower())
		{
			layer.setFilterType(BlockFilterType.ONLY);

			layer.setRequiredBlocks(layer.getReplacementBlocks());
			layer.setReplacementBlocks(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0F));
		}

		final BlockFilter filter = new BlockFilter(layer);

		filter.apply(selectedShape, world, creationData);
	}
}
