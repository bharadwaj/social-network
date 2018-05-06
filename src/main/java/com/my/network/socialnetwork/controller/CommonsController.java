package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.product.phone.PhoneModel;
import org.hibernate.FlushMode;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@RestController
@RequestMapping(value = "/common")
public class CommonsController {

    @Autowired
    EntityManagerFactory entityManagerFactory;


    @GetMapping(value = "/phones/search/{query}")
    public ResponseEntity searchPhones(@PathVariable String query) {
        return new ResponseEntity<>(phoneSearch(query), HttpStatus.OK);
    }

    private List<PhoneModel> phoneSearch(String q) {


        EntityManager em = entityManagerFactory.createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query using the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(PhoneModel.class).get();
        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("name")
                .matching(q)
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query persistenceQuery =
                fullTextEntityManager.createFullTextQuery(query, PhoneModel.class);

// execute search
        List<PhoneModel> result = persistenceQuery.getResultList();

        em.getTransaction().commit();
        em.close();

        return result;
    }
}
