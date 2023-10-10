package com.example.gplx_b2.DAO;

import android.util.Log;

import com.example.gplx_b2.Database.ConnSQL;
import com.example.gplx_b2.Modal.Topic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    public TopicDAO() {
    }

    Connection connection;

    public List<Topic> getListTopics() {
        List<Topic> listTopic = new ArrayList<Topic>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
//                String sqlStatement = "SELECT * FROM topics";
                String sqlStatement =
                        "SELECT T.id, T.title, T.imageFileName, COUNT(Q.id) AS quantity\n" +
                        "FROM topics AS T, questions AS Q, question_topics AS QT\n" +
                        "WHERE T.id = QT.topics_id AND Q.id = QT.questions_id\n" +
                        "GROUP BY T.id, T.title, T.imageFileName";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while (rs.next()) {
                    Topic topic = new Topic();

                    topic.setId(rs.getInt("id"));
                    topic.setImage(rs.getString("imageFileName"));
                    topic.setTitle(rs.getString("title"));
                    topic.setQuantity(0);
                    topic.setSubTitle(rs.getString("quantity"));

                    listTopic.add(topic);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return listTopic;
    }
}
