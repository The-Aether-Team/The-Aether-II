package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.shop.IShopDefinition;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.entities.util.EntityBodyHelperNoRotation;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.shop.ShopInstanceGroup;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Optional;
import java.util.Random;

public class EntityEdison extends EntityCharacter implements IEntityEyesComponentProvider
{
	public static final ResourceLocation SPEAKER = AetherCore.getResource("edison");

	public static final ResourceLocation HOLIDAY_SHOP = AetherCore.getResource("edison_holiday");

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	private BlockPos spawned;

	public EntityEdison(final World worldIn)
	{
		super(worldIn);

		this.setSize(0.9F, 1.35F);

		this.rotationYaw = 0.3F;
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		IShopInstanceGroup group = new ShopInstanceGroup();

		Optional<IShopDefinition> shopDefinition = AetherAPI.content().shop().getShopDefinition(SPEAKER);

		if (shopDefinition.isPresent())
		{
			IShopInstance normalShop = AetherAPI.content().shop().createInstance(SPEAKER, shopDefinition.get(), new Random(this.getRNG().nextLong()));

			group.setShopInstance(0, normalShop);
		}

		shopDefinition = AetherAPI.content().shop().getShopDefinition(HOLIDAY_SHOP);

		if (shopDefinition.isPresent())
		{
			IShopInstance holidayShop = AetherAPI.content().shop().createInstance(HOLIDAY_SHOP, shopDefinition.get(), new Random(this.getRNG().nextLong()));

			group.setShopInstance(1, holidayShop);
		}

		return group;
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
	{
		return new EntityBodyHelperNoRotation(this);
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
	protected void setRotation(final float yaw, final float pitch)
	{

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

	}

	@Override
	public void onUpdate()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.onUpdate();

		this.eyes.update();

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;
	}

	@Override
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		if (!super.processInteract(player, hand))
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			final PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);
			final PlayerProgressModule progressModule = playerAether.getModule(PlayerProgressModule.class);

			dialogModule.setTalkingEntity(this);

			if (!player.world.isRemote)
			{
				boolean hasDied = progressModule.hasDiedInAether();

				if (progressModule.hasTalkedTo(EntityEdison.SPEAKER))
				{
					String node = "start";

					if (hasDied && !progressModule.getBoolean("talkToEdisonAfterDying"))
					{
						node = "start_respawn";
					}

					dialogModule.openScene(AetherCore.getResource("edison/outpost_greet"), node);

					if (hasDied)
					{
						progressModule.setBoolean("talkToEdisonAfterDying", true);
					}
				}
				else
				{
					String node = hasDied ? "start_respawn_not_introduced" : "start_not_introduced";

					dialogModule.openScene(AetherCore.getResource("edison/outpost_greet"), node);
					progressModule.setHasTalkedTo(EntityEdison.SPEAKER, true);

					if (hasDied)
					{
						progressModule.setBoolean("talkToEdisonAfterDying", true);
					}
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

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}
}
