package com.rezakeshavarz.persianpricetranslator.library_exceptions;

public class PriceOutOfRangeException extends RuntimeException
{
    public PriceOutOfRangeException(String errorMessage)
    {
        super(errorMessage);
    }
}