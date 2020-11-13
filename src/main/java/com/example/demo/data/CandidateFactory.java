package com.example.demo.data;

import com.example.demo.domain.Candidate;
import com.example.demo.domain.CandidateList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateFactory {


    public CandidateFactory() {
    }

    public Candidate createPair(ResultSet resultSet) {
        try {
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
            }
            int ownerID = resultSet.getInt("owner_id");
            int candidateID = resultSet.getInt("candidate_id");
            return new Candidate(candidateID, ownerID);
        } catch (SQLException sqlException) {
            throw new NullPointerException("Something went wrong, check your connection to dmbs");
        }
    }

    public CandidateList candidateBatch(ResultSet resultSet) {
        try {
            CandidateList candidateList = new CandidateList();
            while (resultSet.next()) {
                Candidate candidate = createPair(resultSet);
                candidateList.add(candidate);
            }
            return candidateList;
        } catch (SQLException e) {
            throw new NullPointerException("Something is wrong with the ResultSet");
        }
    }


}
