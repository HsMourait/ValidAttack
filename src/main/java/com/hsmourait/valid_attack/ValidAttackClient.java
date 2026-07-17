package com.hsmourait.valid_attack;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ValidAttack.MODID, value = Dist.CLIENT)
public class ValidAttackClient {
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ValidAttack.LOGGER.info("HELLO FROM CLIENT SETUP");
        ValidAttack.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
