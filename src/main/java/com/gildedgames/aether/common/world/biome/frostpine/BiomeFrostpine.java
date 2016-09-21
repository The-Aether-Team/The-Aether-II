package com.gildedgames.aether.common.world.biome.frostpine;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.GenUtil;
import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import com.gildedgames.aether.common.world.features.TemplatePipeline;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import com.gildedgames.aether.common.world.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

import static com.gildedgames.aether.common.world.biome.highlands.BiomeHighlandPlains.pines;

public class BiomeFrostpine extends BiomeAetherBase
{

	public static final TemplateManager MANAGER = new TemplateManager("structures");

	protected WorldGenTemplate tree1, tree2, tree3, tree4, tree5, tree6;

	protected TemplatePipeline templatePipeline;

	public BiomeFrostpine()
	{
		super(new BiomeProperties("Frostpine").setRainDisabled().setTemperature(0.5f));

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.FROSTROOT);
		this.fillerBlock = BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.PERMAFROST);

		this.setRegistryName(AetherCore.getResource("aether_frostpine"));

		this.templatePipeline = new TemplatePipeline();
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos)
	{
		if (world instanceof WorldServer)
		{
			WorldServer worldServer = (WorldServer)world;
			MinecraftServer server = worldServer.getMinecraftServer();

			this.tree1 = new WorldGenTemplate(this.templatePipeline, MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak1")));
			this.tree2 = new WorldGenTemplate(this.templatePipeline, MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak2")));
			this.tree3 = new WorldGenTemplate(this.templatePipeline, MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak3")));
			this.tree4 = new WorldGenTemplate(this.templatePipeline, MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak4")));
			this.tree5 = new WorldGenTemplate(this.templatePipeline, MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak5")));
			this.tree6 = new WorldGenTemplate(this.templatePipeline, MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "frostpine/oak6")));
		}

		super.decorate(world, random, pos);

		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		int x, y, z;

		for (int n = 0; n < 10; n++)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			y = GenUtil.getTopBlock(world, new BlockPos(pos.getX() + x, 0, pos.getZ() + z)).getY() + 1;

			Rotation[] arotation = Rotation.values();
			Rotation rotation = arotation[random.nextInt(arotation.length)];

			int type = random.nextInt(6);

			switch(type)
			{
				case 0:
				{
					this.tree1.generate(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 1:
				{
					this.tree2.generate(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 2:
				{
					this.tree3.generate(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 3:
				{
					this.tree4.generate(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 4:
				{
					this.tree5.generate(world, random, pos.add(x, y, z), rotation);
					break;
				}
				case 5:
				{
					this.tree6.generate(world, random, pos.add(x, y, z), rotation);
					break;
				}
			}
		}

		this.templatePipeline.constructChunk(world, chunkX, chunkZ);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		return null;
	}

}
