package com.example.library;

import com.example.library.domain.OduncKitapAlmaKarari;
import com.example.library.domain.User;

public interface OduncAlmaKurali {
	OduncKitapAlmaKarari calistir(Kitap kitap,User user);
}
