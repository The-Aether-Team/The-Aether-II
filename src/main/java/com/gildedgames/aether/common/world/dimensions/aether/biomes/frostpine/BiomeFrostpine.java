package com.gildedgames.aether.common.world.dimensions.aether.biomes.frostpine;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenAetherFlowers;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import com.gildedgames.aether.common.world.gen.TemplatePipeline;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class BiomeFrostpine extends BiomeAetherBase
{

	public static final TemplateManager MANAGER = new TemplateManager("structures");

	protected WorldGenTemplate tree1, tree2, tree3, tree4, tree5, tree6, pine1, pine2;

	protected TemplatePipeline templatePipeline;

	protected WorldGenAetherFlowers genMoonlitBlooms = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.MOONLIT_BLOOM), 64);

	public BiomeFrostpine()
	{
		super(new BiomeProperties("Frostpine").setRainDisabled().setTemperature(0.5f), AetherCore.getResource("aether_frostpine"));

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.FROSTROOT);
		this.fillerBlock = BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.PERMAFROST);

		this.templatePipeline = new TemplatePipeline();
	}

	/*@Override
	public void decorate(World world, Random random, BlockPos pos)
	{
		if (world instanceof WorldServer)
		{
			WorldServer worldServer = (WorldServer)world;
			MinecraftServer server = worldServer.getMinecraftServer();

			this.tree1 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak1")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
			this.tree2 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak2")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
			this.tree3 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak3")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
			this.tree4 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak4")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
			this.tree5 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak5")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
			this.tree6 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak6")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());

			this.pine1 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/pine1")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
			this.pine2 = new WorldGenTemplate(this.templatePipeline, MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/pine2")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		}

		super.decorate(world, random, pos);

		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		int x, y, z;

		for (int n = 0; n < 15; n++)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			y = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).getY() + 1;

			Rotation[] arotation = Rotation.values();

			Rotation rotation = arotation[random.nextInt(arotation.length)];

			if (random.nextBoolean())
			{
				this.pine1.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
			}
			else
			{
				this.pine2.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
			}
		}

		for (int n = 0; n < 10; n++)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			y = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).getY() + 1;

			Rotation[] arotation = Rotation.values();
			Rotation rotation = arotation[random.nextInt(arotation.length)];

			int type = random.nextInt(6);

			switch(type)
			{
				case 0:
				{
					this.tree1.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 1:
				{
					this.tree2.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 2:
				{
					this.tree3.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 3:
				{
					this.tree4.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 4:
				{
					this.tree5.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 5:
				{
					this.tree6.placeTemplateWithCheck(world, random, pos.add(x, y, z), rotation);
					break;
				}
			}
		}

		this.templatePipeline.constructChunk(world, chunkX, chunkZ);

		// Burstblossom Generator
		for (int count = 0; count < 2; count++)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genMoonlitBlooms.generate(world, random, pos.add(x, y, z));
		}
	}*/

}
