package com.example.demo.presentation;

import com.example.demo.data.CandidateMapper;
import com.example.demo.domain.Candidate;
import com.example.demo.domain.CandidateList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Singleton")
public class CandidateController {
    private final CandidateMapper candidateMapper;

    public CandidateController(CandidateMapper candidateMapper) {
        this.candidateMapper = candidateMapper;
    }

    public CandidateList getCandidatesOfUserID(int userID, int limit, int start_row) {
        return candidateMapper.getCandidatesOfUser(userID, limit, start_row);
    }

    public void InsertCandidate (Candidate candidate){candidateMapper.insertCandidate(candidate);}
}



