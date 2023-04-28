package com.rezakeshavarz.persianprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.rezakeshavarz.persianpricetranslator.PersianPriceTranslator

class MainActivity : AppCompatActivity() {
    private lateinit var txtPrice:AppCompatEditText
    private lateinit var lblTranslatedPrice:AppCompatTextView
    private lateinit var lblTranslatedPriceWithNumber:AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val persianPriceTranslator = PersianPriceTranslator()
            .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_LETTER_ONLY)
            .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.RIAL)

        val persianPriceTranslatorWithNum = PersianPriceTranslator()
            .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_NUMBER_AND_LETTER)
            .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.RIAL)

        txtPrice    = findViewById(R.id.txtPrice);
        lblTranslatedPrice    = findViewById(R.id.lblTranslatedPrice);
        lblTranslatedPriceWithNumber    = findViewById(R.id.lblTranslatedPriceWithNumber);
        txtPrice.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
                lblTranslatedPrice.text = persianPriceTranslator.convertNumeralPriceToLetters(p0.toString())
                lblTranslatedPriceWithNumber.text = persianPriceTranslatorWithNum.convertNumeralPriceToLetters(p0.toString())
/*
                try
                {
                    lblTranslatedPrice.text = persianPriceTranslator.convertNumeralPriceToLetters(p0.toString())
                    lblTranslatedPriceWithNumber.text = persianPriceTranslatorWithNum.convertNumeralPriceToLetters(p0.toString())
                }catch (e:Exception)
                {
                    Log.e("MainActivity"," error message is : ${e.message}  ,, ${e.cause?.message}")
                }
*/

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        )
    }
}