package de.odinoxin.aidware.aiddesk.auth;

public class Credentials {

    private String pwd;

    public Credentials() {

    }

    public Credentials(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
