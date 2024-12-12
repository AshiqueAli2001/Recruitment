package com.interland.candidate.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.candidate.dto.CodingQuestionDTO;
import com.interland.candidate.dto.McqQuestionDTO;
import com.interland.candidate.entity.CodingQuestion;
import com.interland.candidate.entity.Criteria;
import com.interland.candidate.entity.McqQuestion;
import com.interland.candidate.entity.User;
import com.interland.candidate.exception.RecordNotFoundException;
import com.interland.candidate.repository.CodingQuestionRepository;
import com.interland.candidate.repository.CriteriaRepository;
import com.interland.candidate.repository.McqQuestionRepository;
import com.interland.candidate.repository.UserRepository;

@Service
public class QuestionServiceImplement implements QuestionService {

	
	@Autowired
	McqQuestionRepository mcqQuestionRepository;
	@Autowired
	CriteriaRepository criteriaRepository;
	@Autowired
	CodingQuestionRepository codingQuestionRepository;
	@Autowired
	UserRepository userRepository;

	private static Logger logger = LogManager.getLogger(QuestionServiceImplement.class);

	
	@Override
	public List<McqQuestionDTO> generateMcqQuestionsByCriteriaId(Long criteriaId, Principal principal) throws RecordNotFoundException {
		Optional<Criteria> optionalCriteria = criteriaRepository.findById(criteriaId);

		try {
			if (optionalCriteria.isPresent()) {
				Criteria criteria = optionalCriteria.get();

				
//		        int totalQuestions = criteria.getTotalQuestions();
				int easyMcqQuestions = criteria.getEasyMcqQuestions();
				int mediumMcqQuestions = criteria.getMediumMcqQuestions();
				int hardMcqQuestions = criteria.getHardMcqQuestions();

				List<McqQuestion> easyMcqList = mcqQuestionRepository.getRandomQuestionsByDifficultyAndType("easy",
						easyMcqQuestions);
				List<McqQuestion> mediumMcqList = mcqQuestionRepository.getRandomQuestionsByDifficultyAndType("medium",
						mediumMcqQuestions);
				List<McqQuestion> hardMcqList = mcqQuestionRepository.getRandomQuestionsByDifficultyAndType("hard",
						hardMcqQuestions);

				List<McqQuestion> result = new ArrayList<>();
				result.addAll(easyMcqList);
				result.addAll(mediumMcqList);
				result.addAll(hardMcqList);

				Collections.shuffle(result);
//
				// If the total questions requested are less than the combined list, truncate
//		        if (totalQuestions < result.size()) {
//		            result = result.subList(0, totalQuestions);
//		        }
				
				User user = userRepository.findById(principal.getName()).get();
				user.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
				 int noOfAttempts = user.getNoOfAttempts();
		          user.setNoOfAttempts(noOfAttempts+1);

				userRepository.save(user);

				List<McqQuestionDTO> resultDTO = convertToMcqQuestionDTOList(result);
				
				

				return resultDTO;

			} else {
				throw new RecordNotFoundException("Criteria not found with ID: " + criteriaId);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
			  throw new RecordNotFoundException("Error generating MCQ questions", e);
		}
	}

	public List<McqQuestionDTO> convertToMcqQuestionDTOList(List<McqQuestion> questions) {
		List<McqQuestionDTO> questionDTOList = new ArrayList<>();

		for (McqQuestion question : questions) {

			McqQuestionDTO questionDTO = new McqQuestionDTO();
			questionDTO.setQuestionId(question.getQuestionId());
			questionDTO.setCategory(question.getCategory());
			questionDTO.setDifficulty(question.getDifficulty());
			questionDTO.setQuestion(question.getQuestion());
			questionDTO.setOption1(question.getOption1());
			questionDTO.setOption2(question.getOption2());
			questionDTO.setOption3(question.getOption3());
			questionDTO.setOption4(question.getOption4());
			questionDTO.setCorrectAnswer(question.getCorrectAnswer());
			questionDTO.setUserId(question.getUser().getUserId());

			// Add the converted DTO to the list
			questionDTOList.add(questionDTO);
		}

		return questionDTOList;
	}

	@Override
	public List<CodingQuestionDTO> generateCodingQuestionsByCriteriaId(Long criteriaId) throws RecordNotFoundException {
		try {
			Optional<Criteria> optionalCriteria = criteriaRepository.findById(criteriaId);

			if (optionalCriteria.isPresent()) {
				Criteria criteria = optionalCriteria.get();

//			int totalQuestions = criteria.getTotalQuestions();
				int easyCodingQuestions = criteria.getEasyCodingQuestions();
				int mediumCodingQuestions = criteria.getMediumCodingQuestions();
				int hardCodingQuestions = criteria.getHardCodingQuestions();

				List<CodingQuestion> easyCodingList = codingQuestionRepository.getRandomQuestionsByDifficultyAndType("easy",
						easyCodingQuestions);
				List<CodingQuestion> mediumCodingList = codingQuestionRepository
						.getRandomQuestionsByDifficultyAndType("medium", mediumCodingQuestions);
				List<CodingQuestion> hardCodingList = codingQuestionRepository.getRandomQuestionsByDifficultyAndType("hard",
						hardCodingQuestions);

				List<CodingQuestion> result = new ArrayList<>();

				result.addAll(easyCodingList);
				result.addAll(mediumCodingList);
				result.addAll(hardCodingList);
				// Shuffle the combined list
				Collections.shuffle(result);

				List<CodingQuestionDTO> resultDTO = convertToCodingQuestionDTOList(result);

				return resultDTO;

			} else {
				throw new RecordNotFoundException("Criteria not found with ID: " + criteriaId);
			}
		} catch (RecordNotFoundException e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
			  throw new RecordNotFoundException("Error generating MCQ questions", e);
		}
	}

	public List<CodingQuestionDTO> convertToCodingQuestionDTOList(List<CodingQuestion> questions) {
		List<CodingQuestionDTO> questionDTOList = new ArrayList<>();

		for (CodingQuestion question : questions) {

			CodingQuestionDTO questionDTO = new CodingQuestionDTO();
			questionDTO.setQuestionId(question.getQuestionId());
			questionDTO.setDifficulty(question.getDifficulty());
			questionDTO.setQuestion(question.getQuestion());

			questionDTO.setTestCase1(question.getTestCase1());
			questionDTO.setExpected1(question.getExpected1());
			questionDTO.setTestCase2(question.getTestCase2());
			questionDTO.setExpected2(question.getExpected2());
			questionDTO.setTestCase3(question.getTestCase3());
			questionDTO.setExpected3(question.getExpected3());
			questionDTO.setTestCase4(question.getTestCase4());
			questionDTO.setExpected4(question.getExpected4());
			questionDTO.setTestCase5(question.getTestCase5());
			questionDTO.setExpected5(question.getExpected5());

			questionDTO.setUserId(question.getUser().getUserId());

			// Add the converted DTO to the list
			questionDTOList.add(questionDTO);
		}

		return questionDTOList;
	}

	

}
