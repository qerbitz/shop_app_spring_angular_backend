package com.shop.shop.Algorithm;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


public class BasicTools
{

    //Odczytanie tablicy danych z dysku w formacie ARFF
    public static Instances loadData(String fileName)
            throws IOException
    {
        ArffLoader loader = new ArffLoader(); //Utworzenie obiektu czytajacego dane z formatu ARFF
        loader.setFile(new File(fileName)); //Ustawienie pliku do odczytania
        return loader.getDataSet(); //Odczytanie danych z pliku
    }

}
