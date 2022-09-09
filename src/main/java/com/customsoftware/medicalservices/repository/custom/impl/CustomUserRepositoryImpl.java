package com.customsoftware.medicalservices.repository.custom.impl;

import com.customsoftware.medicalservices.domain.User;
import com.customsoftware.medicalservices.repository.custom.CustomUserRepository;
import com.customsoftware.medicalservices.service.dto.search.SearchUserDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String UNSORTED = "UNSORTED";

    @Override
    public Page<User> search(SearchUserDTO searchUserDTO, Pageable pageable) {
        StringBuilder sqlCount = new StringBuilder(" select count(distinct u.id ) from User u ");
        StringBuilder sqlSelect = new StringBuilder(" select distinct u from User u");
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1=1 ");

        if (!StringUtils.isBlank(searchUserDTO.getQuery())) {
            sql.append(
                " and (" +
                " LOWER(u.dni) like CONCAT('%',:query,'%') or " +
                " LOWER(u.firstName) like CONCAT('%',:query,'%') or " +
                " LOWER(u.lastName) like CONCAT('%',:query,'%')" +
                " ) "
            );
        }

        if (CollectionUtils.isNotEmpty(searchUserDTO.getRoles())) {
            sql.append(" and u.authorities in (select a1 from Authority a1 where a1.name in (:roles)) ");
        }

        sqlSelect.append(sql);
        sqlCount.append(sql);

        if (pageable != null && !UNSORTED.equals(pageable.getSort().toString())) {
            String order = " order by u." + pageable.getSort().toString().replace(":", "").trim();
            //  sqlSelect.append(" order by u.id desc ");
            sqlSelect.append(order);
        }

        Query queryCount = entityManager.createQuery(sqlCount.toString());
        Query querySelect = entityManager.createQuery(sqlSelect.toString());

        if (!StringUtils.isBlank(searchUserDTO.getQuery())) {
            queryCount.setParameter("query", searchUserDTO.getQuery().toLowerCase());
            querySelect.setParameter("query", searchUserDTO.getQuery().toLowerCase());
        }

        if (CollectionUtils.isNotEmpty(searchUserDTO.getRoles())) {
            queryCount.setParameter("roles", searchUserDTO.getRoles());
            querySelect.setParameter("roles", searchUserDTO.getRoles());
        }

        querySelect.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        querySelect.setMaxResults(pageable.getPageSize());

        List<User> resultList = querySelect.getResultList();
        Long total = (Long) queryCount.getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
}
