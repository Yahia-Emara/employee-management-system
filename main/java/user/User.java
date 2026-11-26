package user;

import java.io.Serializable;

import database.DbItem;

public class User implements Serializable, DbItem {
    private static final long serialVersionUID = 1L;
    private static int idCount = 0;
    private final int id;
    private final String userName;
    private final String password;
    public User(String userName, String password)
    {
        this.id = idCount++;
        this.userName = userName;
        this.password = password;
    }
    @Override
    public boolean equals(Object user) {
        return this.userName.equals(((User)user).userName) && this.password.equals(((User)user).password);
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public String getName() {
        return userName;
    }
}
