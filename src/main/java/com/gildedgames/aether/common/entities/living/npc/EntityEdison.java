package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketTalkedTo;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEdison extends EntityNPC
{
	public static ResourceLocation SPEAKER = AetherCore.getResource("edison");

	private BlockPos spawned;

	public EntityEdison(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 1.0F);

		this.rotationYaw = 0.3F;
	}

	@Override
	protected void initEntityAI()
	{
		//this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
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
	public void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void setRotation(final float yaw, final float pitch)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void turn(final float yaw, final float pitch)
	{

	}

	@Override
	public void onUpdate()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		this.renderYawOffset = 45F;
		this.rotationYaw = 45F;

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

		this.renderYawOffset = 45F;
		this.rotationYaw = 45F;
	}

	@Override
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		if (!super.processInteract(player, hand))
		{
			if (!player.world.isRemote)
			{
				final PlayerAether playerAether = PlayerAether.getPlayer(player);

				if (playerAether.getProgressModule().hasTalkedTo(EntityEdison.SPEAKER))
				{
					String node = playerAether.getProgressModule().hasDiedInAether() ? "start_respawn" : "start";

					playerAether.getDialogController().openScene(AetherCore.getResource("edison/outpost_greet"), node);
				}
				else
				{
					String node = playerAether.getProgressModule().hasDiedInAether() ? "start_respawn_not_introduced" : "start_not_introduced";

					playerAether.getDialogController().openScene(AetherCore.getResource("edison/outpost_greet"), node);

					playerAether.getProgressModule().setHasTalkedTo(EntityEdison.SPEAKER, true);
					NetworkingAether.sendPacketToPlayer(new PacketTalkedTo(EntityEdison.SPEAKER, true), (EntityPlayerMP) player);
				}
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
