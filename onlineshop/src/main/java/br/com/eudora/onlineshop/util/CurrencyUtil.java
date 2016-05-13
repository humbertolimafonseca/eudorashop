package br.com.eudora.onlineshop.util;

import java.text.NumberFormat;
import java.util.Locale;

import org.javamoney.moneta.Money;

public class CurrencyUtil {

	public static String format(Money m){
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		return format.format( m.getNumber());
	}
}
