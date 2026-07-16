package com.hsmourait.valid_attack;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

/**
 * 自动攻击的核心逻辑。四个功能都在这里实现。
 * <p>
 * 功能一 — 拦截不满蓄力的攻击键单击，抑制挥臂动画。
 * <p>
 * 功能二 — 按住攻击键自动攻击。
 * <p>
 * 功能三 — 有效性检查：攻击前确认范围内有可攻击目标。
 * <p>
 * 功能四 — 瞄准辅助：扫描身前 120° 锥形区域，不要求准星对准。
 */
@EventBusSubscriber(modid = ValidAttack.MODID, value = Dist.CLIENT)
public class AutoAttackHandler {

    // ═══════════════════════════════════════
    // 功能一：拦截不满蓄力的攻击键单击
    // ═══════════════════════════════════════

    @SubscribeEvent
    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) return;

        if (Config.INTERCEPT_CLICK.getAsBoolean()) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player == null) return;

            if (player.getAttackStrengthScale(0.0f) < 1.0f) {
                event.setCanceled(true);
                event.setSwingHand(false);
            }
        }
    }

    // ═══════════════════════════════════════
    // 功能二、三、四：按住自动攻击 & 目标选择
    // ═══════════════════════════════════════

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        if (!Config.AUTO_ATTACK_HOLD.getAsBoolean()) return;

        // ── 判断"按住"状态 ──
        boolean keyDown = mc.options.keyAttack.isDown();
        boolean isHeld = keyDown && wasKeyDown;
        wasKeyDown = keyDown;

        if (!isHeld) return;

        // 蓄力没满就等下一 tick
        if (player.getAttackStrengthScale(0.0f) < 1.0f) return;

        // ── 选择攻击目标 ──
        Entity target = null;

        if (Config.AIM_ASSIST.getAsBoolean()) {
            // 功能四：瞄准辅助 — 扫描身前 120° 锥形区域
            target = findTargetInCone(player);
        } else {
            // 默认：准星指向
            if (mc.hitResult instanceof EntityHitResult entityHit) {
                target = entityHit.getEntity();
            }
        }

        // 功能三：有效性检查 — 必须要有 LivingEntity 目标
        if (Config.REQUIRE_TARGET.getAsBoolean()) {
            if (!(target instanceof LivingEntity)) {
                return;
            }
        }

        // 如果找到了目标，发起攻击
        if (target != null && mc.gameMode != null) {
            player.resetAttackStrengthTicker();
            mc.gameMode.attack(player, target);
            player.swing(InteractionHand.MAIN_HAND);
        }
        // target == null 且 requireTarget == OFF → 空挥
    }

    // ═══════════════════════════════════════
    // 辅助方法：120° 锥形扫描
    // ═══════════════════════════════════════

    /**
     * 在玩家面前 120° 锥形区域内寻找距离最近的 {@link LivingEntity}。
     * 扫描半径使用 {@link LocalPlayer#entityInteractionRange()}，
     * 返回 null 表示没有可攻击目标。
     */
    private static LivingEntity findTargetInCone(LocalPlayer player) {
        if (player == null || player.level() == null) return null;

        double range = player.entityInteractionRange();
        Vec3 eyePos = player.getEyePosition(1.0f);
        Vec3 lookVec = player.getLookAngle().normalize();

        // 1) 以 range 为半径构建包围盒，快速筛选候选实体
        AABB searchBox = player.getBoundingBox().inflate(range, range, range);
        Predicate<Entity> filter = e -> e instanceof LivingEntity living
                && living.isAlive()
                && living != player
                && !living.isRemoved();

        List<LivingEntity> candidates = player.level().getEntitiesOfClass(
                LivingEntity.class, searchBox,
                filter
        );

        if (candidates.isEmpty()) return null;

        // 2) 计算 120° 锥形的半角余弦 (cos(60°) = 0.5)
        final double COS_HALF_ANGLE = 0.5;

        // 3) 过滤并排序：筛选锥形内的实体，按距离升序排列
        return candidates.stream()
                .filter(e -> {
                    Vec3 toTarget = e.position().subtract(eyePos).normalize();
                    return lookVec.dot(toTarget) >= COS_HALF_ANGLE;
                })
                .min(Comparator.comparingDouble(
                        e -> e.distanceToSqr(player)))
                .orElse(null);
    }

    /** 跨 tick 跟踪按键状态，用于判断"按住" vs "新按下"。 */
    private static boolean wasKeyDown = false;
}