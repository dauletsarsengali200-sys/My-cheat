package com.example;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {

    private Category selectedCategory = Category.COMBAT;

    public ClickGuiScreen() {
        super(Text.literal("Delta GUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int x = this.width / 2 - 180;
        int y = this.height / 2 - 120;
        int width = 360;
        int height = 240;

        // Основной фон (Темно-серый)
        context.fill(x, y, x + width, y + height, 0xF2121212);
        // Верхний шапка бренда Delta
        context.fill(x, y, x + width, y + 30, 0xFF1C1C1E);
        context.drawText(this.textRenderer, "DELTA", x + 12, y + 10, 0xFF3B82F6, true);
        context.drawText(this.textRenderer, "v1.0", x + 50, y + 10, 0xFF8E8E93, false);

        // Боковая панель категорий (Sidebar)
        context.fill(x, y + 30, x + 90, y + height, 0xFF18181A);
        int catY = y + 40;
        for (Category cat : Category.values()) {
            boolean isSelected = (cat == selectedCategory);
            int catColor = isSelected ? 0xFF3B82F6 : 0xFF8E8E93;
            
            if (isSelected) {
                context.fill(x + 2, catY - 2, x + 88, catY + 16, 0x203B82F6);
                context.fill(x + 2, catY - 2, x + 5, catY + 16, 0xFF3B82F6); // Активный индикатор слева
            }
            
            context.drawText(this.textRenderer, cat.getName(), x + 12, catY + 2, catColor, false);
            catY += 24;
        }

        // Область модулей
        int modX = x + 100;
        int modY = y + 40;

        for (Module mod : ModuleManager.modules) {
            if (mod.getCategory() == selectedCategory) {
                // Карточка модуля
                int cardBg = mod.isToggled() ? 0xFF242428 : 0xFF1C1C1E;
                int borderColor = mod.isToggled() ? 0xFF3B82F6 : 0xFF2C2C2E;
                
                context.fill(modX, modY, modX + 245, modY + 30, cardBg);
                // Обводка карточки
                context.fill(modX, modY, modX + 245, modY + 1, borderColor);
                context.fill(modX, modY + 29, modX + 245, modY + 30, borderColor);

                // Название модуля
                context.drawText(this.textRenderer, mod.getName(), modX + 10, modY + 11, 0xFFFFFFFF, false);

                // Переключатель (Toggle Switch)
                int switchX = modX + 205;
                int switchY = modY + 8;
                int switchBg = mod.isToggled() ? 0xFF3B82F6 : 0xFF3A3A3C;
                
                context.fill(switchX, switchY, switchX + 30, switchY + 14, switchBg);
                int knobX = mod.isToggled() ? switchX + 16 : switchX + 2;
                context.fill(knobX, switchY + 2, knobX + 12, switchY + 12, 0xFFFFFFFF);

                modY += 35;
            }
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Левая кнопка мыши
            int x = this.width / 2 - 180;
            int y = this.height / 2 - 120;

            // Клик по категориям
            int catY = y + 40;
            for (Category cat : Category.values()) {
                if (mouseX >= x && mouseX <= x + 90 && mouseY >= catY - 2 && mouseY <= catY + 18) {
                    selectedCategory = cat;
                    return true;
                }
                catY += 24;
            }

            // Клик по модулям
            int modX = x + 100;
            int modY = y + 40;
            for (Module mod : ModuleManager.modules) {
                if (mod.getCategory() == selectedCategory) {
                    if (mouseX >= modX && mouseX <= modX + 245 && mouseY >= modY && mouseY <= modY + 30) {
                        mod.toggle();
                        return true;
                    }
                    modY += 35;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false; // Игра не ставится на паузу при открытии GUI
    }
}
