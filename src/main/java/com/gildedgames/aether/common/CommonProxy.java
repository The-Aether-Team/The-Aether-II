package com.gildedgames.aether.common;

import java.io.File;
import java.util.Random;

import com.gildedgames.aether.common.entities.EntityItemWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.effects.EntityEffectsEventHooks;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.properties.EquipmentRegistry;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.player.PlayerAetherEvents;
import com.gildedgames.aether.common.crafting.RecipesAether;
import com.gildedgames.aether.common.tile_entities.TileEntitiesAether;
import com.gildedgames.aether.common.tile_entities.TileEntityBoundary.FetchedEntity;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.aether.common.world.dungeon.DungeonInstance;
import com.gildedgames.util.io.Instantiator;
import com.gildedgames.util.modules.tab.TabModule;

public class CommonProxy
{
	private File storageDir;

	private final RecipesAether recipeManager = new RecipesAether();

	private final EquipmentRegistry equipmentRegistry = new EquipmentRegistry();

	private DimensionType aetherDimension;

	public void construct(FMLConstructionEvent event)
	{

	}

	public void preInit(FMLPreInitializationEvent event)
	{
		this.storageDir = new File(event.getSourceFile().getParent(), "Aether/");

		// Load the configuration file.
		AetherCore.CONFIG.load();

		// Register with NetworkRegistry.
		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());

		// Register dimension type
		this.aetherDimension = DimensionType.register("Aether", "_aether", AetherCore.getAetherDimID(), WorldProviderAether.class, false);

		// Register dimension world
		DimensionManager.registerDimension(AetherCore.getAetherDimID(), this.aetherDimension);

		// Pre-initialize content.
		MaterialsAether.preInit();

		BlocksAether.preInit();
		ItemsAether.preInit();

		NetworkingAether.preInit();

		TileEntitiesAether.preInit();
		EntitiesAether.preInit();

		this.recipeManager.preInit();

		TabModule.api().getInventoryGroup().registerServerTab(new TabEquipment());

		AetherCore.srl().registerSerialization(0, DungeonInstance.class, new Instantiator(DungeonInstance.class));
		AetherCore.srl().registerSerialization(1, FetchedEntity.class, new Instantiator(FetchedEntity.class));
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		MinecraftForge.EVENT_BUS.register(new PlayerAetherEvents());
		MinecraftForge.EVENT_BUS.register(new EntityEffectsEventHooks());
		MinecraftForge.EVENT_BUS.register(new EntityItemWatcher());

		AetherCapabilityManager capabilities = new AetherCapabilityManager();
		capabilities.init();

		MinecraftForge.EVENT_BUS.register(ItemsAether.skyroot_sword);

//		ChunkModule.api().registerHookFactory(new PlacementFlagFactory());
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public void spawnJumpParticles(World world, double x, double y, double z, double radius, int quantity)
	{
		Random random = world.rand;

		for (int i = 0; i < quantity; i++)
		{
			double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
			double y2 = y + (random.nextDouble() * 0.4D) + 0.5D;
			double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);

			world.spawnParticle(EnumParticleTypes.CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * 0.03D, 0.0D);
		}
	}

	public File getAetherStorageDir()
	{
		return this.storageDir;
	}

	public RecipesAether getRecipeManager()
	{
		return this.recipeManager;
	}

	public EquipmentRegistry getEquipmentRegistry()
	{
		return this.equipmentRegistry;
	}

	public void setExtendedReachDistance(EntityPlayer entity, float distance)
	{
		((EntityPlayerMP) entity).interactionManager.setBlockReachDistance(5.0f + distance);
	}

	public DimensionType getAetherDimensionType()
	{
		return this.aetherDimension;
	}
}
