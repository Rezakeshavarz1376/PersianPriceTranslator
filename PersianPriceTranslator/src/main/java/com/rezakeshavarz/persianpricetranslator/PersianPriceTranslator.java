package com.rezakeshavarz.persianpricetranslator;

import com.rezakeshavarz.persianpricetranslator.library_exceptions.PriceInvalidFormatException;
import com.rezakeshavarz.persianpricetranslator.library_exceptions.PriceOutOfRangeException;

import java.util.ArrayList;
import java.util.List;

public class PersianPriceTranslator
{
    private CurrencyUnit currencyUnit=CurrencyUnit.RIAL;
    private LetterPriceFormat letterPriceFormat = LetterPriceFormat.FORMAT_LETTER_ONLY;
    public enum LetterPriceFormat
    {
        FORMAT_LETTER_ONLY,
        FORMAT_NUMBER_AND_LETTER
    }

    public enum CurrencyUnit
    {
        RIAL,
        TOOMAN
    }

    /**
     * this method is for setting currency unit that can use RIAL if price parameter is Rial unit
     * or use TOOMAN if price parameter is Tooman unit
     * @param currencyUnit unit of price that you want pass for translate
     * @return object of PersianPriceTranslator
     */
    public PersianPriceTranslator setCurrencyUnit(CurrencyUnit currencyUnit)
    {
        this.currencyUnit = currencyUnit;
        return this;
    }

    /**
     * this method is for setting format of outputStringValue(Translated Price)
     * if you use FORMAT_LETTER_ONLY translated price returns you with Letter only
     * example : سیصد و بیست هزار تومان
     * if you use FORMAT_NUMBER_AND_LETTER , translate price returns you with number and letter
     * example  :
     * 320 هزار تومان
     * @param letterPriceFormat
     * @return object of PersianPriceTranslator
     */
    public PersianPriceTranslator setLetterPriceFormat(LetterPriceFormat letterPriceFormat)
    {
        this.letterPriceFormat = letterPriceFormat;
        return this;
    }

    /**
     * this method receive price as parameter and return translated price
     * @param price you should set this parameter with price value that you want to translate
     *              type of this parameter is String
     * @return translated price in String format
     */
    public String convertNumeralPriceToLetters(String price)
    {
        if (price == null)
            throw new NullPointerException("Price Parameter of convertNumeralPriceToLetters Method is null");

        if (price.equals(""))
            return "";
        if (price.startsWith("0"))
            throw new PriceInvalidFormatException("Price Parameter Start with 0 ");
        try
        {
             Long.parseLong(price);
        }catch (Exception e)
        {
            throw new PriceInvalidFormatException("The price parameter contains invalid character(s).");
        }
        if (currencyUnit== CurrencyUnit.RIAL)
        {
            if (price.length()==1)
                return "";
            if (price.length()>16)
                throw new PriceOutOfRangeException("The input price parameter exceeds the range supported by the library.");

            price = price.substring(0,price.length()-1);

        }

        if (currencyUnit ==CurrencyUnit.TOOMAN && price.length()>15)
            throw new PriceOutOfRangeException("The input price parameter exceeds the range supported by the library.");


        List<PricePart> priceParts =new ArrayList<>();
        int count=0;
        PricePart.PriceSuffix priceSuffix = PricePart.PriceSuffix.MILLION ;
        for (int i = price.length()-1;i>=0;i-=3)//44534
        {

            count++;
            if (count == PricePart.PriceSuffix.NOUN.getValue())
                priceSuffix = PricePart.PriceSuffix.NOUN;
            if (count == PricePart.PriceSuffix.THOUSAND.getValue())
                priceSuffix = PricePart.PriceSuffix.THOUSAND;
            if (count == PricePart.PriceSuffix.MILLION.getValue())
                priceSuffix = PricePart.PriceSuffix.MILLION;
            if (count == PricePart.PriceSuffix.BILLION.getValue())
                priceSuffix = PricePart.PriceSuffix.BILLION;
            if (count == PricePart.PriceSuffix.TRILLION.getValue())
                priceSuffix = PricePart.PriceSuffix.TRILLION;

            try
            {
                priceParts.add(new PricePart(priceSuffix,price.substring(i-2,i+1)));
            }catch (Exception e)
            {
                priceParts.add(new PricePart(priceSuffix,price.substring(0,i+1)));
            }

        }



        return addSuffix(priceParts);
    }

    private String addSuffix(List<PricePart> priceParts)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = priceParts.size()-1; i>=0; i--)
        {

            if (!checkAllNumberNotZeroInPart(priceParts.get(i).getPrice()))
                continue;
            if (letterPriceFormat == LetterPriceFormat.FORMAT_LETTER_ONLY)
            {
                stringBuilder.append(numbersToLetter(priceParts.get(i).getPrice()));
            }else if (letterPriceFormat == LetterPriceFormat.FORMAT_NUMBER_AND_LETTER)
            {
                stringBuilder.append(priceParts.get(i).getPrice()).append(" ");
            }

            if (priceParts.get(i).getSuffix() == PricePart.PriceSuffix.TRILLION)
            {
                stringBuilder.append(" ").append("تریلیون و ");
            }
            if (priceParts.get(i).getSuffix() == PricePart.PriceSuffix.BILLION)
            {
                stringBuilder.append(" ").append("میلیارد و ");
            }else if (priceParts.get(i).getSuffix() == PricePart.PriceSuffix.MILLION)
            {
                stringBuilder.append(" ").append("میلیون و ");
            }else if (priceParts.get(i).getSuffix() == PricePart.PriceSuffix.THOUSAND)
            {
                stringBuilder.append(" ").append("هزار و ");
            }
        }

        if (stringBuilder.toString().endsWith(" و "))
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("و "));
        stringBuilder.append(" ").append("تومان");
        return stringBuilder.toString();
    }
    private boolean checkAllNumberNotZeroInPart(String num)
    {
        return !num.equals("000") && !num.equals("00") && !num.equals("0");
    }
    private String numbersToLetter(String number)
    {
        StringBuilder stringBuilder =new StringBuilder();
        switch (number.trim().length())
        {
            case 3:
            {
                stringBuilder
                        .append(getSadgan(number.charAt(0),number.charAt(1),number.charAt(2)));
                if (getYekanAndDahgan(number.charAt(1),number.charAt(2)).equals(""))
                {
                    stringBuilder
                            .append(getdahgan(number.charAt(1),number.charAt(2)))
                            .append(getYekan(number.charAt(2)));
                }else
                {
                    stringBuilder.append(getYekanAndDahgan(number.charAt(1),number.charAt(2)));
                }

                break;
            }
            case 2:
            {
                if (getYekanAndDahgan(number.charAt(0),number.charAt(1)).equals(""))
                {
                    stringBuilder
                            .append(getdahgan(number.charAt(0),number.charAt(1)))
                            .append(getYekan(number.charAt(1)));
                }else
                {
                    stringBuilder.append(getYekanAndDahgan(number.charAt(0),number.charAt(1)));
                }
                break;
            }
            case 1:
            {
                stringBuilder
                        .append(getYekan(number.charAt(0)));
                break;
            }
        }
        return stringBuilder.toString();
    }

    private  String getYekanAndDahgan(char yekan,char dahgan)
    {
        String finalStr="";


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(yekan).append(dahgan);

            switch (stringBuilder.toString())
            {
                case "10":finalStr="ده";
                    break;

                case "11":finalStr="یازده";
                    break;

                case "12":finalStr="دوازده";
                    break;

                case "13":finalStr="سیزده";
                    break;

                case "14":finalStr="چهارده";
                    break;

                case "15":finalStr="پانزده";
                    break;

                case "16":finalStr="شانزده";
                    break;

                case "17":finalStr="هفده";
                    break;

                case "18":finalStr="هجده";
                    break;

                case "19":finalStr="نوزده";
                    break;


            }

        return finalStr;
    }
    private  String getYekan(char chara)
    {
        String finalChar="";
        switch (chara)
        {
            case '1': finalChar=" یک ";
                break;
            case '2': finalChar=" دو ";
                break;
            case '3': finalChar=" سه ";
                break;
            case '4': finalChar=" چهار ";
                break;
            case '5': finalChar=" پنج ";
                break;
            case '6': finalChar=" شش ";
                break;
            case '7': finalChar=" هفت ";
                break;
            case '8': finalChar=" هشت ";
                break;
            case '9': finalChar=" نه ";
                break;
            case '0': finalChar="";
                break;
        }
        return finalChar;
    }
    private  String getdahgan(char dahganChar,char yekanChar)
    {
        String finalChar="";
        switch (dahganChar)
        {
            case '2': finalChar=" بیست ";
                break;
            case '3': finalChar=" سی ";
                break;
            case '4': finalChar=" چهل ";
                break;
            case '5': finalChar=" پنجاه ";
                break;
            case '6': finalChar=" شصت ";
                break;
            case '7': finalChar=" هفتاد ";
                break;
            case '8': finalChar=" هشتاد ";
                break;
            case '9': finalChar=" نود ";
                break;
            case '0': finalChar="";
                break;
        }
        if (Integer.parseInt(String.valueOf(yekanChar))!=0 && !finalChar.equals(""))
            finalChar+=" و ";
        return finalChar;
    }
    private  String getSadgan(char Sadgan,char dahgan , char yekan)
    {
        String finalChar ="";
        switch (Sadgan)
        {
            case '1': finalChar=" صد ";
                break;

            case '2': finalChar=" دویست ";
                break;

            case '3': finalChar=" سیصد ";
                break;
            case '4': finalChar=" چهارصد ";
                break;
            case '5': finalChar=" پانصد ";
                break;
            case '6': finalChar=" ششصد ";
                break;
            case '7': finalChar=" هفتصد ";
                break;
            case '8': finalChar=" هشتصد ";
                break;
            case '9': finalChar=" نهصد ";
                break;
            case '0': finalChar="";
                break;
        }

        if((Integer.parseInt(String.valueOf(dahgan))!=0 || Integer.parseInt(String.valueOf(yekan))!=0) && !finalChar.equals(""))
            finalChar+=" و ";
        return finalChar;
    }


}
