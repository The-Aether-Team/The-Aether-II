package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.blocks.natural.leaves.BlockColoredLeaves.Color;
import com.gildedgames.aether.common.blocks.natural.wood.AetherWoodType;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntitySkyrootLizard extends EntityAetherAnimal
{
	private static final DataParameter<Byte> LIZARD_TYPE = EntityDataManager.createKey(EntitySkyrootLizard.class, DataSerializers.BYTE);

	private static final DataParameter<Byte> LIZARD_COLOR = EntityDataManager.createKey(EntitySkyrootLizard.class, DataSerializers.BYTE);

	private static final Type[] RANDOM_TYPES = new Type[] { Type.AMBERROOT, Type.SKYROOT,
			Type.WISPROOT, Type.GREATROOT };

	public EntitySkyrootLizard(World world)
	{
		super(world);

		this.setSize(.8f, .3f);

		this.tasks.addTask(1, new EntityAIWander(this, 0.5D, 10));
		this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 20.0F, .7D, .85D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		Type type = RANDOM_TYPES[this.rand.nextInt(RANDOM_TYPES.length)];
		Color color = Color.VALUES[this.rand.nextInt(Color.VALUES.length)];

		this.dataManager.register(LIZARD_TYPE, (byte) type.ordinal());
		this.dataManager.register(LIZARD_COLOR, (byte) color.ordinal());
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.7D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(2);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(4);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
		if (distance > 4)
		{
			IAetherStatusEffects.applyStatusEffect(this, IAetherStatusEffects.effectTypes.STUN, 100);
		}
	}

	public Type getLeafType()
	{
		int ordinal = this.dataManager.get(LIZARD_TYPE);

		if (ordinal < 0 || ordinal >= Type.VALUES.length)
		{
			return Type.SKYROOT;
		}

		return Type.VALUES[ordinal];
	}

	public Color getLeafColor()
	{
		int ordinal = this.dataManager.get(LIZARD_COLOR);

		if (ordinal < 0 || ordinal > Color.VALUES.length)
		{
			return Color.GREEN;
		}

		return Color.VALUES[ordinal];
	}

	public void setLizardColor(Color color)
	{
		int ordinal = -1;

		if (color != null)
		{
			ordinal = color.ordinal();
		}

		this.dataManager.set(LIZARD_COLOR, (byte) ordinal);
	}

	public void setLizardType(Type type)
	{
		this.dataManager.set(LIZARD_TYPE, (byte) type.ordinal());
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		super.processInteract(player, hand);

		ItemStack itemStack = player.getHeldItem(hand);

		if (!itemStack.isEmpty())
		{
			if (itemStack.getItem() == ItemsAether.skyroot_stick || itemStack.getItem() == Items.STICK)
			{
				this.consumeItemFromStack(player, itemStack);

				ItemStack itemStackLizard = new ItemStack(ItemsAether.skyroot_lizard_stick);

				player.addItemStackToInventory(itemStackLizard);

				if (player.isServerWorld())
				{
					player.playSound(SoundsAether.aerbunny_hurt, 1F, 0.3F);
				}

				this.world.removeEntity(this);

				return true;
			}
		}

		return false;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.aerbunny_death;
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		nbt.setInteger("type", this.dataManager.get(LIZARD_TYPE));
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.dataManager.set(LIZARD_TYPE, (byte) nbt.getInteger("type"));
	}

	@SideOnly(Side.CLIENT)
	public int getLizardAccentColor()
	{
		Type type = this.getLeafType();

		if (type == Type.AMBERROOT)
		{
			return Integer.MIN_VALUE;
		}

		Color color = this.getLeafColor();

		if (type == Type.SKYROOT)
		{
			switch (color)
			{
				case GREEN:
					return 0x6B9157;
				case BLUE:
					return 0x5D8B9A;
				case DARK_BLUE:
					return 0x3B4E9F;
			}
		}
		else if (type == Type.WISPROOT)
		{
			switch (color)
			{
				case GREEN:
					return 0x82A16E;
				case BLUE:
					return 0x659094;
				case DARK_BLUE:
					return 0x6670AA;
			}
		}
		else if (type == Type.GREATROOT)
		{
			switch (color)
			{
				case GREEN:
					return 0x4C6732;
				case BLUE:
					return 0x3E5E67;
				case DARK_BLUE:
					return 0x1D2A5D;
			}
		}

		return 0xFFFFFF;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return null;
	}

	public enum Type
	{
		SKYROOT,
		GREATROOT,
		WISPROOT,
		AMBERROOT;

		public static final Type[] VALUES = Type.values();

		public static Type getFromWoodType(BlockAetherLog block)
		{
			AetherWoodType type = block.getAetherWoodType();

			switch (type)
			{
				case SKYROOT:
					return SKYROOT;
				case GREATROOT:
					return GREATROOT;
				case WISPROOT:
					return WISPROOT;
				case AMBERROOT:
					return AMBERROOT;
			}

			return null;
		}
	}
}
