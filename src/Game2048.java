
/* 2048游戏前端处理类 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// 游戏类 字体接口
public class Game2048 extends Application implements GameFont {
    private static final int GAME_WIDTH = 400;
    private static final int GAME_HEIGHT = 500;

    private GameCtrl Ctrl = new GameCtrl();     // 2048游戏后台控制器
    private LoginPage login = new LoginPage();  // 登录系统

    private GridPane GamePane = new GridPane();
    private StackPane BackPane = new StackPane();
    private Button btReset = new Button("Reset");
    private Button btRankings = new Button("Rankings");
    private Button btLogin = new Button("Login");
    private Button btLogout = new Button("LogOut");
    private Button btRules = new Button("Rules");

    private Label GameTitle = new Label("2048");
    private Label ScoreBoard = new Label("Score");
    private Label Score = new Label();
    private Label UserBoard = new Label("User");
    private Label Username = new Label();
    private Label BestScore = new Label();
    private Label BestScoreBoard = new Label("Best");
    private Group g = new Group();

    private void InitGame() {
        // 设置标题
        GameTitle.setAlignment(Pos.CENTER);
        GameTitle.setFont(TitleFont);
        GameTitle.setLayoutX(10);
        GameTitle.setLayoutY(0);
        GameTitle.setPrefSize(150, 60);

        // 设置背景
        BackPane.setStyle("-fx-background-color: #eee4da;");
        BackPane.setPrefSize(1000, 1000);

        // 设置各标签
        ScoreBoard.setAlignment(Pos.CENTER);
        ScoreBoard.setFont(LabelFont);
        ScoreBoard.setPrefSize(60, 30);
        ScoreBoard.setLayoutX(250);
        ScoreBoard.setLayoutY(0);
        ScoreBoard.setStyle("-fx-background-color: #f2b179;");

        BestScoreBoard.setAlignment(Pos.CENTER);
        BestScoreBoard.setFont(LabelFont);
        BestScoreBoard.setPrefSize(60, 30);
        BestScoreBoard.setLayoutX(320);
        BestScoreBoard.setLayoutY(0);
        BestScoreBoard.setStyle("-fx-background-color: #f2b179;");

        Score.setText("" + Ctrl.getScore());
        Score.setLayoutX(250);
        Score.setLayoutY(30);
        Score.setPrefSize(60, 30);
        Score.setAlignment(Pos.CENTER);
        Score.setStyle("-fx-background-color: #ede0c8;");

        BestScore.setText("Null");
        BestScore.setLayoutX(320);
        BestScore.setLayoutY(30);
        BestScore.setPrefSize(60, 30);
        BestScore.setAlignment(Pos.CENTER);
        BestScore.setStyle("-fx-background-color: #ede0c8;");

        UserBoard.setLayoutX(180);
        UserBoard.setLayoutY(0);
        UserBoard.setPrefSize(60, 30);
        UserBoard.setFont(LabelFont);
        UserBoard.setAlignment(Pos.CENTER);
        UserBoard.setStyle("-fx-background-color: #f2b179;");

        Username.setLayoutX(180);
        Username.setLayoutY(30);
        Username.setPrefSize(60, 30);
        Username.setAlignment(Pos.CENTER);
        Username.setStyle("-fx-background-color: #ede0c8;");
        Username.setText("未登录");

        // 游戏界面
        GamePane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        GamePane.setHgap(5.5);
        GamePane.setVgap(5.5);
        GamePane.setStyle("-fx-background-color: GRAY;");
        GamePane.setLayoutX(0);
        GamePane.setLayoutY(100);
        GamePane.setPrefSize(400, 400);

        // 设置各个方块
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Ctrl.GameBlock[i][j].setStyle(
                        "-fx-text-fill: " + Ctrl.GameBlock[i][j].getForegroundColor() +
                        "-fx-background-color: " + Ctrl.GameBlock[i][j].getBackgroundColor() +
                        "-fx-background-radius:10;" +
                        "-fx-border-style: solid;" +
                        "-fx-border-color: #eee4da;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 10");
                Ctrl.GameBlock[i][j].setFont(Ctrl.GameBlock[i][j].getCheckFont());
                Ctrl.GameBlock[i][j].setPrefSize(90, 90);
                Ctrl.GameBlock[i][j].setText(Ctrl.GameBlock[i][j].data + "");
                Ctrl.GameBlock[i][j].setAlignment(Pos.CENTER);
                GamePane.add(Ctrl.GameBlock[i][j], i, j);
            }
        }

        // 设置按钮
        btReset.setLayoutX(100);
        btReset.setLayoutY(65);
        btReset.setStyle("-fx-background-color: #f67c5f;");

        btRules.setLayoutX(25);
        btRules.setLayoutY(65);
        btRules.setStyle("-fx-background-color: #f67c5f;");

        btRankings.setLayoutX(280);
        btRankings.setLayoutY(65);
        btRankings.setStyle("-fx-background-color: #f67c5f;");

        btLogin.setLayoutX(190);
        btLogin.setLayoutY(65);
        btLogin.setStyle("-fx-background-color: #f67c5f;");

        btLogout.setLayoutX(190);
        btLogout.setLayoutY(65);
        btLogout.setStyle("-fx-background-color: #f67c5f;");

        // 添加各个元素
        g.getChildren().addAll(BackPane, GamePane, ScoreBoard, Score, GameTitle, btReset, UserBoard, Username,
                BestScoreBoard, BestScore, btRankings, btRules, btLogin);
    }
    
    // 更新游戏界面
    private void updatePane() {
        Score.setText("" + Ctrl.getScore());
        if (login.getLoginStatus() == true)
            BestScore.setText("" + Ctrl.getCurrBestScore());
        else
            BestScore.setText("Null");

        if (Ctrl.judgeGameOver()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("失败提示");
            alert.setResizable(false);
            alert.setHeaderText(null);
            alert.setContentText("Game Over! 你的最终分数: "+Ctrl.getScore());
            alert.showAndWait();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // 初始化游戏
        InitGame();
        
        // 规则按钮
        btRules.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // 创建新的stage
                Stage RuleStage = new Stage();
                RuleStage.setResizable(false);
                RuleStage.setTitle("2048游戏规则");
                Label Rules = new Label();
                Rules.disableProperty();
                Rules.setPrefSize(280, 320);
                Rules.setText(
                        "2048游戏共有16个格子，初始时初始数\n字由2或者4构成。\n" +
                        "1. 键盘操作向一个方向滑动，所有格子\n都会向那个方向运动。\n" +
                        "2. 相同数字的两个格子，相撞时数字会\n相加。\n" +
                        "3. 操作 ↑ ↓ ← →键滑动, 按R键或reset\n按钮重新开始\n" +
                        "3 .每次滑动时，空白处会随机刷新出一\n个数字的格子。\n" +
                        "4. 当界面不可运动时（即当界面全部被\n数字填满时），游戏结束；\n" +
                        "5. 登录后可计录你的分数。争取获得更\n高的分数，与他人共同竞技！\n");
                Rules.setStyle("-fx-background-color: #eee4da;");
                Rules.setFont(RuleFont);

                StackPane RulePane = new StackPane(Rules);
                Scene scene = new Scene(RulePane, 280, 320);
                RuleStage.setScene(scene);
                RuleStage.show();
                GamePane.requestFocus();
            }
        });
        btRules.setFocusTraversable(false);

        // 重置按钮
        btReset.setOnMouseClicked(e -> {
            Ctrl.ResetGame();
            Ctrl.UpdateData();
            Ctrl.UpdataUser();
            updatePane();
            GamePane.requestFocus();

        });
        btReset.setFocusTraversable(false);

        // 排行榜功能
        btRankings.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // 加载信息
                LoginCtrl.LoadUser();
                Ctrl.UpdateData();
                Ctrl.UpdataUser();
                updatePane();

                Stage RankingStage = new Stage();
                Group g = new Group();

                // 背景板
                StackPane BackPane = new StackPane();
                BackPane.setStyle("-fx-background-color: #eee4da;");
                BackPane.setPrefSize(1000, 1000);

                // 排行榜标题
                Label Title = new Label("Top 10 Player"); // 放一个标签
                Title.setAlignment(Pos.CENTER);
                Title.setFont(RankingFont);
                Title.setLayoutX(50);
                Title.setLayoutY(10);
                Title.setPrefSize(200, 60);

                // 排行榜标签
                Label Rank = new Label("排名"); // 放一个标签
                Rank.setFont(LabelFont);
                Label User = new Label("玩家"); // 放一个标签
                User.setFont(LabelFont);
                Label Score = new Label("最高分"); // 放一个标签
                Score.setFont(LabelFont);

                // 排行榜网格
                GridPane RankingPane = new GridPane();
                RankingPane.setPrefSize(200, 300);
                RankingPane.setLayoutX(60);
                RankingPane.setLayoutY(80);
                RankingPane.setAlignment(Pos.BASELINE_CENTER);

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 3; j++) {
                        Label data = new Label();
                        if (LoginCtrl.list.size() <= i)
                            data.setText("--"); // 空元素
                        else
                            switch (j) {
                                case 0: // 排名
                                    data.setText("" + (i + 1));
                                    break;
                                case 1: // 用户
                                    data.setText("" + LoginCtrl.list.get(i).getAccount());
                                    break;
                                case 2: // 分数
                                    data.setText("" + LoginCtrl.list.get(i).getBestScore());
                                    break;
                            }
                        data.setPrefSize(60, 30);
                        data.setAlignment(Pos.BASELINE_LEFT);
                        RankingPane.add(data, j, i + 1);
                    }
                }
                RankingPane.add(Rank, 0, 0);
                RankingPane.add(User, 1, 0);
                RankingPane.add(Score, 2, 0);
                g.getChildren().addAll(BackPane, Title, RankingPane);

                Scene RankingScene = new Scene(g, 300, 400);
                RankingStage.setScene(RankingScene);
                RankingStage.setTitle("排行榜");
                RankingStage.setResizable(false);
                RankingStage.show();
                GamePane.requestFocus();
            }
        });
        btRankings.setFocusTraversable(false);

        // 登录按钮
        Stage LoginStage = new Stage();
        btLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    login.start(LoginStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btLogin.setFocusTraversable(false);

        // 监听关闭登录页面
        LoginStage.setOnHidden(e -> {
            if (login.getLoginStatus() == true) {
                
                Ctrl.setUsername(login.getLoginAccount());
                Ctrl.isLogin = login.getLoginStatus();
                g.getChildren().remove(btLogin);
                g.getChildren().add(btLogout);
                // 更新用户信息
                Ctrl.ResetGame();
                Ctrl.UpdateData();
                BestScore.setText("" + Ctrl.getBestScore());
                Username.setText(Ctrl.getUsername());
                Score.setText("0");
            }
        });

        // 退出登录
        btLogout.setOnMouseClicked(e -> {
            Ctrl.UpdataUser();
            Ctrl.isLogin = false;
            login.logOut();
            g.getChildren().add(btLogin);
            g.getChildren().remove(btLogout);
            Score.setText("0");
            BestScore.setText("Null");
            Username.setText("未登录");
            Ctrl.ResetGame();
            Ctrl.UpdateData();
            updatePane();
        });
        btLogout.setFocusTraversable(false);

        GamePane.setOnKeyPressed(e -> {
            // 按下“上”键
            if (e.getCode() == KeyCode.UP) {
                Ctrl.moveUp();
                Ctrl.createBlock();
                Ctrl.UpdateData();
                updatePane();          
            }
            // 按下“下”键
            else if (e.getCode() == KeyCode.DOWN) {
                
                Ctrl.moveDown();
                Ctrl.createBlock();
                Ctrl.UpdateData();
                updatePane();       
            }
            // 按下“左”键
            else if (e.getCode() == KeyCode.LEFT) {  
                Ctrl.moveLeft();
                Ctrl.createBlock();
                Ctrl.UpdateData();
                updatePane();         
            }
            // 按下“右”键
            else if (e.getCode() == KeyCode.RIGHT) {  
                Ctrl.moveRight();
                Ctrl.createBlock();
                Ctrl.UpdateData();
                updatePane();          
            }
            // 按下“R”键
            else if (e.getCode() == KeyCode.R) {
                Ctrl.ResetGame();
                Ctrl.UpdateData();
                Ctrl.UpdataUser();
                updatePane();
            }
        });

        // 关闭主窗口后全部关闭
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });

        Scene scene = new Scene(g, GAME_WIDTH, GAME_HEIGHT);
        primaryStage.setTitle("2048游戏");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        GamePane.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
