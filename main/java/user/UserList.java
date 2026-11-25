package user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<User> users;
    public UserList(){
        users = new ArrayList<>();
    }
    UserList(List<User> users){
        this.users = users;
    }
    public void addUser(User user){
        users.add(user);
    }
    public boolean authentication(User user){
        return users.contains(user);
    }
    public List<User> getUserlist(){
        return users;
    }
}
