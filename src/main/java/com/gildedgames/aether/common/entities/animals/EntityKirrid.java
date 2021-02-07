package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.kirrid.EntityAIEatAetherGrass;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartEntity;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartShearable;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.vecmath.Point3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityKirrid extends EntitySheep implements IEntityMultiPart, IEntityEyesComponentProvider, IDefenseLevelsHolder
{
	protected Map<String, Float> defenseMap = Maps.newHashMap();
	{{
		this.defenseMap.put("Very Weak", 4.0F);
		this.defenseMap.put("Weak", 2.0F);
		this.defenseMap.put("Average", 0.0F);
		this.defenseMap.put("Strong", -2.0F);
		this.defenseMap.put("Very Strong", -4.0F);
	}}

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemsAether.valkyrie_wings);

	private final Point3d[] old;

	private final AetherMultiPartEntity[] parts;

	private final AetherMultiPartEntity head = new AetherMultiPartShearable(this, "head", 0.7F, 0.8F);

	private final AetherMultiPartEntity back = new AetherMultiPartShearable(this, "back", 0.8F, 1.5F);

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	protected EntityAIEatAetherGrass entityAIEatGrass;
	private int kirridTimer;

	public EntityKirrid(World world)
	{
		super(world);


		this.setSize(1.0F, 1.5F);

		this.spawnableBlock = BlocksAether.aether_grass;
		this.parts = new AetherMultiPartEntity[] { this.head, this.back };
		this.old = new Point3d[this.parts.length];

		for (int i = 0; i < this.old.length; i++)
		{
			this.old[i] = new Point3d();
		}
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.entityAIEatGrass = new EntityAIEatAetherGrass(this, this.getEatChance());

		this.tasks.addTask(2, new EntityAIRestrictRain(this));
		this.tasks.addTask(3, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(3, new EntityAIHideFromRain(this, 1.3D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.tasks.addTask(9, this.entityAIEatGrass);
	}

	protected void updateAITasks()
	{
		this.kirridTimer = this.entityAIEatGrass.getTimer();
		super.updateAITasks();
	}

	@Override
	public World getWorld()
	{
		return this.getEntityWorld();
	}

	@Override
	public void onLivingUpdate()
	{
		if (this.world.isRemote)
		{
			this.kirridTimer = Math.max(0, this.kirridTimer - 1);
		}

		if (this.isChild())
		{
			this.head.updateSize(0.35F, 0.4F);
			this.back.updateSize(0.4F, 0.75F);
		}

		super.onLivingUpdate();

		this.eyes.update();

		for (int i = 0; i < this.parts.length; i++)
		{
			this.old[i].set(this.parts[i].posX, this.parts[i].posY, this.parts[i].posZ);
		}

		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI);
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI);

		float headOffset = !this.isChild() ? .9f : .15f;
		float headHeight = !this.isChild() ? .75f : .5f;
		float backOffset = !this.isChild() ? .8f : .4f;

		this.head.setLocationAndAngles(this.posX - f2 * headOffset, this.posY + headHeight, this.posZ - f1 * headOffset, 0F, 0F);
		this.head.onUpdate();
		this.back.setLocationAndAngles(this.posX + f2 * backOffset, this.posY, this.posZ + f1 * backOffset, 0F, 0F);
		this.back.onUpdate();

		for (int i = 0; i < this.parts.length; i++)
		{
			this.parts[i].prevPosX = this.old[i].getX();
			this.parts[i].prevPosY = this.old[i].getY();
			this.parts[i].prevPosZ = this.old[i].getZ();
		}
	}

	public int getEatChance()
	{
		return 1000;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage)
	{
		if (this.hurtResistantTime <= 10)
		{
			return this.attackEntityFrom(source, damage * 1.1f);
		}
		else
		{
			return false;
		}
	}

	@Nullable
	@Override
	public MultiPartEntityPart[] getParts()
	{
		return this.parts;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return super.getBlockPathWeight(pos);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(-2.0f);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(2.0f);
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		this.setFleeceColor(EnumDyeColor.WHITE);

		return livingdata;
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
		return SoundsAether.kirrid_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundsAether.kirrid_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.kirrid_death;
	}

	@Override
	public EntityKirrid createChild(EntityAgeable ageable)
	{
		return new EntityKirrid(this.world);
	}

	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		if (this.getSheared())
		{
			return LootTablesAether.ENTITY_KIRRID_SHEARED;
		}

		return LootTablesAether.ENTITY_KIRRID;
	}

	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 10)
		{
			this.kirridTimer = 40;
		}
		else
		{
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
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

	@SideOnly(Side.CLIENT)
	public float getHeadRotationPointY(float p_70894_1_)
	{
		if (this.kirridTimer <= 0)
		{
			return 0.0F;
		}
		else if (this.kirridTimer >= 4 && this.kirridTimer <= 36)
		{
			return 1.0F;
		}
		else
		{
			return this.kirridTimer < 4 ? ((float)this.kirridTimer - p_70894_1_) / 4.0F : -((float)(this.kirridTimer - 40) - p_70894_1_) / 4.0F;
		}
	}

	@SideOnly(Side.CLIENT)
	public float getHeadRotationAngleX(float p_70890_1_)
	{
		if (this.kirridTimer > 4 && this.kirridTimer <= 36)
		{
			float f = ((float)(this.kirridTimer - 4) - p_70890_1_) / 32.0F;
			return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
		}
		else
		{
			return this.kirridTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
		}
	}

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}
}
