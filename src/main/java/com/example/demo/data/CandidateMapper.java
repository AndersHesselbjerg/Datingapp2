package com.example.demo.data;

import com.example.demo.domain.Candidate;
import com.example.demo.domain.CandidateList;
import com.example.demo.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("Singleton")
public class CandidateMapper {
    private Connection connection;
    private CandidateFactory candidateFactory;
    private UserMapper userMapper;

    CandidateMapper() { } // tom constructor fikser bean autowire

    CandidateMapper(Connector connector, CandidateFactory candidateFactory, UserMapper userMapper) {
        this.connection = connector.getConnection();
        this.candidateFactory = candidateFactory;
        this.userMapper = userMapper;

    }

    public void insertCandidate(Candidate candidate) {
        String statement = "INSERT INTO candidates " + "(owner_id, candidate_id) " + "VALUES(?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, candidate.getOwner_id());
            preparedStatement.setInt(2, candidate.getUser_id());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException(sqlException.getMessage());
        }
    }
  /*      --legacy--
    public Candidate getCandidatesByID(String pairID) {
        String statement = "SELECT * FROM mydb.candidates WHERE pair_id LIKE ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, pairID);
            ResultSet resultSet = preparedStatement.executeQuery();
            Candidate candidate = candidateFactory.createPair(resultSet);
            return candidate;
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }
*/
    public CandidateList getCandidatesOfUser(int owner_id, int start_row, int limit) {
        String statement = "SELECT * FROM candidates WHERE owner_id LIKE ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, owner_id);
            // preparedStatement.setInt(2, start_row);
            // preparedStatement.setInt(3, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            CandidateList candidateList = candidateFactory.candidateBatch(resultSet);
            return candidateList;
        } catch (SQLException e) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }


        public void deleteCandidate(Candidate candidate) {
        String statement = "DELETE FROM candidates WHERE ID=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, (candidate.getUser_id()));
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");

        }
    }

    public String getUsernameByCandidate(Candidate candidate) {
        User user = userMapper.getUserById(candidate.getUser_id());
        return user.getUserName();
    }
}