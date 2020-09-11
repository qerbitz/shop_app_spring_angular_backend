package com.shop.shop.Algorithm;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.associations.AssociationRules;
import weka.associations.Item;
import weka.core.Instances;
import weka.core.Utils;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Weka {

    public String Apriori(String danie) throws Exception {

        StringBuilder recommendedProduct = new StringBuilder();

        Instances data = BasicTools.loadData("src/main/resources/Apriori.arff");
        data.setClassIndex(data.numAttributes() - 1);


        //Opcje liczenia regul asocjacyjnych
        //-N ->Liczba regul do policzenia (standardowo: 10)
        //-C ->Minmalna ufnosc reguly (standardowo: 0.9).
        String[] options = Utils.splitOptions("-N 10 -C 0.6");
        Apriori apriori = new Apriori();
        apriori.setOptions(options);
        apriori.buildAssociations(data); //Generowanie regul asocjacyjnych

        System.out.println("Liczba regul=" + apriori.getNumRules());

        AssociationRules rules = apriori.getAssociationRules();
        List<AssociationRule> ruleList = rules.getRules();

        for (int i = 0; i < ruleList.size(); i++) {
            AssociationRule rule = ruleList.get(i); //Pobranie pojedynczej reguly
            //Pobranie opisu poprzednika reguly
            Collection<Item> poprzednik = rule.getPremise();
            Iterator<Item> iteratorPoprzednik = poprzednik.iterator();
            String poprzednikText = new String();
            while (iteratorPoprzednik.hasNext()) {
                poprzednikText = poprzednikText + "(" + iteratorPoprzednik.next().toString() + ")";
                if (iteratorPoprzednik.hasNext()) poprzednikText = poprzednikText + "&";
            }

            //Pobranie opisu nastepnika reguly
            Collection<Item> nastepnik = rule.getConsequence();
            Iterator<Item> iteratorNastepnik = nastepnik.iterator();
            String nastepnikText = new String();
            while (iteratorNastepnik.hasNext()) {
                nastepnikText = nastepnikText + "(" + iteratorNastepnik.next().toString() + ")";
                if (iteratorNastepnik.hasNext()) nastepnikText = nastepnikText + "&";
            }


            //Pobranie wsparcie i obliczenia ufnosci
            int wsparciePoprzednika = rule.getPremiseSupport();
            int wsparcieCalosci = rule.getTotalSupport();
            double ufnosc = (double) wsparcieCalosci / wsparciePoprzednika;



            String xd2 = poprzednikText;
            String xd3 = nastepnikText;


               // if(xd2.matches(".ubranie=" + danie+".*")){
                    recommendedProduct.append(poprzednikText);
                    recommendedProduct.append("=>");
                    recommendedProduct.append(nastepnikText);
                    recommendedProduct.append(", ");
                    recommendedProduct.append("Wsparcie:");
                    recommendedProduct.append(wsparcieCalosci);
                    recommendedProduct.append(", ");
                    recommendedProduct.append("Ufnosc:");
                    recommendedProduct.append(ufnosc);
                    recommendedProduct.append("\n");
                    recommendedProduct.append("\n");
                    recommendedProduct.append("\n");
               // }


            }
        System.out.println(recommendedProduct.toString());

        return "1";
    }

}





