package com.example.library.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.library.Kitap;
import com.example.library.OduncAlmaKurali;

public class Ogrenci extends User {

	public Ogrenci(List<OduncAlmaKurali> oduncAlmaKurallari) {
		super(oduncAlmaKurallari);
	}

	@Override
	public List<OduncKitapAlmaKarari> oduncKitapAl(Kitap kitap) {
		var kararlar = new ArrayList<OduncKitapAlmaKarari>();
		for (var businessRule : oduncAlmaKurallari) {
			var sonuc = businessRule.calistir(kitap, this);	
			kararlar.add(sonuc);
		}
		return kararlar;
	}

}
