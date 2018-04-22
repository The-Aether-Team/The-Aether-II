package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.ai.necromancer.NecromancerAIGoUpTower;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstance;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityNecromancer extends EntityNPC
{
	private BlockPos floor1, floor2, floor3, floor4;

	private BlockPos spawned;

	private NecromancerAIGoUpTower upTowerTask;

	public EntityNecromancer(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 2.5F);

		this.rotationYaw = 0.3F;

		if (!this.world.isRemote && this.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			final NecromancerTowerInstance instance = InstancesAether.NECROMANCER_TOWER_HANDLER.getFromDimId(worldIn.provider.getDimension());

			this.floor1 = instance.getTower().getScheduleFromTriggerID("p2").getBounds().getMin();
			this.floor2 = instance.getTower().getScheduleFromTriggerID("p3").getBounds().getMin();
			this.floor3 = instance.getTower().getScheduleFromTriggerID("p4").getBounds().getMin();
			this.floor4 = instance.getTower().getScheduleFromTriggerID("p5").getBounds().getMin();

			this.upTowerTask = new NecromancerAIGoUpTower(this, this.floor1);

			this.tasks.addTask(1, this.upTowerTask);
		}
	}

	public NecromancerAIGoUpTower getUpTowerTask()
	{
		return this.upTowerTask;
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

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(250);
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
	public void entityInit()
	{
		super.entityInit();
	}

	@Override
	public void onUpdate()
	{
		this.setHealth(this.getMaxHealth());
		this.isDead = false;

		this.setHomePosAndDistance(this.getPosition(), 500);

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
		}

		super.onUpdate();
	}

	@Override
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		if (!super.processInteract(player, hand))
		{
			if (!player.world.isRemote)
			{
				final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
				aePlayer.getDialogController().setTalkingEntity(this);
				aePlayer.getDialogController().openScene(AetherCore.getResource("necromancer/start"));
			}
		}

		return true;
	}

}
