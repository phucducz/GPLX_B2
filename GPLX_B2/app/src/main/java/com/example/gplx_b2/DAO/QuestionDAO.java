package com.example.gplx_b2.DAO;

import android.util.Log;

import com.example.gplx_b2.Database.ConnSQL;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public QuestionDAO() {}
    Connection connection;

    public List<Question> getListQuestions() {
        List<Question> questionList = new ArrayList<Question>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if(connection != null) {
            try {
                String sqlStatement = "SELECT question, answer_a, answer_b, answer_c, answer_d, answer_correct FROM questions";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while(rs.next()) {
                    Question question = new Question();
                    List<String> answerList = new ArrayList<String>();

                    question.setQuestion(rs.getString("question"));
                    question.setAnswerCorrect(rs.getString("answer_correct"));

                    answerList.add(rs.getString("answer_a"));
                    answerList.add(rs.getString("answer_b"));
                    answerList.add(rs.getString("answer_c"));
                    answerList.add(rs.getString("answer_d"));

                    question.setAnswerList(answerList);
                    questionList.add(question);
                }

                connection.close();
            } catch(Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return questionList;
    }
}
