import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class KillAura extends Module {
    
    private final float range = 4.0f; // Радиус атаки в блоках

    public KillAura() {
        super("KillAura");
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        // Перебираем всех сущностей в мире
        for (Entity entity : mc.world.getEntities()) {
            // Пропускаем самого себя и неживых существ
            if (entity == mc.player || !(entity instanceof LivingEntity)) continue;

            // Проверяем дистанцию до цели
            if (mc.player.distanceTo(entity) <= range) {
                
                // Простейшая логика атаки
                attackTarget(entity);
                
                // Прерываем цикл, чтобы бить только одну цель за тик (Single Target)
                break; 
            }
        }
    }

    private void attackTarget(Entity target) {
        // Отправляем пакет атаки на сервер
        mc.interactionManager.attackEntity(mc.player, target);
        // Воспроизводим анимацию взмаха руки
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
