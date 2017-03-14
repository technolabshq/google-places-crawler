package in.technolabs.scrapper.service.impl;

import in.technolabs.scrapper.convertor.EdGoogleScrapperConvertor;
import in.technolabs.scrapper.entity.EdGoogleScrapperEntity;
import in.technolabs.scrapper.exception.EdnomyException;
import in.technolabs.scrapper.model.EdGoogleScrapper;
import in.technolabs.scrapper.repository.EdGoogleScrapperRepository;
import in.technolabs.scrapper.service.EdGoogleScrapperService;
import in.technolabs.scrapper.util.CopyUtility;

import java.util.Map;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EdGoogleScrapperServiceImpl implements EdGoogleScrapperService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger
			.getLogger(EdGoogleScrapperServiceImpl.class);

	@Autowired
	EdGoogleScrapperRepository edGoogleScrapperRepository;
	
	@Autowired
	EdGoogleScrapperConvertor edGoogleScrapperConvertor;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private CopyUtility copyUtility;

	@Override
	@Transactional
	public EdGoogleScrapper save(EdGoogleScrapper edGoogleScrapper) {
		EdGoogleScrapperEntity edGoogleScrapperEntity = edGoogleScrapperConvertor.deassembler(edGoogleScrapper, 0);
		edGoogleScrapperEntity = edGoogleScrapperRepository.save(edGoogleScrapperEntity);
		return edGoogleScrapperConvertor.assembler(edGoogleScrapperEntity, 0);
	}
	
	@Override
	@Transactional
	public long count(Map<String, String> queryMap) {
		return edGoogleScrapperRepository.count(queryMap);
	}
	
	@Override
	@Transactional
	public EdGoogleScrapper get(String id) {
		EdGoogleScrapperEntity tempCodeEntity = edGoogleScrapperRepository.findOne(id);
		if (tempCodeEntity == null) {
			throw new EdnomyException(this.messageSource.getMessage(
					"webapi.response.dataNotFound", new String[] { "" }, null));
		}
		return edGoogleScrapperConvertor.assembler(tempCodeEntity, 0);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		EdGoogleScrapperEntity tempEdGoogleScrapperListEntity = edGoogleScrapperRepository
				.findOne(id);
		if (tempEdGoogleScrapperListEntity == null) {
			throw new EdnomyException(this.messageSource.getMessage(
					"webapi.response.dataNotFound", new String[] { "" }, null));
		} else {
			edGoogleScrapperRepository.delete(tempEdGoogleScrapperListEntity);
		}
	}

	@Override
	@Transactional
	public Set<EdGoogleScrapper> list(Map<String, String> queryMap) {
		Set<EdGoogleScrapperEntity> edGoogleScrapperEntities = edGoogleScrapperRepository.list(queryMap);
		return edGoogleScrapperConvertor.assembler(edGoogleScrapperEntities, 0);
	}
	
	
	@Override
	@Transactional
	public EdGoogleScrapper patch(EdGoogleScrapper edGoogleScrapper) {
		EdGoogleScrapper tempEdGoogleScrapperList = get(edGoogleScrapper.getId());
		copyUtility.copyUtil(edGoogleScrapper, tempEdGoogleScrapperList);
		return save(tempEdGoogleScrapperList);
	}

}