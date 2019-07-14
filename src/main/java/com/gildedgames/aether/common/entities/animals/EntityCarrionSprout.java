package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.init.LootTablesAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityCarrionSprout extends EntityAetherAnimal
{
	private static final DataParameter<Integer> SIZE = new DataParameter<>(13, DataSerializers.VARINT);

	@OnlyIn(Dist.CLIENT)
	public float sinage, prevSinage;

	private int maxSproutSize;

	public EntityCarrionSprout(EntityType<? extends AnimalEntity> type, World world)
	{
		super(type, world);
	}

	@Override
	public boolean canBeLeashedTo(PlayerEntity player)
	{
		return false;
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 8));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.setMaxSproutSize(8);

		final int min = 3;

		this.setSproutSize(min + this.rand.nextInt(this.getMaxSproutSize() - min));

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(3);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(6);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(6);
	}

	@Override
	public void registerData()
	{
		super.registerData();

		this.dataManager.register(SIZE, 0);
	}

	@Override
	public void writeAdditional(CompoundNBT nbt)
	{
		super.writeAdditional(nbt);

		nbt.putInt("size", this.getSproutSize());
		nbt.putInt("maxSize", this.getMaxSproutSize());
	}

	@Override
	public void readAdditional(CompoundNBT nbt)
	{
		super.readAdditional(nbt);

		this.setSproutSize(nbt.getInt("size"));
		this.setMaxSproutSize(nbt.getInt("maxSize"));
	}

	@Override
	public void tick()
	{
		super.tick();

		if (this.world.isRemote())
		{
			this.clientUpdate();
		}
		else
		{
			if (!this.canStayHere(new BlockPos(this)))
			{
				this.setHealth(0);
			}
		}
	}

	private boolean canStayHere(final BlockPos pos)
	{
		if (!Block.isOpaque(this.world.getBlockState(pos).getShape(this.world, pos)))
		{
			return false;
		}

		final BlockState rootBlock = this.world.getBlockState(pos.down());

		return rootBlock.getBlock() == BlocksAether.aether_grass
				|| rootBlock.getBlock() == BlocksAether.aether_dirt
				|| rootBlock.getBlock() == BlocksAether.highlands_snow_layer
				|| rootBlock.getBlock() == BlocksAether.highlands_snow;
	}

	@Override
	public void livingTick()
	{
		this.setMotion(0.0D, this.getMotion().getY(), 0.0D);

		if (this.ticksExisted == 0)
		{
			this.setRotation(this.world.rand.nextFloat() * 360F, 0.0F);
			this.renderYawOffset = this.rotationYaw;
		}

		super.livingTick();

		if (!this.isFullyGrown() && this.ticksExisted % 800 == 0)
		{
			this.setSproutSize(this.getSproutSize() + 2);
		}
	}

	public boolean isFullyGrown()
	{
		return (this.getSproutSize() >= this.getMaxSproutSize());
	}

	public int getMaxSproutSize()
	{
		return this.maxSproutSize;
	}

	public void setMaxSproutSize(final int x)
	{
		this.maxSproutSize = x;
	}

	public int getSproutSize()
	{
		return this.dataManager.get(SIZE);
	}

	public void setSproutSize(final int newSize)
	{
		this.dataManager.set(SIZE, newSize);

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8f + (newSize * 1.5f));
	}

	@Override
	public ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_CARRION_SPROUT;
	}

	@OnlyIn(Dist.CLIENT)
	public void clientUpdate()
	{
		this.prevSinage = this.sinage;

		if (this.hurtTime > 0)
		{
			this.sinage += 0.9F;
		}
		else
		{
			this.sinage += 0.15F;
		}

		if (this.sinage > 3.141593F * 2F)
		{
			this.sinage -= (3.141593F * 2F);
			this.prevSinage -= (3.141593F * 2F);
		}
	}

	@Override
	public void jump()
	{
	} // remove jump

	@Override
	public void knockBack(final Entity entityIn, final float distance, final double motionX, final double motionY)
	{
	} // remove player damage knock-back

	@Override
	public AgeableEntity createChild(final AgeableEntity ageable)
	{
		return null;
	}
}
