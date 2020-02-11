package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.*;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketAerbunnySetRiding;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import java.util.Set;

public class EntityAerbunny extends EntityAetherAnimal
{
    private static final Set<Item> TEMPTATION_ITEMS = Sets
            .newHashSet(Items.CARROT, Items.POTATO, Items.BEETROOT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry,
                    ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);

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

        this.tasks.addTask(2, new EntityAIRestrictRain(this));
        this.tasks.addTask(3, new EntityAIUnstuckBlueAercloud(this));
        this.tasks.addTask(3, new EntityAIHideFromRain(this, 1.3D));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(3, new EntityAIEggnogTempt(this, 2.2D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 12.0F, 1.2F, 1.8F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D, 10));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

        this.jumpHelper = new AerbunnyJumpHelper(this);

        this.spawnableBlock = BlocksAether.aether_grass;

        this.setSize(0.65F, 0.65F);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return super.getBlockPathWeight(pos);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);

        //this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(2);
        //this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(4);
        //this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
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

        if (this.isRiding()) {
            final Entity entity = this.getRidingEntity();

            if (!this.world.isRemote) {
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
        final ItemStack stack = player.getHeldItem(hand);

        if (!super.processInteract(player, hand) && !this.isBreedingItem(stack)) {
            if (!this.isRiding() && player.getPassengers().size() <= 0) {
                if (!this.world.isRemote) {
                    this.startRiding(player, true);

                    NetworkingAether.sendPacketToWatching(new PacketAerbunnySetRiding(player, this), this, false);
                }

                return true;
            } else {
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
        return new AerbunnyNavigator(this, worldIn);
    }

    @Override
    public EntityAgeable createChild(final EntityAgeable ageable)
    {
        return new EntityAerbunny(this.world);
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
    public boolean canRiderInteract()
    {
        return true;
    }

    @Override
    public boolean isBreedingItem(@Nullable final ItemStack stack)
    {
        return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
    }

    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return !this.isRiding() && super.isEntityInsideOpaqueBlock();
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
