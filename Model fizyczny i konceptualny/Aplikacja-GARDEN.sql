BEGIN TRANSACTION;
DROP TABLE IF EXISTS "uzytkownicy";
CREATE TABLE IF NOT EXISTS "uzytkownicy" (
	"id_u"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"nazwa"	varchar(30) NOT NULL,
	"login"	varchar(30) NOT NULL UNIQUE,
	"haslo"	char(32) NOT NULL
);
DROP TABLE IF EXISTS "egzemplarze";
CREATE TABLE IF NOT EXISTS "egzemplarze" (
	"id_e"	integer PRIMARY KEY AUTOINCREMENT UNIQUE,
	"id_ogrodka"	integer NOT NULL,
	"id_rosliny"	integer NOT NULL,
	"miejsce"	varchar(100) NOT NULL,
	"status"	TEXT NOT NULL DEFAULT 'NZ' CHECK(`status` IN ('NZ','Z','DZ','ZE')),
	"data_zmiany_statusu"	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY("id_ogrodka") REFERENCES "ogrodki"("id_og") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "miejsce_w_ogrodku" UNIQUE("id_ogrodka","miejsce"),
	CONSTRAINT "egzemplarze_ibfk_1" FOREIGN KEY("id_rosliny") REFERENCES "rosliny"("id_r") ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "powiadomienia";
CREATE TABLE IF NOT EXISTS "powiadomienia" (
	"id_p"	intEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
	"id_egzemplarza"	int DEFAULT NULL,
	"id_ogrodka"	int DEFAULT NULL,
	"tresc"	varchar(400) NOT NULL,
	"data_powiadomienia"	datetime NOT NULL,
	CONSTRAINT "powiadomienia_ibfk_1" FOREIGN KEY("id_ogrodka") REFERENCES "ogrodki"("id_og") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "powiadomienia_ibfk_2" FOREIGN KEY("id_egzemplarza") REFERENCES "egzemplarze"("id_e") ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "obserwacje";
CREATE TABLE IF NOT EXISTS "obserwacje" (
	"id_o"	integer PRIMARY KEY AUTOINCREMENT UNIQUE,
	"id_uzytkownika"	int NOT NULL,
	"id_egzemplarza"	int NOT NULL,
	"tresc"	varchar(1000) NOT NULL,
	"data_obserwacji"	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT "obserwacje_ibfk_2" FOREIGN KEY("id_uzytkownika") REFERENCES "uzytkownicy"("id_u") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "obserwacje_ibfk_1" FOREIGN KEY("id_egzemplarza") REFERENCES "egzemplarze"("id_e") ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "ogrodki";
CREATE TABLE IF NOT EXISTS "ogrodki" (
	"id_og"	integer PRIMARY KEY AUTOINCREMENT UNIQUE,
	"id_uzytkownika"	integer NOT NULL,
	"nazwa"	varchar(100) NOT NULL,
	"adres"	varchar(100) NOT NULL,
	CONSTRAINT "ogrodki_ibfk_1" FOREIGN KEY("id_uzytkownika") REFERENCES "uzytkownicy"("id_u") ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "zasady";
CREATE TABLE IF NOT EXISTS "zasady" (
	"id_z"	integer PRIMARY KEY AUTOINCREMENT UNIQUE,
	"id_rosliny"	int NOT NULL,
	"tresc"	varchar(1000) NOT NULL,
	CONSTRAINT "zasady_ibfk_1" FOREIGN KEY("id_rosliny") REFERENCES "rosliny"("id_r") ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS "rosliny";
CREATE TABLE IF NOT EXISTS "rosliny" (
	"id_r"	integer PRIMARY KEY AUTOINCREMENT UNIQUE,
	"nazwa"	varchar(100) NOT NULL,
	"odmiana"	tinytext NOT NULL,
	"okres_siewu_poczatek"	datetime NOT NULL,
	"okres_siewu_koniec"	datetime NOT NULL,
	"okres_zbioru_poczatek"	datetime NOT NULL,
	"okres_zbioru_koniec"	datetime NOT NULL,
	"czestotliwosc_podlewania"	int NOT NULL
);
INSERT INTO "uzytkownicy" ("id_u","nazwa","login","haslo") VALUES (1,'Magdalena','Madzia','AA1420F182E88B9E5F874F6FBE7459291E8F4601'),
 (2,'Filip','FFFFX','6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9

'),
 (3,'Noname','tester','8DCDD69CE7D121DE8013062AEAEB2A148910D50E
'),
 (5,'Noname','tester2','6D45FD76D5E9C6A404E39C25106A7F032659ACB8'),
 (6,'Tester3','LoginTestera','529443E73B0ACE7DF5E7AA47FB366E701F0754FD');
INSERT INTO "egzemplarze" ("id_e","id_ogrodka","id_rosliny","miejsce","status","data_zmiany_statusu") VALUES (1,1,1,'A1','NZ','2020-05-03 12:10:10'),
 (2,1,2,'A2','NZ','2020-05-03 12:10:10'),
 (3,2,1,'22','NZ','2020-05-03 12:10:10'),
 (4,2,2,'NZ','NZ','2020-05-03 12:10:10'),
 (8,2,2,'A3','NZ','2020-05-03 12:10:10'),
 (10,1,1,'A3','NZ','2020-05-03 19:11:47'),
 (15,1,1,'A7','NZ','2020-05-03 21:10:33'),
 (17,1,3,'A8','NZ','2020-05-04 13:52:57'),
 (18,1,1,'a10','NZ','2020-05-05 12:10:58'),
 (19,1,1,'A32','NZ','2020-05-08 17:09:30');
INSERT INTO "powiadomienia" ("id_p","id_egzemplarza","id_ogrodka","tresc","data_powiadomienia") VALUES (2,NULL,1,' Przekop ogrÃ³dek','2020-03-01 12:00:00'),
 (54,1,1,'Nie zapomnij mnie zasadziÄ‡!','2020-04-15 00:00:00'),
 (55,1,1,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-15 00:00:00'),
 (56,2,1,'Nie zapomnij mnie zasadziÄ‡!','2020-05-30 00:00:00'),
 (57,2,1,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-01 00:00:00'),
 (58,3,2,'Nie zapomnij mnie zasadziÄ‡!','2020-04-15 00:00:00'),
 (59,3,2,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-15 00:00:00'),
 (64,4,2,'Nie zapomnij mnie zasadziÄ‡!','2020-05-30 00:00:00'),
 (65,4,2,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-01 00:00:00'),
 (70,8,2,'Nie zapomnij mnie zasadziÄ‡!','2020-05-30 00:00:00'),
 (71,8,2,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-01 00:00:00'),
 (72,10,1,'Nie zapomnij mnie zasadziÄ‡!','2020-04-15 00:00:00'),
 (73,10,1,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-15 00:00:00'),
 (74,15,1,'Nie zapomnij mnie zasadziÄ‡!','2020-04-15 00:00:00'),
 (75,15,1,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-15 00:00:00'),
 (76,17,1,'Nie zapomnij mnie zasadziÄ‡!','2020-04-15 00:00:00'),
 (77,17,1,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-15 00:00:00'),
 (78,18,1,'Nie zapomnij mnie zasadziÄ‡!','2020-04-15 00:00:00'),
 (79,18,1,'MoÅ¼esz mnie zasadziÄ‡!','2020-03-15 00:00:00'),
 (80,19,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),
 (81,NULL,13,'Przekop ogródek!','2021-03-01 00:00:00'),
 (82,NULL,14,'Przekop ogródek!','2021-04-01 00:00:00');
INSERT INTO "obserwacje" ("id_o","id_uzytkownika","id_egzemplarza","tresc","data_obserwacji") VALUES (1,1,1,'Marchew roÅ›nie ksiÄ…Å¼kowo','2020-05-03 12:05:48'),
 (2,1,1,'Faza wzrostu: poczÄ…tkowa ','2020-05-03 12:24:31'),
 (9,2,3,'faza wzrostu: poczÄ…tkowa','2020-05-03 16:45:18'),
 (12,1,10,'Faza wzrostu: poczÄ…tkowa ','2020-05-05 13:26:27'),
 (19,1,17,'Faza wzrostu: poczÄ…tkowa ','2020-05-05 13:27:46'),
 (20,1,18,'Faza wzrostu: poczÄ…tkowa ','2020-05-05 13:27:51');
INSERT INTO "ogrodki" ("id_og","id_uzytkownika","nazwa","adres") VALUES (1,1,'OgrÃ³dek warzywny','ul. Kolejowa 22 BorkÃ³w'),
 (2,2,'OgrÃ³dek1','ul. Majowa 13 Malczyce'),
 (3,1,'OgrÃ³dek owocowy','ul.Kolejowa 22 BorkÃ³w'),
 (5,5,'OgrÃ³dek A4','ul. Brzoskwiniowa 6 WrocÅ‚aw'),
 (8,1,'Ogrodek mamy','ul. WrocÅ‚awska 2 WrocÅ‚aw'),
 (9,1,'Ogródek warzywny - nowy','ul. Kolejowa 22 Borków'),
 (10,1,'Ogródek warzywny - nowy NOWY nowy','ul. Kolejowa 22 Borków'),
 (11,1,'Ogródek warzywny - nowy NOWY nowy Nowy','ul. Kolejowa 22 Borków'),
 (12,1,'Ogródek warzywny - nowy NOWY nowy Nowy nowyyy','ul. Kolejowa 22 Borków'),
 (13,1,'Ogródek warzywny - nowy NOWY nowy Nowy nowyyy nowyyyyy','ul. Kolejowa 22 Borków'),
 (14,1,'Ogródek warzywny - nowy NOWY nowy Nowy nowyyy nowyyyyy 2','ul. Kolejowa 22 Borków');
INSERT INTO "zasady" ("id_z","id_rosliny","tresc") VALUES (1,5,'Stanowisko: sÅ‚oneczneâ€šcieniste, z podÅ‚oÅ¼em przepuszczalnym, prÃ³chnicznym i nieprzesychajÄ…cym'),
 (2,5,'gatunek nie jest bowiem odporny na spadki temperatury poniÅ¼ej 0Â°C'),
 (3,3,'Stanowisko: Burak lubi rosnÄ…Ä‡ w miejscu z dostÄ™pem do Å›wiatÅ‚a, chociaÅ¼ stanowisko pod uprawÄ™ tych warzyw nie powinno znajdowaÄ‡ siÄ™ w peÅ‚nym sÅ‚oÅ„cu. Jest to roÅ›lina, ktÃ³ra najlepiej czuje siÄ™ na glebie wilgotnej i lekkiej. Optymalny dla jego rozwoju jest obojÄ™tny odczyn pH podÅ‚oÅ¼a. Do uprawy buraka Opolskiego w miarÄ™ moÅ¼liwoÅ›ci wybieramy mniej kamieniste miejsca.'),
 (4,3,'Rozstawa: Nasiona buraka Opolskiego wysiewamy w rzÄ™dach oddalonych o 30-40 centymetrÃ³w. Po wzejÅ›ciu roÅ›lin robimy przerywkÄ™ pozostawiajÄ…c roÅ›liny co okoÅ‚o 5 centymetrÃ³w.'),
 (5,2,'Warunki uprawy: pietruszkÄ™ naciowÄ… naleÅ¼y uprawiaÄ‡ na glebach lekkich, obojÄ™tnych i bogatych w prÃ³chnicÄ™. NaleÅ¼y unikaÄ‡ gleb podmokÅ‚ych, kamienistych i zaskorupiajÄ…cych siÄ™.');
INSERT INTO "rosliny" ("id_r","nazwa","odmiana","okres_siewu_poczatek","okres_siewu_koniec","okres_zbioru_poczatek","okres_zbioru_koniec","czestotliwosc_podlewania") VALUES (1,'Marchew','wczesna','2020-03-15 00:00:00','2020-04-15 00:00:00','2020-06-01 00:00:00','2020-07-30 00:00:00',3),
 (2,'Pietruszka','naciowa Mooskrause 2 - kÄ™dzieÅ¼awa','2020-03-01 00:00:00','2020-05-30 00:00:00','2020-05-30 00:00:00','2020-12-31 00:00:00',7),
 (3,'Burak','Cukrowy podÅ‚uÅ¼ny Opolski','2020-03-15 00:00:00','2020-04-15 00:00:00','2020-07-15 00:00:00','2020-09-30 00:00:00',14),
 (5,'Koper','ogrodowy Sprinter','2020-03-01 00:00:00','2020-04-30 00:00:00','2020-05-01 00:00:00','2020-10-30 00:00:00',1),
 (6,'Dynia','olbrzymia Uchiki Kuri','2020-05-01 00:00:00','2020-05-30 00:00:00','2020-08-01 00:00:00','2020-09-30 00:00:00',2);
DROP TRIGGER IF EXISTS "dodaj_powiadomienie_nowy_ogrodek_kolejny_rok";
CREATE TRIGGER `dodaj_powiadomienie_nowy_ogrodek_kolejny_rok` AFTER INSERT ON ogrodki FOR EACH ROW
 WHEN (datetime('now') not between datetime('now', 'start of year') and datetime('now', 'start of year','+3 months'))
 BEGIN INSERT INTO powiadomienia(id_ogrodka,tresc,data_powiadomienia) VALUES 
(NEW.id_og,'Przekop ogródek!',(SELECT datetime ('now','start of year','+1 year','start of month','+3 month')));
 END;
DROP TRIGGER IF EXISTS "dodaj_powiadomienie_nowy_ogrodek";
CREATE TRIGGER `dodaj_powiadomienie_nowy_ogrodek` AFTER INSERT ON ogrodki FOR EACH ROW
 WHEN (datetime('now') between datetime('now', 'start of year') and datetime('now', 'start of year','+3 months'))
 BEGIN INSERT INTO powiadomienia(id_ogrodka,tresc,data_powiadomienia) VALUES 
(NEW.id_og,'Przekop ogródek!',(SELECT datetime ('now','start of year','end of month','+2 month')));
 END;
DROP TRIGGER IF EXISTS "dodaj_powiadomienie_status_z";
CREATE TRIGGER dodaj_powiadomienie_status_z AFTER UPDATE ON egzemplarze FOR EACH ROW
 WHEN NEW.status = 'Z'
 BEGIN
INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES 
(NEW.id_e,NEW.id_ogrodka,'Gotowe do zebrania!',(SELECT okres_zbioru_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)); 
 END;
DROP TRIGGER IF EXISTS "dodaj_powiadomienie_status_nz";
CREATE TRIGGER dodaj_powiadomienie_status_nz AFTER UPDATE ON egzemplarze FOR EACH ROW
 WHEN NEW.status = 'NZ'
 BEGIN
 INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES
 (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zasadzić!',(SELECT okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)),
 (NEW.id_e, NEW.id_ogrodka,'Możesz mnie zasadzić!',(SELECT okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)); 
 END;
DROP TRIGGER IF EXISTS "zmien_powiadomienie_status_ze";
CREATE TRIGGER zmien_powiadomienie_status_ze AFTER UPDATE ON egzemplarze FOR EACH ROW
 WHEN NEW.status = 'ZE'
 BEGIN
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';
 END;
DROP TRIGGER IF EXISTS "zmien_powiadomienie_status_dz";
CREATE TRIGGER zmien_powiadomienie_status_dz AFTER UPDATE ON egzemplarze FOR EACH ROW
 WHEN NEW.status = 'DZ'
 BEGIN
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';
INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES
 (NEW.id_e,NEW.id_ogrodka,'Nie zapomnij mnie zebrać!',(SELECT okres_zbioru_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));
 END;
DROP TRIGGER IF EXISTS "zmien_powiadomienie_status_z";
CREATE TRIGGER zmien_powiadomienie_status_z AFTER UPDATE ON egzemplarze FOR EACH ROW
 WHEN NEW.status = 'Z'
 BEGIN
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';
INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES 
(NEW.id_e,NEW.id_ogrodka,'Gotowe do zebrania!',(SELECT okres_zbioru_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));
 END;
DROP TRIGGER IF EXISTS "zmien_powiadomienie_status_nz";
CREATE TRIGGER zmien_powiadomienie_status_nz AFTER UPDATE ON egzemplarze FOR EACH ROW
 WHEN NEW.status = 'NZ'
 BEGIN
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';
 DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';
 INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES
 (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zasadzić!',(SELECT okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));
INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES
 (NEW.id_e, NEW.id_ogrodka,'Możesz mnie zasadzić!',(SELECT okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));  
 END;
COMMIT;
