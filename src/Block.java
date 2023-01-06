
/* 2048游戏方块类 */

import javafx.scene.control.Label;
import javafx.scene.text.Font;

// 继承label 字体接口
public class Block extends Label implements GameFont{
    public int data;
 
    // 构造方法
    public Block() {
        Clear();
    }
	
    // 清空数据
    public void Clear() {
        data = 0;
    }
	
    // 获得字体颜色
    public String getForegroundColor() {
        switch (data) {
            case 0:
                return "#cdc1b4;";
            case 2:
            case 4:
                return "BLACK;";
            default:
                return "WHITE;";
        }
    }
	
    // 获得背景颜色
    public String getBackgroundColor() {
        switch (data) {
            case 0:
                return "#cdc1b4;";
            case 2:
                return "#eee4da;";
            case 4:
                return "#ede0c8;";
            case 8:
                return "#f2b179;";
            case 16:
                return "#f59563;";
            case 32:
                return "#f67c5f;";
            case 64:
                return "#f65e3b;";
            case 128:
                return "#edcf72;";
            case 256:
                return "#edcc61;";
            case 512:
                return "#edc850;";
            case 1024:
                return "#edc53f;";
            case 2048:
                return "#edc22e;";
            case 4096:
                return "#65da92;";
            case 8192:
                return "#5abc65;";
            case 16384:
                return "#248c51;";
            default:
                return "#248c51;";
        }
    }
	
    // 获得字体样式
	public Font getCheckFont() {
		if (data < 10) {
			return font1;
		}
		if (data < 100) {
			return font2;
		}
		if (data < 1000) {
			return font3;
		}
		if (data < 10000) {
			return font4;
		}
        return font5;
	}
}
