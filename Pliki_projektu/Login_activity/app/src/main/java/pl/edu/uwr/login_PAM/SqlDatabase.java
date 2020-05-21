package pl.edu.uwr.login_PAM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// obsługuje bazę danych
public class SqlDatabase  extends SQLiteOpenHelper {

    // user table
    public static final String createDatabase = "DROP TABLE IF EXISTS rosliny;\n" +
            "CREATE TABLE IF NOT EXISTS rosliny (\n" +
            "\t\"id_r\"\tinteger PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"nazwa\"\tvarchar(100) NOT NULL,\n" +
            "\t\"odmiana\"\ttinytext NOT NULL,\n" +
            "\t\"okres_siewu_poczatek\"\tdatetime NOT NULL,\n" +
            "\t\"okres_siewu_koniec\"\tdatetime NOT NULL,\n" +
            "\t\"okres_zbioru_poczatek\"\tdatetime NOT NULL,\n" +
            "\t\"okres_zbioru_koniec\"\tdatetime NOT NULL,\n" +
            "\t\"czestotliwosc_podlewania\"\tint NOT NULL\n" +
            ");\n" +
            "DROP TABLE IF EXISTS \"zasady\";\n" +
            "CREATE TABLE IF NOT EXISTS \"zasady\" (\n" +
            "\t\"id_z\"\tinteger PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"id_rosliny\"\tint NOT NULL,\n" +
            "\t\"tresc\"\tvarchar(1000) NOT NULL,\n" +
            "\tCONSTRAINT \"zasady_ibfk_1\" FOREIGN KEY(\"id_rosliny\") REFERENCES \"rosliny\"(\"id_r\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ");\n" +
            "DROP TABLE IF EXISTS \"ogrodki\";\n" +
            "CREATE TABLE IF NOT EXISTS \"ogrodki\" (\n" +
            "\t\"id_og\"\tinteger PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"id_uzytkownika\"\tinteger NOT NULL,\n" +
            "\t\"nazwa\"\tvarchar(100) NOT NULL,\n" +
            "\t\"adres\"\tvarchar(100) NOT NULL,\n" +
            "\tCONSTRAINT \"ogrodki_ibfk_1\" FOREIGN KEY(\"id_uzytkownika\") REFERENCES \"uzytkownicy\"(\"id_u\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ");\n" +
            "DROP TABLE IF EXISTS \"obserwacje\";\n" +
            "CREATE TABLE IF NOT EXISTS \"obserwacje\" (\n" +
            "\t\"id_o\"\tinteger PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"id_egzemplarza\"\tint NOT NULL,\n" +
            "\t\"tresc\"\tvarchar(1000) NOT NULL,\n" +
            "\t\"data_obserwacji\"\tdatetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "\tCONSTRAINT \"obserwacje_ibfk_1\" FOREIGN KEY(\"id_egzemplarza\") REFERENCES \"egzemplarze\"(\"id_e\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ");\n" +
            "DROP TABLE IF EXISTS \"powiadomienia\";\n" +
            "CREATE TABLE IF NOT EXISTS \"powiadomienia\" (\n" +
            "\t\"id_p\"\tintEGER PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"id_egzemplarza\"\tint DEFAULT NULL,\n" +
            "\t\"id_ogrodka\"\tint DEFAULT NULL,\n" +
            "\t\"tresc\"\tvarchar(400) NOT NULL,\n" +
            "\t\"data_powiadomienia\"\tdatetime NOT NULL,\n" +
            "\tCONSTRAINT \"powiadomienia_ibfk_1\" FOREIGN KEY(\"id_ogrodka\") REFERENCES \"ogrodki\"(\"id_og\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
            "\tCONSTRAINT \"powiadomienia_ibfk_2\" FOREIGN KEY(\"id_egzemplarza\") REFERENCES \"egzemplarze\"(\"id_e\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ");\n" +
            "DROP TABLE IF EXISTS \"egzemplarze\";\n" +
            "CREATE TABLE IF NOT EXISTS \"egzemplarze\" (\n" +
            "\t\"id_e\"\tinteger PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"id_ogrodka\"\tinteger NOT NULL,\n" +
            "\t\"id_rosliny\"\tinteger NOT NULL,\n" +
            "\t\"miejsce\"\tvarchar(100) NOT NULL,\n" +
            "\t\"status\"\tTEXT NOT NULL DEFAULT 'NZ' CHECK(`status` IN ('NZ','Z','DZ','ZE')),\n" +
            "\t\"data_zmiany_statusu\"\tdatetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "\tFOREIGN KEY(\"id_ogrodka\") REFERENCES \"ogrodki\"(\"id_og\") ON DELETE CASCADE ON UPDATE CASCADE,\n" +
            "\tCONSTRAINT \"miejsce_w_ogrodku\" UNIQUE(\"id_ogrodka\",\"miejsce\"),\n" +
            "\tCONSTRAINT \"egzemplarze_ibfk_1\" FOREIGN KEY(\"id_rosliny\") REFERENCES \"rosliny\"(\"id_r\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ");\n" +
            "DROP TABLE IF EXISTS \"uzytkownicy\";\n" +
            "CREATE TABLE IF NOT EXISTS \"uzytkownicy\" (\n" +
            "\t\"id_u\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"nazwa\"\tvarchar(30) NOT NULL,\n" +
            "\t\"login\"\tvarchar(30) NOT NULL UNIQUE,\n" +
            "\t\"haslo\"\tchar(32) NOT NULL\n" +
            ");\n" +
            "INSERT INTO \"rosliny\" (\"id_r\",\"nazwa\",\"odmiana\",\"okres_siewu_poczatek\",\"okres_siewu_koniec\",\"okres_zbioru_poczatek\",\"okres_zbioru_koniec\",\"czestotliwosc_podlewania\") VALUES (1,'Marchew','wczesna','2020-03-15 00:00:00','2020-04-15 00:00:00','2020-06-01 00:00:00','2020-07-30 00:00:00',3),\n" +
            " (2,'Pietruszka','naciowa Mooskrause 2 - kędzieżawa','2020-03-01 00:00:00','2020-05-30 00:00:00','2020-05-30 00:00:00','2020-12-31 00:00:00',7),\n" +
            " (3,'Burak','Cukrowy podłużny Opolski','2020-03-15 00:00:00','2020-04-15 00:00:00','2020-07-15 00:00:00','2020-09-30 00:00:00',14),\n" +
            " (5,'Koper','ogrodowy Sprinter','2020-03-01 00:00:00','2020-04-30 00:00:00','2020-05-01 00:00:00','2020-10-30 00:00:00',1),\n" +
            " (6,'Dynia','olbrzymia Uchiki Kuri','2020-05-01 00:00:00','2020-05-30 00:00:00','2020-08-01 00:00:00','2020-09-30 00:00:00',2);\n" +
            "INSERT INTO \"zasady\" (\"id_z\",\"id_rosliny\",\"tresc\") VALUES (1,5,'Stanowisko: słoneczne, z podłożem przepuszczalnym, próchnicznym i nieprzesychającym'),\n" +
            " (2,5,'gatunek nie jest bowiem odporny na spadki temperatury poniżej 0°C'),\n" +
            " (3,3,'Stanowisko: Burak lubi rosnący w miejscu z dostępem do światła, chociaż stanowisko pod uprawę tych warzyw nie powinno znajdować się w pełnym słońcu. Jest to roślina, która najlepiej czuje się na glebie wilgotnej i lekkiej. Optymalny dla jego rozwoju jest obojętny odczyn pH podłoża. Do uprawy buraka Opolskiego w miarę możliwości wybieramy mniej kamieniste miejsca.'),\n" +
            " (4,3,'Rozstawa: Nasiona buraka Opolskiego wysiewamy w rzędach oddalonych o 30-40 centymetrów.'),\n" +
            " (5,2,'Warunki uprawy: pietruszkę naciową należy uprawiać na glebach lekkich, obojętnych i bogatych w próchnicę. ');\n" +
            "INSERT INTO \"ogrodki\" (\"id_og\",\"id_uzytkownika\",\"nazwa\",\"adres\") VALUES (0,1,'Ogródek warzywny - nowy','ul. Kolejowa 22 Borków'),\n" +
            " (1,1,'Ogródek warzywny','ul. Kolejowa 22 Borków'),\n" +
            " (2,2,'Ogródek1','ul. Majowa 13 Malczyce'),\n" +
            " (3,1,'Ogródek owocowy','ul.Kolejowa 22 Borków'),\n" +
            " (5,5,'Ogródek A4','ul. Brzoskwiniowa 6 Wrocław'),\n" +
            " (8,1,'Ogrodek mamy','ul. Wrocławska 2 Wrocław\n" +
            "'),\n" +
            " (14,1,'Ogródek warzywny - nowy NOWY nowy Nowy nowyyy nowyyyyy 2','ul. Kolejowa 22 Borków');\n" +
            "INSERT INTO \"obserwacje\" (\"id_o\",\"id_egzemplarza\",\"tresc\",\"data_obserwacji\") VALUES (1,1,'Marchew rośnie książkowo.','2020-05-03 12:05:48'),\n" +
            " (2,1,'Faza wzrostu: początkowa ','2020-05-03 12:24:31'),\n" +
            " (9,3,'faza wzrostu: początkowa','2020-05-03 16:45:18'),\n" +
            " (12,10,'Faza wzrostu: początkowa ','2020-05-05 13:26:27'),\n" +
            " (19,17,'Faza wzrostu: początkowa ','2020-05-05 13:27:46');\n" +
            "INSERT INTO \"powiadomienia\" (\"id_p\",\"id_egzemplarza\",\"id_ogrodka\",\"tresc\",\"data_powiadomienia\") VALUES (2,NULL,1,' Przekop ogródek','2020-03-01 12:00:00'),\n" +
            " (82,NULL,14,'Przekop ogródek!','2021-04-01 00:00:00'),\n" +
            " (90,1,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),\n" +
            " (91,1,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),\n" +
            " (92,2,1,'Nie zapomnij mnie zasadzić!','2020-05-30 00:00:00'),\n" +
            " (93,2,1,'Możesz mnie zasadzić!','2020-03-01 00:00:00'),\n" +
            " (94,3,2,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),\n" +
            " (95,3,2,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),\n" +
            " (96,4,2,'Nie zapomnij mnie zasadzić!','2020-05-30 00:00:00'),\n" +
            " (97,4,2,'Możesz mnie zasadzić!','2020-03-01 00:00:00'),\n" +
            " (98,8,2,'Nie zapomnij mnie zasadzić!','2020-05-30 00:00:00'),\n" +
            " (99,8,2,'Możesz mnie zasadzić!','2020-03-01 00:00:00'),\n" +
            " (100,10,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),\n" +
            " (101,10,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),\n" +
            " (102,15,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),\n" +
            " (103,15,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),\n" +
            " (104,17,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),\n" +
            " (105,17,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00');\n" +
            "INSERT INTO \"egzemplarze\" (\"id_e\",\"id_ogrodka\",\"id_rosliny\",\"miejsce\",\"status\",\"data_zmiany_statusu\") VALUES (1,1,1,'A1','NZ','2020-05-03 12:10:10'),\n" +
            " (2,1,2,'A2','NZ','2020-05-03 12:10:10'),\n" +
            " (3,2,1,'22','NZ','2020-05-03 12:10:10'),\n" +
            " (4,2,2,'NZ','NZ','2020-05-03 12:10:10'),\n" +
            " (8,2,2,'A3','NZ','2020-05-03 12:10:10'),\n" +
            " (10,1,1,'A3','NZ','2020-05-03 19:11:47'),\n" +
            " (15,1,1,'A7','NZ','2020-05-03 21:10:33'),\n" +
            " (17,1,3,'A8','NZ','2020-05-04 13:52:57'),\n" +
            " (22,1,3,'A21','ZE','2020-05-08 20:59:24');\n" +
            "INSERT INTO \"uzytkownicy\" (\"id_u\",\"nazwa\",\"login\",\"haslo\") VALUES (1,'Magdalena','Madzia','AA1420F182E88B9E5F874F6FBE7459291E8F4601'),\n" +
            " (2,'Filip','FFFFX','6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9\n" +
            "'),\n" +
            " (3,'Noname','tester','8DCDD69CE7D121DE8013062AEAEB2A148910D50E\n" +
            "'),\n" +
            " (5,'Noname','tester2','6D45FD76D5E9C6A404E39C25106A7F032659ACB8'),\n" +
            " (6,'Tester3','LoginTestera','529443E73B0ACE7DF5E7AA47FB366E701F0754FD');\n" +
            "DROP TRIGGER IF EXISTS \"zmien_powiadomienie_status_nz\";\n" +
            "CREATE TRIGGER zmien_powiadomienie_status_nz AFTER UPDATE ON egzemplarze FOR EACH ROW\n" +
            " WHEN NEW.status = 'NZ'\n" +
            " BEGIN\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';\n" +
            " INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES\n" +
            " (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zasadzić!',(SELECT okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));\n" +
            "INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES\n" +
            " (NEW.id_e, NEW.id_ogrodka,'Możesz mnie zasadzić!',(SELECT okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));  \n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"zmien_powiadomienie_status_z\";\n" +
            "CREATE TRIGGER zmien_powiadomienie_status_z AFTER UPDATE ON egzemplarze FOR EACH ROW\n" +
            " WHEN NEW.status = 'Z'\n" +
            " BEGIN\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';\n" +
            "INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES \n" +
            "(NEW.id_e,NEW.id_ogrodka,'Gotowe do zebrania!',(SELECT okres_zbioru_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));\n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"zmien_powiadomienie_status_dz\";\n" +
            "CREATE TRIGGER zmien_powiadomienie_status_dz AFTER UPDATE ON egzemplarze FOR EACH ROW\n" +
            " WHEN NEW.status = 'DZ'\n" +
            " BEGIN\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';\n" +
            "INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES\n" +
            " (NEW.id_e,NEW.id_ogrodka,'Nie zapomnij mnie zebrać!',(SELECT okres_zbioru_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny));\n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"zmien_powiadomienie_status_ze\";\n" +
            "CREATE TRIGGER zmien_powiadomienie_status_ze AFTER UPDATE ON egzemplarze FOR EACH ROW\n" +
            " WHEN NEW.status = 'ZE'\n" +
            " BEGIN\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebrać!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzić!';\n" +
            " DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Możesz mnie zasadzić!';\n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"dodaj_powiadomienie_nowy_ogrodek\";\n" +
            "CREATE TRIGGER `dodaj_powiadomienie_nowy_ogrodek` AFTER INSERT ON ogrodki FOR EACH ROW\n" +
            " WHEN (datetime('now') between datetime('now', 'start of year') and datetime('now', 'start of year','+3 months'))\n" +
            " BEGIN INSERT INTO powiadomienia(id_ogrodka,tresc,data_powiadomienia) VALUES \n" +
            "(NEW.id_og,'Przekop ogródek!',(SELECT datetime ('now','start of year','end of month','+2 month')));\n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"dodaj_powiadomienie_nowy_ogrodek_kolejny_rok\";\n" +
            "CREATE TRIGGER `dodaj_powiadomienie_nowy_ogrodek_kolejny_rok` AFTER INSERT ON ogrodki FOR EACH ROW\n" +
            " WHEN (datetime('now') not between datetime('now', 'start of year') and datetime('now', 'start of year','+3 months'))\n" +
            " BEGIN INSERT INTO powiadomienia(id_ogrodka,tresc,data_powiadomienia) VALUES \n" +
            "(NEW.id_og,'Przekop ogródek!',(SELECT datetime ('now','start of year','+1 year','start of month','+3 month')));\n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"dodaj_powiadomienie_status_nz\";\n" +
            "CREATE TRIGGER `dodaj_powiadomienie_status_nz` AFTER INSERT ON egzemplarze FOR EACH ROW\n" +
            " WHEN NEW.status = 'NZ'\n" +
            " BEGIN\n" +
            " INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES\n" +
            " (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zasadzić!',(SELECT okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)),\n" +
            " (NEW.id_e, NEW.id_ogrodka,'Możesz mnie zasadzić!',(SELECT okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)); \n" +
            " END;\n" +
            "DROP TRIGGER IF EXISTS \"dodaj_powiadomienie_status_z\";\n" +
            "CREATE TRIGGER `dodaj_powiadomienie_status_z` AFTER UPDATE ON egzemplarze FOR EACH ROW\n" +
            " WHEN NEW.status = 'Z'\n" +
            " BEGIN\n" +
            "INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES \n" +
            "(NEW.id_e,NEW.id_ogrodka,'Gotowe do zebrania!',(SELECT okres_zbioru_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)); \n" +
            " END;";

    public SqlDatabase(@Nullable Context context) {
        super(context,"Garden.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createDatabase);
        // sqLiteDatabase.execSQL(createSeeds);
        // sqLiteDatabase.execSQL(createSownSeeds);
        // sqLiteDatabase.execSQL(createPrinciples);
        // sqLiteDatabase.execSQL(createReminder);
        // sqLiteDatabase.execSQL(createObservation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String _name, String _password)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login",_name);
        cv.put("haslo",_password);

        long result = gardenDb.insert("uzytkownicy",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public Cursor getAllData(String _table_name)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM " + _table_name;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }
}
