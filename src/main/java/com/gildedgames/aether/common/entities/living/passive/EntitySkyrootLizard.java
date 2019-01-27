package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import java.awt.Color;

import javax.annotation.Nullable;
import java.util.HashMap;

public class EntitySkyrootLizard extends EntityAetherAnimal
{

	private static final DataParameter<Integer> LIZARD_TYPE = EntityDataManager.createKey(EntitySkyrootLizard.class, DataSerializers.VARINT);

	public static final SkyrootLizardVarieties VARIETIES = new SkyrootLizardVarieties();

	public EntitySkyrootLizard(World world)
	{
		super(world);
		this.setSize(.8f, .3f);

		this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 12.0F, .8D, 1D));
		this.tasks.addTask(1, new EntityAIWander(this, 0.5D, 10));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	}

	@Override
	public void entityInit()
	{
		super.entityInit();
		this.dataManager.register(LIZARD_TYPE, this.getEntityWorld().rand.nextInt(10));
	}


	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.8D);
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
			this.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).applyStatusEffect(IAetherStatusEffects.effectTypes.STUN, 100);
		}
	}

	public int getLogType()
	{
		int type = this.dataManager.get(LIZARD_TYPE);

		return type == 0 ? 0 : (type + 2) / 3;
	}

	public int getLeafType()
	{
		return this.dataManager.get(LIZARD_TYPE);
	}

	public void setLizardType(BlockAetherLeaves leaf)
	{
		if (leaf.equals(BlocksAether.amberoot_leaves))
		{
			this.dataManager.set(LIZARD_TYPE, 0);
		}
		else
		{
			System.out.println(leaf.getTranslationKey().substring(12));

			int val = VARIETIES.table.get(leaf.getTranslationKey().substring(12));

			this.dataManager.set(LIZARD_TYPE, val);
		}
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

		this.dataManager.set(LIZARD_TYPE, nbt.getInteger("type"));
	}

	public Color getColor(int leafType)
	{
		return SkyrootLizardVarieties.colors[leafType - 1];
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return null;
	}

	static class SkyrootLizardVarieties
	{
		public HashMap<String, Integer> table;

		public static final Color[] colors = new Color[] { new Color(0x1D2A5D), new Color(0x3E5E67), new Color(0x4C6732), new Color(0x5D8B9A),
				new Color(0x3B4E9F), new Color(0x6B9157), new Color(0x659094), new Color(0x6670AA), new Color(0x82A16E)};

		public SkyrootLizardVarieties()
		{
			this.table = new HashMap<>();

			this.table.put("dark_blue_dark_skyroot_leaves", 1);
			this.table.put("blue_dark_skyroot_leaves", 2);
			this.table.put("green_dark_skyroot_leaves", 3);
			this.table.put("blue_skyroot_leaves", 4);
			this.table.put("dark_blue_skyroot_leaves", 5);
			this.table.put("green_skyroot_leaves", 6);
			this.table.put("blue_light_skyroot_leaves", 7);
			this.table.put("dark_blue_light_skyroot_leaves", 8);
			this.table.put("green_light_skyroot_leaves", 9);
		}
	}
}
