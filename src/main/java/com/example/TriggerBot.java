import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class TriggerBot extends Module {

    private int attackDelay = 0;

    public TriggerBot() {
        super("TriggerBot");
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        // Искусственная задержка (Cooldown), чтобы не бить каждый тик
        if (attackDelay > 0) {
            attackDelay--;
            return;
        }

        // Проверяем, на что смотрит игрок в данный момент
        HitResult target = mc.crosshairTarget;

        if (target != null && target.getType() == HitResult.Type.ENTITY) {
            // Если прицел на сущности, получаем её объект
            Entity entityTarget = ((EntityHitResult) target).getEntity();
            
            // Наносим удар
            mc.interactionManager.attackEntity(mc.player, entityTarget);
            mc.player.swingHand(Hand.MAIN_HAND);
            
            // Устанавливаем задержку (например, 10 тиков)
            attackDelay = 10;
        }
    }
}
