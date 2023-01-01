package net.kuwulkid94.kuwulkid.item.custom;

import com.google.common.collect.ImmutableMap;
import net.kuwulkid94.kuwulkid.item.ModArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, StatusEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterials.bone,
                            new StatusEffectInstance(StatusEffects.BLINDNESS, 400, 1)).build();

    public ModArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity)entity;

                //if(hasFullSuitOfArmorOn(player)) {
                    //evaluateArmorEffects(player);
                //}

                if(isWearingBone(player))
                {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * (int)2), null);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * (int)2), null);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean isWearingBone(PlayerEntity player) {
        Item helmet = (player.getInventory().getArmorStack(3).getItem());
        if(helmet.toString().equals("skull_mask"))
        {
            return true;
        }
        return false;
    }

    //private void evaluateArmorEffects(PlayerEntity player) {
        //for (Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            //ArmorMaterial mapArmorMaterial = entry.getKey();
            //StatusEffectInstance mapStatusEffect = entry.getValue();

            //if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                //addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            //}
        //}
    //}

    //private void addStatusEffectForMaterial(PlayerEntity player, ArmorMaterial mapArmorMaterial, StatusEffectInstance mapStatusEffect) {
        //boolean hasPlayerEffect = player.hasStatusEffect(mapStatusEffect.getEffectType());

        //if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            //player.addStatusEffect(new StatusEffectInstance(mapStatusEffect.getEffectType(),
                    //mapStatusEffect.getDuration(), mapStatusEffect.getAmplifier()));

            // if(new Random().nextFloat() > 0.6f) { // 40% of damaging the armor! Possibly!
            //     player.getInventory().damageArmor(DamageSource.MAGIC, 1f, new int[]{0, 1, 2, 3});
            // }
        //}
    //}

    //private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        //ItemStack boots = player.getInventory().getArmorStack(0);
        //ItemStack leggings = player.getInventory().getArmorStack(1);
        //ItemStack breastplate = player.getInventory().getArmorStack(2);
        //ItemStack helmet = player.getInventory().getArmorStack(3);

        //return !helmet.isEmpty() && !breastplate.isEmpty()
                //&& !leggings.isEmpty() && !boots.isEmpty();
    //}

    //private boolean hasCorrectArmorOn(ArmorMaterial material, PlayerEntity player) {
        //ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        //ArmorItem leggings = ((ArmorItem)player.getInventory().getArmorStack(1).getItem());
        //ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
        //ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());

        //return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                //leggings.getMaterial() == material && boots.getMaterial() == material;
    //}

    //--line--//

    private boolean hasBoneHelm(PlayerEntity player){
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());
        return helmet.getMaterial() == ModArmorMaterials.bone;
    }
//https://open.spotify.com/track/2A9BLDVuT6lfbRK3ZKBCFz?si=17843be0de134e0c
}