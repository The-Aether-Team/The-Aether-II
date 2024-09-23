package com.aetherteam.aetherii.entity.npc;

import com.google.common.collect.Lists;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;

public abstract class MerchantEntity extends NpcEntity implements Merchant {
    @Nullable
    private Player tradingPlayer;
    @Nullable
    protected MerchantOffers offers;

    public MerchantEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.is(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isTrading()) {
            if (!this.level().isClientSide()) {
                if (this.getOffers().isEmpty()) {
                    return InteractionResult.CONSUME;
                }

                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide());
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void setTradingPlayer(@Nullable Player tradingPlayer) {
        this.tradingPlayer = tradingPlayer;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.level().isClientSide()) {
            throw new IllegalStateException("Cannot load Villager offers on the client");
        } else {
            if (this.offers == null) {
                this.offers = new MerchantOffers();
                this.updateTrades();
            }

            return this.offers;
        }
    }

    protected abstract void updateTrades();

    protected void addOffersFromItemListings(MerchantOffers merchantOffers, MerchantTrades.ItemListing[] newTrades, int maxNumbers) {
        ArrayList<MerchantTrades.ItemListing> arraylist = Lists.newArrayList(newTrades);
        int i = 0;
        while (i < maxNumbers && !arraylist.isEmpty()) {
            MerchantOffer randomOffer = arraylist.remove(this.random.nextInt(arraylist.size())).getOffer(this, this.random);
            if (randomOffer != null) {
                merchantOffers.add(randomOffer);
                i++;
            }
        }
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {

    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
        if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.makeSound(this.getTradeUpdatedSound(!stack.isEmpty()));
        }
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int xp) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    protected SoundEvent getTradeUpdatedSound(boolean isYesSound) {
        return isYesSound ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide();
    }

    public static class TradeWithPlayerGoal extends Goal {
        private final MerchantEntity mob;

        public TradeWithPlayerGoal(MerchantEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!this.mob.isAlive()) {
                return false;
            } else if (this.mob.isInWater()) {
                return false;
            } else if (!this.mob.onGround()) {
                return false;
            } else if (this.mob.hurtMarked) {
                return false;
            } else {
                Player player = this.mob.getTradingPlayer();
                if (player == null) {
                    return false;
                } else {
                    return !(this.mob.distanceToSqr(player) > 16.0);
                }
            }
        }

        @Override
        public void start() {
            this.mob.getNavigation().stop();
        }

        @Override
        public void stop() {
            this.mob.setTradingPlayer(null);
        }
    }

    public static class LookAtTradingPlayerGoal extends LookAtPlayerGoal {
        private final MerchantEntity mob;

        public LookAtTradingPlayerGoal(MerchantEntity mob) {
            super(mob, Player.class, 8.0F);
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            if (this.mob.isTrading()) {
                this.lookAt = this.mob.getTradingPlayer();
                return true;
            } else {
                return false;
            }
        }
    }
}
