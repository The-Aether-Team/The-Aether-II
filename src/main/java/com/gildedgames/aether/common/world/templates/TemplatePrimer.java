package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.util.BlockAccessChunkPrimer;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.api.util.TemplateUtil;
import com.gildedgames.aether.api.world.generation.*;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiDummy;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiDummyHalf;
import com.gildedgames.aether.common.entities.tiles.TileEntityWildcard;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class TemplatePrimer
{
	private static final Field GET_BLOCKS_FIELD = ReflectionAether.getField(Template.class, "blocks", "field_186270_a");

	private static final Field GET_ENTITIES_FIELD = ReflectionAether.getField(Template.class, "entities", "field_186271_b");

	public static boolean canGenerate(final IBlockAccessExtended blockAccess, final TemplateDefinition def, final TemplateLoc loc)
	{
		BlockPos pos = loc.getPos();

		if (loc.isCentered())
		{
			pos = TemplateUtil.getCenteredPos(def, loc);
		}

		final StructureBoundingBox bb = TemplateUtil.getBoundingBoxFromTemplate(def, loc);

		if (bb.maxY > 256)
		{
			return false;
		}

		final List<Template.BlockInfo> info = TemplatePrimer.getBlocks(def.getTemplate());

		final List<Template.BlockInfo> infoTransformed = TemplatePrimer.getBlocks(info, pos, loc.getSettings(), def.getTemplate());

		for (final Template.BlockInfo block : infoTransformed)
		{
			for (final PlacementCondition condition : def.getConditions())
			{
				if (!blockAccess.canAccess(pos) || !condition.canPlace(def.getTemplate(), blockAccess, pos, block))
				{
					return false;
				}
			}
		}

		for (final PlacementCondition condition : def.getConditions())
		{
			if (!condition.canPlaceCheckAll(def.getTemplate(), blockAccess, pos, infoTransformed))
			{
				return false;
			}
		}

		return true;
	}

	public static boolean canGenerate(final World world, final TemplateDefinition def, final TemplateLoc loc)
	{
		return canGenerate(world, def, loc, true);
	}

	public static boolean canGenerateWithoutAreaCheck(final World world, final TemplateDefinition def, final TemplateLoc loc)
	{
		return canGenerate(world, def, loc, false);
	}

	private static boolean canGenerate(final World world, final TemplateDefinition def, final TemplateLoc loc, final boolean checkAreaLoaded)
	{
		BlockPos pos = loc.getPos();

		if (loc.isCentered())
		{
			pos = TemplateUtil.getCenteredPos(def, loc);
		}

		final StructureBoundingBox bb = TemplateUtil.getBoundingBoxFromTemplate(def, loc);

		if ((checkAreaLoaded && !world.isAreaLoaded(bb)) || bb.maxY > world.getActualHeight())
		{
			return false;
		}

		final BlockAccessExtendedWrapper blockAccess = new BlockAccessExtendedWrapper(world);

		final List<Template.BlockInfo> info = TemplatePrimer.getBlocks(def.getTemplate());

		final List<Template.BlockInfo> infoTransformed = TemplatePrimer.getBlocks(info, pos, loc.getSettings(), def.getTemplate());

		for (final Template.BlockInfo block : infoTransformed)
		{
			for (final PlacementCondition condition : def.getConditions())
			{
				if (!condition.canPlace(def.getTemplate(), blockAccess, pos, block))
				{
					return false;
				}
			}
		}

		for (final PlacementCondition condition : def.getConditions())
		{
			if (!condition.canPlaceCheckAll(def.getTemplate(), blockAccess, pos, infoTransformed))
			{
				return false;
			}
		}

		return true;
	}

	public static void generateTemplate(final IBlockAccessExtended blockAccess, final TemplateDefinition def, final TemplateLoc loc)
	{
		BlockPos pos = loc.getPos();

		if (loc.isCentered())
		{
			pos = TemplateUtil.getCenteredPos(def, loc);
		}

		final ITemplateProcessorExtended processor = new BlockRotationProcessorExtended(pos, loc.getSettings());
		TemplatePrimer.populateAll(def.getTemplate(), blockAccess, pos, processor, loc.getSettings());
	}

	public static void generateTemplateSingleChunk(final ChunkPos chunk, final World world, final IBlockAccessExtended blockAccess,
			final TemplateDefinition def,
			final TemplateLoc loc)
	{
		BlockPos pos = loc.getPos();

		if (loc.isCentered())
		{
			pos = TemplateUtil.getCenteredPos(def, loc);
		}

		final ITemplateProcessorExtended processor = new BlockRotationProcessorExtended(pos, loc.getSettings());
		TemplatePrimer.populateChunk(def.getTemplate(), world, blockAccess, chunk, pos, processor, loc.getSettings());
	}

	public static void primeTemplateSingleChunk(final ChunkPos chunk, final World world, final ChunkPrimer primer,
			final TemplateDefinition def,
			final TemplateLoc loc)
	{
		BlockPos pos = loc.getPos();

		if (loc.isCentered())
		{
			pos = TemplateUtil.getCenteredPos(def, loc);
		}

		final IBlockAccessExtended blockAccess = new BlockAccessChunkPrimer(world, primer);

		final ITemplateProcessorExtended processor = new BlockRotationProcessorExtended(pos, loc.getSettings());
		TemplatePrimer.primeChunk(def.getTemplate(), blockAccess, world, chunk, primer, pos, processor, loc.getSettings());
	}

	@SuppressWarnings("unchecked")
	public static List<Template.BlockInfo> getBlocks(final Template template)
	{
		return (List<Template.BlockInfo>) ReflectionAether.getValue(GET_BLOCKS_FIELD, template);
	}

	public static List<Template.BlockInfo> getBlocks(final List<Template.BlockInfo> blockInfo, final BlockPos pos, final PlacementSettings settings,
			final Template template)
	{
		final List<Template.BlockInfo> newInfo = Lists.newArrayList();

		for (final Template.BlockInfo info : blockInfo)
		{
			final BlockPos blockpos = Template.transformedBlockPos(settings, info.pos).add(pos);

			newInfo.add(new Template.BlockInfo(blockpos, info.blockState, info.tileentityData));
		}

		return newInfo;
	}

	public static List<BlockPos> getBlockPos(final BlockPos pos, final PlacementSettings settings, final Template template)
	{
		return TemplatePrimer.getBlockPos(TemplatePrimer.getBlocks(template), pos, settings);
	}

	public static List<BlockPos> getBlockPos(final List<Template.BlockInfo> blockInfo, final BlockPos pos, final PlacementSettings settings)
	{
		final List<BlockPos> blockPos = Lists.newArrayList();

		for (final Template.BlockInfo info : blockInfo)
		{
			final BlockPos blockpos = Template.transformedBlockPos(settings, info.pos).add(pos);

			blockPos.add(blockpos);
		}

		return blockPos;
	}

	@SuppressWarnings("unchecked")
	private static List<Template.EntityInfo> getEntities(final Template template)
	{
		return (List<Template.EntityInfo>) ReflectionAether.getValue(GET_ENTITIES_FIELD, template);
	}

	public static void populateAll(final Template template, final IBlockAccessExtended blockAccess, final BlockPos pos,
			@Nullable final ITemplateProcessorExtended processor,
			final PlacementSettings settings)
	{
		final List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			final Block block = settings.getReplacedBlock();
			final StructureBoundingBox bb = settings.getBoundingBox();

			for (final Template.BlockInfo template$blockinfo : blocks)
			{
				final BlockPos blockpos = Template.transformedBlockPos(settings, template$blockinfo.pos).add(pos);

				final Template.BlockInfo template$blockinfo1 =
						processor != null ? processor.processBlock(blockAccess, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					final Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && (
							bb == null || bb.isVecInside(blockpos)))
					{
						final IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						final IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						if (iblockstate1.getBlock() instanceof BlockMultiDummy || iblockstate1.getBlock() instanceof BlockMultiDummyHalf)
						{
							continue;
						}

						if (iblockstate1.getBlock() == BlocksAether.wildcard)
						{
							final TileEntityWildcard wildcard = new TileEntityWildcard();

							if (blockAccess.getWorld() != null)
							{
								final World world = blockAccess.getWorld();

								wildcard.setWorld(world);

								template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
								template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
								template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
								wildcard.readFromNBT(template$blockinfo1.tileentityData);
								wildcard.mirror(settings.getMirror());
								wildcard.rotate(settings.getRotation());

								wildcard.onSchematicGeneration(blockAccess, world.rand);
							}

							continue;
						}

						if (template$blockinfo1.tileentityData != null)
						{
							final TileEntity tileentity = blockAccess.getTileEntity(blockpos);

							if (tileentity != null)
							{
								if (tileentity instanceof IInventory)
								{
									((IInventory) tileentity).clear();
								}
							}
						}

						if (blockAccess.setBlockState(blockpos, iblockstate1, 2) && template$blockinfo1.tileentityData != null)
						{
							final TileEntity tileentity2 = blockAccess.getTileEntity(blockpos);

							if (tileentity2 != null)
							{
								template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
								template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
								template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
								tileentity2.readFromNBT(template$blockinfo1.tileentityData);
								tileentity2.mirror(settings.getMirror());
								tileentity2.rotate(settings.getRotation());

								tileentity2.markDirty();

								if (tileentity2 instanceof TileEntityMultiblockController)
								{
									final TileEntityMultiblockController controller = (TileEntityMultiblockController) tileentity2;

									controller.rebuild();
								}
							}
						}
					}
				}
			}

			for (final Template.BlockInfo template$blockinfo2 : blocks)
			{
				if (block == null || block != template$blockinfo2.blockState.getBlock())
				{
					final BlockPos blockpos1 = Template.transformedBlockPos(settings, template$blockinfo2.pos).add(pos);

					if (bb == null || bb.isVecInside(blockpos1))
					{
						if (blockAccess.getWorld() != null)
						{
							blockAccess.getWorld().notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock(), false);
						}

						if (template$blockinfo2.tileentityData != null)
						{
							final TileEntity tileentity1 = blockAccess.getTileEntity(blockpos1);

							if (tileentity1 != null)
							{
								tileentity1.markDirty();
							}
						}
					}
				}
			}

			if (!settings.getIgnoreEntities())
			{
				if (blockAccess.getWorld() != null)
				{
					TemplatePrimer.addEntitiesToWorld(template, blockAccess.getWorld(), pos, settings.getMirror(), settings.getRotation(), bb);
				}
			}
		}
	}

	public static void primeChunk(final Template template, final IBlockAccessExtended blockAccess, final World world, final ChunkPos chunk,
			final ChunkPrimer primer, final BlockPos pos,
			@Nullable final ITemplateProcessorExtended processor, final PlacementSettings settings)
	{
		final List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			final Block block = settings.getReplacedBlock();

			final int minX = chunk.chunkXPos * 16;
			final int minY = 0;
			final int minZ = chunk.chunkZPos * 16;

			final int maxX = minX + 15;
			final int maxY = world.getActualHeight();
			final int maxZ = minZ + 15;

			final StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

			for (final Template.BlockInfo template$blockinfo : blocks)
			{
				final BlockPos blockpos = Template.transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				final Template.BlockInfo template$blockinfo1 =
						processor != null ? processor.processBlock(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					final Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK)
							&& chunkBB.isVecInside(blockpos))
					{
						final IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						final IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						if (iblockstate1.getBlock() instanceof BlockMultiDummy || iblockstate1.getBlock() instanceof BlockMultiDummyHalf)
						{
							continue;
						}

						if (iblockstate1.getBlock() == BlocksAether.wildcard)
						{
							final TileEntityWildcard wildcard = new TileEntityWildcard();

							wildcard.setWorld(world);

							template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
							template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
							template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
							wildcard.readFromNBT(template$blockinfo1.tileentityData);
							wildcard.mirror(settings.getMirror());
							wildcard.rotate(settings.getRotation());

							wildcard.onSchematicGeneration(blockAccess, world.rand);

							continue;
						}

						try
						{
							primer.setBlockState(
									blockpos.getX() - minX, blockpos.getY(),
									blockpos.getZ() - minZ, iblockstate1);
						}
						catch (final ArrayIndexOutOfBoundsException ex)
						{
							System.out.println("Array out of bounds in TemplatePrimer.primeChunk");
							System.out.println(blockpos.getX() - minX);
							System.out.println("z " + (blockpos.getZ() - minZ));
						}
					}
				}
			}
		}
	}

	public static void populateChunk(final Template template, final World world, final IBlockAccessExtended blockAccess, final ChunkPos chunk,
			final BlockPos pos,
			@Nullable final ITemplateProcessorExtended processor,
			final PlacementSettings settings)
	{
		final List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			final Block block = settings.getReplacedBlock();

			final int minX = chunk.chunkXPos * 16;
			final int minY = 0;
			final int minZ = chunk.chunkZPos * 16;

			final int maxX = minX + 15;
			final int maxY = world.getActualHeight();
			final int maxZ = minZ + 15;

			final StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

			for (final Template.BlockInfo template$blockinfo : blocks)
			{
				final BlockPos blockpos = Template.transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				final Template.BlockInfo template$blockinfo1 =
						processor != null ? processor.processBlock(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					final Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK)
							&& chunkBB.isVecInside(blockpos))
					{
						final IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						final IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						if (iblockstate1.getBlock() instanceof BlockMultiDummy || iblockstate1.getBlock() instanceof BlockMultiDummyHalf)
						{
							continue;
						}

						if (iblockstate1.getBlock() == BlocksAether.wildcard)
						{
							final TileEntityWildcard wildcard = new TileEntityWildcard();

							if (blockAccess.getWorld() != null)
							{
								wildcard.setWorld(world);

								template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
								template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
								template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
								wildcard.readFromNBT(template$blockinfo1.tileentityData);
								wildcard.mirror(settings.getMirror());
								wildcard.rotate(settings.getRotation());

								wildcard.onSchematicGeneration(blockAccess, world.rand);
							}

							continue;
						}

						if (template$blockinfo1.tileentityData != null)
						{
							final TileEntity tileentity = blockAccess.getTileEntity(blockpos);

							if (tileentity != null)
							{
								if (tileentity instanceof IInventory)
								{
									((IInventory) tileentity).clear();
								}
							}
						}

						if (blockAccess.setBlockState(blockpos, iblockstate1))
						{
							iblockstate.getBlock().onBlockAdded(world, blockpos, iblockstate1);

							if (template$blockinfo1.tileentityData != null)
							{
								final TileEntity tileentity2 = blockAccess.getTileEntity(blockpos);

								if (tileentity2 != null)
								{
									template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
									template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
									template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
									tileentity2.readFromNBT(template$blockinfo1.tileentityData);
									tileentity2.mirror(settings.getMirror());
									tileentity2.rotate(settings.getRotation());

									tileentity2.markDirty();

									if (tileentity2 instanceof TileEntityMultiblockController)
									{
										final TileEntityMultiblockController controller = (TileEntityMultiblockController) tileentity2;

										controller.rebuild();
									}
								}
							}
						}
					}
				}
			}

			for (final Template.BlockInfo template$blockinfo2 : blocks)
			{
				if (block == null || block != template$blockinfo2.blockState.getBlock())
				{
					final BlockPos blockpos1 = Template.transformedBlockPos(settings, template$blockinfo2.pos).add(pos);

					if (chunkBB.isVecInside(blockpos1))
					{
						world.notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock(), false);

						if (template$blockinfo2.tileentityData != null)
						{
							final TileEntity tileentity1 = world.getTileEntity(blockpos1);

							if (tileentity1 != null)
							{
								tileentity1.markDirty();
							}
						}
					}
				}
			}

			if (!settings.getIgnoreEntities())
			{
				TemplatePrimer.addEntitiesToWorld(template, world, pos, settings.getMirror(), settings.getRotation(), chunkBB);
			}
		}
	}

	private static void addEntitiesToWorld(final Template template, final World worldIn, final BlockPos pos, final Mirror mirrorIn, final Rotation rotationIn,
			@Nullable final StructureBoundingBox aabb)
	{
		final List<Template.EntityInfo> entities = TemplatePrimer.getEntities(template);

		for (final Template.EntityInfo template$entityinfo : entities)
		{
			final BlockPos blockpos = transformedBlockPos(template$entityinfo.blockPos, mirrorIn, rotationIn).add(pos);

			if (aabb == null || aabb.isVecInside(blockpos))
			{
				final NBTTagCompound nbttagcompound = template$entityinfo.entityData;
				final Vec3d vec3d = transformedVec3d(template$entityinfo.pos, mirrorIn, rotationIn);
				final Vec3d vec3d1 = vec3d.addVector((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
				final NBTTagList nbttaglist = new NBTTagList();
				nbttaglist.appendTag(new NBTTagDouble(vec3d1.xCoord));
				nbttaglist.appendTag(new NBTTagDouble(vec3d1.yCoord));
				nbttaglist.appendTag(new NBTTagDouble(vec3d1.zCoord));
				nbttagcompound.setTag("Pos", nbttaglist);
				nbttagcompound.setUniqueId("UUID", UUID.randomUUID());
				Entity entity;

				try
				{
					entity = EntityList.createEntityFromNBT(nbttagcompound, worldIn);
				}
				catch (final Exception var15)
				{
					entity = null;
				}

				if (entity != null)
				{
					float f = entity.getMirroredYaw(mirrorIn);
					f = f + (entity.rotationYaw - entity.getRotatedYaw(rotationIn));
					entity.setLocationAndAngles(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, f, entity.rotationPitch);
					worldIn.spawnEntity(entity);
				}
			}
		}
	}

	public static BlockPos transformedBlockPos(final BlockPos pos, final Mirror mirrorIn, final Rotation rotationIn)
	{
		int i = pos.getX();
		final int j = pos.getY();
		int k = pos.getZ();
		boolean flag = true;

		switch (mirrorIn)
		{
			case LEFT_RIGHT:
				k = -k;
				break;
			case FRONT_BACK:
				i = -i;
				break;
			default:
				flag = false;
		}

		switch (rotationIn)
		{
			case COUNTERCLOCKWISE_90:
				return new BlockPos(k, j, -i);
			case CLOCKWISE_90:
				return new BlockPos(-k, j, i);
			case CLOCKWISE_180:
				return new BlockPos(-i, j, -k);
			default:
				return flag ? new BlockPos(i, j, k) : pos;
		}
	}

	private static Vec3d transformedVec3d(final Vec3d vec, final Mirror mirrorIn, final Rotation rotationIn)
	{
		double d0 = vec.xCoord;
		final double d1 = vec.yCoord;
		double d2 = vec.zCoord;
		boolean flag = true;

		switch (mirrorIn)
		{
			case LEFT_RIGHT:
				d2 = 1.0D - d2;
				break;
			case FRONT_BACK:
				d0 = 1.0D - d0;
				break;
			default:
				flag = false;
		}

		switch (rotationIn)
		{
			case COUNTERCLOCKWISE_90:
				return new Vec3d(d2, d1, 1.0D - d0);
			case CLOCKWISE_90:
				return new Vec3d(1.0D - d2, d1, d0);
			case CLOCKWISE_180:
				return new Vec3d(1.0D - d0, d1, 1.0D - d2);
			default:
				return flag ? new Vec3d(d0, d1, d2) : vec;
		}
	}

}
