[![](https://jitpack.io/v/Rezakeshavarz1376/PersianPriceTranslator.svg)](https://jitpack.io/#Rezakeshavarz1376/PersianPriceTranslator)
# PersianPriceTranslator
This Java library converts Persian numeric Price to Persian letter Price in two formats
## Installation
This library can be utilized in any project that utilizes Java or Kotlin programming languages and supports both Maven and Gradle.
### Maven 
#### step1 :
  Add the jetpack repository to you'r project (pom.xml)
  
  	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
#### step2 : 
Add the dependency

	<dependency>
	    <groupId>com.github.Rezakeshavarz1376</groupId>
	    <artifactId>PersianPriceTranslator</artifactId>
	    <version>latest-version</version>
	</dependency>
  
### gradle
#### step1 :
Add it in your root build.gradle at the end of repositories

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

#### step2 :
Add the dependency

	dependencies {
	        implementation 'com.github.Rezakeshavarz1376:PersianPriceTranslator:latest-version'
	}
  
  
## Usage

To convert Persian numeric price to Persian letter price (translate PersianPrice to PersianPrice in Letter) , perform the following steps:
1.	Instantiate a new object of the PersianPriceTranslator class.
2.	Use the SetCurrencyUnit method to specify the currency unit.
3.	Use the setLetterPriceFormat method to set the output letter price format.
4.	If you do not set the currency unit and letter price format, the converter will use the default values of RIAL and FORMAT_LETTER_ONLY, in that order.

### Java Code
```java
// instantiate object of class
PersianPriceTranslator persianPriceTranslator = new PersianPriceTranslator()
              .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.TOOMAN)
              .setLetterCurrencyFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_NUMBER_AND_LETTER);
// call method for converting price
String priceInLetter = persianPriceTranslator.convertNumeralPriceToLetters("12345968752");
```
### Kotlin Code
```kotlin
// instantiate object of class
 val persianPriceTranslator = PersianPriceTranslator()
              .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_LETTER_ONLY)
              .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.RIAL)
// call method for converting price
 var priceInLetter = persianPriceTranslator.convertNumeralPriceToLetters(p0.toString())     

```

### Currency Unit Table

| Currency Unit | Description | input | output |
| :---: | :---: | :---: | :---: |
| CurrencyUnit.RIAL | you can choose this enum when you'r price parameter is Rial | 3242000 | سیصد و بیست و چهار هزار و دویست تومان |
| CurrencyUnit.TOOMAN | you can choose this enum when you'r price parameter is Tooman | 324200 | سیصد و بیست و چهار هزار و دویست تومان |  

### LetterPriceFormat Table

| LetterPriceFormat | Description | Input | output (persian) |
| :---: | :---: | :---: | :---: |
| FORMAT_LETTER_ONLY | you can use it when you want that output show you with Letter only | 324200 | سیصد و بیست و چهار هزار و دویست تومان |
| FORMAT_NUMBER_AND_LETTER | you can use it when you want output show you number of price with number and other with letter | 324200 | <div dir="rtl"> 324 هزار و 200 تومان </div> |

## Notes
 1. This library supports prices ranging from 1 tooman to 999,999,999,999,999 tooman. 
 2. The priceParameter in convertNumeralPriceToLetters function should only contain numbers.

## Example

### case RIAL and FORMAT_LETTER_ONLY
```java

PersianPriceTranslator persianPriceTranslator =new PersianPriceTranslator()
              .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.RIAL)
              .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_LETTER_ONLY);

String priceLetter = persianPriceTranslator.convertNumeralPriceToLetters("324200"); 
```
#### output
سی و دو هزار و چهارصد و بیست تومان


### case TOOMAN and FORMAT_LETTER_ONLY
```java

PersianPriceTranslator persianPriceTranslator =new PersianPriceTranslator()
              .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.TOOMAN)
              .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_LETTER_ONLY);

String priceLetter = persianPriceTranslator.convertNumeralPriceToLetters("324200"); 
```
#### output
سیصد و بیست و چهار هزار و دویست تومان

### case RIAL and FORMAT_NUMBER_AND_LETTER
```java

PersianPriceTranslator persianPriceTranslator =new PersianPriceTranslator()
              .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.RIAL)
              .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_NUMBER_AND_LETTER);

String priceLetter =  persianPriceTranslator.convertNumeralPriceToLetters("324200");
```
#### output
32 هزار و 420 تومان 



### case TOOMAN and FORMAT_NUMBER_AND_LETTER
```java

PersianPriceTranslator persianPriceTranslator =new PersianPriceTranslator()
              .setCurrencyUnit(PersianPriceTranslator.CurrencyUnit.TOOMAN)
              .setLetterPriceFormat(PersianPriceTranslator.LetterPriceFormat.FORMAT_NUMBER_AND_LETTER);

String priceLetter = persianPriceTranslator.convertNumeralPriceToLetters("324200");
```
#### output
324 هزار و 200 تومان

## Exceptions
There are two exceptions in this library
### PriceOutOfRangeException
This exception is triggered when the input price parameter exceeds the range of values supported by the library.

### PriceInvalidFormatException
This exception is triggered when the input price parameter contains invalid characters such as letters, spaces, or other non-numeric symbols, or when the input price parameter begins with a '0'
