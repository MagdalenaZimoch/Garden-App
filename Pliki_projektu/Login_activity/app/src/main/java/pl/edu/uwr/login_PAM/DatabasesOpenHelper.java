package pl.edu.uwr.login_PAM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import androidx.annotation.Nullable;


public class DatabasesOpenHelper extends SQLiteOpenHelper {

    private static Context context2;

    public DatabasesOpenHelper(@Nullable Context context) {
        super(context,"Garden.db", null, 25);
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
        String trigger7 = "CREATE TRIGGER dodaj_powiadomienie_status_nz AFTER INSERT ON egzemplarze FOR EACH ROW WHEN NEW.status = \"NZ\" BEGIN INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES (NEW.id_e, NEW.id_ogrodka,\"Nie zapomnij mnie zasadzić!\",(SELECT rosliny.okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)), (NEW.id_e, NEW.id_ogrodka,\"Możesz mnie zasadzić!\",(SELECT rosliny.okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny)); END";
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

        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS zmien_powiadomienie_status_nz");
        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS zmien_powiadomienie_status_z");
        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS zmien_powiadomienie_status_dz");
        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS zmien_powiadomienie_status_ze");

        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS dodaj_powiadomienie_nowy_ogrodek");
        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS dodaj_powiadomienie_nowy_ogrodek_kolejny_rok");

        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS dodaj_powiadomienie_status_nz");
        sqLiteDatabase.execSQL("DROP TRIGGER IF EXISTS dodaj_powiadomienie_status_z");

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
    public boolean insert_egzemplarz(int _id_ogrodka, int _id_rosliny, String _miejsce, String _status)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_ogrodka",_id_ogrodka);
        cv.put("id_rosliny",_id_rosliny);
        cv.put("miejsce",_miejsce);
        cv.put("status",_status);

        long result = gardenDb.insert("egzemplarze",null,cv);
        if(result == -1) return false;
        else return true;
    }
    public boolean insert_obserwacje(int _id_egzemplarza, String _tresc)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_egzemplarza",_id_egzemplarza);
        cv.put("tresc",_tresc);

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
    public boolean update_uzytkownik(int _id_u, String _haslo)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("haslo",_haslo);

        long result = gardenDb.update("uzytkownicy",cv,"id_u = "+String.valueOf(_id_u),null);
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


        long result = gardenDb.update("rosliny",cv, "id_r = "+String.valueOf(_id_r),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_zasada(int _id_z, String _tresc)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tresc",_tresc);

        long result = gardenDb.update("zasady",cv, "id_z = "+ String.valueOf(_id_z),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_ogrodek(int _id_o, String _nazwa,String _adres)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("adres",_adres);
        cv.put("nazwa",_nazwa);

        long result = gardenDb.update("ogrodki",cv, "id_og = "+String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_egzemplarz(int _id_e, int _id_ogrodka, String _miejsce, String _status)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_ogrodka",_id_ogrodka);
        cv.put("miejsce",_miejsce);
        cv.put("status",_status);

        long result = gardenDb.update("egzemplarze",cv,"id_e = "+ String.valueOf(_id_e),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_obserwacje(int _id_o, int _id_egzemplarza, String _tresc)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_egzemplarza",_id_egzemplarza);
        cv.put("tresc",_tresc);

        long result = gardenDb.update("obserwacje",cv, "id_o = " + String.valueOf(_id_o),null);
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

        long result = gardenDb.update("powiadomienia",cv,"id_p = "+ String.valueOf(_id_p),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean update_powiadomienia_update_roslina(int id_ro, String osp, String osk, String ozp, String ozk) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        Cursor e = gardenDb.rawQuery("select id_e from egzemplarze join rosliny on id_rosliny = id_r where id_r =" +id_ro,null);
        while (e.moveToNext()){
            Cursor c = gardenDb.rawQuery("SELECT id_p from powiadomienia where tresc =\"Nie zapomnij mnie zebrać!\"",null);
            while(c.moveToNext())
            {
                ContentValues cv = new ContentValues();
                cv.put("data_powiadomienia",ozk);
                long result = gardenDb.update("powiadomienia",cv,"id_p = "+ c.getString(0) + " and id_egzemplarza=" + e.getString(0),null);
                if(result == -1) return false;
            }
            Cursor d = gardenDb.rawQuery("SELECT id_p from powiadomienia where tresc =\"Możesz mnie zasadzić!\"",null);
            while(d.moveToNext())
            {
                ContentValues cv = new ContentValues();
                cv.put("data_powiadomienia",osp);
                long result = gardenDb.update("powiadomienia",cv,"id_p = "+ d.getString(0) + " and id_egzemplarza=" + e.getString(0),null);
                if(result == -1) return false;
            }
            Cursor f = gardenDb.rawQuery("SELECT id_p from powiadomienia where tresc =\"Nie zapomnij mnie zasadzić!\"",null);
            while(f.moveToNext())
            {
                ContentValues cv = new ContentValues();
                cv.put("data_powiadomienia",osk);
                long result = gardenDb.update("powiadomienia",cv,"id_p = "+ f.getString(0) + " and id_egzemplarza=" + e.getString(0),null);
                if(result == -1) return false;
            }
            Cursor g = gardenDb.rawQuery("SELECT id_p from powiadomienia where tresc =\"Gotowe do zebrania!\"",null);
            while(g.moveToNext())
            {
                ContentValues cv = new ContentValues();
                cv.put("data_powiadomienia",ozp);
                long result = gardenDb.update("powiadomienia",cv,"id_p = "+ g.getString(0) + " and id_egzemplarza=" + e.getString(0),null);
                if(result == -1) return false;
            }
        }
        return true;

    }

    //delete
    public boolean delete_uzytkownik(int _id_u)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("uzytkownicy","id_u = "+String.valueOf(_id_u),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_roslina(int _id_r)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("rosliny", "id_r = "+String.valueOf(_id_r),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_zasada(int _id_z)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("zasady","id_z = "+ String.valueOf(_id_z),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_ogrodek(int _id_o)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("ogrodki","id_og = "+String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_egzemplarz(int _id_e)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("egzemplarze","id_e = "+ String.valueOf(_id_e),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_obserwacje(int _id_o)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("obserwacje","id_o = " + String.valueOf(_id_o),null);
        if(result == -1) return false;
        else return true;
    }
    public boolean delete_powiadomienia(int _id_p)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        long result = gardenDb.delete("powiadomienia","id_p = "+ String.valueOf(_id_p),null);
        if(result == -1) return false;
        else return true;
    }


    /// READ
    public Cursor getAllData(String _table_name)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM " + _table_name;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;

    }

    public Cursor get_Reminder_User(int _id_uz)
    {
        SQLiteDatabase gardenDb = this.getWritableDatabase();

        gardenDb.execSQL("drop view if exists pomocnicza_powiadomienia");
        gardenDb.execSQL("create  view  pomocnicza_powiadomienia as " +
                "select powiadomienia.id_p, rosliny.nazwa, powiadomienia.id_ogrodka,powiadomienia.tresc,powiadomienia.data_powiadomienia from powiadomienia " +
                "join egzemplarze on powiadomienia.id_egzemplarza = egzemplarze.id_e left join rosliny on egzemplarze.id_rosliny = rosliny.id_r;");
        gardenDb.execSQL(" drop view if exists pomocnicza_2;");
        gardenDb.execSQL(" create view pomocnicza_2 as select p.id_p,p.nazwa,p.id_ogrodka,ogrodki.nazwa as nazwa_ogrodka,ogrodki.id_uzytkownika,p.tresc,p.data_powiadomienia from pomocnicza_powiadomienia as p left join ogrodki on p.id_ogrodka = ogrodki.id_og ");
        //id egzemplarza //id ogrodka naleza do uzytkownika o id
        String select_all ="select p.id_p,p.nazwa,p.nazwa_ogrodka,p.tresc,p.data_powiadomienia from pomocnicza_2 as p join uzytkownicy on p.id_uzytkownika = uzytkownicy.id_u where uzytkownicy.id_u = " + _id_uz;
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

    public Cursor getAllData_whereidr(String _table_name, int id_r) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all ="";
        switch (_table_name){
            case "rosliny":
                select_all = "SELECT * FROM " +_table_name + " WHERE id_r = " + id_r;
                break;
            case "zasady":
                select_all = "SELECT id_z,tresc FROM " +_table_name + " WHERE id_rosliny = " + id_r;
        }
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getDataObserwacje(int id_eg, String obserwacje) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM " + obserwacje + " WHERE id_egzemplarza = " + id_eg;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getTextObserwacje(int id_obserwacji) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT tresc FROM obserwacje WHERE id_o = " + id_obserwacji;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }


    public Cursor getPlaceEgzemplarz(int id_eg) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT miejsce,status FROM egzemplarze WHERE id_e = " + id_eg;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getInfoGarden(int id_og) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT nazwa, adres FROM ogrodki WHERE id_og = " + id_og;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getPrinciples(int id_ro) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM zasady WHERE id_rosliny = " + id_ro;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getTextZasady(int id_za) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT tresc FROM zasady WHERE id_z = " + id_za;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getPassword(int id_uz) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT haslo FROM uzytkownicy WHERE id_u = " + id_uz;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }

    public Cursor getSeed(int id_ro) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM rosliny WHERE id_r = " + id_ro;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }


    public Cursor getInfoReminder(int id_po) {
        SQLiteDatabase gardenDb = this.getWritableDatabase();
        String select_all = "SELECT * FROM powiadomienia WHERE id_p = " + id_po;
        Cursor data = gardenDb.rawQuery(select_all,null);
        return data;
    }
}

