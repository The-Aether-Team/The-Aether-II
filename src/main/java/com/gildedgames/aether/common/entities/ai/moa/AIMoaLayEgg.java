package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.api.genes.GeneUtil;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockWovenSticks;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.util.MoaNest;
import com.gildedgames.aether.common.tile_entities.TileEntityMoaEgg;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class AIMoaLayEgg extends EntityAIBase
{

	public World world;
	
	public EntityMoa mother;

	public float moveSpeed;
	
	public boolean hasLayedEgg;

	public AIMoaLayEgg(EntityMoa mother, float moveSpeed)
	{
		this.world = mother.worldObj;
		this.mother = mother;

		this.moveSpeed = moveSpeed;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.hasLayedEgg)
		{
			return false;
		}
		
		if (this.mother == null || this.world == null)
		{
			return false;
		}
		
		MoaNest nest = this.mother.getFamilyNest();
		
		if (nest == null)
		{
			return false;
		}
		else if (!nest.hasInitialized)
		{
			return false;
		}
		
		if (this.world.getBlockState(nest.pos.add(0, 1, 0)).getBlock() == BlocksAether.moa_egg)
		{
			return false;
		}
		
		if (!(this.world.getBlockState(nest.pos).getBlock() instanceof BlockWovenSticks))
		{
			return false;
		}
		
		return true;
	}

	@Override
	public void updateTask()
	{
		super.updateTask();
		
		MoaNest nest = this.mother.getFamilyNest();
		
		this.mother.getNavigator().tryMoveToXYZ(nest.pos.getX(), nest.pos.getY() + 1, nest.pos.getZ(), this.moveSpeed);
		
		if (this.mother.getNavigator().getPath() != null && this.mother.getNavigator().getPath().isFinished())
		{
			this.world.setBlockState(nest.pos.add(0, 1, 0), BlocksAether.moa_egg.getDefaultState());
			
			TileEntityMoaEgg egg = (TileEntityMoaEgg)this.world.getTileEntity(nest.pos.add(0, 1, 0));
			
			if (egg != null)
			{
				MoaGenePool teGenes = egg.getGenePool();
				MoaGenePool entityGenes = this.mother.getGenePool();

				teGenes.transformFromParents(GeneUtil.getRandomSeed(this.mother.getEntityWorld()), entityGenes.getStorage().getFatherSeed(), entityGenes.getStorage().getMotherSeed());
			}

			this.mother.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.mother.getRNG().nextFloat() - this.mother.getRNG().nextFloat()) * 0.2F + 1.0F);
			
			this.hasLayedEgg = true;
		}
	}
	
}
