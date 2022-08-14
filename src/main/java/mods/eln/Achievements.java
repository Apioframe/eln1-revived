package mods.eln;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import static mods.eln.i18n.I18N.*;

public class Achievements {

    public static Achievement openGuide;
    public static Achievement craft50VSocket;
    public static Achievement craft50VMacerator;
    public static AchievementPage achievementPageEln;

    public static void init() {

        TR_DESC(Type.ACHIEVEMENT, "open_guide");

        craft50VMacerator = new Achievement(TR("achievement.craft_50v_macerator"),
            "craft_50v_macerator", 0, 2, Eln.findItemStack("50V Macerator", 0), openGuide);

        TR_DESC(Type.ACHIEVEMENT, "craft_50v_macerator");*/

        craft50VSocket = new Achievement(TR("achievement.craft_50v_socket"),
            "craft_50v_socket", 2, 2, Eln.findItemStack("50V Power Socket", 0), craft50VMacerator);

        TR_DESC(Type.ACHIEVEMENT, "craft_50v_socket");

        achievementPageEln = new AchievementPage(tr("Electrical Age [WIP]"),
            openGuide, craft50VMacerator, craft50VSocket);

        AchievementPage.registerAchievementPage(achievementPageEln);
    }
}
