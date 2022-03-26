package com.example.library.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.library.Kitap;
import com.example.library.OduncAlmaKurali;

public abstract class User {
	protected List<OduncAlmaKurali> oduncAlmaKurallari;
	protected List<Kitap> kitaplar;

	public User(List<OduncAlmaKurali> oduncAlmaKurallari) {
		this.oduncAlmaKurallari = oduncAlmaKurallari;
	}

	public int getOduncKitapSayisi() {
		return kitaplar.size();
	}

	public List<OduncKitapAlmaKarari> oduncKitapAl(Kitap kitap) {
		var kararlar = new ArrayList<OduncKitapAlmaKarari>();
		for (var businessRule : oduncAlmaKurallari) {
			var sonuc = businessRule.calistir(kitap, this);
			kararlar.add(sonuc);
		}
		return kararlar;
	}
}
