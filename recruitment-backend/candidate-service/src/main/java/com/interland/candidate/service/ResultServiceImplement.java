package com.interland.candidate.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.candidate.dto.ResultDTO;
import com.interland.candidate.dto.ServiceResponse;
import com.interland.candidate.entity.CodingQuestion;
import com.interland.candidate.entity.Criteria;
import com.interland.candidate.entity.FinalResult;
import com.interland.candidate.entity.McqQuestion;
import com.interland.candidate.entity.Result;
import com.interland.candidate.entity.User;
import com.interland.candidate.repository.CodingQuestionRepository;
import com.interland.candidate.repository.CriteriaRepository;
import com.interland.candidate.repository.FinalResultRepository;
import com.interland.candidate.repository.McqQuestionRepository;
import com.interland.candidate.repository.ResultRepository;
import com.interland.candidate.repository.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ResultServiceImplement implements ResultService{
	
	@Autowired
	ResultRepository resultRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CriteriaRepository criteriaRepository;
	@Autowired
	McqQuestionRepository mcqQuestionRepository;
	@Autowired
	CodingQuestionRepository codingQuestionRepository;
	@Autowired
	FinalResultRepository finalResultRepository;

	@Override
	public ServiceResponse addMcqResults(List<ResultDTO> dtoList) {
		try {
	        for (ResultDTO dto : dtoList) {
	        	
	            Result result = new Result();
	            
	            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
	            User user = optionalUser.get();
	            
	            Optional<McqQuestion> optionalMcqQuestion = mcqQuestionRepository.findById(dto.getQuestionId());
	            McqQuestion mcqQuestion = optionalMcqQuestion.get();
	            
		        result.setUser(user);
		        result.setMcqQuestion(mcqQuestion);
		        result.setType("MCQ");
		        result.setSelectedOption(dto.getSelectedOption());
		        result.setAttemptNo(user.getNoOfAttempts());
		        resultRepository.save(result);
				
	        }
	        return new ServiceResponse("McqQuestions for the candidate saved successfully", null, null);
		}catch(Exception e) {
			return new ServiceResponse("Error saving mcqQuestions for the candidate : " + e.getMessage(), null, null);
		}
	       
	    }

	@Override
	public ServiceResponse addCodingResults(ResultDTO dto) {
		try {
	       
	        	
	            Result result = new Result();
	            
	            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
	            User user = optionalUser.get();
	            
	            Optional<CodingQuestion> optionalCodingQuestion = codingQuestionRepository.findById(dto.getQuestionId());
	            CodingQuestion codingQuestion = optionalCodingQuestion.get();
	            
		        result.setUser(user);
		        result.setCodingQuestion(codingQuestion);
		        result.setType("CODING");
		        result.setTestCase1Status(dto.getTestCase1Status());
		        result.setTestCase2Status(dto.getTestCase2Status());
		        result.setTestCase3Status(dto.getTestCase3Status());
		        result.setTestCase4Status(dto.getTestCase4Status());
		        result.setTestCase5Status(dto.getTestCase5Status());
		        result.setCode(dto.getCode());
		        result.setAttemptNo(user.getNoOfAttempts());
		        resultRepository.save(result);
				
	        
	        return new ServiceResponse("CodingQuestions for the candidate saved successfully", null, null);
		}catch(Exception e) {
			return new ServiceResponse("Error saving CodingQuestions for the candidate : " + e.getMessage(), null, null);
		}
	}

	@Override
	public ServiceResponse addFinalResult(String userId, Long finishingTime, Long criteriaId) {
		try {
			
			  Optional<User> optionalUser = userRepository.findById(userId);
	          User user = optionalUser.get();
	          int noOfAttempts = user.getNoOfAttempts();
	          System.out.println("No of attempts"+noOfAttempts);
	          List<Result> resultList = resultRepository.getResultByUserIdAndAttemptNo(userId,noOfAttempts);
	          int mcqPoints=0,codingPoints=0,totalPoints=0;
	          Document document = new Document();
	          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	          PdfWriter.getInstance(document, byteArrayOutputStream);
	          document.open();
	          
	          for (Result result : resultList) {
	        	  if(result.getType().equals("MCQ")) {
	        		  McqQuestion mcqQusetion = mcqQuestionRepository.findById(result.getMcqQuestion().getQuestionId()).get();
	        		  if(result.getSelectedOption()!=null && result.getSelectedOption().equals(mcqQusetion.getCorrectAnswer()))
	        		  {
	        			  mcqPoints++;
	        		  }
	        	  }
	        	  else if(result.getType().equals("CODING")){
	        		  CodingQuestion codingQuestion = result.getCodingQuestion();
	        		  document.add(new Paragraph("Question: " + codingQuestion.getQuestion()));
	                  document.add(new Paragraph("Code: " + result.getCode()));
	                  document.add(new Paragraph("Test Case 1 Status: " + result.getTestCase1Status()));
	                  document.add(new Paragraph("Test Case 2 Status: " + result.getTestCase2Status()));
	                  document.add(new Paragraph("Test Case 3 Status: " + result.getTestCase3Status()));
	                  document.add(new Paragraph("Test Case 4 Status: " + result.getTestCase4Status()));
	                  document.add(new Paragraph("Test Case 5 Status: " + result.getTestCase5Status()));
	                  document.add(new Paragraph("\n"));

	        		  if(result.getTestCase1Status().equals("PASS")&&result.getTestCase2Status().equals("PASS")&&result.getTestCase3Status().equals("PASS")&&result.getTestCase4Status().equals("PASS")&&result.getTestCase5Status().equals("PASS")) {
	        			  codingPoints++;
	        		  }
	        	  }
		        
					
		        }
	          document.close();
	          totalPoints=mcqPoints+codingPoints;
	          byte[] pdfContent = byteArrayOutputStream.toByteArray();
	          FinalResult finalResult = new FinalResult();
	          finalResult.setUser(user);
	          finalResult.setFinishingTime(finishingTime);
	          finalResult.setMcqScore(mcqPoints);
	          finalResult.setCodingScore(codingPoints);
	          finalResult.setScore(totalPoints);
	          Criteria criteria = criteriaRepository.findById(criteriaId).get();
	          finalResult.setCriteria(criteria);
	          finalResult.setPdf(pdfContent);
	          
	          int totalQuestions = criteria.getTotalQuestions();
	          int correctAnswers = totalPoints;

	        
	          double percentage = ((double)correctAnswers / totalQuestions) * 100;

	          if (percentage >= 75.0) {
	              finalResult.setResult("PASS");
	          } else {
	              finalResult.setResult("FAIL");
	          }
	          
	          finalResultRepository.save(finalResult);
	          if(user.getHighestSCore()<(totalPoints))
	        	  user.setHighestSCore(totalPoints);
	          userRepository.save(user);
			return new ServiceResponse("Your test has been saved successfully...", null, null);
		}
		catch(Exception e) {
			return new ServiceResponse("Error Calculating Finalresult for the candidate : " + e.getMessage(), null, null);
		}
	}
		

	
	
	
	

}
