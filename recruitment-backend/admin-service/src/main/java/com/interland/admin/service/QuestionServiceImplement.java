package com.interland.admin.service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.interland.admin.dto.CodingQuestionDTO;
import com.interland.admin.dto.McqQuestionDTO;
import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.entity.CodingQuestion;
import com.interland.admin.entity.McqQuestion;
import com.interland.admin.entity.User;
import com.interland.admin.exception.RecordCreateException;
import com.interland.admin.exception.RecordNotFoundException;
import com.interland.admin.repository.CodingQuestionRepository;
import com.interland.admin.repository.McqQuestionRepository;
import com.interland.admin.repository.UserRepository;
import com.interland.admin.repository.specification.CodingQuestionSpec;
import com.interland.admin.repository.specification.McqQuestionSpec;
import com.interland.admin.utils.Constants;

@Service
public class QuestionServiceImplement implements QuestionService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	McqQuestionRepository mcqQuestionRepository;

	@Autowired
	CodingQuestionRepository codingQuestionRepository;

	@Autowired
	MessageSource messageSource;

	private static Logger logger = LogManager.getLogger(QuestionServiceImplement.class);

	@Override
	public ServiceResponse addMcqQuestion(McqQuestionDTO dto, Principal principal) {
		try {
			Optional<User> obj = userRepository.findById(dto.getUserId());

			if (obj.isEmpty())
				throw new RecordNotFoundException("admin.details.psh.VAL0006");
			User user = obj.get();

			McqQuestion mcqQuestion = new McqQuestion();

			BeanUtils.copyProperties(dto, mcqQuestion);

			mcqQuestion.setUser(user);
			mcqQuestion.setCreatedBy(principal.getName());
			mcqQuestion.setCreatedTime(new Date());
			mcqQuestion.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			mcqQuestion.setModifiededBy(principal.getName());
			mcqQuestionRepository.save(mcqQuestion);
			return new ServiceResponse(
					messageSource.getMessage("mcq.details.psh.VAL0001", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.SUCCESS,null);
		}

		catch (RecordNotFoundException e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("mcq.details.psh.VAL0002", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.FAILED,null);
		}
	}

	@Override
	public ServiceResponse addCodingQuestion(CodingQuestionDTO dto, Principal principal) {
		try {
			Optional<User> obj = userRepository.findById(dto.getUserId());

			if (obj.isEmpty())
				throw new RecordNotFoundException("admin.details.psh.VAL0006");
			User user = obj.get();

			CodingQuestion codingQuestion = new CodingQuestion();

			BeanUtils.copyProperties(dto, codingQuestion);

			codingQuestion.setUser(user);
			codingQuestion.setCreatedBy(principal.getName());
			codingQuestion.setCreatedTime(new Date());
			codingQuestion.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			codingQuestion.setModifiededBy(principal.getName());
			codingQuestionRepository.save(codingQuestion);
			return new ServiceResponse(
					messageSource.getMessage("coding.details.psh.VAL0001", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);

		} catch (RecordNotFoundException e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("coding.details.psh.VAL0002", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}
	}

	@Override
	public JSONObject searchMcqQuestion(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<McqQuestion> spec = McqQuestionSpec.getMcqQuestionSpec(searchParam);
			Page<McqQuestion> questionList = mcqQuestionRepository.findAll(spec, pageable);

//			JSONArray countByStatus = countByStatus(spec);
			JSONArray array = new JSONArray();

			for (McqQuestion question : questionList) {
				JSONObject obj = new JSONObject();

				obj.put("questionId", question.getQuestionId());
				obj.put("category", question.getCategory());
				obj.put("difficulty", question.getDifficulty());
				obj.put("question", question.getQuestion());
				obj.put("option1", question.getOption1());
				obj.put("option2", question.getOption2());
				obj.put("option3", question.getOption3());
				obj.put("option4", question.getOption4());
				obj.put("correctAnswer", question.getCorrectAnswer());
				if (question.getUser() != null)
					obj.put("userId", question.getUser().getUserId());
				else
					obj.put("userId", null);
				obj.put("status", question.getStatus());

				array.add(obj);

			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", mcqQuestionRepository.findAll(spec).size());
			result.put("iTotalRecords", mcqQuestionRepository.findAll(spec).size());
//			result.put("countByStatus", countByStatus);

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public JSONObject searchCodingQuestion(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<CodingQuestion> spec = CodingQuestionSpec.getCodingQuestionSpec(searchParam);
			Page<CodingQuestion> questionList = codingQuestionRepository.findAll(spec, pageable);

//			JSONArray countByStatus = countByStatus(spec);
			JSONArray array = new JSONArray();

			for (CodingQuestion question : questionList) {
				JSONObject obj = new JSONObject();

				obj.put("questionId", question.getQuestionId());
//				obj.put("category", question.getCategory());
//				obj.put("type", question.getType());
				obj.put("difficulty", question.getDifficulty());
				obj.put("question", question.getQuestion());
				obj.put("testCase1", question.getTestCase1());
				obj.put("expected1", question.getExpected1());
				obj.put("testCase2", question.getTestCase2());
				obj.put("expected2", question.getExpected2());
				obj.put("testCase3", question.getTestCase3());
				obj.put("expected3", question.getExpected3());
				obj.put("testCase4", question.getTestCase4());
				obj.put("expected4", question.getExpected4());
				obj.put("testCase5", question.getTestCase5());
				obj.put("expected5", question.getExpected5());
				if (question.getUser() != null)
					obj.put("userId", question.getUser().getUserId());
				else
					obj.put("userId", null);
				obj.put("status", question.getStatus());

				array.add(obj);

			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", codingQuestionRepository.findAll(spec).size());
			result.put("iTotalRecords", codingQuestionRepository.findAll(spec).size());
//			result.put("countByStatus", countByStatus);

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ServiceResponse updateMcqQuestion(McqQuestionDTO dto, Principal principal) {
		try {
			Optional<McqQuestion> optionalMcqQuestion = mcqQuestionRepository.findById(dto.getQuestionId());

			if (optionalMcqQuestion.isPresent()) {
				McqQuestion existingMcqQuestion = optionalMcqQuestion.get();

				if (existingMcqQuestion.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
							messageSource.getMessage("mcq.details.psh.VAL0003", null, LocaleContextHolder.getLocale()),
							null);
				}
				BeanUtils.copyProperties(dto, existingMcqQuestion);

				User user = userRepository.findById(dto.getUserId())
						.orElseThrow(() -> new RecordNotFoundException("admin.details.psh.VAL0006"));
				existingMcqQuestion.setUser(user);
				existingMcqQuestion.setModifiededBy(principal.getName());
				mcqQuestionRepository.save(existingMcqQuestion);
				return new ServiceResponse(
		                messageSource.getMessage("mcq.details.psh.VAL0005", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("mcq.details.psh.VAL0011", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		} catch(RecordNotFoundException e) {
	        logger.error("Error:" + e.getMessage(), e);

			return new ServiceResponse(
					messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("mcq.details.psh.VAL0004", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);	  
		}
	}

	@Override
	public ServiceResponse updateCodingQuestion(CodingQuestionDTO dto, Principal principal) {
		try {
			Optional<CodingQuestion> optionalCodingQuestion = codingQuestionRepository.findById(dto.getQuestionId());

			if (optionalCodingQuestion.isPresent()) {
				CodingQuestion existingCodingQuestion = optionalCodingQuestion.get();
				if (existingCodingQuestion.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(
							messageSource.getMessage("coding.details.psh.VAL0003", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}
				BeanUtils.copyProperties(dto, existingCodingQuestion);

				User user = userRepository.findById(dto.getUserId())
						.orElseThrow(() -> new RecordNotFoundException("admin.details.psh.VAL0006"));
				existingCodingQuestion.setUser(user);
				existingCodingQuestion.setModifiededBy(principal.getName());

				codingQuestionRepository.save(existingCodingQuestion);
				return new ServiceResponse(
		                messageSource.getMessage("coding.details.psh.VAL0005", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("coding.details.psh.VAL0011", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		}catch(RecordNotFoundException e) {
	        logger.error("Error:" + e.getMessage(), e);

			return new ServiceResponse(
					messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}
		catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("coding.details.psh.VAL0004", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);	  
		}
	}

	@Override
	public ServiceResponse deleteMcqQuestion(Long questionId, Principal principal) {
		try {
			Optional<McqQuestion> optionalMcqQuestion = mcqQuestionRepository.findById(questionId);
			if (optionalMcqQuestion.isPresent()) {
				

			
				mcqQuestionRepository.deleteById(questionId);
				return new ServiceResponse(
						messageSource.getMessage("mcq.details.psh.VAL0007", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.DELETE, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("mcq.details.psh.VAL0011", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);

			return new ServiceResponse(
					messageSource.getMessage("mcq.details.psh.VAL0008", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);		
		}
	}

	@Override
	public ServiceResponse deleteCodingQuestion(Long questionId, Principal principal) {
		try {
			Optional<CodingQuestion> optionalCodingQuestion = codingQuestionRepository.findById(questionId);
			if (optionalCodingQuestion.isPresent()) {

				
				codingQuestionRepository.deleteById(questionId);
				return new ServiceResponse(
						messageSource.getMessage("coding.details.psh.VAL0007", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.DELETE, null);
				
			} else {
				return new ServiceResponse(
						messageSource.getMessage("coding.details.psh.VAL0011", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.FAILED,null);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);

			return new ServiceResponse(
					messageSource.getMessage("coding.details.psh.VAL0008", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);	
		}
	}

}
