package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.api.registry.cooler.ITemperatureRegistry;
import com.gildedgames.aether.api.registry.equipment.IEquipmentProperties;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.CapabilityManagerAether;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffectsEventHooks;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityProperties;
import com.gildedgames.aether.common.capabilities.player.ItemSlot;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherEvents;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.crafting.RecipesAether;
import com.gildedgames.aether.common.entities.BossProcessor;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.EntityItemWatcher;
import com.gildedgames.aether.common.entities.MountProcessor;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.boss.slider.BreakFloorActionSlider;
import com.gildedgames.aether.common.entities.living.boss.slider.FirstStageSlider;
import com.gildedgames.aether.common.entities.living.boss.slider.SecondStageSlider;
import com.gildedgames.aether.common.entities.living.boss.slider.ThirdStageSlider;
import com.gildedgames.aether.common.entities.util.SimpleBossManager;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.registry.EquipmentRegistry;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.TemperatureRegistry;
import com.gildedgames.aether.common.registry.TemplatesAether;
import com.gildedgames.aether.common.registry.minecraft.BiomesAether;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.tiles.TileEntitiesAether;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstanceFactory;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstanceHandler;
import com.gildedgames.util.io.Instantiator;
import com.gildedgames.util.modules.instances.InstanceModule;
import com.gildedgames.util.modules.tab.TabModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.util.Random;

public class CommonProxy
{
	private File storageDir;

	private final RecipesAether recipeManager = new RecipesAether();

	private final IEquipmentRegistry equipmentRegistry = new EquipmentRegistry();

	private final ITemperatureRegistry coolerRegistry = new TemperatureRegistry();

	private DungeonInstanceHandler dungeonInstanceHandler;

	public void construct(FMLConstructionEvent event)
	{

	}

	public void preInit(FMLPreInitializationEvent event)
	{
		this.storageDir = new File(event.getSourceFile().getParent(), "Aether/");

		BlocksAether.preInit();
		ItemsAether.preInit();

		EntitiesAether.preInit();
		TileEntitiesAether.preInit();

		NetworkingAether.preInit();

		DimensionsAether.preInit();
		BiomesAether.preInit();

		TemplatesAether.init();
		GenerationAether.init();

		SoundsAether.preInit();

		this.recipeManager.preInit();

		TabModule.api().getInventoryGroup().registerServerTab(new TabEquipment());
		TabModule.api().getInventoryGroup().registerServerTab(new TabBugReport());

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
		AetherCore.srl().registerSerialization(10, ItemSlot.class, new Instantiator(ItemSlot.class));
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(CommonEvents.class);
		MinecraftForge.EVENT_BUS.register(PlayerAetherEvents.class);
		MinecraftForge.EVENT_BUS.register(EntityEffectsEventHooks.class);
		MinecraftForge.EVENT_BUS.register(EntityItemWatcher.class);
		MinecraftForge.EVENT_BUS.register(MountProcessor.class);
		MinecraftForge.EVENT_BUS.register(EntityProperties.class);
		MinecraftForge.EVENT_BUS.register(BossProcessor.class);

		MinecraftForge.EVENT_BUS.register(ItemSkyrootSword.class);

		CapabilityManagerAether.init();

		DungeonInstanceFactory factory = new DungeonInstanceFactory(DimensionsAether.SLIDER_LABYRINTH);

		this.dungeonInstanceHandler = new DungeonInstanceHandler(InstanceModule.INSTANCE.createInstanceHandler(factory));
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

		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(player);

		if (aePlayer == null)
		{
			return false;
		}

		IInventoryEquipment equipment = aePlayer.getEquipmentInventory();

		IEquipmentProperties props = AetherAPI.equipment().getProperties(stack.getItem());

		if (props != null)
		{
			int slot = equipment.getNextEmptySlotForType(props.getEquipmentType());

			if (slot < 0)
			{
				return false;
			}

			equipment.setInventorySlotContents(slot, stack.copy());

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

	public RecipesAether getRecipeManager()
	{
		return this.recipeManager;
	}

	public IEquipmentRegistry getEquipmentRegistry()
	{
		return this.equipmentRegistry;
	}

	public ITemperatureRegistry getCoolerRegistry()
	{
		return this.coolerRegistry;
	}

	public void setExtendedReachDistance(EntityPlayer entity, float distance)
	{
		((EntityPlayerMP) entity).interactionManager.setBlockReachDistance(5.0f + distance);
	}

	public DungeonInstanceHandler getDungeonInstanceHandler()
	{
		return this.dungeonInstanceHandler;
	}

	public void displayDismountMessage()
	{

	}

	public EntityPlayer getPlayer()
	{
		return null;
	}

}
