package com.azrin.email.service;

import com.azrin.email.ExceptionHandler.BadRequest;
import com.azrin.email.Model.EmailModel;
import com.azrin.email.Model.History;
import com.azrin.email.config.Messages;
import com.azrin.email.dto.HistoryDto;
import com.azrin.email.dto.PageInfoDto;
import com.azrin.email.repository.EmailRepository;
import com.azrin.email.repository.HistoryRepository;
import com.azrin.email.utils.Constants;
import com.azrin.email.utils.Converters;
import com.azrin.email.utils.MessagePropertyKey;
import com.azrin.email.utils.Status;
import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HistoryService {
    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private Converters converters;

    @Autowired
    private Messages messages;

    @Autowired
    private HistoryRepository historyRepository;

    @Value("${default.page.number}")
    private String defaultPageNumber;

    @Value("${default.page.size}")
    private String defaultPageSize;

    @Value("${max.page.element}")
    private String maxPageElement;

    public boolean saveHistory(EmailModel emailModel, boolean sent) throws Exception {
        String status = null;
        if(sent){
            status = Status.EMAIL_STATUS_SENT;
        }else{
            status = Status.EMAIL_STATUS_FAILED;
        }
        logger.info("Calling converters");
        History history = converters.convertEmailModelToHistory(emailModel, status);
        logger.info("Calling repository");
        History savedHistory = emailRepository.saveHistory(history);
        if(savedHistory != null){
            DeleteResult deleteResult = emailRepository.deleteEmail(emailModel.getId());
            if(deleteResult != null && deleteResult.getDeletedCount() >0){
                return true;
            }
        }
        return false;
    }

    public List<HistoryDto> getHistory(String pageNumber, String pageSize, PageInfoDto pageInfoDto) throws Exception{
        List<HistoryDto> historyDtos = new ArrayList<>();
        int pSize = (pageSize == null || pageSize.equalsIgnoreCase("")) ? Integer.parseInt(defaultPageSize) : Integer.parseInt(pageSize);
        int pNumber = (pageNumber == null || pageNumber.equalsIgnoreCase("")) ? Integer.parseInt(defaultPageNumber) : Integer.parseInt(pageNumber);
        if(pSize > Integer.parseInt(maxPageElement)){
            throw new BadRequest(messages.get(MessagePropertyKey.MAX_PAGE_SIZE_EXCEPTION));
        }
        logger.info("Calling dao layer for paginated data");
        Pageable pageable = PageRequest.of(pNumber, pSize, Sort.Direction.ASC, Constants.TIME);
        Page<History> page = historyRepository.getPagableHistory(pageable);
        if(page != null){
            logger.info("Got the data");
            List<History> histories = page.getContent();
            logger.info("Calling converters to convert the entities");
            for(History history : histories){
                historyDtos.add(converters.convertHistoryEntitytoDto(history));
            }
            pageInfoDto.setFirst(page.isFirst());
            pageInfoDto.setTotalPages(page.getTotalPages());
            pageInfoDto.setTotalElements(page.getTotalElements());
            pageInfoDto.setFirst(page.isFirst());
            pageInfoDto.setNumberOfElements(page.getNumberOfElements());
            pageInfoDto.setSize(page.getSize());
            pageInfoDto.setNumber(page.getNumber());
        }
        logger.info("Returning to controller from service");
        return historyDtos;
    }
}
