package com.gildedgames.aether.api.entity;

import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class EntityCharacter extends EntityCreature implements Character, IWorldObjectHoverable
{
	private static final DataParameter<NBTTagCompound> SHOP_INSTANCE_GROUP_TAG = new DataParameter<>(16, DataSerializers.COMPOUND_TAG);

	private IShopInstanceGroup shopInstanceGroup;

	private boolean startupSynced;

	private NBTTagCompound prevShopInstanceTag;

	public EntityCharacter(final World worldIn)
	{
		super(worldIn);

		this.isImmuneToFire = true;
		this.shopInstanceGroup = this.createShopInstanceGroup();
	}

	@Override
	public boolean isEntityInvulnerable(final DamageSource source)
	{
		return !source.canHarmInCreative();
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

	@Nullable
	public abstract IShopInstanceGroup createShopInstanceGroup();

	public NBTTagCompound getShopInstanceGroupTag()
	{
		return this.dataManager.get(SHOP_INSTANCE_GROUP_TAG);
	}

	public void setShopInstanceGroupTag(NBTTagCompound tag)
	{
		this.dataManager.set(SHOP_INSTANCE_GROUP_TAG, tag);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(SHOP_INSTANCE_GROUP_TAG, new NBTTagCompound());
	}

	@Override
	public IShopInstanceGroup getShopInstanceGroup()
	{
		return this.shopInstanceGroup;
	}

	private void onClientUpdate()
	{
		if (this.getShopInstanceGroup() != null)
		{
			if (this.prevShopInstanceTag != this.getShopInstanceGroupTag())
			{
				this.prevShopInstanceTag = this.getShopInstanceGroupTag();

				this.getShopInstanceGroup().read(this.getShopInstanceGroupTag());
			}
		}
	}

	private void onServerUpdate()
	{
		if (this.getShopInstanceGroup() != null)
		{
			for (IShopInstance shopInstance : this.getShopInstanceGroup().getShopInstances())
			{
				if (shopInstance != null)
				{
					shopInstance.tick();

					if (shopInstance.isDirty() || !this.startupSynced)
					{
						this.startupSynced = true;

						NBTTagCompound tag = new NBTTagCompound();
						this.getShopInstanceGroup().write(tag);

						this.setShopInstanceGroupTag(tag);

						this.getShopInstanceGroup().getShopInstances().forEach(IShopInstance::markClean);
					}
				}
			}
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.world.isRemote)
		{
			this.onServerUpdate();
		}
		else
		{
			this.onClientUpdate();
		}
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		NBTFunnel funnel = new NBTFunnel(compound);

		funnel.set("shopInstanceGroup", this.shopInstanceGroup);
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		NBTFunnel funnel = new NBTFunnel(compound);

		this.shopInstanceGroup = funnel.getWithDefault("shopInstanceGroup", () -> this.shopInstanceGroup);
	}

	@Override
	public ITextComponent getHoverText(World world, RayTraceResult result)
	{
		return new TextComponentTranslation("gui.aether.hover.npc", this.getName());
	}

	@SubscribeEvent
	public static void onEntityMounted(final EntityMountEvent event)
	{
		if (EntityCharacter.class.isAssignableFrom(event.getEntityMounting().getClass()))
		{
			event.setCanceled(true);
		}
	}
}
