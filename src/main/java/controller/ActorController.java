package controller;

import model.ActorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActorController {

    private Connection connection;

    public ActorController(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<ActorDTO> actorList(String actor_id) {
        String query = "select * from actor where first_name like ? or last_name like ?";
        ArrayList<ActorDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "%"+actor_id+"%");
            pstmt.setString(2, "%"+actor_id+"%");
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ActorDTO a = new ActorDTO();
                a.setFirst_name(resultSet.getString("first_name"));
                a.setLast_name(resultSet.getString("last_name"));
                a.setActor_id(resultSet.getInt("actor_id"));
                temp.add(a);
            }

        } catch (SQLException e) {
            System.out.printf("배우리스트 출력 오류");
        }
        return temp;
    }

}
