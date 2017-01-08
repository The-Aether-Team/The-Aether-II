package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.capabilites.instances.IInstanceRegistry;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.CapabilityManagerAether;
import com.gildedgames.aether.common.capabilities.instances.InstanceRegistryImpl;
import com.gildedgames.aether.common.capabilities.item.EquipmentRegistry;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherEvents;
import com.gildedgames.aether.common.containers.tab.TabRegistryImpl;
import com.gildedgames.aether.common.entities.BossProcessor;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.MountProcessor;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.boss.slider.BreakFloorActionSlider;
import com.gildedgames.aether.common.entities.living.boss.slider.FirstStageSlider;
import com.gildedgames.aether.common.entities.living.boss.slider.SecondStageSlider;
import com.gildedgames.aether.common.entities.living.boss.slider.ThirdStageSlider;
import com.gildedgames.aether.common.entities.util.SimpleBossManager;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.tools.ItemToolHandler;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.ItemPropertiesRegistry;
import com.gildedgames.aether.common.registry.SimpleCraftingRegistry;
import com.gildedgames.aether.common.registry.content.*;
import com.gildedgames.aether.common.registry.minecraft.MinecraftRecipesAether;
import com.gildedgames.aether.common.tiles.TileEntitiesAether;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.util.io.Instantiator;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstanceFactory;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstanceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.*;

import java.io.File;
import java.util.Optional;
import java.util.Random;

public class CommonProxy
{
	private File storageDir;

	private final MinecraftRecipesAether recipeManager = new MinecraftRecipesAether();

	private final IItemPropertiesRegistry itemPropertiesRegistry = new ItemPropertiesRegistry();

	private final ITabRegistry tabRegistry = new TabRegistryImpl();

	private final IInstanceRegistry instanceRegistry = new InstanceRegistryImpl();

	private final SimpleCraftingRegistry simpleCraftingRegistry = new SimpleCraftingRegistry();

	private final SimpleCraftingRegistry masonryRegistry = new SimpleCraftingRegistry();

	private final IEquipmentRegistry equipmentRegistry = new EquipmentRegistry();

	private DungeonInstanceHandler dungeonInstanceHandler;

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

		AetherCore.srl().registerSerialization(0, DungeonInstance.class, new Instantiator(DungeonInstance.class));
		AetherCore.srl().registerSerialization(1, MoaGenePool.class, new Instantiator(MoaGenePool.class));
		AetherCore.srl().registerSerialization(2, TickTimer.class, new Instantiator(TickTimer.class));
		AetherCore.srl().registerSerialization(3, FirstStageSlider.class, new Instantiator(FirstStageSlider.class));
		AetherCore.srl().registerSerialization(4, SecondStageSlider.class, new Instantiator(SecondStageSlider.class));
		AetherCore.srl().registerSerialization(5, ThirdStageSlider.class, new Instantiator(ThirdStageSlider.class));
		AetherCore.srl().registerSerialization(6, BreakFloorActionSlider.class, new Instantiator(BreakFloorActionSlider.class));
		AetherCore.srl().registerSerialization(7, SimpleBossManager.class, new Instantiator(SimpleBossManager.class));
		AetherCore.srl().registerSerialization(8, IslandSector.class, new Instantiator(IslandSector.class));
		AetherCore.srl().registerSerialization(9, IslandData.class, new Instantiator(IslandData.class));
	}

	public void init(FMLInitializationEvent event)
	{
		TemplatesAether.init();
		GenerationAether.init();

		this.recipeManager.init();

		MinecraftForge.EVENT_BUS.register(CommonEvents.class);
		MinecraftForge.EVENT_BUS.register(PlayerAetherEvents.class);
		MinecraftForge.EVENT_BUS.register(MountProcessor.class);
		MinecraftForge.EVENT_BUS.register(BossProcessor.class);
		MinecraftForge.EVENT_BUS.register(ItemToolHandler.class);

		MinecraftForge.EVENT_BUS.register(ItemSkyrootSword.class);

		CapabilityManagerAether.init();

		DungeonInstanceFactory factory = new DungeonInstanceFactory(DimensionsAether.SLIDER_LABYRINTH);

		this.dungeonInstanceHandler = new DungeonInstanceHandler(AetherAPI.instances().createAndRegisterInstanceHandler(factory));
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

	public boolean tryEquipEquipment(EntityPlayer player, ItemStack stack, EnumHand hand)
	{
		if (stack == null)
		{
			return false;
		}

		IPlayerAether aePlayer = PlayerAether.getPlayer(player);

		if (aePlayer == null)
		{
			return false;
		}

		IInventoryEquipment inventory = aePlayer.getEquipmentInventory();

		Optional<IEquipmentProperties> equipment = AetherAPI.items().getEquipmentProperties(stack.getItem());

		if (equipment.isPresent())
		{
			int slot = inventory.getNextEmptySlotForType(equipment.get().getSlot());

			if (slot < 0)
			{
				return false;
			}

			ItemStack copy = stack.copy();
			copy.stackSize = 1;

			inventory.setInventorySlotContents(slot, copy);

			if (!player.capabilities.isCreativeMode)
			{
				// Technically, there should never be STACKABLE equipment, but in case there is, we need to handle it.
				stack.stackSize--;
			}

			player.setHeldItem(hand, stack.stackSize <= 0 ? null : stack);

			return true;
		}

		return false;
	}

	public File getAetherStorageDir()
	{
		return this.storageDir;
	}

	public MinecraftRecipesAether getRecipeManager()
	{
		return this.recipeManager;
	}

	public IItemPropertiesRegistry getItemPropertiesRegistry()
	{
		return this.itemPropertiesRegistry;
	}

	public ITabRegistry getTabRegistry()
	{
		return this.tabRegistry;
	}

	public IInstanceRegistry getInstanceRegistry()
	{
		return this.instanceRegistry;
	}

	public SimpleCraftingRegistry getSimpleCraftingRegistry() { return this.simpleCraftingRegistry; }

	public SimpleCraftingRegistry getMasonryRegistry() { return this.masonryRegistry; }

	public void setExtendedReachDistance(EntityPlayer entity, float distance)
	{
		((EntityPlayerMP) entity).interactionManager.setBlockReachDistance(5.0f + distance);
	}

	public DungeonInstanceHandler getDungeonInstanceHandler()
	{
		return this.dungeonInstanceHandler;
	}

	public void displayDismountMessage(EntityPlayer player)
	{

	}

	// NO-OP
	public void onWorldLoaded(WorldEvent event) { }

	public IEquipmentRegistry getEquipmentRegistry()
	{
		return this.equipmentRegistry;
	}
}
