package com.cloud.survey.controller;

import com.cloud.survey.dto.SurveyDTO;
import com.cloud.survey.entity.IsYn;
import com.cloud.survey.entity.SurveyStatus;
import com.cloud.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="v1/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    // 설문 리스트 조회
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<SurveyDTO>> getSurveyList(@RequestParam (value = "status") SurveyStatus status,
                                                         @RequestParam (value = "is_private") IsYn isPrivateYn) {
        return new ResponseEntity<>(surveyService.getSurveyList(status, isPrivateYn), HttpStatus.OK);
    }

    // 설문 상세정보, 질문 조회
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getSurveyDetail(@RequestParam (value = "sur_id") int surId) {

        Map<String, Object> map = new HashMap<>();
        map.put("info", surveyService.getSurveyDetail(surId));
        map.put("question_list", surveyService.getSurveyQuestion(surId));

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
