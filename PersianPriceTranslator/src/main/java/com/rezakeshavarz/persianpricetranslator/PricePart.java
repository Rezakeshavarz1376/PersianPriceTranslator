package com.rezakeshavarz.persianpricetranslator;

public class PricePart
{

    private PriceSuffix Suffix;
    private String price;

    public enum PriceSuffix
    {
        NOUN(1),
        THOUSAND(2),
        MILLION(3),
        BILLION(4),
        TRILLION(5);
        private final int value ;
        PriceSuffix(int id)
        {
            this.value = id;
        }
        public int getValue()
        {
            return value;
        }
    }
    public PricePart(PriceSuffix suffix, String price) {
        Suffix = suffix;
        this.price = price;
    }

    public PriceSuffix getSuffix() {
        return Suffix;
    }

    public String getPrice() {
        return price;
    }
}
