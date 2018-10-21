package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.EntityNPC;
import com.gildedgames.aether.api.shop.IShopDefinition;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.Random;

public class EntityMysteriousFigure extends EntityNPC
{
	public static ResourceLocation SPEAKER = AetherCore.getResource("mysterious_figure");

	private BlockPos spawned;

	public EntityMysteriousFigure(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 2.0F);
	}

	@Override
	public void applyEntityCollision(Entity entityIn)
	{
		super.applyEntityCollision(entityIn);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return super.canBeCollidedWith();
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox()
	{
		return super.getEntityBoundingBox();
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
	{
		return new EntityBodyHelper(this);
	}

	@Override
	public IShopInstance createShopInstance(long seed)
	{
		Optional<IShopDefinition> shopDefinition = AetherAPI.content().shop().getShopDefinition(SPEAKER);
		IShopInstance instance = null;

		if (shopDefinition.isPresent())
		{
			instance = AetherAPI.content().shop().createInstance(shopDefinition.get(), new Random(seed));
		}

		return instance;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 50.0F, 1.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.spawned = NBTHelper.readBlockPos(compound.getCompoundTag("spawned"));

		if (this.spawned != null)
		{
			this.setHomePosAndDistance(this.spawned, 3);
		}
	}

	@Override
	protected void setRotation(final float yaw, final float pitch)
	{
		//super.setRotation(yaw, pitch);
	}

	@Override
	public void entityInit()
	{
		super.entityInit();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void turn(final float yaw, final float pitch)
	{
		//super.turn(yaw, pitch);
	}

	@Override
	public void onUpdate()
	{
		LocalDateTime time = LocalDateTime.now();

		if (time.getMonth() != Month.OCTOBER)
		{
			if (this.width > 0 && this.height > 0)
			{
				this.setSize(0, 0);
			}
		}
		else
		{
			if (this.width == 0 && this.height == 0)
			{
				this.setSize(1.0F, 2.0F);
			}
		}

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		this.setHealth(this.getMaxHealth());
		this.isDead = false;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.onUpdate();

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;
	}

	@Override
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		LocalDateTime time = LocalDateTime.now();

		if (time.getMonth() != Month.OCTOBER)
		{
			return false;
		}

		if (!super.processInteract(player, hand))
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getDialogController().setTalkingEntity(this);

			if (!player.world.isRemote)
			{
				playerAether.getDialogController().openScene(AetherCore.getResource("mysterious_figure/start"), "start");
			}
		}

		return true;
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

}
