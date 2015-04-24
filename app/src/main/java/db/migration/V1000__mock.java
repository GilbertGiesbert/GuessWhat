package db.migration;


import android.util.Log;

import com.joern.guesswhat.activity.login.PasswordFactory;
import com.joern.guesswhat.constants.DB;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class V1000__mock implements JdbcMigration{

    private static final String LOG_TAG = V1000__mock.class.getSimpleName();

    private static final boolean USE_MOCK = false;

    @Override
    public void migrate(Connection connection) throws Exception {

        if(USE_MOCK){
            try{
                addMockUsers(connection);
            }catch(Exception ex){
                Log.d(LOG_TAG, "problems adding mock users");
            }

            addMockFriendships(connection);
        }
    }

    private void addMockUsers(Connection connection)throws Exception{

        ArrayList<String> names = new ArrayList<>();
        names.add("Anton");
        names.add("Bert");
        names.add("Carl");
        names.add("Dude");
        names.add("Egon");
        names.add("Friedbert");
        names.add("Gustav");
        names.add("Hans");

        ArrayList<String> users = new ArrayList<>();
        for(String name: names){
            for(int i = 0; i < 10; i++){
                users.add(name + i);
            }
        }

        for(String user: users){

//            INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)
//            VALUES (1, 'Paul', 32, 'California', 20000.00 );

            String email = (user.substring(0,1)+user.substring(user.length()-1,user.length())+"@").toLowerCase();
            String pswd = (user.substring(0,1)).toLowerCase();

            Integer pswdHash = PasswordFactory.buildPasswordHash(pswd, user, email);

            String query = "INSERT INTO " + DB.USERS.TABLE_NAME + " ("+DB.USERS.COL_NAME+","+DB.USERS.COL_EMAIL+","+DB.USERS.COL_PASSWORD_HASH+") "+
                            "VALUES ('"+user+"','"+email+"',"+pswdHash+");";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        }
    }

    private void addMockFriendships(Connection connection) {



    }
}