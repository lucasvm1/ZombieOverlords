package com.lucasvm.zombieoverlords.events;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "zombieoverlords")
public class ZombieEvents {

    @SubscribeEvent
    public static void onZombieTick(LivingEvent.LivingTickEvent event) {
        // Check if the entity is a zombie
        if (event.getEntity() instanceof Zombie zombie) {
            Level world = zombie.level();

            // Is it daytime and is the zombie on fire?
            if (zombie.isOnFire() && world.isDay()) {
                BlockPos position = zombie.blockPosition();

                // Make sure the zombie is actually under the sky (not burning for other reasons)
                if (world.canSeeSky(position)) {
                    // It's probably the sun causing the fire, so let's put it out
                    // This is what makes my zombies special - they don't fear the daylight!
                    zombie.clearFire();
                }
            }
        }
    }
}