
/* 游戏字体接口 */

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public interface GameFont {
    Font TitleFont = Font.font("微软雅黑", 40);
    Font LabelFont = Font.font("微软雅黑", FontWeight.BOLD,15);
    Font BlockFont = Font.font("微软雅黑", 20);
    Font RuleFont = Font.font("微软雅黑", 15);
    Font RankingFont = Font.font("微软雅黑", FontWeight.BOLD, 25);

    Font font1 = Font.font("宋体", 46);
    Font font2 = Font.font("宋体", 40);
    Font font3 = Font.font("宋体", 34);
    Font font4 = Font.font("宋体", 28);
    Font font5 = Font.font("宋体", 22);
}
