package net.kuwulkid94.kuwulkid.sounds;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static SoundEvent CROW_IDLE = registerSoundEvent("crow_idle");
    public static SoundEvent CROW_IDLE_0 = registerSoundEvent("crow_idle_0");
    public static SoundEvent CROW_IDLE_1 = registerSoundEvent("crow_idle_1");
    public static SoundEvent CROW_IDLE_2 = registerSoundEvent("crow_idle_2");
    public static SoundEvent CROW_IDLE_3 = registerSoundEvent("crow_idle_3");
    public static SoundEvent CROW_HURT = registerSoundEvent("crow_hurt");
    public static SoundEvent CROW_DEATH = registerSoundEvent("crow_death");
    public static SoundEvent SCORPION_HURT = registerSoundEvent("scorpion_hurt");
    public static SoundEvent SCORPION_IDLE = registerSoundEvent("scorpion_idle");
    public static SoundEvent SCORPION_WALK = registerSoundEvent("scorpion_walk");
    public static SoundEvent SCORPION_ATTACK = registerSoundEvent("scorpion_attack");
    public static SoundEvent SHRIMP_IDLE = registerSoundEvent("shrimp_idle");
    public static SoundEvent FIREBOWL_WOOSH = registerSoundEvent("firebowl_woosh");
    public static SoundEvent FIREBOWL_WOOSH_1 = registerSoundEvent("firebowl_woosh_1");
    public static SoundEvent FIREBOWL_WOOSH_2 = registerSoundEvent("firebowl_woosh_2");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(JustaFantasyAddon.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));

    }

    public static void registerSounds(){
        System.out.println("Registering ModSounds for " + JustaFantasyAddon.MOD_ID);
    }
}
