package com.shop.ecommerce.config;

import com.shop.ecommerce.entity.Country;
import com.shop.ecommerce.entity.Product;
import com.shop.ecommerce.entity.ProductCategory;
import com.shop.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        HttpMethod[] theUnsupportedActions= {HttpMethod.PUT, HttpMethod.POST,HttpMethod.DELETE};

        disableHttpMethods(ProductCategory.class,config, theUnsupportedActions);
        disableHttpMethods(Product.class,config, theUnsupportedActions);
        disableHttpMethods(State.class,config, theUnsupportedActions);
        disableHttpMethods(Country.class,config, theUnsupportedActions);

        //call an internal helper method
        exposeIds(config);
    }

    private ExposureConfigurer disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        return config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        //expose entity ids

        //get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();

        List<Class> entityClass=new ArrayList<>();

        for(EntityType tempEn:entities){
            entityClass.add(tempEn.getJavaType());
        }

        //expose the entity ids for the array of entity types

        Class[] domainTypes=entityClass.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }
}
