package com.gildedgames.aether.common.entities.living.enemies;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFloat;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFollowAttackTarget;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopWander;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.entities.ai.swet.AILeech;
import com.gildedgames.aether.common.entities.util.EntityExtendedMob;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.EntityUtil;
import com.google.common.base.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntitySwet extends EntityExtendedMob
{

	public enum Type
	{
		BLUE("blue"), GOLDEN("golden"), DARK("dark"), LIGHT("light");

		public final String name;

		public final ResourceLocation texture;

		Type(String name)
		{
			this.name = name;
			this.texture = AetherCore.getResource("textures/entities/swet/" + this.name + "_swet.png");
		}

		public static Type fromOrdinal(int ordinal)
		{
			Type[] gummy = values();

			return gummy[ordinal > gummy.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySwet.class, DataSerializers.VARINT);

	public float squishAmount;

	public float squishFactor;

	public float prevSquishFactor;

	private boolean wasOnGround;

	public EntitySwet(World worldIn)
	{
		super(worldIn);

		HoppingMoveHelper hoppingMoveHelper = new HoppingMoveHelper(this, SoundEvents.ENTITY_SLIME_JUMP);

		this.moveHelper = hoppingMoveHelper;

		this.tasks.addTask(0, new AILeech(this, hoppingMoveHelper, 4.0D));
		this.tasks.addTask(1, new AIHopWander(this, hoppingMoveHelper));
		this.tasks.addTask(2, new AIHopFloat(this, hoppingMoveHelper));
		this.tasks.addTask(3, new AIHopFollowAttackTarget(this, hoppingMoveHelper, 1.0D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.0F, 1.0F);

		this.setType(Type.values()[this.worldObj.rand.nextInt(Type.values().length)]);
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntitySwet.TYPE, 0);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
	}

	@Override
	protected void handleClientAttack()
	{
		this.squishAmount = 3F;

		Entity target = this.worldObj.getNearestPlayerNotCreative(this, 10D);

		if (target == null)
		{
			return;
		}

		this.faceEntity(target, 10.0F, 10.0F);

		EntityUtil.spawnParticleLineBetween(this, target, EnumParticleTypes.SLIME, 2D);
		EntityUtil.spawnParticleLineBetween(this, target, EnumParticleTypes.SPELL_MOB_AMBIENT, 7.4D);
	}

	@Override
	protected void jump()
	{
		if (this.getMoveHelper().getSpeed() <= 0)
		{
			return;
		}

		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	public int getVerticalFaceSpeed()
	{
		return 0;
	}

	@Override
	public void onUpdate()
	{
		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;

		super.onUpdate();

		if (this.onGround && !this.wasOnGround)
		{
			this.squishAmount = -0.5F;
		}
        else if (!this.onGround && this.wasOnGround)
		{
			this.squishAmount = 1.0F;
		}

		this.wasOnGround = this.onGround;
		this.alterSquishAmount();
	}

	protected void alterSquishAmount()
	{
		this.squishAmount *= 0.6F;
	}

	public Type getType()
	{
		return Type.fromOrdinal(this.dataManager.get(EntitySwet.TYPE));
	}

	public void setType(Type type)
	{
		this.dataManager.set(EntitySwet.TYPE, type.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		this.setType(Type.fromOrdinal(tag.getInteger("type")));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);

		tag.setInteger("type", this.getType().ordinal());

		return tag;
	}

	@Override
	protected boolean isValidLightLevel()
	{
		return true;
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		ItemStack stack = new ItemStack(ItemsAether.swet_jelly, this.rand.nextInt(4) + 1, this.getType().ordinal());
		this.entityDropItem(stack, 0f);

		ItemStack sugar = new ItemStack(Items.SUGAR, this.rand.nextInt(4), 0);
		this.entityDropItem(sugar, 0f);
	}

}
