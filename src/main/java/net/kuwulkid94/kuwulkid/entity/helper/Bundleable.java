//
// Source code from Bucketable.class, decompiled from a .class file by IntelliJ IDEA
//

package net.kuwulkid94.kuwulkid.entity.helper;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Optional;

public interface Bundleable {
    boolean isFromBundle();

    void setFromBundle(boolean fromBundle);

    void copyDataToStack(ItemStack stack);

    void copyDataFromNbt(NbtCompound nbt);

    ItemStack getBundleItem();

    SoundEvent getBundleedSound();

    /** @deprecated */
    @Deprecated
    static void copyDataToStack(MobEntity entity, ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (entity.hasCustomName()) {
            stack.setCustomName(entity.getCustomName());
        }

        if (entity.isAiDisabled()) {
            nbtCompound.putBoolean("NoAI", entity.isAiDisabled());
        }

        if (entity.isSilent()) {
            nbtCompound.putBoolean("Silent", entity.isSilent());
        }

        if (entity.hasNoGravity()) {
            nbtCompound.putBoolean("NoGravity", entity.hasNoGravity());
        }

        if (entity.isGlowingLocal()) {
            nbtCompound.putBoolean("Glowing", entity.isGlowingLocal());
        }

        if (entity.isInvulnerable()) {
            nbtCompound.putBoolean("Invulnerable", entity.isInvulnerable());
        }

        nbtCompound.putFloat("Health", entity.getHealth());
    }

    /** @deprecated */
    @Deprecated
    static void copyDataFromNbt(MobEntity entity, NbtCompound nbt) {
        if (nbt.contains("NoAI")) {
            entity.setAiDisabled(nbt.getBoolean("NoAI"));
        }

        if (nbt.contains("Silent")) {
            entity.setSilent(nbt.getBoolean("Silent"));
        }

        if (nbt.contains("NoGravity")) {
            entity.setNoGravity(nbt.getBoolean("NoGravity"));
        }

        if (nbt.contains("Glowing")) {
            entity.setGlowing(nbt.getBoolean("Glowing"));
        }

        if (nbt.contains("Invulnerable")) {
            entity.setInvulnerable(nbt.getBoolean("Invulnerable"));
        }

        if (nbt.contains("Health", 99)) {
            entity.setHealth(nbt.getFloat("Health"));
        }

    }

    static <T extends LivingEntity & Bundleable> Optional<ActionResult> tryBundle(PlayerEntity player, Hand hand, T entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.BUNDLE && entity.isAlive()) {
            entity.playSound(((Bundleable)entity).getBundleedSound(), 1.0F, 1.0F);
            ItemStack itemStack2 = ((Bundleable)entity).getBundleItem();
            ((Bundleable)entity).copyDataToStack(itemStack2);
            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, false);
            player.setStackInHand(hand, itemStack3);
            World world = entity.world;
            if (!world.isClient) {
                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemStack2);
            }

            entity.discard();
            return Optional.of(ActionResult.success(world.isClient));
        } else {
            return Optional.empty();
        }
    }
}
