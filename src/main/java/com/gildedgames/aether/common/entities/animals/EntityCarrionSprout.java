package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class EntityCarrionSprout extends EntityAetherAnimal
{
	protected Map<String, Float> defenseMap = Maps.newHashMap();
	{{
		this.defenseMap.put("Very Weak", 4.0F);
		this.defenseMap.put("Weak", 2.0F);
		this.defenseMap.put("Average", 0.0F);
		this.defenseMap.put("Strong", -2.0F);
		this.defenseMap.put("Very Strong", -4.0F);
	}}

	private static final DataParameter<Integer> SIZE = new DataParameter<>(13, DataSerializers.VARINT);

	@SideOnly(Side.CLIENT)
	public float sinage, prevSinage;

	private int maxSproutSize;

	public EntityCarrionSprout(final World world)
	{
		super(world);

		this.setSize(0.5F, 1.5F);

		this.spawnableBlock = BlocksAether.aether_grass;

		this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
	}

	@Override
	public boolean canBeLeashedTo(EntityPlayer player)
	{
		return false;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(0.0f);

		this.setMaxSproutSize(8);

		final int min = 3;

		this.setSproutSize(min + this.rand.nextInt(this.getMaxSproutSize() - min));
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(SIZE, 0);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("size", this.getSproutSize());
		nbt.setInteger("maxSize", this.getMaxSproutSize());

		return nbt;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.setSproutSize(nbt.getInteger("size"));
		this.setMaxSproutSize(nbt.getInteger("maxSize"));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.world.isRemote)
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
		if (this.world.getBlockState(pos).isFullCube())
		{
			return false;
		}

		final IBlockState rootBlock = this.world.getBlockState(pos.down());

		return rootBlock.getBlock() == BlocksAether.aether_grass
				|| rootBlock.getBlock() == BlocksAether.aether_dirt
				|| rootBlock.getBlock() == BlocksAether.highlands_snow_layer
				|| rootBlock.getBlock() == BlocksAether.highlands_snow;
	}

	@Override
	public void onLivingUpdate()
	{
		this.motionX = 0.0F;
		this.motionZ = 0.0F;

		if (this.ticksExisted == 0)
		{
			this.setRotation(this.world.rand.nextFloat() * 360F, 0.0F);
			this.renderYawOffset = this.rotationYaw;
		}

		super.onLivingUpdate();

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
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_CARRION_SPROUT;
	}

	@SideOnly(Side.CLIENT)
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
	public EntityAgeable createChild(final EntityAgeable ageable)
	{
		return null;
	}
}
