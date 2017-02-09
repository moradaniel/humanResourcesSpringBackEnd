package org.humanResources.security.entity;


import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.List;

public class RepositoryHelper {

    static public List findByNamedParam(EntityManager entityManager,final String queryString, final List<String> paramNames, final List<Object> values/*, final Type[] types*/, final Integer startIndex, final Integer maxResults)/* throws DataAccessException*/ {
        Query query = buildQuerByNamedParam(entityManager,queryString,paramNames,values,startIndex,maxResults);
        return query.getResultList();
    }

    static private Query buildQuerByNamedParam(EntityManager entityManager,final String queryString, final List<String> paramNames, final List<Object> values/*, final Type[] types*/, final Integer startIndex, final Integer maxResults)/* throws DataAccessException*/ {

        if (paramNames.size() != values.size()) {
            throw new IllegalArgumentException("Length of paramNames list must match length of values list");
        }

        Query query = entityManager.createQuery( queryString);

        if (maxResults != null) {
            query.setMaxResults(maxResults);
        }

        if (startIndex != null) {
            query.setFirstResult(startIndex);
        }

        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                applyNamedParameterToQuery(query, paramNames.get(i), values.get(i));
            }
        }
        return query;
    }

    static public long countByNamedParam(EntityManager entityManager, final String queryString, final List<String> paramNames, final List<Object> values){

        String queryStringWithNoFetchs = queryString.replaceAll("FETCH", " ").replaceAll("fetch", " ");

        Query query = buildQuerByNamedParam(entityManager,queryStringWithNoFetchs,paramNames,values,null,null);

        long count = (long) query.getSingleResult();
        return count;

    }

    public static Object uniqueElement(List list) throws NonUniqueResultException {
        int size = list.size();
        if (size == 0)
            return null;
        Object first = list.get(0);
        for (int i = 1; i < size; i++) {
            if (list.get(i) != first) {
                throw new NonUniqueResultException("non uniqueElement, size: "+list.size());
            }
        }
        return first;
    }


    public static void applyNamedParameterToQuery(Query queryObject, String paramName, Object value/*, Type type*/) {

        queryObject.setParameter(paramName, value);

    }

}
