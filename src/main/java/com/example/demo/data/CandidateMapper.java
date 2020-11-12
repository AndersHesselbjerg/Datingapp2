package com.example.demo.data;

import com.example.demo.domain.Candidate;
import com.example.demo.domain.CandidateList;
import com.example.demo.domain.User;
import com.example.demo.domain.UserList;
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

    CandidateMapper() { } // tom constructor fikser bean autowire

    CandidateMapper(Connector connector, CandidateFactory candidateFactory) {
        this.connection = connector.getConnection();
        this.candidateFactory = candidateFactory;

    }

    public void insertCandidate(Candidate candidate) {
        String statement = "INSERT INTO pairs " + "(pair_id + user) " + "VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, candidate.getPairID());
            preparedStatement.setInt(2, candidate.getUserID());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException(sqlException.getMessage());
        }
    }


    public Candidate getPairsByID(String pairID) {
        String statement = "SELECT * FROM mydb.pairs WHERE pair_id LIKE ?;";
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

    public CandidateList getUserCandidates (int userID, int start_row, int limit) {
        String statement = "SELECT * FROM mydb.pairs WHERE pair_id LIKE ? LIMIT ?, ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, start_row);
            preparedStatement.setInt(3, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            CandidateList candidateList = candidateFactory.candidateBatch(resultSet);
            return candidateList;
        } catch (SQLException e) {
            throw new NullPointerException("Your SQL statement is false");
        }
    }


        public void deletePair(Candidate candidate) { // skal måske tage pairID istedet
        String statement = "DELETE FROM pairs WHERE ID=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, (candidate.getPairID()));
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new NullPointerException("Your SQL statement is false");

        }
    }
}