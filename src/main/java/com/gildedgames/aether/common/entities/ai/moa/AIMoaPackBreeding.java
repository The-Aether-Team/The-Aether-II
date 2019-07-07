package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockWovenSticks;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.tiles.TileEntityMoaEgg;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AIMoaPackBreeding extends EntityAIBase
{

	public final World world;

	public final EntityMoa moa;

	public EntityLiving packLeader;

	public final float moveSpeed;

	public BlockPos eggPos;

	public int timeUntilLay;

	public AIMoaPackBreeding(final EntityMoa moa, final float moveSpeed)
	{
		this.world = moa.world;
		this.moveSpeed = moveSpeed;

		this.moa = moa;
	}

	public void resetTimer()
	{
		this.timeUntilLay = 30 + this.moa.getRNG().nextInt(60);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.moa == null || this.world == null)
		{
			return false;
		}

		if (this.moa.isChild())
		{
			return false;
		}

		if (this.moa.getFamilyNest() == null)
		{
			return false;
		}
		else if (!this.moa.getFamilyNest().hasInitialized)
		{
			return false;
		}

		this.eggPos = this.moa.getFamilyNest().pos.add(0, 1, 0);

		if (this.world.getBlockState(this.eggPos).getBlock() == BlocksAether.moa_egg)
		{
			this.resetTimer();

			return false;
		}

		if (!(this.world.getBlockState(this.moa.getFamilyNest().pos).getBlock() instanceof BlockWovenSticks))
		{
			return false;
		}

		if (this.timeUntilLay > 0)
		{
			if (this.moa.ticksExisted % 20 == 0)
			{
				this.timeUntilLay--;
			}

			return false;
		}

		return true;
	}

	@Override
	public void updateTask()
	{
		super.updateTask();

		final Path path = this.moa.getNavigator().getPath();

		final boolean isNearEgg = this.moa.getDistanceSq(this.eggPos.getX() - 1, this.eggPos.getY(), this.eggPos.getZ() - 1) <= 4.0D;

		if ((path == null || path.isFinished()) && !isNearEgg)
		{
			this.moa.getNavigator().tryMoveToXYZ(this.eggPos.getX() - 1, this.eggPos.getY(), this.eggPos.getZ() - 1, this.moveSpeed);
		}
		else if (isNearEgg && this.moa.getNavigator().getPath() != null && this.moa.getNavigator().getPath().isFinished())
		{
			this.world.setBlockState(this.eggPos, BlocksAether.moa_egg.getDefaultState());

			final TileEntityMoaEgg egg = (TileEntityMoaEgg) this.world.getTileEntity(this.eggPos);

			if (egg != null)
			{
				final MoaGenePool teGenes = egg.getGenePool();
				final MoaGenePool entityGenes = this.moa.getGenePool();

				teGenes.transformFromParents(entityGenes.getStorage().getSeed(), entityGenes.getStorage().getFatherSeed(),
						entityGenes.getStorage().getMotherSeed());

				egg.setFamilyNest(this.moa.getFamilyNest());

				if (!this.moa.getAnimalPack().hasLeader())
				{
					egg.setGender(AnimalGender.MALE);
				}
			}

			this.moa.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F,
					(this.moa.getRNG().nextFloat() - this.moa.getRNG().nextFloat()) * 0.2F + 1.0F);

			this.resetTimer();
		}
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.shouldExecute();
	}

}
