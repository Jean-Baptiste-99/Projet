package com.ict.vmarket;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseVmarket extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Vmarket1";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "T_pannier";
    public static final String TABLE_NAME2 = "T_pannier2";

    public DatabaseVmarket(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String StrSql = "create table T_pannier ("
                +"idpannier integer primary key autoincrement,"
                +"img varchar (255) not null,"
                +"nomPdt varchar (30) not null,"
                +"entreprise varchar (50) not null,"
                +"prix integer not null,"
                +"qte integer not null"
                +")";
        db.execSQL(StrSql);

        String StrSql2 = "create table T_pannier2 ("
                +"idpannier integer primary key autoincrement,"
                +"img varchar (255) not null,"
                +"nomPdt varchar (30) not null,"
                +"entreprise varchar (50) not null,"
                +"prix integer not null,"
                +"promo integer not null,"
                +"qte integer not null"
                +")";
        db.execSQL(StrSql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       /* String req2 = "create view montant as select SUM(qte*prix) as credit from T_pannier";
        db.execSQL(req2);


        String req = "create view montant2 as select SUM(qte*prix) as credit from T_pannier2";
        db.execSQL(req);*/

    }

    public  boolean   Insertpannier (String img, String nomPdt, String ent, int prix, int qte){

        img = img.replace("'","''");
        nomPdt = nomPdt.replace("'","''");
        ent = ent.replace("'","''");

        String req = "insert into T_pannier (img, nomPdt, entreprise, prix, qte) " +
                "values ('"+img+"', '"+nomPdt+"', '"+ent+"' ,"+prix+" ,"+qte+")";

        try {
            this.getWritableDatabase().execSQL(req);
            Log.i("DATABASE", "insertion reussi");
            return true;

        }catch (SQLException e){
            return false;
        }

    }

    public  boolean   Insertpannier2 (String img, String nomPdt, String ent, int prix, int promo, int qte){

        img = img.replace("'","''");
        nomPdt = nomPdt.replace("'","''");
        ent = ent.replace("'","''");

        String req = "insert into T_pannier2 (img, nomPdt, entreprise, prix, promo, qte) " +
                "values ('"+img+"', '"+nomPdt+"', '"+ent+"' ,"+prix+" , "+promo+" , "+qte+")";

        try {
            this.getWritableDatabase().execSQL(req);
            Log.i("DATABASE", "insertion reussi");
            return true;

        }catch (SQLException e){
            return false;
        }

    }

    public void deletePannier(int id){
        SQLiteDatabase database6 = this.getWritableDatabase();
        String request = "DELETE FROM T_pannier WHERE idpannier=?";

        SQLiteStatement statement = database6.compileStatement(request);
        statement.clearBindings();
        statement.bindDouble(1, (double) id );
        statement.execute();
        database6.close();

    }


    public Integer delete(int id){
        SQLiteDatabase data = this.getWritableDatabase();
        return data.delete("T_pannier", "idpannier = ?", new String[]{String.valueOf(id)});

    }


    public int remove_User(int id) {
        SQLiteDatabase bdd = this.getWritableDatabase();
        return bdd.delete(TABLE_NAME, "idpannier" + " = " + id, null);
    }

    public void deleteAll(){
        SQLiteDatabase data2 = this.getWritableDatabase();
        data2.equals("DELETE FROM T_pannier");
    }


    public Cursor getdata(String request){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(request, null);

    }


    public Cursor viewpannier(){
        SQLiteDatabase database2 = this.getWritableDatabase();
        Cursor res2 = database2.rawQuery("select * from T_pannier", null);
        return res2;

    }

    public Cursor viewpannier2(){
        SQLiteDatabase database3 = this.getWritableDatabase();
        Cursor res2 = database3.rawQuery("select * from T_pannier2", null);
        return res2;

    }

    public Cursor pannier(){
        SQLiteDatabase database4 = this.getWritableDatabase();
        Cursor res2 = database4.rawQuery("select SUM(qte*prix) as credit from T_pannier", null);
        return res2;

    }

    public Cursor pannier2(){
        SQLiteDatabase database5 = this.getWritableDatabase();
        Cursor res2 = database5.rawQuery("select SUM(qte*promo) as credit from T_pannier2", null);
        return res2;

    }
}
