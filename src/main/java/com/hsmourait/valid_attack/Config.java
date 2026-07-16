package com.hsmourait.valid_attack;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ValidAttack.MODID, value = Dist.CLIENT)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue INTERCEPT_CLICK = BUILDER
            .comment("Cancel attack clicks when the attack strength bar is not full.")
            .translation("valid_attack.configuration.interceptClick")
            .define("interceptClick", true);

    public static final ModConfigSpec.BooleanValue AUTO_ATTACK_HOLD = BUILDER
            .comment("Auto-attack while holding down the attack key and the strength bar is full.")
            .translation("valid_attack.configuration.autoAttackHold")
            .define("autoAttackHold", true);

    public static final ModConfigSpec.BooleanValue REQUIRE_TARGET = BUILDER
            .comment("Only attack when there is a valid living entity target (used by aim assist or crosshair).")
            .translation("valid_attack.configuration.requireTarget")
            .define("requireTarget", false);

    public static final ModConfigSpec.BooleanValue AIM_ASSIST = BUILDER
            .comment("Scan a 120° cone in front of the player and attack the nearest entity. " +
                     "When enabled together with requireTarget, the check covers the entire cone " +
                     "instead of only the crosshair line.")
            .translation("valid_attack.configuration.aimAssist")
            .define("aimAssist", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        ValidAttack.LOGGER.info("Config loaded: interceptClick={}, autoAttackHold={}, requireTarget={}, aimAssist={}",
                INTERCEPT_CLICK.get(), AUTO_ATTACK_HOLD.get(), REQUIRE_TARGET.get(), AIM_ASSIST.get());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        ValidAttack.LOGGER.info("Config reloaded: interceptClick={}, autoAttackHold={}, requireTarget={}, aimAssist={}",
                INTERCEPT_CLICK.get(), AUTO_ATTACK_HOLD.get(), REQUIRE_TARGET.get(), AIM_ASSIST.get());
    }
}