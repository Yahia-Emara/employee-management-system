package user;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String userName;
    private final String password;
    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }
    @Override
    public boolean equals(Object user) {
        return this.userName.equals(((User)user).userName) && this.password.equals(((User)user).password);
    }
}
