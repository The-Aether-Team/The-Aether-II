package com.gildedgames.aether.common.util.structure;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.tiles.TileEntityWildcard;
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
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TemplatePrimer
{

	public static List<Template.BlockInfo> getBlocks(Template template)
	{
		return ObfuscationReflectionHelper.getPrivateValue(Template.class, template, 0);
	}

	public static List<Template.BlockInfo> getBlocks(BlockPos pos, PlacementSettings settings, Template template)
	{
		return TemplatePrimer.getBlocks(TemplatePrimer.getBlocks(template), pos, settings, template);
	}

	public static List<Template.BlockInfo> getBlocks(List<Template.BlockInfo> blockInfo, BlockPos pos, PlacementSettings settings, Template template)
	{
		List<Template.BlockInfo> newInfo = Lists.newArrayList();

		for (Template.BlockInfo info : blockInfo)
		{
			BlockPos blockpos = Template.transformedBlockPos(settings, info.pos).add(pos);

			newInfo.add(new Template.BlockInfo(blockpos, info.blockState, info.tileentityData));
		}

		return newInfo;
	}

	public static List<BlockPos> getBlockPos(BlockPos pos, PlacementSettings settings, Template template)
	{
		return TemplatePrimer.getBlockPos(TemplatePrimer.getBlocks(template), pos, settings, template);
	}

	public static List<BlockPos> getBlockPos(List<Template.BlockInfo> blockInfo, BlockPos pos, PlacementSettings settings, Template template)
	{
		List<BlockPos> blockPos = Lists.newArrayList();

		for (Template.BlockInfo info : blockInfo)
		{
			BlockPos blockpos = Template.transformedBlockPos(settings, info.pos).add(pos);

			blockPos.add(blockpos);
		}

		return blockPos;
	}

	private static List<Template.EntityInfo> getEntities(Template template)
	{
		return ObfuscationReflectionHelper.getPrivateValue(Template.class, template, 1);
	}

	public static void populateAll(Template template, World world, BlockPos pos, @Nullable ITemplateProcessor processor, PlacementSettings settings)
	{
		List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			Block block = settings.getReplacedBlock();
			StructureBoundingBox bb = settings.getBoundingBox();

			for (Template.BlockInfo template$blockinfo : blocks)
			{
				BlockPos blockpos = Template.transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				Template.BlockInfo template$blockinfo1 = processor != null ? processor.processBlock(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && (bb == null || bb.isVecInside(blockpos)))
					{
						IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						if (iblockstate1.getBlock() == BlocksAether.wildcard)
						{
							TileEntityWildcard wildcard = new TileEntityWildcard();

							wildcard.setWorldObj(world);

							template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
							template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
							template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
							wildcard.readFromNBT(template$blockinfo1.tileentityData);
							wildcard.mirror(settings.getMirror());
							wildcard.rotate(settings.getRotation());

							wildcard.onSchematicGeneration();

							continue;
						}

						if (template$blockinfo1.tileentityData != null)
						{
							TileEntity tileentity = world.getTileEntity(blockpos);

							if (tileentity != null)
							{
								if (tileentity instanceof IInventory)
								{
									((IInventory)tileentity).clear();
								}
							}
						}

						if (world.setBlockState(blockpos, iblockstate1, 2) && template$blockinfo1.tileentityData != null)
						{
							TileEntity tileentity2 = world.getTileEntity(blockpos);

							if (tileentity2 != null)
							{
								template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
								template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
								template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
								tileentity2.readFromNBT(template$blockinfo1.tileentityData);
								tileentity2.mirror(settings.getMirror());
								tileentity2.rotate(settings.getRotation());

								tileentity2.markDirty();
							}
						}
					}
				}
			}

			for (Template.BlockInfo template$blockinfo2 : blocks)
			{
				if (block == null || block != template$blockinfo2.blockState.getBlock())
				{
					BlockPos blockpos1 = Template.transformedBlockPos(settings, template$blockinfo2.pos).add(pos);

					if (bb == null || bb.isVecInside(blockpos1))
					{
						world.notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock());

						if (template$blockinfo2.tileentityData != null)
						{
							TileEntity tileentity1 = world.getTileEntity(blockpos1);

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
				TemplatePrimer.addEntitiesToWorld(template, world, pos, settings.getMirror(), settings.getRotation(), bb);
			}
		}
	}

	public static void primeChunk(Template template, World world, ChunkPos chunk, ChunkPrimer primer, BlockPos pos, @Nullable ITemplateProcessor processor, PlacementSettings settings)
	{
		List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			Block block = settings.getReplacedBlock();

			int minX = chunk.chunkXPos * 16;
			int minY = 0;
			int minZ = chunk.chunkZPos * 16;

			int maxX = minX + 15;
			int maxY = world.getActualHeight();
			int maxZ = minZ + 15;

			StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

			for (Template.BlockInfo template$blockinfo : blocks)
			{
				BlockPos blockpos = Template.transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				Template.BlockInfo template$blockinfo1 = processor != null ? processor.processBlock(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && chunkBB.isVecInside(blockpos))
					{
						IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						try
						{
							primer.setBlockState(blockpos.getX() - minX, blockpos.getY(), blockpos.getZ() - minZ, iblockstate1.getBlock() == Blocks.AIR ? Blocks.STRUCTURE_VOID.getDefaultState() : iblockstate1);
						}
						catch (ArrayIndexOutOfBoundsException ex)
						{
							System.out.println(blockpos.getX() - minX);
							System.out.println("z " + (blockpos.getZ() - minZ));
						}
					}
				}
			}
		}
	}

	public static void populateChunk(Template template, World world, ChunkPos chunk, BlockPos pos, @Nullable ITemplateProcessor processor, PlacementSettings settings, int placementFlags)
	{
		List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			Block block = settings.getReplacedBlock();

			int minX = chunk.chunkXPos * 16;
			int minY = 0;
			int minZ = chunk.chunkZPos * 16;

			int maxX = minX + 15;
			int maxY = world.getActualHeight();
			int maxZ = minZ + 15;

			StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

			for (Template.BlockInfo template$blockinfo : blocks)
			{
				BlockPos blockpos = Template.transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				Template.BlockInfo template$blockinfo1 = processor != null ? processor.processBlock(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && chunkBB.isVecInside(blockpos))
					{
						IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						if (iblockstate1.getBlock() == BlocksAether.wildcard)
						{
							TileEntityWildcard wildcard = new TileEntityWildcard();

							wildcard.setWorldObj(world);

							template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
							template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
							template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
							wildcard.readFromNBT(template$blockinfo1.tileentityData);
							wildcard.mirror(settings.getMirror());
							wildcard.rotate(settings.getRotation());

							wildcard.onSchematicGeneration();

							continue;
						}

						if (template$blockinfo1.tileentityData != null)
						{
							TileEntity tileentity = world.getTileEntity(blockpos);

							if (tileentity != null)
							{
								if (tileentity instanceof IInventory)
								{
									((IInventory)tileentity).clear();
								}
							}
						}

						if (template$blockinfo1.tileentityData != null)
						{
							TileEntity tileentity2 = world.getTileEntity(blockpos);

							if (tileentity2 != null)
							{
								template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
								template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
								template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
								tileentity2.readFromNBT(template$blockinfo1.tileentityData);
								tileentity2.mirror(settings.getMirror());
								tileentity2.rotate(settings.getRotation());

								tileentity2.markDirty();
							}
						}
					}
				}
			}

			for (Template.BlockInfo template$blockinfo2 : blocks)
			{
				if (block == null || block != template$blockinfo2.blockState.getBlock())
				{
					BlockPos blockpos1 = Template.transformedBlockPos(settings, template$blockinfo2.pos).add(pos);

					if (chunkBB.isVecInside(blockpos1))
					{
						world.notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock());

						if (template$blockinfo2.tileentityData != null)
						{
							TileEntity tileentity1 = world.getTileEntity(blockpos1);

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

	private static void addEntitiesToWorld(Template template, World worldIn, BlockPos pos, Mirror mirrorIn, Rotation rotationIn, @Nullable StructureBoundingBox aabb)
	{
		List<Template.EntityInfo> entities = TemplatePrimer.getEntities(template);

		for (Template.EntityInfo template$entityinfo : entities)
		{
			BlockPos blockpos = transformedBlockPos(template$entityinfo.blockPos, mirrorIn, rotationIn).add(pos);

			if (aabb == null || aabb.isVecInside(blockpos))
			{
				NBTTagCompound nbttagcompound = template$entityinfo.entityData;
				Vec3d vec3d = transformedVec3d(template$entityinfo.pos, mirrorIn, rotationIn);
				Vec3d vec3d1 = vec3d.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
				NBTTagList nbttaglist = new NBTTagList();
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
				catch (Exception var15)
				{
					entity = null;
				}

				if (entity != null)
				{
					float f = entity.getMirroredYaw(mirrorIn);
					f = f + (entity.rotationYaw - entity.getRotatedYaw(rotationIn));
					entity.setLocationAndAngles(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, f, entity.rotationPitch);
					worldIn.spawnEntityInWorld(entity);
				}
			}
		}
	}

	public static BlockPos transformedBlockPos(BlockPos pos, Mirror mirrorIn, Rotation rotationIn)
	{
		int i = pos.getX();
		int j = pos.getY();
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

	private static Vec3d transformedVec3d(Vec3d vec, Mirror mirrorIn, Rotation rotationIn)
	{
		double d0 = vec.xCoord;
		double d1 = vec.yCoord;
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
