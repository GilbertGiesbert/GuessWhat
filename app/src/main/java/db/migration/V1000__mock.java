package db.migration;


import android.util.Log;

import com.joern.guesswhat.activity.login.PasswordFactory;
import com.joern.guesswhat.constants.DB;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class V1000__mock implements JdbcMigration{

    private static final String LOG_TAG = V1000__mock.class.getSimpleName();

    private static final boolean USE_MOCK = true;
    
    private HashMap<Integer, User> mockUsers;

    @Override
    public void migrate(Connection connection) throws Exception {

        if(USE_MOCK){

            mockUsers = createMockUsers();

            try {saveMockUsers(connection);}
            catch(Exception ex) {Log.e(LOG_TAG, "problems adding mock users", ex);}

            try {addMockFriendships(connection);}
            catch(Exception ex) {Log.e(LOG_TAG, "problems adding mock friends", ex);}
        }
    }

    private HashMap<Integer, User> createMockUsers() {

        ArrayList<String> names = new ArrayList<>();
        names.add("Anton");
        names.add("Bert");
        names.add("Carl");
        names.add("Dude");
        names.add("Egon");
        names.add("Friedbert");
        names.add("Gustav");
        names.add("Hans");

        int id = 0;
        HashMap<Integer, User>  users = new HashMap<>();
        for(String n: names){
            for(int i = 0; i < 10; i++){

                String name = n+id;
                String mail = n.substring(0, 1)+"@"+id+".com";
                Integer pswdHash = PasswordFactory.buildPasswordHash(n.substring(0, 1), name, mail);
                users.put(id, new User(id, null, name, mail, pswdHash));
                id++;
            }
        }
        return users;
    }

    private void saveMockUsers(Connection connection)throws Exception{

        for(User user: mockUsers.values()){

//            INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)
//            VALUES (1, 'Paul', 32, 'California', 20000.00 );

            String query = "INSERT INTO " + DB.USERS.TABLE_NAME + " ("+DB.USERS.COL_NAME+","+DB.USERS.COL_EMAIL+","+DB.USERS.COL_PASSWORD_HASH+") "+
                            "VALUES ('"+user.getName()+"','"+user.getEmail()+"',"+user.getPasswordHash()+");";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        }
    }

    private void addMockFriendships(Connection connection) throws Exception{

        User popular = mockUsers.values().iterator().next();
        Log.d(LOG_TAG, "popular:"+popular.getName());

        for(User user: mockUsers.values()){
            if(user.getId() != popular.getId()){

                String query = "INSERT INTO " + DB.FRIENDSHIPS.TABLE_NAME + " ("+DB.FRIENDSHIPS.COL_EMAIL_REQUESTER+","+DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR+","+DB.FRIENDSHIPS.COL_STATE+") "+
                        "VALUES ('"+popular.getEmail()+"','"+user.getEmail()+"',"+ FriendshipState.ACTIVE.getValue()+");";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.execute();
            }
        }
    }
}