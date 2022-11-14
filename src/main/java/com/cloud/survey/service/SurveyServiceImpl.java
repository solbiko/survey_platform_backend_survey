package com.cloud.survey.service;

import com.cloud.survey.dto.QuestionDTO;
import com.cloud.survey.dto.SurveyDTO;
import com.cloud.survey.entity.*;
import com.cloud.survey.openfeign.AuthServiceClient;
import com.cloud.survey.dto.UserDTO;
import com.cloud.survey.repository.AnswerRepository;
import com.cloud.survey.repository.QuestionRepository;
import com.cloud.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SurveyRepository surveyRepository;
    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    private final AnswerRepository answerRepository;
    @Autowired
    private final AuthServiceClient authServiceClient;
    @Autowired
    private final ModelMapper mapper;

    @Override
    public List<SurveyDTO> getSurveyList(SurveyStatus status, IsYn isPrivateYn){

        List<SurveyDTO> surveyDtoList = new ArrayList<>();
        List<Survey> surveyList = surveyRepository.findByStatusAndIsPrivateYn(status, isPrivateYn);

        surveyList.forEach(survey -> {
            SurveyDTO surveydto = mapper.map(survey, SurveyDTO.class);
            surveyDtoList.add(surveydto);
        });
        return surveyDtoList;
    }

    public SurveyDTO getSurveyDetail (int surId){
        return mapper.map(surveyRepository.findBySurId(surId), SurveyDTO.class);
    }

    public List<QuestionDTO> getSurveyQuestion (int surId){
        List<QuestionDTO> questionDtoList = new ArrayList<>();
        List<Question> questionList = questionRepository.findBySurId(surId);

        questionList.forEach(question -> {
            QuestionDTO questionDTO = mapper.map(question, QuestionDTO.class);
            questionDtoList.add(questionDTO);
        });
        return questionDtoList;
    }
    public List<Map<String,Object>> getUserAnswer (String userId, int surId){
        UserDTO userDetail = authServiceClient.getUserDetailInfo(userId);
        log.info(userDetail.getUserId());

        return answerRepository.findByRegIdAndSurId(userId, surId);
    }


}
