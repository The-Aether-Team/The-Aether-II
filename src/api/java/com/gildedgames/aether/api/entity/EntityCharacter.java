package com.gildedgames.aether.api.entity;

import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public abstract class EntityCharacter extends CreatureEntity implements Character, IWorldObjectHoverable
{
	private static final DataParameter<CompoundNBT> SHOP_INSTANCE_GROUP_TAG = new DataParameter<>(16, DataSerializers.COMPOUND_NBT);

	private IShopInstanceGroup shopInstanceGroup;

	private boolean startupSynced;

	private CompoundNBT prevShopInstanceTag;

	public EntityCharacter(final EntityType<? extends EntityCharacter> type, final World worldIn)
	{
		super(type, worldIn);

		this.shopInstanceGroup = this.createShopInstanceGroup();
	}

	@Override
	public boolean isInvulnerableTo(final DamageSource source)
	{
		return !source.canHarmInCreative();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	public boolean canDespawn(double distance)
	{
		return false;
	}

	@Override
	public boolean canBeLeashedTo(final PlayerEntity player)
	{
		return false;
	}

	@Nullable
	public abstract IShopInstanceGroup createShopInstanceGroup();

	public CompoundNBT getShopInstanceGroupTag()
	{
		return this.dataManager.get(SHOP_INSTANCE_GROUP_TAG);
	}

	public void setShopInstanceGroupTag(CompoundNBT tag)
	{
		this.dataManager.set(SHOP_INSTANCE_GROUP_TAG, tag);
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(SHOP_INSTANCE_GROUP_TAG, new CompoundNBT());
	}

	@Override
	public IShopInstanceGroup getShopInstanceGroup()
	{
		return this.shopInstanceGroup;
	}

	private void onClientLivingTick()
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

	private void onServerLivingTick()
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

						CompoundNBT tag = new CompoundNBT();
						this.getShopInstanceGroup().write(tag);

						this.setShopInstanceGroupTag(tag);

						this.getShopInstanceGroup().getShopInstances().forEach(IShopInstance::markClean);
					}
				}
			}
		}
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		if (!this.world.isRemote)
		{
			this.onServerLivingTick();
		}
		else
		{
			this.onClientLivingTick();
		}
	}

	@Override
	public void writeAdditional(CompoundNBT nbt)
	{
		super.writeAdditional(nbt);

		new NBTFunnel(nbt).set("shopInstanceGroup", this.shopInstanceGroup);
	}

	@Override
	public void readAdditional(CompoundNBT nbt)
	{
		super.readAdditional(nbt);

		NBTFunnel funnel = new NBTFunnel(nbt);

		this.shopInstanceGroup = funnel.getWithDefault("shopInstanceGroup", () -> this.shopInstanceGroup);

	}
	
	@Override
	public ITextComponent getHoverText(World world, RayTraceResult result)
	{
		return new TranslationTextComponent("gui.aether.hover.npc", this.getName());
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
