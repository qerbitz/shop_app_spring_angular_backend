package com.shop.shop.Algorithm;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.associations.AssociationRules;
import weka.associations.Item;
import weka.core.Instances;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Weka {


    public List<Integer> Apriori(String product_name) throws Exception {

        List<Integer> id_products_list = new ArrayList<>();

        StringBuilder recommendedProduct = new StringBuilder();

        Instances data = BasicTools.loadData("src/main/resources/Apriori.arff");
        data.setClassIndex(data.numAttributes() - 1);


        //Opcje liczenia regul asocjacyjnych
        //-N ->Liczba regul do policzenia (standardowo: 10)
        //-C ->Minmalna ufnosc reguly (standardowo: 0.9).
        String[] options = Utils.splitOptions("-N 40 -C 0.6");
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


            String first = poprzednikText;
            String second = nastepnikText;

            int id_product1 = 0;
            int id_product2 = 0;

            if (first.matches(".product.*=" + product_name + ".*")) {

                id_product1 = Integer.parseInt(first.substring(first.length() - 2, first.length() - 1));
                id_product2 = Integer.parseInt(second.substring(second.length() - 2, second.length() - 1));
                if (!id_products_list.contains(id_product1) ) {
                    id_products_list.add(id_product1);
                }
                if (!id_products_list.contains(id_product2) ) {
                    id_products_list.add(id_product2);
                }
            }
            if (second.matches(".product.*=" + product_name + ".*")) {

                id_product1 = Integer.parseInt(first.substring(first.length() - 2, first.length() - 1));
                id_product2 = Integer.parseInt(second.substring(second.length() - 2, second.length() - 1));
                if (!id_products_list.contains(id_product1) ) {
                    id_products_list.add(id_product1);
                }
                if (!id_products_list.contains(id_product2) ) {
                    id_products_list.add(id_product2);
                }
            }

            //System.out.println(poprzednikText + "=>" + nastepnikText);



        }
        System.out.println(recommendedProduct.toString());
        return id_products_list;
    }

}





