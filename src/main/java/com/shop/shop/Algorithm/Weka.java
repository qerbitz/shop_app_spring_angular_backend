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

        Instances data = BasicTools.loadData("src/main/resources/Apriori.arff");
        data.setClassIndex(data.numAttributes() - 1);


        String[] options = Utils.splitOptions("-N 100000 -C 0.1");
        Apriori apriori = new Apriori();
        apriori.setOptions(options);
        apriori.buildAssociations(data); //Generowanie regul asocjacyjnych

        AssociationRules rules = apriori.getAssociationRules();
        List<AssociationRule> ruleList = rules.getRules();

        for (int i = 0; i < ruleList.size(); i++) {
            AssociationRule rule = ruleList.get(i); //Pobranie pojedynczej reguly

            //Pobranie opisu poprzednika reguly
            Collection<Item> poprzednik = rule.getPremise();
            Iterator<Item> iteratorPoprzednik = poprzednik.iterator();
            Iterator<Item> iteratorPoprzednik2 = poprzednik.iterator();
            String poprzednikText = new String();
            while (iteratorPoprzednik.hasNext()) {
                poprzednikText = poprzednikText + "(" + iteratorPoprzednik.next().toString() + ")";
                if (iteratorPoprzednik.hasNext()) {
                    poprzednikText = poprzednikText + "&";
                }
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
            //int wsparciePoprzednika = rule.getPremiseSupport();
            //int wsparcieCalosci = rule.getTotalSupport();
            //double ufnosc = (double) wsparcieCalosci / wsparciePoprzednika;


            String first = poprzednikText;
            String second = nastepnikText;

            int id_product1 = 0;
            int id_product2 = 0;


            String[] firstRule = first.split("&");
            String[] secondRule = second.split("&");


            for (int j = 0; j < firstRule.length; j++) {
                if (firstRule[j].matches(".product.*=" + product_name + ".*")) {

                    //Dla poprzednika
                    for (int k = 0; k < firstRule.length; k++) {
                        if (firstRule[k].length() == 14) {
                            id_product1 = Integer.parseInt(firstRule[k].substring(firstRule[k].length() - 3, firstRule[k].length() - 1));
                        }
                        if (firstRule[k].length() == 13) {
                            id_product1 = Integer.parseInt(firstRule[k].substring(firstRule[k].length() - 3, firstRule[k].length() - 1));
                        }
                        if (firstRule[k].length() == 12) {
                            id_product1 = Integer.parseInt(firstRule[k].substring(firstRule[k].length() - 2, firstRule[k].length() - 1));
                        }
                        if (!id_products_list.contains(id_product1)) {
                            id_products_list.add(id_product1);
                        }
                    }

                    //Dla nastepnika
                    for (int p = 0; p < secondRule.length; p++) {
                        if (secondRule[p].length() == 14) {
                            id_product2 = Integer.parseInt(secondRule[p].substring(secondRule[p].length() - 3, secondRule[p].length() - 1));
                        }
                        if (secondRule[p].length() == 13) {
                            id_product2 = Integer.parseInt(secondRule[p].substring(secondRule[p].length() - 3, secondRule[p].length() - 1));
                        }
                        if (secondRule[p].length() == 12) {
                            id_product2 = Integer.parseInt(secondRule[p].substring(secondRule[p].length() - 2, secondRule[p].length() - 1));
                        }
                        if (!id_products_list.contains(id_product2)) {
                            id_products_list.add(id_product2);
                        }
                    }
                }
            }

            for (int j = 0; j < secondRule.length; j++) {
                if (secondRule[j].matches(".product.*=" + product_name + ".*")) {

                    //Dla poprzednika
                    for (int k = 0; k < firstRule.length; k++) {
                        if (firstRule[k].length() == 14) {
                            id_product1 = Integer.parseInt(firstRule[k].substring(firstRule[k].length() - 3, firstRule[k].length() - 1));
                        }
                        if (firstRule[k].length() == 13) {
                            id_product1 = Integer.parseInt(firstRule[k].substring(firstRule[k].length() - 3, firstRule[k].length() - 1));
                        }
                        if (firstRule[k].length() == 12) {
                            id_product1 = Integer.parseInt(firstRule[k].substring(firstRule[k].length() - 2, firstRule[k].length() - 1));
                        }
                        if (!id_products_list.contains(id_product1)) {
                            id_products_list.add(id_product1);
                        }
                    }

                    //Dla nastepnika
                    for (int p = 0; p < secondRule.length; p++) {
                        if (secondRule[p].length() == 14) {
                            id_product2 = Integer.parseInt(secondRule[p].substring(secondRule[p].length() - 3, secondRule[p].length() - 1));
                        }
                        if (secondRule[p].length() == 13) {
                            id_product2 = Integer.parseInt(secondRule[p].substring(secondRule[p].length() - 3, secondRule[p].length() - 1));
                        }
                        if (secondRule[p].length() == 12) {
                            id_product2 = Integer.parseInt(secondRule[p].substring(secondRule[p].length() - 2, secondRule[p].length() - 1));
                        }
                        if (!id_products_list.contains(id_product2)) {
                            id_products_list.add(id_product2);
                        }
                    }
                }
            }
            //System.out.println(poprzednikText + "=>" + nastepnikText);
        }


        //Usuwanie aktualnie dodanego do koszyka
        for (int i = 0; i < id_products_list.size(); i++) {
            if (id_products_list.get(i) == Integer.parseInt(product_name)) {
                id_products_list.remove(i);
            }
        }


        return id_products_list;
    }

}





