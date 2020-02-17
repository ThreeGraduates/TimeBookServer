package com.service;

import com.dao.UserDao;
import com.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Page<User> findAllByPage(int pageSize, int page, final String search){
        Specification<User> spec=new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList=new ArrayList<>();
                if(!StringUtils.isEmpty(search)){
                    Predicate p=cb.like(root.<String>get("username"),'%'+search+'%');
                    predicateList.add(p);
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(predicates));
            }
        };
        Sort sort=new Sort(Sort.Direction.ASC,"createTime");
        return this.userDao.findAll(spec,new PageRequest(page,pageSize,sort));
    }
}
