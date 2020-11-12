package com.example.demo.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

    @Component
    @Scope("singleton")
    public class CandidateList extends ArrayList<Candidate> {

    }
