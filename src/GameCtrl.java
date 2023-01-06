
/* 2048游戏后台处理 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameCtrl {
    private int score ;
    private int bestscore;
    private String username;
    public boolean isLogin ;
    public Block[][] GameBlock = new Block[4][4];   // 游戏方块界面4*4
    private boolean isadd;   // 判断每次操作后是否需要生成新方块

    public GameCtrl() {
        initGame();
    }

    // 设置正在登陆的用户名
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    // 获取当前分数
    public int getScore() {
        return score;
    }

    // 获取文件中用户历史最高分
    public int getBestScore() {
        if (isLogin) {
            LoginCtrl.LoadUser();
            return LoginCtrl.findAccount(username).getBestScore();
        }
        return 0;
    }

    // 获取当前进行游戏的最高分
    public int getCurrBestScore() {
        return bestscore;
    }
        
    // 初始化游戏
    public void initGame() {
        score = 0;
        bestscore = getBestScore();
        isLogin = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                GameBlock[i][j] = new Block();
            }
        }
        // 生成两个新方块
        isadd = true;
        createBlock();
        isadd = true;
        createBlock();
    }

    // 重置游戏
    public void ResetGame() {
        score = 0;
        bestscore = getBestScore();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                GameBlock[i][j].data = 0;
            }
        }
        // 生成两个数
        isadd = true;
        createBlock();
        isadd = true;
        createBlock();
    }

    // 生成新的方块
    public void createBlock() {
        // 找到空的位置
        List<Block> list = getEmptyBlocks();
        if (!list.isEmpty() && isadd) {
            Random random = new Random();
            int index = random.nextInt(list.size());
            Block EmptyBlock = list.get(index);
            // 2, 4出现概率3:1
            EmptyBlock.data = (random.nextInt(4) % 3 == 0) ? 2 : 4;
            isadd = false;
        }
    }

    // 获取空白方块位置
    public List<Block> getEmptyBlocks() {
        List<Block> BlockList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (GameBlock[i][j].data == 0) {
                    BlockList.add(GameBlock[i][j]);
                }
            }
        }
        return BlockList;
    }
    
    // 更新每步操作后的数据
    public void UpdateData() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                GameBlock[i][j].setText(GameBlock[i][j].data + "");
                GameBlock[i][j].setFont(GameBlock[i][j].getBlockFont());
                GameBlock[i][j].setStyle(
                        "-fx-text-fill: " + GameBlock[i][j].getForegroundColor() +
                        "-fx-background-color: " + GameBlock[i][j].getBackgroundColor() +
                        "-fx-background-radius:10;" +
                        "-fx-border-style: solid;" +
                        "-fx-border-color: #eee4da;" +
                        " -fx-border-width: 3;" +
                        " -fx-border-radius: 10");
            }
        }
        // 若登录，更新最高分
        if (isLogin) {
            if (score > bestscore) {
                bestscore = score;
            }
        }
    }
    
    // 更新用户最高分进文件
    public void UpdataUser() {
        if (isLogin) {
            LoginCtrl.updateBestScore(username, bestscore);
        }
    }

    // 操作游戏上移
    public boolean moveUp() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1, index = 0; j < 4; j++) {
                if (GameBlock[i][j].data > 0) {
                    // 遍历各元素 该方向有相同的就合并，分数改变。是0则直接替换
                    if (GameBlock[i][j].data == GameBlock[i][index].data) {
                        score += GameBlock[i][index++].data <<= 1;
                        GameBlock[i][j].data = 0;
                        isadd = true;
                    } else if (GameBlock[i][index].data == 0) {
                        GameBlock[i][index].data = GameBlock[i][j].data;
                        GameBlock[i][j].data = 0;
                        isadd = true;
                    } else if (GameBlock[i][++index].data == 0) {
                        GameBlock[i][index].data = GameBlock[i][j].data;
                        GameBlock[i][j].data = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    // 操作游戏下移
    public boolean moveDown() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2, index = 3; j >= 0; j--) {
                if (GameBlock[i][j].data > 0) {
                    if (GameBlock[i][j].data == GameBlock[i][index].data) {
                        score += GameBlock[i][index--].data <<= 1;
                        GameBlock[i][j].data = 0;
                        isadd = true;
                    } else if (GameBlock[i][index].data == 0) {
                        GameBlock[i][index].data = GameBlock[i][j].data;
                        GameBlock[i][j].data = 0;
                        isadd = true;
                    } else if (GameBlock[i][--index].data == 0) {
                        GameBlock[i][index].data = GameBlock[i][j].data;
                        GameBlock[i][j].data = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    // 操作游戏左移
    public boolean moveLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1, index = 0; j < 4; j++) {
                if (GameBlock[j][i].data > 0) {
                    if (GameBlock[j][i].data == GameBlock[index][i].data) {
                        score += GameBlock[index++][i].data <<= 1;
                        GameBlock[j][i].data = 0;
                        isadd = true;
                    } else if (GameBlock[index][i].data == 0) {
                        GameBlock[index][i].data = GameBlock[j][i].data;
                        GameBlock[j][i].data = 0;
                        isadd = true;
                    } else if (GameBlock[++index][i].data == 0) {
                        GameBlock[index][i].data = GameBlock[j][i].data;
                        GameBlock[j][i].data = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    // 操作游戏右移
    public boolean moveRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2, index = 3; j >= 0; j--) {
                if (GameBlock[j][i].data > 0) {
                    if (GameBlock[j][i].data == GameBlock[index][i].data) {
                        score += GameBlock[index--][i].data <<= 1;
                        GameBlock[j][i].data = 0;
                        isadd = true;
                    } else if (GameBlock[index][i].data == 0) {
                        GameBlock[index][i].data = GameBlock[j][i].data;
                        GameBlock[j][i].data = 0;
                        isadd = true;
                    } else if (GameBlock[--index][i].data == 0) {
                        GameBlock[index][i].data = GameBlock[j][i].data;
                        GameBlock[j][i].data = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    // 判断游戏是否结束
    public boolean judgeGameOver() {
        // 有空格子，游戏未结束
        if (!getEmptyBlocks().isEmpty()) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //判断是否存在可合并的方格，存在则游戏未结束
                if (GameBlock[i][j].data == GameBlock[i][j + 1].data
                        || GameBlock[i][j].data == GameBlock[i + 1][j].data)
                    return false;
            }
        }
        
        // 特别判断格子(3,3)
        if (GameBlock[3][3].data == GameBlock[3][2].data || GameBlock[3][3].data == GameBlock[2][3].data)
            return false;

        // 特别判断格子(1,3)(3,1)
        if (GameBlock[3][1].data == GameBlock[3][2].data || GameBlock[3][1].data == GameBlock[3][0].data)
            return false;
        if (GameBlock[1][3].data == GameBlock[2][3].data || GameBlock[1][3].data == GameBlock[0][3].data)
            return false;

        // 游戏结束
        return true;
    }
}
