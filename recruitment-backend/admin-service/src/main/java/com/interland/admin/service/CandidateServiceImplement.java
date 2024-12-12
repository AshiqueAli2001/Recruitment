package com.interland.admin.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.interland.admin.exception.RecordNotFoundException;
import com.interland.admin.repository.FinalResultRepository;
import com.interland.admin.repository.UserRepository;
import com.interland.admin.repository.specification.UserSpec;
import com.interland.admin.utils.Constants;

@Service
public class CandidateServiceImplement implements CandidateService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FinalResultRepository finalResultRepository;
	@Autowired
	UserService userService;
	@Autowired
	MessageSource messageSource;

	@Autowired
	PasswordEncoder passwordEncoder;
	
    private static Logger logger =LogManager.getLogger(CandidateServiceImplement.class);

	

	public String isNumeric(String no) {
		try {
			BigInteger a = new BigDecimal(no).toBigInteger();
			return String.valueOf(a);
		} catch (Exception e) {
			return no;
		}

	}

	@Override
	public ServiceResponse addCandidate(MultipartFile file, Principal principal) {

		try {

			List<JSONObject> customMessage = new ArrayList<>();
			JSONObject msgDet = new JSONObject();
			User user = new User();

			UserDTO dto = new UserDTO();
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow header = sheet.getRow(sheet.getFirstRowNum());

			int rowIndex = 0;
			int flag;
			int createdCount = 0;
			int existCount = 0;
			int corruptCount = 0;
			int totalCount = sheet.getPhysicalNumberOfRows() - 1;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					totalCount--;
					continue;
				}
				flag = 0;
				if (rowIndex == 0) {
					rowIndex++;
					continue;
				}

				int cellIndex = 0;
				for (int j = 0; j < header.getPhysicalNumberOfCells(); j++) {
					Cell cell = row.getCell(j);
					if ( cell==null) {
						flag = 1;
						cellIndex++;
						continue;
					}

					switch (cellIndex) {
					case 0 -> dto.setUserId(cell.toString());
					case 1 -> dto.setPassword(cell.toString());
					case 2 -> dto.setName(cell.toString());
					case 3 -> dto.setEmail(cell.toString());
					case 4 -> dto.setPhone(isNumeric(cell.toString()));
					case 5 -> dto.setCollege(cell.toString());
					case 6 -> dto.setDepartment(cell.toString());

					default -> {
					}
					}
					cellIndex++;

				}

				Optional<User> obj = userRepository.findById(dto.getUserId());

				if (flag == 1) {
					corruptCount++;
				}

				else if (obj.isPresent()) {
					existCount++;
				} else {

					BeanUtils.copyProperties(dto, user);

					user.setPassword(passwordEncoder.encode(dto.getPassword()));
					user.setRole(Role.USER);
					user.setNoOfAttempts(0);
					user.setHighestSCore(0);
					
					user.setCreatedBy(principal.getName());
					user.setCreatedTime(new Date());
					user.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
					user.setModifiededBy(principal.getName());
					userRepository.save(user);
					createdCount++;
				}


			}
			msgDet.put("createdCount", createdCount);
			msgDet.put("existCount", existCount);
			msgDet.put("corruptCount", corruptCount);
			msgDet.put("totalCount", totalCount);
			customMessage.add(msgDet);
			workbook.close();
			return new ServiceResponse(messageSource.getMessage("candidate.details.psh.VAL0001", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, customMessage);
		}

		catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse( messageSource.getMessage("candidate.details.psh.VAL0002", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}
	}
	
	

	private JSONArray countByStatus(Specification<User> spec) {
		JSONArray array = new JSONArray();
		try {
			List<User> headerList = userRepository.findAll(spec);
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(User::getStatus, Collectors.counting()));
			for (String status : countByStatus.keySet()) {
				JSONObject obj = new JSONObject();
				obj.put("name", status);
				obj.put("count", countByStatus.get(status));
				array.add(obj);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
		}
		return array;
}


	
	
	@Override
	public JSONObject searchCandidate(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<User> spec = UserSpec.getUserSpec(searchParam);
			Page<User> userList = userRepository.findAll(spec, pageable);

			JSONArray countByStatus = countByStatus(spec);
			JSONArray array = new JSONArray();

			for (User user : userList) {
				JSONObject obj = new JSONObject();

				obj.put("userId", user.getUserId());
				obj.put("name", user.getName());
				obj.put("email", user.getEmail());
				obj.put("phone", user.getPhone());
				obj.put("college", user.getCollege());
				obj.put("department", user.getDepartment());
				obj.put("image", user.getImage());
				obj.put("noOfAttempts", user.getNoOfAttempts());
				obj.put("highestScore", user.getHighestSCore());
				obj.put("status", user.getStatus());

				array.add(obj);

			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", userRepository.findAll(spec).size());
			result.put("iTotalRecords", userRepository.findAll(spec).size());
			
			

			result.put("countByStatus", countByStatus);

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ServiceResponse updateCandidate(UserDTO dto, MultipartFile file, Principal principal) {
		try {
			Optional<User> optionalUser = userRepository.findById(dto.getUserId());

			if (optionalUser.isPresent()) {
				User existingUser = optionalUser.get();
				
				if (existingUser.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(
							messageSource.getMessage("candidate.details.psh.VAL0003", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}

				if (dto.getPassword() != null && dto.getPassword() != existingUser.getPassword()) {
					dto.setPassword(passwordEncoder.encode(dto.getPassword()));
				}
				if(dto.getPassword()==null) {
	            	dto.setPassword(existingUser.getPassword());            	
	            }

				BeanUtils.copyProperties(dto, existingUser);

//	           
				if (file != null) {
	            	existingUser.setImage(file.getBytes());
				}

				existingUser.setModifiededBy(principal.getName());
				userRepository.save(existingUser);

				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0005", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("candidate.details.psh.VAL0004", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}
	}

	@Override
	public ServiceResponse deleteCandidate(String userId,Principal principal) {
		try {
			Optional<User> userOptional = userRepository.findById(userId);

			if (userOptional.isPresent()) {
				User existingUsers = userOptional.get();

				if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETE)) {
					return new ServiceResponse(
							messageSource.getMessage("candidate.details.psh.VAL0009", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}
				if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(
							messageSource.getMessage("candidate.details.psh.VAL0009", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}

				existingUsers.setStatus(Constants.MESSAGE_STATUS.DELETE);
				existingUsers.setModifiededBy(principal.getName());

				userRepository.save(existingUsers);
//				userRepository.deleteById(userId);
				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0007", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.DELETE, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);

			return new ServiceResponse(
					messageSource.getMessage("candidate.details.psh.VAL0008", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);	
			}
	}

	@Override
	public User getUserById(String id) {
		Optional<User> obj = userRepository.findById(id);
		if (obj.isPresent()) {

			User user = obj.get();


			return user;
		} else {

return null;		}
	}

	@Override
	public FinalResult searchFinalResultById(Long id) {
		Optional<FinalResult> obj = finalResultRepository.findById(id);
		if (obj.isPresent()) {

			FinalResult finalResult = obj.get();


			return finalResult;
		} else {

return null;		}
	}

	@Override
	public ServiceResponse verifyCandidate(String userId, Principal principal) {
		try {
			Optional<User> optionalUser = userRepository.findById(userId);

			if (optionalUser.isPresent()) {
				User existingUser = optionalUser.get();
				
				if (existingUser.getStatus().contentEquals(Constants.MESSAGE_STATUS.VERIFIED)) {
					return new ServiceResponse(
							messageSource.getMessage("candidate.details.psh.VAL0010", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}
				if (existingUser.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(
							messageSource.getMessage("candidate.details.psh.VAL0003", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED,
							null);
				}

				if (existingUser.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETE)) {
					existingUser.setStatus(Constants.MESSAGE_STATUS.DELETED);
					existingUser.setVerifiedBy(principal.getName());
					existingUser.setVerifiedTime(new Date());
				} else {
					existingUser.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
					existingUser.setVerifiedBy(principal.getName());
					existingUser.setVerifiedTime(new Date());
				}
				
			
			
				userRepository.save(existingUser);
				System.out.println(existingUser);
				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0011", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("candidate.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("candidate.details.psh.VAL0012", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}
	}

	

	@Override
	public ServiceResponse addIndividualCandidate(UserDTO dto, Principal principal) {
	    try {
	        User user = new User();
	        if (userRepository.existsById(dto.getUserId())) {
	            throw new RecordCreateException("candidate.details.psh.VAL0013");
	        }

	        BeanUtils.copyProperties(dto, user);
			user.setPassword(passwordEncoder.encode(dto.getUserId()) );
			user.setRole(Role.USER);
			user.setHighestSCore(0);
			user.setNoOfAttempts(0);
			
			user.setCreatedBy(principal.getName());
			user.setCreatedTime(new Date());
			user.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			user.setModifiededBy(principal.getName());
			userRepository.save(user);	        
	        return new ServiceResponse(
	                messageSource.getMessage("candidate.details.psh.VAL0001", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.SUCCESS,null);
	    } catch (RecordCreateException e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("candidate.details.psh.VAL0013", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
	    } catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("candidate.details.psh.VAL0002", null, LocaleContextHolder.getLocale()), Constants.MESSAGE_STATUS.FAILED,null);
	    }
	}

	
	
}
