package com.mortgageBank.mortgageRequestsIdGenerator.controller;

import com.mortgageBank.mortgageRequestsIdGenerator.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/id_generator")
public class SequenceController {

    private final SequenceService sequenceService;

    @Autowired
    public SequenceController(SequenceService sequenceGeneratorService) {
        this.sequenceService = sequenceGeneratorService;
    }

    @GetMapping("/generate-sequence/{seqName}")
    public ResponseEntity<Long> getNextId(@PathVariable String seqName) {
        long nextId = sequenceService.getNextId(seqName);
        return ResponseEntity.ok(nextId);
    }
}
