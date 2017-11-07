package com.gildedgames.aether.api.orbis_core;

import com.gildedgames.aether.api.io.Instantiator;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.api.GameRegistrar;
import com.gildedgames.aether.api.orbis_core.block.*;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.DataCondition;
import com.gildedgames.aether.api.orbis_core.data.management.impl.*;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.orbis_core.data.shapes.*;
import com.gildedgames.aether.api.util.IClassSerializer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrbisCore
{
	public static final Logger LOGGER = LogManager.getLogger("Orbis_CORE");

	private static final IOHelper io = new IOHelper();

	private static final GameRegistrar GAME_REGISTRAR = new GameRegistrar();

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static GameRegistrar getRegistrar()
	{
		return OrbisCore.GAME_REGISTRAR;
	}

	public static IOHelper io()
	{
		return OrbisCore.io;
	}

	public static void preInit()
	{
		final IClassSerializer s = OrbisCore.io().getSerializer();

		s.register(0, Region.class, new Instantiator<>(Region.class));
		s.register(1, BlueprintData.class, new Instantiator<>(BlueprintData.class));

		s.register(7, BlockDataContainer.class, new Instantiator<>(BlockDataContainer.class));

		s.register(12, SphereShape.class, new Instantiator<>(SphereShape.class));
		s.register(14, LineShape.class, new Instantiator<>(LineShape.class));
		s.register(15, BlockFilter.class, new Instantiator<>(BlockFilter.class));
		s.register(16, BlockFilterLayer.class, new Instantiator<>(BlockFilterLayer.class));
		s.register(17, BlockDataWithConditions.class, new Instantiator<>(BlockDataWithConditions.class));
		s.register(18, DataCondition.class, new Instantiator<>(DataCondition.class));

		s.register(20, DataMetadata.class, new Instantiator<>(DataMetadata.class));
		s.register(21, ProjectIdentifier.class, new Instantiator<>(ProjectIdentifier.class));
		s.register(22, OrbisProject.class, new Instantiator<>(OrbisProject.class));
		s.register(23, DataIdentifier.class, new Instantiator<>(DataIdentifier.class));
		s.register(24, OrbisProjectCache.class, new Instantiator<>(OrbisProjectCache.class));
		s.register(25, ProjectMetadata.class, new Instantiator<>(ProjectMetadata.class));

		s.register(27, BlockDataContainerDefaultVoid.class, new Instantiator<>(BlockDataContainerDefaultVoid.class));
		s.register(28, CreationData.class, new Instantiator<>(CreationData.class));
		s.register(29, PyramidShape.class, new Instantiator<>(PyramidShape.class));
		s.register(30, ConeShape.class, new Instantiator<>(ConeShape.class));
		s.register(31, CylinderShape.class, new Instantiator<>(CylinderShape.class));
		s.register(32, DomeShape.class, new Instantiator<>(DomeShape.class));
		s.register(33, CuboidShape.class, new Instantiator<>(CuboidShape.class));
		s.register(34, DataCache.class, new Instantiator<>(DataCache.class));
	}
}
