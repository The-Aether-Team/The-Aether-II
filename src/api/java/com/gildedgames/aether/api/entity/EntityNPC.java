package com.gildedgames.aether.api.entity;

import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityNPC extends EntityCreature implements NPC
{
	private static final DataParameter<NBTTagCompound> SHOP_INSTANCE_TAG = new DataParameter<>(16, DataSerializers.COMPOUND_TAG);

	private IShopInstance shopInstance;

	private boolean startupSynced;

	private NBTTagCompound prevShopInstanceTag;

	public EntityNPC(final World worldIn)
	{
		super(worldIn);

		this.isImmuneToFire = true;
	}

	public NBTTagCompound getShopInstanceTag()
	{
		return this.dataManager.get(SHOP_INSTANCE_TAG);
	}

	public void setShopInstanceTag(NBTTagCompound tag)
	{
		this.dataManager.set(SHOP_INSTANCE_TAG, tag);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(SHOP_INSTANCE_TAG, new NBTTagCompound());
	}

	@Override
	public boolean isEntityInvulnerable(final DamageSource source)
	{
		return source != DamageSource.OUT_OF_WORLD || super.isEntityInvulnerable(source);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean canBeLeashedTo(final EntityPlayer player)
	{
		return false;
	}

	@Override
	public IShopInstance getShopInstance()
	{
		return this.shopInstance;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.world.isRemote)
		{
			if (this.getShopInstance() != null)
			{
				this.getShopInstance().tick();

				if (this.getShopInstance().isDirty() || !this.startupSynced)
				{
					this.startupSynced = true;

					NBTTagCompound tag = new NBTTagCompound();
					this.getShopInstance().write(tag);

					this.setShopInstanceTag(tag);

					this.getShopInstance().markClean();
				}
			}
			else
			{
				this.shopInstance = this.createShopInstance(this.getRNG().nextLong());
			}
		}
		else
		{
			if (this.getShopInstance() == null)
			{
				this.shopInstance = this.createShopInstance(this.getRNG().nextLong());
			}

			if (this.prevShopInstanceTag != this.getShopInstanceTag() && this.shopInstance != null)
			{
				this.prevShopInstanceTag = this.getShopInstanceTag();

				this.shopInstance.read(this.getShopInstanceTag());
			}
		}
	}

	public IShopInstance createShopInstance(long seed)
	{
		return null;
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		NBTFunnel funnel = new NBTFunnel(compound);

		funnel.set("shopInstance", this.shopInstance);
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		NBTFunnel funnel = new NBTFunnel(compound);

		this.shopInstance = funnel.getWithDefault("shopInstance", () -> this.shopInstance);
	}
}
