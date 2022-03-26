package com.example.library.rules;

import com.example.library.Kitap;
import com.example.library.OduncAlmaKurali;
import com.example.library.domain.OduncKitapAlmaKarari;
import com.example.library.domain.OgretimUyesi;
import com.example.library.domain.User;

public class OgretimUyesiOduncAlmaSuresiKurali implements OduncAlmaKurali {

	@Override
	public OduncKitapAlmaKarari calistir(Kitap kitap, User user) {
		if (!(user instanceof OgretimUyesi))
			throw new IllegalStateException("This rule is for ogretim uyesi only");
		return new OduncKitapAlmaKarari(true, "Kitap odunc alma süresi kurali basarili", 12);
	}

}
