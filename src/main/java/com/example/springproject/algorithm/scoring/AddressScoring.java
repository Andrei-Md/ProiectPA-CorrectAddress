package com.example.springproject.algorithm.scoring;

import com.example.springproject.algorithm.model.BasicAddress;
import com.example.springproject.algorithm.model.ScoredAddress;

import java.util.List;

public interface AddressScoring {

    public List<ScoredAddress> addressScoring(BasicAddress basicAddress);
}
