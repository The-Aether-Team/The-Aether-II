package com.gildedgames.aether.common.entities.dungeon.labyrinth.boss;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.util.BossStage;
import com.gildedgames.aether.common.entities.util.sliding.EntitySliding;
import com.gildedgames.aether.common.entities.util.sliding.SlidingHorizontalMoveHelper;
import com.gildedgames.aether.common.entities.util.sliding.SlidingMoveHelper;
import com.gildedgames.aether.common.items.tools.EnumToolType;
import com.gildedgames.aether.common.items.tools.ItemAetherTool;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthBridge;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.util.core.nbt.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class EntitySlider extends EntitySliding implements IMob
{

	private static final DataParameter<Boolean> IS_AWAKE = EntityDataManager.createKey(EntitySlider.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> IS_CRITICAL = EntityDataManager.createKey(EntitySlider.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Integer> DIRECTION = EntityDataManager.createKey(EntitySlider.class, DataSerializers.VARINT);

	private int chatCooldown, attackTime;

	private TickTimer signalTimer = new TickTimer();

	private SlidingHorizontalMoveHelper.Direction prevDirection;

	private BlockPos startLocation;

	private BossStage firstStage = new BossStage()
	{
		@Override
		protected boolean conditionsMet()
		{
			return EntitySlider.this.getHealth() <= 400;
		}

		@Override
		protected void onStageBegin()
		{
			BlockPos min = EntitySlider.this.startLocation.add(-50, -1, -50);
			BlockPos max = EntitySlider.this.startLocation.add(50, -1, 50);

			for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max))
			{
				IBlockState state = EntitySlider.this.worldObj.getBlockState(pos);

				if (state != null && state.getBlock() == BlocksAether.unstable_labyrinth_capstone)
				{
					EntitySlider.this.worldObj.setBlockToAir(pos);
				}
			}
		}
	};

	private BossStage[] stages = new BossStage[]
	{
			this.firstStage
	};

	public EntitySlider(World world)
	{
		super(world);

		this.setSize(2.0F, 2.0F);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntitySlider.IS_AWAKE, Boolean.FALSE);
		this.dataManager.register(EntitySlider.IS_CRITICAL, Boolean.FALSE);
		this.dataManager.register(EntitySlider.DIRECTION, 0);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(300);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.4D, true));

		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
	}

	@Override
	public void onUpdate()
	{
		if (this.startLocation == null)
		{
			this.startLocation = this.getPosition();
		}

		if (!this.worldObj.isRemote)
		{
			for (BossStage stage : this.stages)
			{
				stage.update();
			}
		}

		this.jumpMovementFactor = 0.0F;
		this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;

		this.fireResistance = -1;
		this.extinguish();

		this.signalTimer.tick();

		if (this.prevDirection != this.getDirection())
		{
			this.signalTimer.reset();
		}

		if (this.chatCooldown > 0)
		{
			this.chatCooldown--;
		}

		if (this.attackTime > 0)
		{
			this.attackTime--;
		}

		if (this.isAwake() && !this.worldObj.isRemote)
		{
			EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 2.3D);

			if (player != null && this.isAttackingPlayer(player))
			{
				this.playSound(SoundsAether.slider_collide, 2.5F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F));

				player.motionY += 0.35D;
				player.motionX *= 2.0D;
				player.motionZ *= 2.0D;

				this.attackTime = 20;
			}
		}

		super.onUpdate();

		this.jumpMovementFactor = 0.0F;
		this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;

		this.prevDirection = this.getDirection();
	}

	@Override
	public void onLivingUpdate()
	{
		this.jumpMovementFactor = 0.0F;
		this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;

		if (!this.isAwake())
		{
			this.posX = MathHelper.floor_double(this.posX);
			this.posY = MathHelper.floor_double(this.posY);
			this.posZ = MathHelper.floor_double(this.posZ);

			return;
		}

		if (!this.worldObj.isRemote && this.getAttackTarget() == null)
		{
			this.setAwake(false);
		}

		super.onLivingUpdate();
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox()
	{
		return this.getEntityBoundingBox();
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity)
	{
		return this.getEntityBoundingBox();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage)
	{
		if (!this.worldObj.isRemote && source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)source.getEntity();

			if (this.canAttackSlider(player))
			{
				boolean hasAttacked = super.attackEntityFrom(source, damage);

				if (!this.isAwake())
				{
					this.playSound(SoundsAether.slider_awake, 2.5F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F));

					this.setAwake(true);
					this.setAttackTarget(player);
				}

				return hasAttacked;
			}
		}

		return false;
	}

	private boolean isAttackingPlayer(EntityPlayer player)
	{
		if (this.attackTime <= 0)
		{
			if (player.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
			{
				return player.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
			}
			else
			{
				return player.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
			}
		}

		return false;
	}

	public boolean canAttackSlider(EntityPlayer player)
	{
		ItemStack equippedItem = player.getHeldItemMainhand();

		String tipPrefix = null;

		if (equippedItem != null)
		{
			if (!(equippedItem.getItem() instanceof ItemPickaxe))
			{
				if (equippedItem.getItem() instanceof ItemAetherTool)
				{
					ItemAetherTool aetherTool = (ItemAetherTool) equippedItem.getItem();

					if (aetherTool.getToolType() == EnumToolType.PICKAXE)
					{
						return true;
					}
				}

				tipPrefix = "My " + equippedItem.getDisplayName() + " doesn't seem to hurt it. Maybe I need a pickaxe?";
			}
		}
		else
		{
			tipPrefix = "My fist doesn't seem to hurt it. Maybe I need a pickaxe?";
		}

		if (tipPrefix != null)
		{
			if (this.chatCooldown <= 0)
			{
				player.addChatComponentMessage(new TextComponentString("Hmm. It's a rock-solid block. " + tipPrefix));
				this.chatCooldown = 120;
			}

			return false;
		}

		return true;
	}

	public TickTimer getSignalTimer()
	{
		return this.signalTimer;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.slider_die;
	}

	public boolean isAwake()
	{
		return this.dataManager.get(EntitySlider.IS_AWAKE);
	}

	public void setAwake(boolean flag)
	{
		this.dataManager.set(EntitySlider.IS_AWAKE, flag);
	}

	public boolean isCritical()
	{
		return this.dataManager.get(EntitySlider.IS_CRITICAL);
	}

	public void setCritical(boolean flag)
	{
		this.dataManager.set(EntitySlider.IS_CRITICAL, flag);
	}

	public SlidingHorizontalMoveHelper.Direction getDirection()
	{
		return SlidingHorizontalMoveHelper.Direction.values()[this.dataManager.get(EntitySlider.DIRECTION)];
	}

	public void setDirection(SlidingHorizontalMoveHelper.Direction direction)
	{
		this.dataManager.set(EntitySlider.DIRECTION, direction.ordinal());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setBoolean("isAwake", this.isAwake());
		tag.setBoolean("isCritical", this.isCritical());
		tag.setTag("startLocation", NBTHelper.serializeBlockPos(this.startLocation));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);

		this.setAwake(tag.getBoolean("isAwake"));
		this.setCritical(tag.getBoolean("isCritical"));
		this.startLocation = NBTHelper.readBlockPos((NBTTagCompound) tag.getTag("startLocation"));
	}

	@Override
	public void onSlide()
	{
		this.playSound(SoundsAether.slider_move, 2.5F, 1.0F / (this.getRNG().nextFloat() * 0.2F + 0.9F));

		this.setDirection(SlidingHorizontalMoveHelper.Direction.NONE);
	}

	@Override
	public void onSliding()
	{
		if (this.firstStage.hasBegun())
		{
			List<AxisAlignedBB> boxes = this.worldObj.getCollisionBoxes(this.getEntityBoundingBox().offset(0.0D, -0.1D, 0.0D));

			for (AxisAlignedBB box : boxes)
			{
				if (box != null)
				{
					BlockPos pos = new BlockPos(MathHelper.floor_double(box.minX + 0.5D), MathHelper.floor_double(box.minY + 0.5D), MathHelper.floor_double(box.minZ + 0.5D));

					TileEntity te = this.worldObj.getTileEntity(pos);

					if (te instanceof TileEntityLabyrinthBridge)
					{
						TileEntityLabyrinthBridge bridge = (TileEntityLabyrinthBridge)te;

						bridge.setDamage(bridge.getDamage() + 4);
					}
				}
			}
		}
	}

	@Override
	public void onStartSlideCooldown(SlidingHorizontalMoveHelper.Direction direction)
	{
		this.playSound(SoundsAether.slider_signal, 2.5F, 1.0F);

		if (this.getDirection() != direction)
		{
			this.setDirection(direction);
		}
	}

	@Override
	public int getSlideCooldown()
	{
		return 12;
	}

}
