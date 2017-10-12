package de.odinoxin.aidware.aiddesk;

import de.odinoxin.aidware.aidcloud.provider.LoginProvider;
import de.odinoxin.aidware.aidcloud.provider.PersonProvider;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aidware.aiddesk.dialogs.MsgDialog;
import de.odinoxin.aidware.aiddesk.plugins.Plugin;
import de.odinoxin.aidware.aiddesk.plugins.people.Person;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.InetAddress;

/**
 * The Login plugin.
 * UI to log in to AidCloud.
 */
public class Login extends Plugin {

    private static String serverUrl;
    private static Person person;

    private TextField txfServer;
    private Button btnConnect;
    private RefBox<Person> refboxUser;
    private PasswordField pwfPwd;
    private Button btnLogin;

    private LoginProvider loginProvider = new LoginProvider();

    public Login() {
        super("/login.fxml", "Login");

        this.txfServer = (TextField) this.root.lookup("#txfServer");
        this.txfServer.textProperty().addListener((observable, oldValue, newValue) -> {
            this.refboxUser.setDisable(true);
            this.pwfPwd.setDisable(true);
            this.btnLogin.setDisable(true);
        });
        this.txfServer.setOnAction(ev -> this.btnConnect.fire());
        this.btnConnect = (Button) this.root.lookup("#btnConnect");
        this.btnConnect.setOnAction(ev -> {
            try {
                Login.serverUrl = this.txfServer.getText();
                if (loginProvider.checkConnection()) {
                    this.refboxUser.setDisable(false);
                    this.pwfPwd.setDisable(false);
                    this.btnLogin.setDisable(false);
                    this.refboxUser.requestFocus();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                new MsgDialog(this, Alert.AlertType.ERROR, null, ex.getLocalizedMessage()).showAndWait();
            }
        });
        Plugin.setButtonEnter(this.btnConnect);
        this.refboxUser = (RefBox<Person>) this.root.lookup("#refboxUser");
        this.refboxUser.setOnAction(ev -> this.tryLogin());
        this.refboxUser.setProvider(loginProvider);
        this.pwfPwd = (PasswordField) this.root.lookup("#pwfPwd");
        this.pwfPwd.setOnAction(ev -> this.tryLogin());
        this.btnLogin = (Button) this.root.lookup("#btnLogin");
        this.btnLogin.setOnAction(ev -> this.tryLogin());
        this.btnLogin.setOnKeyPressed(ev -> {
            if (ev.getCode() == KeyCode.ENTER)
                this.tryLogin();
        });
        Plugin.setButtonEnter(this.btnLogin);

        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {

        }
        this.txfServer.setText(String.format("http://%s:15123/AidCloud", hostName));
        this.show();
    }

    private void tryLogin() {
        Person p = this.refboxUser.getRecord();
        if (p == null)
            return;
        String pwd = this.pwfPwd.getText();
        if (loginProvider.authenticate(p.getId(), pwd)) {
            Login.person = new PersonProvider().get(p.getId());
            Login.person.setPwd(pwd);
            this.close();
            new MainMenu();
            return;
        }
        new MsgDialog(this, Alert.AlertType.ERROR, "Login", "User or password incorrect!").showAndWait();
    }

    public static String getServerUrl() {
        return Login.serverUrl;
    }

    public static Person getPerson() {
        return Login.person;
    }
}
