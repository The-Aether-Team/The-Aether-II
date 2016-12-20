package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.client.gui.dialog.GuiDialogController;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.dialog.data.EdisonDialog;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityEdison extends EntityNPC
{

	private BlockPos spawned;

	public EntityEdison(World worldIn)
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
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("spawned", NBTHelper.serializeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
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
	protected void setRotation(float yaw, float pitch)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setAngles(float yaw, float pitch)
	{

	}

	@Override
	public void onUpdate()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		this.renderYawOffset = 315F;

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

		this.renderYawOffset = 315F;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
	{
		if (!super.processInteract(player, hand, stack))
		{
			if (player.worldObj.isRemote)
			{
				PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(player);

				GuiDialogController controller = new GuiDialogController(player);

				if (!playerAether.hasDiedInAetherBefore())
				{
					controller.show(EdisonDialog.Scenes.BUSY_SCENE);
				}
				else
				{
					controller.show(EdisonDialog.Scenes.OUTPOST_SCENE);
				}

				Minecraft.getMinecraft().displayGuiScreen(controller);
			}
		}

		return true;
	}

}
