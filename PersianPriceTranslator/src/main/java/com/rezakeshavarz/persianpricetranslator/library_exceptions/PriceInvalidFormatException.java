package com.rezakeshavarz.persianpricetranslator.library_exceptions;

public class PriceInvalidFormatException extends RuntimeException
{
    public PriceInvalidFormatException(String errorMessage)
    {
        super(errorMessage);
    }
}
