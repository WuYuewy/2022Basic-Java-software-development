
/* 登录系统前端处理 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginPage extends Application {
    private boolean isLogin;
    private String username;

    // 初始化
    public LoginPage() {
        this.isLogin = false;
    }

    // 退出登陆
    public void logOut() {
        this.isLogin = false;
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("成功提示");
        alert.setHeaderText(null);
        alert.setContentText("成功退出登陆");
        alert.showAndWait();
    }

    // 获得登陆状态
    public boolean getLoginStatus() {
        return this.isLogin;
    }

    // 获得登陆信息
    public String getLoginAccount() {
        return this.username;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginCtrl.LoadUser();
        primaryStage.setTitle("2048登录");

        // 网格界面
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // 界面信息
        Text title = new Text("登录");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label Account = new Label("账户:");
        TextField userAccount = new TextField();
        Label pwd = new Label("密码:");
        PasswordField userPwd = new PasswordField();
        Label no = new Label("没有账号?->");
        no.setTextFill(Color.BLUE);
        Button confirm = new Button("确定");
        Button register = new Button("注册");

        // 确定按钮
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // 用户密码对应，登陆成功
                if (LoginCtrl.findUser(userAccount.getText(), userPwd.getText())) {
                    isLogin = true;
                    username = userAccount.getText();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("成功提示");
                    alert.setHeaderText(null);
                    alert.setContentText("登录成功");
                    alert.showAndWait();
                    primaryStage.close();
                }
                // 密码错误
                else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("错误提示");
                    alert.setHeaderText(null);
                    alert.setContentText("用户名或密码错误,请重新输入");
                    alert.showAndWait();
                    userAccount.clear();
                    userPwd.clear();
                }
            }
        });

        // 注册按钮
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 输入新信息
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                Alert alert = new Alert(AlertType.INFORMATION);
                dialog.setTitle("注册新用户");
                Label Account = new Label("账户:");
                TextField newAccount = new TextField();
                PasswordField newPwd = new PasswordField();
                Label pwd = new Label("密码");
                Button confirm = new Button("确认");
                confirm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // 新建用户
                        if (LoginCtrl.findAccount(newAccount.getText()) == null) {
                            User user = new User(newAccount.getText(), newPwd.getText());
                            LoginCtrl.addNewUser(user);
                            alert.setTitle("成功提示");
                            alert.setHeaderText(null);
                            alert.setContentText("注册成功,请返回登录");
                            alert.showAndWait();
                            dialog.close();
                            userAccount.setText(user.getAccount());
                            userPwd.clear();
                        }
                        // 避免重复用户名 
                        else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("错误提示");
                            alert.setHeaderText(null);
                            alert.setContentText("用户名已存在！");
                            alert.showAndWait();
                            userAccount.clear();
                            userPwd.clear();
                        }
                    }
                });
                // 设置界面
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));
                grid.add(Account, 0, 0);
                grid.add(newAccount, 1, 0);
                grid.add(pwd, 0, 1);
                grid.add(newPwd, 1, 1);
                grid.add(confirm, 1, 2);
                dialog.setScene(new Scene(grid, 300, 100));
                dialog.show();
            }
        });

        // 显示登录页面
        grid.add(title, 0, 0, 2, 1);
        grid.add(Account, 0, 1);
        grid.add(userAccount, 1, 1);
        grid.add(pwd, 0, 2);
        grid.add(userPwd, 1, 2);

        HBox panel = new HBox(40);
        panel.setAlignment(Pos.BOTTOM_RIGHT);
        panel.getChildren().add(confirm);

        HBox panel2 = new HBox(40);
        panel2.setAlignment(Pos.BOTTOM_RIGHT);
        panel2.getChildren().add(no);
        panel2.getChildren().add(register);
        grid.add(panel, 1, 4);
        grid.add(panel2, 1, 5);

        primaryStage.setScene(new Scene(grid, 350, 300));
        primaryStage.show();
    }
}
