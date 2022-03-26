package com.example.library.rules;

import com.example.library.Kitap;
import com.example.library.OduncAlmaKurali;
import com.example.library.domain.OduncKitapAlmaKarari;
import com.example.library.domain.Ogrenci;
import com.example.library.domain.User;

public class OgrenciEnFazla3KitapKurali implements OduncAlmaKurali {

	@Override
	public OduncKitapAlmaKarari calistir(Kitap kitap, User user) {
		if (!(user instanceof Ogrenci))
			throw new IllegalStateException("This rule is for students only");
		if (user.getOduncKitapSayisi() == 3)
			return new OduncKitapAlmaKarari(false, "Already has 3 books allocated", null);
		return new OduncKitapAlmaKarari(true, "Kitap sayisi kurali basarili", null);
	}

}
