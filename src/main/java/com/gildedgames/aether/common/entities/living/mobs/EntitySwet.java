package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFloat;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFollowAttackTarget;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopWander;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.entities.ai.swet.AILatchOn;
import com.gildedgames.aether.common.entities.util.EntityExtendedMob;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketDetachSwet;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.ArrayList;

public class EntitySwet extends EntityExtendedMob
{

	public static final int FOOD_SATURATION_REQUIRED = 5;

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySwet.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> FOOD_SATURATION = EntityDataManager.createKey(EntitySwet.class, DataSerializers.VARINT);

	public float squishAmount;

	public float squishFactor;

	public float prevSquishFactor;

	private boolean wasOnGround;

	private float squishPoolSize, actualSaturation;

	private int timeSinceSucking, digestTime, timeStarved, transitionTime;

	public EntitySwet(final World worldIn)
	{
		super(worldIn);

		final HoppingMoveHelper hoppingMoveHelper = new HoppingMoveHelper(this,
				() -> EntitySwet.this.getFoodSaturation() == 0 ? SoundEvents.ENTITY_SLIME_JUMP : SoundEvents.ENTITY_SLIME_JUMP,
				() -> EntitySwet.this.getRNG().nextInt(EntitySwet.this.getFoodSaturation() == 3 ? 10 : 60) + (EntitySwet.this.getFoodSaturation() == 3 ? 40 : 50));

		this.moveHelper = hoppingMoveHelper;

		this.tasks.addTask(0, new AILatchOn(this, hoppingMoveHelper));
		this.tasks.addTask(1, new AIHopWander(this, hoppingMoveHelper));
		this.tasks.addTask(2, new AIHopFloat(this, hoppingMoveHelper));
		this.tasks.addTask(3, new AIHopFollowAttackTarget(this, hoppingMoveHelper, 1.0D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 10, true, false,
				e -> EntitySwet.canLatch(EntitySwet.this, e)));

		this.setSize(1.0F, 1.0F);

		this.setType(Type.values()[this.world.rand.nextInt(Type.values().length)]);

		this.experienceValue = 3;

		this.setFoodSaturation(3);
		actualSaturation = 3;
	}

	public static boolean canLatch(final EntitySwet swet, final EntityPlayer player)
	{
		return !player.isInWater() && swet.getFoodSaturation() == 3 && PlayerAether.getPlayer(player).getSwetTracker()
				.canLatchOn();
	}

	public int getFoodSaturation()
	{
		return this.dataManager.get(EntitySwet.FOOD_SATURATION);
	}

	public void setFoodSaturation(final int foodSaturation)
	{
		this.dataManager.set(EntitySwet.FOOD_SATURATION, foodSaturation);
	}

	public void processSucking(final EntityPlayer player)
	{
		PotionEffect slowness = new PotionEffect(Potion.getPotionById(2), 100, timeSinceSucking / 80, true, false);

		player.addPotionEffect(slowness);

		this.timeSinceSucking++;

		if (this.timeSinceSucking % 40 == 0)
		{
			if (!this.world.isRemote)
			{
				player.getFoodStats().setFoodLevel((int) (player.getFoodStats().getFoodLevel() * 0.95F));

				Collection<PotionEffect> effects = player.getActivePotionEffects();

				if (!effects.isEmpty())
				{
					ArrayList<PotionEffect> negEffects = new ArrayList();

					for (PotionEffect p : effects)
					{
						if (p.getPotion().isBadEffect())
						{
							negEffects.add(p);
						}
					}

					/*
						Due to how potions work; give the swet the same potion, and add a new one to the player with 3/4th of time and amplifier to make it be removed with time
					 */
					for (PotionEffect p : negEffects)
					{
						if (!p.getPotion().equals(Potion.getPotionById(2)))
						{
							addPotionEffect(p);
							player.removePotionEffect(p.getPotion());

							if (p.getAmplifier() - 1 > 0 && p.getDuration() * .75 > 60)
							{
								player.addPotionEffect(new PotionEffect(p.getPotion(), (int) (p.getDuration() * .75), p.getAmplifier() - 1));
							}
						}
					}
				}
			}

			player.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.2F + 1.0F);
		}

		if (timeSinceSucking >= 300 || player.getFoodStats().getFoodLevel() <= 0)
		{
			if (!this.world.isRemote)
			{
				this.setFoodSaturation(4);
				PlayerAether.getPlayer(player).getSwetTracker().detachSwet(this);
				NetworkingAether.sendPacketToPlayer(new PacketDetachSwet(this.getType()), (EntityPlayerMP) player);
			}
		}
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntitySwet.TYPE, 0);
		this.dataManager.register(EntitySwet.FOOD_SATURATION, 1);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
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

	@Override
	public int getVerticalFaceSpeed()
	{
		return 0;
	}

	@Override
	public void onUpdate()
	{
		if (this.isInWater())
		{
			timeStarved = 0;
			this.setFoodSaturation(0);
		}

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

		if (onGround)
		{
			squishPoolSize += 0.2f;

			if (squishPoolSize > 1.54f)
			{
				squishPoolSize = 1.54f;
			}
		}
		else
		{
			squishPoolSize -= 0.2f;

			if (squishPoolSize < 1)
			{
				squishPoolSize = 1;
			}
		}

		if (actualSaturation < getFoodSaturation() && transitionTime > 20)
		{
			actualSaturation += 0.02f;

			if (actualSaturation > getFoodSaturation())
			{
				actualSaturation = getFoodSaturation();

				if (getFoodSaturation() < 3)
				{
					transitionTime = 0;
				}
				else
				{
					((HoppingMoveHelper) getMoveHelper()).setActive(true);
				}
			}
		}
		else if (actualSaturation > getFoodSaturation())
		{
			actualSaturation -= 0.2f;

			if (actualSaturation < getFoodSaturation())
			{
				actualSaturation = getFoodSaturation();
			}

			if (getActualSaturation() != 0)
			{
				float[] redColors = new float[] { 0.486f, 0.45f, 0.411f};
				float[] greenColors = new float[] { 0.439f, 0.686f, 0.654f };
				float[] blueColors = new float[] { 0.67f, 0.819f, 0.525f};

				int type = getType().name.equals("purple") ? 0 : (getType().name.equals("blue") ? 1 : 2);

				float f1 = getActualSaturation() / 5f;

				for (int i = 0; i < 5 + rand.nextInt(5); i++)
				{
					world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX - f1 + rand.nextFloat() * f1 * 2f,
							this.posY - f1 + rand.nextFloat() * f1 * 2f, this.posZ - f1 + rand.nextFloat()  * f1 * 2f, redColors[type], greenColors[type], blueColors[type]);
				}
			}
		}
		else
		{
			if (getFoodSaturation() < 3 && timeStarved >= 2400)
			{
				((HoppingMoveHelper) getMoveHelper()).setActive(false);
				setFoodSaturation(getFoodSaturation() + 1);
			}

			transitionTime++;
		}

		if (getFoodSaturation() < 3)
		{
			timeStarved++;
			setAttackTarget(null);
		}
		else if (getFoodSaturation() != 3)
		{
			timeStarved = 0;
		}

		if (getFoodSaturation() == 4)
		{
			addPotionEffect(new PotionEffect(Potion.getPotionById(2), 10, 2, true, false));

			digestTime++;

			if (digestTime > 1200)
			{
				setFoodSaturation(3);
				digestTime = 0;
			}
		}

		this.wasOnGround = this.onGround;
		this.alterSquishAmount();
	}

	public void setSucking(int timeSucking)
	{
		this.timeSinceSucking = timeSucking;
	}

	public int getTimeSinceSucking() { return timeSinceSucking; }

	protected void alterSquishAmount()
	{
		this.squishAmount *= 0.6F;
	}

	public Type getType()
	{
		return Type.fromOrdinal(this.dataManager.get(EntitySwet.TYPE));
	}

	public void setType(final Type type)
	{
		this.dataManager.set(EntitySwet.TYPE, type.ordinal());
	}

	@Override
	public void readFromNBT(final NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		this.setType(Type.fromOrdinal(tag.getInteger("type")));
		this.timeSinceSucking = tag.getInteger("timeSinceSucking");
		this.setFoodSaturation(tag.getInteger("foodSaturation"));
		this.actualSaturation = getFoodSaturation();
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound tag)
	{
		super.writeToNBT(tag);

		tag.setInteger("type", this.getType().ordinal());
		tag.setInteger("timeSinceSucking", this.timeSinceSucking);
		tag.setInteger("foodSaturation", this.getFoodSaturation());

		return tag;
	}

	@Override
	protected boolean isValidLightLevel()
	{
		return true;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		switch (this.getType())
		{
			case BLUE:
				return LootTablesAether.ENTITY_SWET_BLUE;
			case GREEN:
				return LootTablesAether.ENTITY_SWET_GREEN;
			case PURPLE:
				return LootTablesAether.ENTITY_SWET_PURPLE;
			default:
				return LootTablesAether.ENTITY_SWET;
		}
	}

	@SideOnly(Side.CLIENT)
	public float getSquishPool()
	{
		return squishPoolSize;
	}

	@SideOnly(Side.CLIENT)
	public float getActualSaturation()
	{
		return actualSaturation;
	}

	public enum Type
	{
		BLUE("blue"), GREEN("green"), PURPLE("purple");

		public final String name;

		public final ResourceLocation texture_head, texture_jelly;

		public final ResourceLocation left1, left2, right1, right2;

		Type(final String name)
		{
			this.name = name;

			this.texture_head = AetherCore.getResource("textures/entities/swet/swet_head_" + this.name + ".png");
			this.texture_jelly = AetherCore.getResource("textures/entities/swet/swet_jelly_" + this.name + ".png");

			this.left1 = AetherCore.getResource("textures/gui/overlay/swet/" + this.name + "_left_1.png");
			this.left2 = AetherCore.getResource("textures/gui/overlay/swet/" + this.name + "_left_2.png");

			this.right1 = AetherCore.getResource("textures/gui/overlay/swet/" + this.name + "_right_1.png");
			this.right2 = AetherCore.getResource("textures/gui/overlay/swet/" + this.name + "_right_2.png");
		}

		public static Type fromOrdinal(final int ordinal)
		{
			final Type[] gummy = values();

			return gummy[ordinal > gummy.length || ordinal < 0 ? 0 : ordinal];
		}
	}
}
