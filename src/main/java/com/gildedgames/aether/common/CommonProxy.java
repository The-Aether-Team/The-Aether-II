package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.IAetherServiceLocator;
import com.gildedgames.aether.api.dialog.IDialogManager;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.CapabilityManagerAether;
import com.gildedgames.aether.common.capabilities.item.EquipmentRegistry;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherEvents;
import com.gildedgames.aether.common.containers.tab.TabRegistryImpl;
import com.gildedgames.aether.common.dialog.DialogManager;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.MountProcessor;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.tools.ItemToolHandler;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.ItemPropertiesRegistry;
import com.gildedgames.aether.common.registry.content.*;
import com.gildedgames.aether.common.registry.minecraft.MinecraftRecipesAether;
import com.gildedgames.aether.common.tiles.TileEntitiesAether;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.util.io.Instantiator;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.*;

import java.io.File;
import java.util.Random;

public class CommonProxy implements IAetherServiceLocator
{
	private File storageDir;

	protected final MinecraftRecipesAether recipeManager = new MinecraftRecipesAether();

	protected final ItemPropertiesRegistry itemPropertiesRegistry = new ItemPropertiesRegistry();

	protected final TabRegistryImpl tabRegistry = new TabRegistryImpl();

	protected final EquipmentRegistry equipmentRegistry = new EquipmentRegistry();

	protected final DialogManager dialogManager = new DialogManager(true);

	public void construct(FMLConstructionEvent event)
	{

	}

	public void preInit(FMLPreInitializationEvent event)
	{
		this.storageDir = new File(event.getSourceFile().getParent(), "Aether/");

		BlocksAether.preInit();
		ItemsAether.preInit();

		this.recipeManager.preInit();

		EquipmentContent.preInit();

		LootTablesAether.preInit();

		EntitiesAether.preInit();
		TileEntitiesAether.preInit();

		NetworkingAether.preInit();

		DimensionsAether.preInit();
		BiomesAether.preInit();

		SoundsAether.preInit();

		AetherAPI.tabs().getInventoryGroup().registerServerTab(new TabEquipment());
		AetherAPI.tabs().getInventoryGroup().registerServerTab(new TabBugReport());

		AetherCore.srl().registerSerialization(1, MoaGenePool.class, new Instantiator(MoaGenePool.class));
		AetherCore.srl().registerSerialization(2, TickTimer.class, new Instantiator(TickTimer.class));
		AetherCore.srl().registerSerialization(8, IslandSector.class, new Instantiator(IslandSector.class));
		AetherCore.srl().registerSerialization(9, IslandData.class, new Instantiator(IslandData.class));
	}

	public void init(FMLInitializationEvent event)
	{
		CapabilityManagerAether.init();

		TemplatesAether.init();
		GenerationAether.init();

		this.recipeManager.init();

		MinecraftForge.EVENT_BUS.register(CommonEvents.class);
		MinecraftForge.EVENT_BUS.register(PlayerAetherEvents.class);
		MinecraftForge.EVENT_BUS.register(MountProcessor.class);
		MinecraftForge.EVENT_BUS.register(ItemToolHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemSkyrootSword.class);
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public void serverStarting(FMLServerStartingEvent event)
	{

	}

	public void spawnJumpParticles(World world, double x, double y, double z, double radius, int quantity)
	{
		Random random = world.rand;

		for (int i = 0; i < quantity; i++)
		{
			double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
			double y2 = y + (random.nextDouble() * 0.4D);
			double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);

			world.spawnParticle(EnumParticleTypes.CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * 0.03D, 0.0D);
		}
	}

	public File getAetherStorageDir()
	{
		return this.storageDir;
	}

	public MinecraftRecipesAether getRecipeManager()
	{
		return this.recipeManager;
	}

	@Override
	public IItemPropertiesRegistry getItemPropertiesRegistry()
	{
		return this.itemPropertiesRegistry;
	}

	@Override
	public ITabRegistry getTabRegistry()
	{
		return this.tabRegistry;
	}

	public void displayDismountMessage(EntityPlayer player)
	{

	}

	// NO-OP
	public void onWorldLoaded(WorldEvent event)
	{
	}

	@Override
	public IEquipmentRegistry getEquipmentRegistry()
	{
		return this.equipmentRegistry;
	}

	@Override
	public IDialogManager getDialogManager()
	{
		return this.dialogManager;
	}

	@Override
	public IAltarRecipeRegistry getAltarRecipeRegistry()
	{
		return this.recipeManager.getAltarRegistry();
	}
}
