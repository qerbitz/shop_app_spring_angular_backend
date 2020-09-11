package com.shop.shop.Algorithm;

import weka.core.Instances;
import weka.experiment.InstanceQuery;

import java.io.File;

public class SQLDataImporter
{


    public static Instances getDataSetFromPostgreSQL(String userName, String password,
                                                     String queryText, int limit) //limit liczby wierszy od początku tabeli
            throws Exception
    {

        InstanceQuery query = new InstanceQuery();
        query.setUsername(userName);
        query.setPassword(password);

        // plik ustawien polaczenia z baza danych
        query.setCustomPropsFile(new File("./src/settings/DatabaseUtils.props"));

        if (limit>0) query.setQuery(queryText +" limit "+limit+";");
        else query.setQuery(queryText+";");

        Instances data = query.retrieveInstances();

        return data;
    }
}