package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.kirrid.EntityAIEatAetherGrass;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.init.LootTablesAether;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntityKirrid extends SheepEntity implements IEntityEyesComponentProvider
{
	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	protected EntityAIEatAetherGrass entityAIEatGrass;

	public EntityKirrid(EntityType<? extends SheepEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		this.entityAIEatGrass = new EntityAIEatAetherGrass(this, this.getEatChance());

		this.goalSelector.addGoal(2, new EntityAIRestrictRain(this));
		this.goalSelector.addGoal(3, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(3, new EntityAIHideFromRain(this, 1.3D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, false, Ingredient.fromItems(ItemsAether.valkyrie_wings)));
		this.goalSelector.addGoal(9, this.entityAIEatGrass);
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		this.eyes.update();
	}

	public int getEatChance()
	{
		return 1000;
	}


	@Override
	protected PathNavigator createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader reader)
	{
		return super.getBlockPathWeight(pos);
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(2);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(5);
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData data, CompoundNBT nbt)
	{
		data = super.onInitialSpawn(world, difficulty, reason, data, nbt);

		this.setFleeceColor(DyeColor.WHITE);

		return data;
	}

	/*@Override
	protected float getSoundPitch()
	{
		return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.55F;
	}*/

	@Override
	protected float getSoundPitch()
	{
		return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.55F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.kirrid.ambient"));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return new SoundEvent(AetherCore.getResource("mob.kirrid.hurt"));
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.kirrid.death"));
	}

	@Override
	public EntityKirrid createChild(AgeableEntity ageable)
	{
		return EntityTypesAether.KIRRID.create(this.world);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return stack.getItem() == ItemsAether.valkyrie_wings;
	}

	@Override
	@Nullable
	public ResourceLocation getLootTable()
	{
		if (this.getSheared())
		{
			return LootTablesAether.ENTITY_KIRRID_SHEARED;
		}

		return LootTablesAether.ENTITY_KIRRID;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune)
	{
		this.setSheared(true);

		int count = 1 + this.rand.nextInt(3);

		List<ItemStack> ret = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			ret.add(new ItemStack(BlocksAether.cloudwool_block));
		}

		this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);

		return ret;
	}

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}
}
