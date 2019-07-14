package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.tiles.*;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class TileEntitiesAether
{
	@SubscribeEvent
	public static void register(RegistryEvent.Register<TileEntityType<?>> event)
	{
		IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();

		registry.register(TileEntityType.Builder.create(TileEntityAltar::new, BlocksAether.altar).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityHolystoneFurnace::new, BlocksAether.holystone_furnace).build(null));
		registry.register(TileEntityType.Builder.create(TileEntitySkyrootChest::new, BlocksAether.skyroot_chest).build(null));
		registry.register(TileEntityType.Builder.create(TileEntitySkyrootSign::new, BlocksAether.standing_skyroot_sign, BlocksAether.wall_skyroot_sign).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityMultiblockDummy::new, BlocksAether.multiblock_dummy, BlocksAether.multiblock_dummy_half).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityMoaEgg::new, BlocksAether.moa_egg).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityIcestoneCooler::new, BlocksAether.icestone_cooler).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityIncubator::new, BlocksAether.incubator).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityPresent::new, BlocksAether.present).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityWildcard::new, BlocksAether.wildcard).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityMasonryBench::new, BlocksAether.masonry_bench).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityOutpostCampfire::new, BlocksAether.outpost_campfire).build(null));
		registry.register(TileEntityType.Builder.create(TileEntityTeleporter::new, BlocksAether.aether_teleporter).build(null));
	}

}
