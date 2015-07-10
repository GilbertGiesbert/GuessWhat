package db.migration;


import android.util.Log;

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

    private static final boolean USE_MOCK = false;
    
    private HashMap<Integer, User> mockUsers;

    @Override
    public void migrate(Connection connection) throws Exception {

        if(USE_MOCK){

            mockUsers = createMockUsers();

            try {saveMockUsers(connection);}
            catch(Exception ex) {Log.e(LOG_TAG, "problems adding mock users", ex);}

//            try {addMockFriendships(connection);}
//            catch(Exception ex) {Log.e(LOG_TAG, "problems adding mock friends", ex);}
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
        for(String name: names){
            for(int i = 0; i < 10; i++){

                String mail = name+"@"+i+".com";
                String password = "123";
                users.put(id, new User(id, name, mail));
                id++;
            }
        }
        return users;
    }

    private void saveMockUsers(Connection connection)throws Exception{

        for(User user: mockUsers.values()){

//            INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)
//            VALUES (1, 'Paul', 32, 'California', 20000.00 );

            String query = "INSERT INTO " + DB.TABLE_USERS.NAME + " ("+ DB.TABLE_USERS.COL_NAME+","+ DB.TABLE_USERS.COL_EMAIL+") "+
                            "VALUES ('"+user.getName()+"','"+user.getEmail()+");";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        }
    }

    private void addMockFriendships(Connection connection) throws Exception{

        User popular = mockUsers.values().iterator().next();
        Log.d(LOG_TAG, "popular:"+popular.getName());

        for(User user: mockUsers.values()){
            if(user.getId() != popular.getId()){

                String query = "INSERT INTO " + DB.TABLE_FRIENDSHIPS.NAME + " ("+ DB.TABLE_FRIENDSHIPS.COL_USER_ID +","+ DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID +","+ DB.TABLE_FRIENDSHIPS.COL_STATE+") "+
                        "VALUES ('"+popular.getEmail()+"','"+user.getEmail()+"',"+ FriendshipState.ACTIVE.getValue()+");";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.execute();
            }
        }
    }
}