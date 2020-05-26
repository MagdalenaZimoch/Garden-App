package pl.edu.uwr.login_PAM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabasesOpenHelper extends SQLiteOpenHelper {

    private static Context context2;

    public DatabasesOpenHelper(@Nullable Context context) {
        super(context,"Garden.db", null, 17);
        context2 = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //zczytywanie
        String create_rosliny = context2.getString(R.string.sql_create_rosliny);
        String create_uzytkownicy = context2.getString(R.string.sql_create_uzytkownicy);
        String create_zasady = context2.getString(R.string.sql_create_zasady);
        String create_ogrodki = context2.getString(R.string.sql_create_ogrodki);
        String create_egzemplarze = context2.getString(R.string.sql_create_egzemplarze);
        String create_powiadomienia = context2.getString(R.string.sql_create_powiadomienia);
        String create_obserwacje = context2.getString(R.string.sql_create_obserwacje);
        //tworzenie i wpisanie poczatkowych wartosci
        sqLiteDatabase.execSQL(create_rosliny);
        sqLiteDatabase.execSQL(create_uzytkownicy);
        sqLiteDatabase.execSQL(create_zasady);
        sqLiteDatabase.execSQL(create_ogrodki);
        sqLiteDatabase.execSQL(create_egzemplarze);
        sqLiteDatabase.execSQL(create_powiadomienia);
        sqLiteDatabase.execSQL(create_obserwacje);
        //sqLiteDatabase.execSQL(create_triggers);
        //wpisanie poczatkowych wartosci:
        String _rosliny = context2.getString(R.string.sql_dodaj_wartosci_roslin);
        String _uzytkownicy = context2.getString(R.string.sql_dodaj_wartosci_uzytkownicy);
        String _zasady = context2.getString(R.string.sql_dodaj_wartosci_zasady);
        String _ogrodki = context2.getString(R.string.sql_dodaj_wartosci_ogrodki);
        String _egzemplarze = context2.getString(R.string.sql_dodaj_wartosci_egzemplarze);
        String _powiadomienia = context2.getString(R.string.sql_dodaj_wartosci_powiadomienia);
        String _obserwacje = context2.getString(R.string.sql_dodaj_wartosci_obserwacje);
        //String _triggers = context2.getString(R.string.sql);
        //tworzenie i wpisanie poczatkowych wartosci
        sqLiteDatabase.execSQL(_rosliny);
        sqLiteDatabase.execSQL(_uzytkownicy);
        sqLiteDatabase.execSQL(_zasady);
        sqLiteDatabase.execSQL(_ogrodki);
        sqLiteDatabase.execSQL(_egzemplarze);
        sqLiteDatabase.execSQL(_powiadomienia);
        sqLiteDatabase.execSQL(_obserwacje);
        //sqLiteDatabase.execSQL(_triggers);
        String trigger1 = context2.getString(R.string.trigger1);
        sqLiteDatabase.execSQL(trigger1);
        String trigger2 = context2.getString(R.string.trigger2);
        sqLiteDatabase.execSQL(trigger2);
        String trigger3 = context2.getString(R.string.trigger3);
        sqLiteDatabase.execSQL(trigger3);
        String trigger4 = context2.getString(R.string.trigger4);
        sqLiteDatabase.execSQL(trigger4);
        String trigger5 = context2.getString(R.string.trigger5);
        sqLiteDatabase.execSQL(trigger5);
        String trigger6 = context2.getString(R.string.trigger6);
        sqLiteDatabase.execSQL(trigger6);
        String trigger7 = context2.getString(R.string.trigger7);
        sqLiteDatabase.execSQL(trigger7);
        String trigger8 = context2.getString(R.string.trigger8);
        sqLiteDatabase.execSQL(trigger8);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS powiadomienia");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS obserwacje");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS egzemplarze");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS zasady");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ogrodki");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rosliny");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS uzytkownicy");

        onCreate(sqLiteDatabase);
    }

    //insert
    public boolean insert_uzytkownik(String _login, String _haslo,String _nazwa)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nazwa",_nazwa);
        cv.put("haslo",_haslo);
        cv.put("login",_login);

        long result = gardenDb.insert("uzytkownicy",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_roslina(String _nazwa, String _odmiana,String _okres_sp,String _okres_sk,String _okres_zp,String _okres_zk,int _czest_podl)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nazwa",_nazwa);
        cv.put("odmiana",_odmiana);
        cv.put("okres_siewu_poczatek",_okres_sp);
        cv.put("okres_siewu_koniec",_okres_sk);
        cv.put("okres_zbioru_poczatek",_okres_zp);
        cv.put("okres_zbioru_koniec",_okres_zk);
        cv.put("czestotliwosc_podlewania",_czest_podl);


        long result = gardenDb.insert("rosliny",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_zasada(int _id_rosliny, String _tresc)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_rosliny",_id_rosliny);
        cv.put("tresc",_tresc);


        long result = gardenDb.insert("zasady",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_ogrodek(int _id_uzytkownika, String _nazwa,String _adres)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_uzytkownika",_id_uzytkownika);
        cv.put("adres",_adres);
        cv.put("nazwa",_nazwa);

        long result = gardenDb.insert("ogrodki",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_egzemplarz(int _id_ogrodka, int _id_rosliny, String _miejsce, String _status, String _data_zmiany)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_ogrodka",_id_ogrodka);
        cv.put("id_rosliny",_id_rosliny);
        cv.put("miejsce",_miejsce);
        cv.put("status",_status);
        cv.put("data_zmiany_statusu",_data_zmiany);

        long result = gardenDb.insert("egzemplarze",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_obserwacje(int _id_egzemplarza, String _tresc, String _data_obserwacji)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_egzemplarza",_id_egzemplarza);
        cv.put("tresc",_tresc);
        cv.put("data_obserwacji",_data_obserwacji);

        long result = gardenDb.insert("obserwacje",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_powiadomienia(int _id_egzemplarza, int _id_ogrodka, String _tresc, String _data)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_egzemplarza",_id_egzemplarza);
        cv.put("id_ogrodka",_id_ogrodka);
        cv.put("tresc",_tresc);
        cv.put("data_powiadomienia",_data);

        long result = gardenDb.insert("powiadomienia",null,cv);
        if(result == -1) return false;
        else return true;
    }

    //update
    public boolean update_uzytkownik(int _id_u, String _login, String _haslo,String _nazwa)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nazwa",_nazwa);
        cv.put("haslo",_haslo);
        cv.put("login",_login);

        long result = gardenDb.update("uzytkownicy",cv,"where id_u = "+String.valueOf(_id_u),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_roslina(int _id_r, String _nazwa, String _odmiana,String _okres_sp,String _okres_sk,String _okres_zp,String _okres_zk,int _czest_podl)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nazwa",_nazwa);
        cv.put("odmiana",_odmiana);
        cv.put("okres_siewu_poczatek",_okres_sp);
        cv.put("okres_siewu_koniec",_okres_sk);
        cv.put("okres_zbioru_poczatek",_okres_zp);
        cv.put("okres_zbioru_koniec",_okres_zk);
        cv.put("czestotliwosc_podlewania",_czest_podl);


        long result = gardenDb.update("rosliny",cv, "where id_r = "+String.valueOf(_id_r),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_zasada(int _id_z, int _id_rosliny, String _tresc)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_rosliny",_id_rosliny);
        cv.put("tresc",_tresc);

        long result = gardenDb.update("zasady",cv, "where id_z = "+ String.valueOf(_id_z),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_ogrodek(int _id_o, int _id_uzytkownika, String _nazwa,String _adres)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_uzytkownika",_id_uzytkownika);
        cv.put("adres",_adres);
        cv.put("nazwa",_nazwa);

        long result = gardenDb.update("ogrodki",cv, "where id_og = "+String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_egzemplarz(int _id_e, int _id_ogrodka, int _id_rosliny, String _miejsce, String _status, String _data_zmiany)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_ogrodka",_id_ogrodka);
        cv.put("id_rosliny",_id_rosliny);
        cv.put("miejsce",_miejsce);
        cv.put("status",_status);
        cv.put("data_zmiany_statusu",_data_zmiany);

        long result = gardenDb.update("egzemplarze",cv,"where id_e = "+ String.valueOf(_id_e),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_obserwacje(int _id_o, int _id_egzemplarza, String _tresc, String _data_obserwacji)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_egzemplarza",_id_egzemplarza);
        cv.put("tresc",_tresc);
        cv.put("data_obserwacji",_data_obserwacji);

        long result = gardenDb.update("obserwacje",cv, "where id_o = " + String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_powiadomienia(int _id_p, int _id_egzemplarza, int _id_ogrodka, String _tresc, String _data)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_egzemplarza",_id_egzemplarza);
        cv.put("id_ogrodka",_id_ogrodka);
        cv.put("tresc",_tresc);
        cv.put("data_powiadomienia",_data);

        long result = gardenDb.update("powiadomienia",cv,"where id_p = "+ String.valueOf(_id_p),null);
        if(result == -1) return false;
        else return true;
    }

    //delete
    public boolean delete_uzytkownik(int _id_u)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("uzytkownicy","where id_u = "+String.valueOf(_id_u),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_roslina(int _id_r)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("rosliny", "where id_r = "+String.valueOf(_id_r),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_zasada(int _id_z)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("zasady","where id_z = "+ String.valueOf(_id_z),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_ogrodek(int _id_o)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("ogrodki","where id_og = "+String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_egzemplarz(int _id_e)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("egzemplarze","where id_e = "+ String.valueOf(_id_e),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_obserwacje(int _id_o)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("obserwacje","where id_o = " + String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_powiadomienia(int _id_p)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("powiadomienia","where id_p = "+ String.valueOf(_id_p),null);
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
    public Cursor get_Where_User(int _id_uz, String _table_name)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM "+ _table_name + " WHERE id_uzytkownika = " + _id_uz;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;

    }

    public Cursor get_Where_Garden(int _id_og, String _table_name)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM "+ _table_name + " WHERE id_ogrodka = " + _id_og;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;

    }

    public Cursor get_Where_Garden_egzemplarz(int _id_og, String _table_name)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT e.id_e, e.id_rosliny, e.miejsce, e.status, (SELECT r.nazwa FROM rosliny as r WHERE r.id_r = e.id_rosliny) FROM egzemplarze as e WHERE e.id_ogrodka ="+ _id_og ;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;

    }
}

