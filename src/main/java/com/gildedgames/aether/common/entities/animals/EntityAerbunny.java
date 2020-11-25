package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.*;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketAerbunnySetRiding;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class EntityAerbunny extends EntityTameable implements IDefenseLevelsHolder
{
    protected Map<String, Float> defenseMap = Maps.newHashMap();
    {{
        this.defenseMap.put("Very Weak", 4.0F);
        this.defenseMap.put("Weak", 2.0F);
        this.defenseMap.put("Average", 0.0F);
        this.defenseMap.put("Strong", -2.0F);
        this.defenseMap.put("Very Strong", -4.0F);
    }}

    private static final Set<Item> TEMPTATION_ITEMS = Sets
            .newHashSet(ItemsAether.blueberries);

    private static final Set<Item> TAMING_ITEMS = Sets
            .newHashSet(ItemsAether.orange);

    private static final Set<Item> HEALING_ITEMS = Sets
            .newHashSet(ItemsAether.orange);

    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityAerbunny.class, DataSerializers.VARINT);

    @SideOnly(Side.CLIENT)
    private double prevMotionY;

    @SideOnly(Side.CLIENT)
    private int puffiness;

    @SideOnly(Side.CLIENT)
    private float curRotation;

    private boolean quickFall;

    public EntityAerbunny(final World world)
    {
        super(world);

        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(2, new EntityAIRestrictRain(this));
        this.tasks.addTask(3, new EntityAIUnstuckBlueAercloud(this));
        this.tasks.addTask(3, new EntityAIHideFromRain(this, 1.3D));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(3, new EntityAIEggnogTempt(this, 2.2D));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.5D, 5.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D, 10));
        this.tasks.addTask(8, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 12.0F, 1.2F, 1.8F));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

        this.jumpHelper = new AerbunnyJumpHelper(this);

        this.spawnableBlock = BlocksAether.aether_grass;

        this.setSize(0.65F, 0.65F);

        this.setTamed(false);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(COLLAR_COLOR, EnumDyeColor.BLUE.getDyeDamage());
    }

    @Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return this.world.getBlockState(pos.down()).getBlock() == BlocksAether.aether_grass ? 10.0F :
                this.world.getLightBrightness(pos) - 0.5F;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);

        this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(2.0F);
        this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(-2.0F);
        this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(0.0F);
    }

    @Override
    public void onUpdate()
    {
        if (this.motionX != 0 || this.motionZ != 0) {
            this.setJumping(true);
        }

        super.onUpdate();

        if (this.world.isRemote) {
            if (this.puffiness > 0) {
                this.puffiness--;
            }

            if (this.prevMotionY <= 0 && this.motionY > 0) {
                final BlockPos pos = this.getPosition();

                // Make sure we only spawn particles when it's jumping off a block
                if (this.world.isBlockFullCube(pos.down())) {
                    AetherCore.PROXY.spawnJumpParticles(this.world, this.posX, pos.getY(), this.posZ, 0.6D, 6);
                }

                this.puffiness = 10;
            }

            this.prevMotionY = this.motionY;
        }

        if (this.isRiding())
        {
            final Entity entity = this.getRidingEntity();

            if (!this.world.isRemote)
            {
                boolean isPlayer = entity instanceof EntityPlayer;

                if (isPlayer && ((EntityPlayer) entity).isSpectator())
                {
                    NetworkingAether.sendPacketToWatching(new PacketAerbunnySetRiding(null, this), this, false);

                    this.dismountRidingEntity();
                    this.setPosition(entity.posX, entity.posY + entity.getEyeHeight() + 0.5D, entity.posZ);
                }
                else if (!isPlayer || !(((EntityPlayer) entity).isSpectator()))
                {
                    if (entity.isSneaking()) {
                        if (entity.onGround && !this.quickFall) {
                            NetworkingAether.sendPacketToWatching(new PacketAerbunnySetRiding(null, this), this, false);

                            this.dismountRidingEntity();
                            this.setPosition(entity.posX, entity.posY + entity.getEyeHeight() + 0.5D, entity.posZ);
                        } else {
                            this.quickFall = true;
                        }
                    } else {
                        this.quickFall = false;
                    }
                }
            }

            if (entity.motionY < 0) {
                entity.motionY *= entity.isSneaking() ? 0.9D : 0.7D;

                entity.fallDistance = 0;
            }

            this.setRotation(entity.rotationYaw, entity.rotationPitch);
        }

        if (this.motionY < -0.1D) {
            this.motionY = -0.1D;
        }

        this.fallDistance = 0.0F;
    }

    @Override
    public boolean processInteract(final EntityPlayer player, final EnumHand hand)
    {
        final ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed())
        {
            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (isHealingItem(itemstack) && this.getHealth() < this.getMaxHealth())
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }

                        this.heal((float) itemfood.getHealAmount(itemstack));

                        return true;
                    }
                }
                /*
                else if (itemstack.getItem() == Items.DYE)
                {
                    EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

                    if (enumdyecolor != this.getCollarColor())
                    {
                        this.setCollarColor(enumdyecolor);

                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }

                        return true;
                    }
                }
                 */
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack) && !this.isTamingItem(itemstack) && !this.isHealingItem(itemstack) && player.isSneaking())
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
            }
        }
        else if (this.isTamingItem(itemstack))
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote)
            {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.aiSit.setSitting(true);
                    this.setHealth(this.getMaxHealth());
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        if (!super.processInteract(player, hand) && !this.isBreedingItem(itemstack) && !this.isTamingItem(itemstack) && !this.isHealingItem(itemstack) && !player.isSneaking() && !this.isSitting())
        {
            if (!this.isRiding() && player.getPassengers().size() <= 0)
            {
                if (!this.world.isRemote)
                {
                    this.startRiding(player, true);

                    NetworkingAether.sendPacketToWatching(new PacketAerbunnySetRiding(player, this), this, false);
                }

                return true;
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public double getYOffset()
    {
        return this.getRidingEntity() != null ? 0.45D : 0.0D;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return LootTablesAether.ENTITY_AERBUNNY;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsAether.aerbunny_ambient;
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource src)
    {
        return SoundsAether.aerbunny_hurt;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsAether.aerbunny_death;
    }

    @Override
    protected PathNavigate createNavigator(final World worldIn)
    {
        return new AetherNavigateGround(this, worldIn);
    }

    @Override
    public EntityAgeable createChild(final EntityAgeable ageable)
    {
        EntityAerbunny entityAerbunny = new EntityAerbunny(this.world);
        UUID uuid = this.getOwnerId();

        if (uuid != null)
        {
            entityAerbunny.setOwnerId(uuid);
            entityAerbunny.setTamed(true);
        }

        return entityAerbunny;
    }

    @SideOnly(Side.CLIENT)
    public int getPuffiness()
    {
        return this.puffiness;
    }

    @SideOnly(Side.CLIENT)
    public float getRotation()
    {
        if (this.motionY > 0) {
            this.curRotation += MathHelper.clamp(this.curRotation / 10f, -4f, -2f);
        } else if (this.motionY < 0) {
            this.curRotation += MathHelper.clamp(this.curRotation / 10f, 2f, 4f);
        }

        if (this.onGround) {
            this.curRotation = 0f;
        }

        this.curRotation = MathHelper.clamp(this.curRotation, -30f, 30f);

        return this.curRotation;
    }

    @Override
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    @Override
    public boolean canRiderInteract()
    {
        return true;
    }

    @Override
    public boolean isBreedingItem(@Nullable final ItemStack stack)
    {
        return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
    }

    public boolean isTamingItem(@Nullable final ItemStack stack)
    {
        return stack != null && TAMING_ITEMS.contains(stack.getItem());
    }

    public boolean isHealingItem(@Nullable final ItemStack stack)
    {
        return stack != null && HEALING_ITEMS.contains(stack.getItem());
    }

    public EnumDyeColor getCollarColor()
    {
        return EnumDyeColor.byDyeDamage(this.dataManager.get(COLLAR_COLOR) & 15);
    }

    public void setCollarColor(EnumDyeColor collarColor)
    {
        this.dataManager.set(COLLAR_COLOR, collarColor.getDyeDamage());
    }

    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return !this.isRiding() && super.isEntityInsideOpaqueBlock();
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("CollarColor", 99))
        {
            this.setCollarColor(EnumDyeColor.byDyeDamage(compound.getByte("CollarColor")));
        }
    }

    private class AerbunnyJumpHelper extends EntityJumpHelper
    {
        private final EntityLiving entity;

        public AerbunnyJumpHelper(final EntityAerbunny entity)
        {
            super(entity);

            this.entity = entity;
        }

        @Override
        public void doJump()
        {
            this.entity.setJumping(true);

            if (this.entity.motionX == 0 && this.entity.motionZ == 0) {
                this.isJumping = false;
                this.entity.setJumping(false);
            }
        }
    }

    private class AerbunnyNavigator extends AetherNavigateGround
    {
        public AerbunnyNavigator(final EntityLiving entity, final World world)
        {
            super(entity, world);
        }

        @Override
        protected boolean canNavigate()
        {
            return !this.entity.isRiding();
        }
    }

}
