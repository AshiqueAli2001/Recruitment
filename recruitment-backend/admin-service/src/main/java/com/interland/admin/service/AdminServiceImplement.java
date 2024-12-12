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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.dto.UserDTO;
import com.interland.admin.entity.FinalResult;
import com.interland.admin.entity.Role;
import com.interland.admin.entity.User;
import com.interland.admin.exception.RecordCreateException;
import com.interland.admin.repository.FinalResultRepository;
import com.interland.admin.repository.UserRepository;
import com.interland.admin.repository.specification.FinalResultSpec;
import com.interland.admin.repository.specification.UserSpec;
import com.interland.admin.utils.Constants;

@Service
public class AdminServiceImplement implements AdminService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FinalResultRepository finalResultRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MessageSource messageSource;
	
	 private static Logger logger =LogManager.getLogger(AdminServiceImplement.class);
	

	@Override
	public ServiceResponse addAdmin(UserDTO dto, Principal principal) {
	    try {
	        User user = new User();
	        if (userRepository.existsById(dto.getUserId())) {
	            throw new RecordCreateException("admin.details.psh.VAL0010");
	        }

	        BeanUtils.copyProperties(dto, user);
			user.setPassword(passwordEncoder.encode(dto.getUserId()) );
			user.setRole(Role.ADMIN);
			user.setHighestSCore(0);
			user.setNoOfAttempts(0);
			
			user.setCreatedBy(principal.getName());
			user.setCreatedTime(new Date());
			user.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			user.setModifiededBy(principal.getName());
			userRepository.save(user);	        
	        return new ServiceResponse(
	                messageSource.getMessage("admin.details.psh.VAL0001", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.SUCCESS,null);
	    } catch (RecordCreateException e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("admin.details.psh.VAL0010", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
	    } catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("admin.details.psh.VAL0002", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.FAILED,null);
	    }
	}
	
	
	
	
	@Override
	public ServiceResponse updateAdmin(UserDTO dto, MultipartFile file, Principal principal) {
	    try {
	    	
	        Optional<User> optionalUser = userRepository.findById(dto.getUserId());

	        if (optionalUser.isPresent()) {
	            User existingUser = optionalUser.get();
	    	            
	        	if (existingUser.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(
							messageSource.getMessage("admin.details.psh.VAL0003", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}

	            if(dto.getPassword()!=null && dto.getPassword()!=existingUser.getPassword()) {
	            	dto.setPassword(passwordEncoder.encode(dto.getPassword()));            	
	            }
	            if(dto.getPassword()==null) {
	            	dto.setPassword(existingUser.getPassword());            	
	            }
	            
	            BeanUtils.copyProperties(dto, existingUser);
	            if (file != null) {
	            	existingUser.setImage(file.getBytes());
				}
	            existingUser.setModifiededBy(principal.getName());
	            userRepository.save(existingUser);
	            return new ServiceResponse(
		                messageSource.getMessage("admin.details.psh.VAL0005", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
	        } else {
				return new ServiceResponse(
						messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
	    } catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("admin.details.psh.VAL0004", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);	    }
	}
	
	@Override
	public ServiceResponse deleteAdmin(String userId, Principal principal) {
		 try {
		        Optional<User> userOptional = userRepository.findById(userId);
		        if (userOptional.isPresent()) {
		        	User existingUsers = userOptional.get();

					if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETE)) {
						return new ServiceResponse(
								messageSource.getMessage("admin.details.psh.VAL0009", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
								null);
					}
					if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
						return new ServiceResponse(
								messageSource.getMessage("admin.details.psh.VAL0009", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
								null);
					}

					existingUsers.setStatus(Constants.MESSAGE_STATUS.DELETE);
					existingUsers.setModifiededBy(principal.getName());

					userRepository.save(existingUsers);
//					userRepository.deleteById(userId);
					return new ServiceResponse(
							messageSource.getMessage("admin.details.psh.VAL0007", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.DELETE, null);
		        } else {
		        	return new ServiceResponse(
							messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		        }
		    } catch (Exception e) {
		    	logger.error("Error:" + e.getMessage(), e);

				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0008", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);			    }
	}

	@Override
	public JSONObject searchAdmin(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<User> spec = UserSpec.getUserSpec(searchParam);
	        Page<User> userList = userRepository.findAll(spec, pageable);

			JSONArray array = new JSONArray();

			for (User user : userList) {
				JSONObject obj = new JSONObject();

				obj.put("userId", user.getUserId());
				obj.put("name", user.getName());
				obj.put("email", user.getEmail());
				obj.put("phone", user.getPhone());
				obj.put("status", user.getStatus());
				obj.put("image", user.getImage());
		
				array.add(obj);

			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", userRepository.findAll(spec).size());
			result.put("iTotalRecords", userRepository.findAll(spec).size());
//			result.put("countByStatus", countByStatus);

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public JSONObject searchFinalResult(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<FinalResult> spec = FinalResultSpec.getFinalResultSpec(searchParam);
	        Page<FinalResult> finalResultList = finalResultRepository.findAll(spec, pageable);

			JSONArray array = new JSONArray();

			for (FinalResult finalResult : finalResultList) {
				JSONObject obj = new JSONObject();

				obj.put("finalResultId", finalResult.getFinalResultId());
				obj.put("userId", finalResult.getUser().getUserId());
				obj.put("finishingTime", finalResult.getFinishingTime());
				obj.put("mcqScore", finalResult.getMcqScore());
				obj.put("codingScore", finalResult.getCodingScore());
				obj.put("score", finalResult.getScore());
				obj.put("result", finalResult.getResult());
				obj.put("criteriaId", finalResult.getCriteria().getCriteriaId());
				obj.put("pdf", finalResult.getPdf());
		
		
				array.add(obj);

			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", finalResultRepository.findAll(spec).size());
			result.put("iTotalRecords", finalResultRepository.findAll(spec).size());

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}




	@Override
	public FinalResult downloadFile(Long id) {
		

		Optional<FinalResult> obj = finalResultRepository.findById(id);
		if (obj.isPresent()) {
			
			FinalResult finalResult = obj.get();
		return finalResult;
		} else {
			return null;
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	
}
