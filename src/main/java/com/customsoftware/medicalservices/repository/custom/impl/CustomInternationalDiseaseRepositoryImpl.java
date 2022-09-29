package com.customsoftware.medicalservices.repository.custom.impl;

import com.customsoftware.medicalservices.domain.InternationalDisease;
import com.customsoftware.medicalservices.repository.RepositoryConstants;
import com.customsoftware.medicalservices.repository.custom.CustomInternationalDiseaseRepository;
import com.customsoftware.medicalservices.service.dto.search.SearchInternationalDiseaseDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomInternationalDiseaseRepositoryImpl implements CustomInternationalDiseaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<InternationalDisease> search(SearchInternationalDiseaseDTO searchInternationalDiseaseDTO, Pageable pageable) {
        StringBuilder sqlCount = new StringBuilder(" select count(distinct iD.id ) from InternationalDisease iD ");
        StringBuilder sqlSelect = new StringBuilder(" select iD from InternationalDisease iD ");
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1=1 ");

        if (StringUtils.isNotBlank(searchInternationalDiseaseDTO.getQuery())) {
            sql.append(" and LOWER(iD.cod) like CONCAT('%',:query,'%') or LOWER(iD.description) like CONCAT('%',:query,'%')");
        }

        sqlSelect.append(sql);
        sqlCount.append(sql);

        if (pageable != null && !RepositoryConstants.UNSORTED.equals(pageable.getSort().toString())) {
            String order = " order by iD." + pageable.getSort().toString().replace(":", "").trim();
            sqlSelect.append(order);
        }

        Query queryCount = entityManager.createQuery(sqlCount.toString());
        Query querySelect = entityManager.createQuery(sqlSelect.toString());

        if (StringUtils.isNotBlank(searchInternationalDiseaseDTO.getQuery())) {
            queryCount.setParameter("query", searchInternationalDiseaseDTO.getQuery().toLowerCase());
            querySelect.setParameter("query", searchInternationalDiseaseDTO.getQuery().toLowerCase());
        }

        querySelect.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        querySelect.setMaxResults(pageable.getPageSize());

        List<InternationalDisease> resultList = querySelect.getResultList();
        Long total = (Long) queryCount.getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
}
