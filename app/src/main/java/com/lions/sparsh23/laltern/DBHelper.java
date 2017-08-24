package com.lions.sparsh23.laltern;

/**
 * Created by Sparsh23 on 08/07/16.
 */

import android.app.SearchManager;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import layout.Order_Struct;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lalterndDataRelease1.db";
    public static final  String reqdirect = "REQDIRECT";
    HashMap<String,String> mAliasMap = new HashMap<>();



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
db.execSQL("CREATE TABLE ImageData (UID text, DES text,  OWN_NAME text, PRICE float, PATH text, TYPE text, QUANTITY int, NOIMAGES int, OWNER text, TITLE text, CATEGORY text, SUBCAT text, META text, CRAFT text, PROTYPE text, RATING text, SIZE text, COLOR text, REVPRICE text, REVQUANTITY text, SIZEAVAIL text, STOCK text);");
        db.execSQL("CREATE TABLE "+Profile_Strut.Table_Name+" ( "+Profile_Strut.Uid+" text, "+Profile_Strut.Name+" text, "+Profile_Strut.Comp_Name+" text, "+Profile_Strut.Designation+" text, "+Profile_Strut.Addr+" text, "+Profile_Strut.City+" text, "+Profile_Strut.Email+" text, "+Profile_Strut.Cont+" text, "+Profile_Strut.State+" text, "+Profile_Strut.ToB+" text, "+Profile_Strut.Pan+" text, "+Profile_Strut.Web+" text);");
        db.execSQL("CREATE TABLE "+Artisian_Struct.Table_Name+" ( "+Artisian_Struct.name+" text, "+Artisian_Struct.state+" text, "+Artisian_Struct.craft+" text, "+Artisian_Struct.awards+" text, "+Artisian_Struct.description+" text, "+Artisian_Struct.tob+" text, "+Artisian_Struct.pics+" text, "+Artisian_Struct.noimg+" text, "+Artisian_Struct.authentic+" text, "+Artisian_Struct.price+" text, "+Artisian_Struct.ratings+" text, "+Artisian_Struct.uid+" text);");
        db.execSQL("CREATE TABLE "+Request_Struct.table_name+" ( "+Request_Struct.ord_id+" text, "+Request_Struct.pro_id+" text, "+Request_Struct.buy_id+" text, "+Request_Struct.des+" text, "+Request_Struct.path+" text, "+Request_Struct.craft+" text, "+Request_Struct.status+" text, "+Request_Struct.quantity+" text);");

        db.execSQL("CREATE TABLE SearchData ( UID INTEGER PRIMARY KEY AUTOINCREMENT, TAG text, SUGGEST text, TYPE text );");

        db.execSQL("CREATE TABLE "+Cart_Struct.Table_Name+" ( "+Cart_Struct.cartuid+" text , "+Cart_Struct.useruid+" text, "+Cart_Struct.prouid+" text, "+Cart_Struct.quantity+" text, "+Cart_Struct.size+" text, "+Cart_Struct.rate+" text, "+Cart_Struct.title+" text, "+Cart_Struct.path+" text, "+Cart_Struct.price+" text);");

      //  db.execSQL("CREATE TABLE "+Request_Struct.table_name+" ( "+Request_Struct.ord_id+" text, "+Request_Struct.pro_id+" text, "+Request_Struct.buy_id+" text, "+Request_Struct.des+" text, "+Request_Struct.path+" text, "+Request_Struct.craft+" text, "+Request_Struct.status+" text, "+Request_Struct.quantity+" text, "+Request_Struct.reply+" text);");

        db.execSQL("CREATE TABLE "+Filter_Struct.table_name+" ("+Filter_Struct.uid+" text, "+Filter_Struct.cat+" text, "+Filter_Struct.subcat+" text, "+Filter_Struct.colorfil+" text, "+ Filter_Struct.sizefil+" text, "+Filter_Struct.producttype+" text);");
        db.execSQL("CREATE TABLE SearchFilter (CRAFT text, PROTYPE text);");
        db.execSQL("CREATE TABLE "+Addr_Struct.Table_Name+" ("+Addr_Struct.title+" text, "+Addr_Struct.area+" text, "+Addr_Struct.city+" text, "+Addr_Struct.dist+" text, "+Addr_Struct.state+" text, "+Addr_Struct.pin+" text, "+Addr_Struct.contact+" text, "+Addr_Struct.addr+" text, "+Addr_Struct.country+" text);");
        db.execSQL("CREATE TABLE "+ Order_Struct.table_name+" ("+Order_Struct.ord_uid+" text, "+Order_Struct.pay_uid+" text, "+Order_Struct.ord_add+" text, "+Order_Struct.user_uid+" text, "+Order_Struct.date_time+" text, "+Order_Struct.pay_mode+" text, "+Order_Struct.status+" text, "+Order_Struct.user_name+" text, "+Order_Struct.price+" text );");



    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS ImageData");
        onCreate(db);
    }

    public void InitCart(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+Cart_Struct.Table_Name);
        Log.d("sql craft","Deleted");


    }

    public void InitAddr()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+Addr_Struct.Table_Name);
        Log.d("addr","deleted");

    }

    public void InitOrders()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+Order_Struct.table_name);
        Log.d("orders_table","deleted");

    }

    public void Filter_Search_Table()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM SearchData WHERE TAG IS NULL OR trim(TAG) = ''");
        Log.d("cleared","search");

    }




    public boolean InitImg()

    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM ImageData");
        Log.d("sql","Deleted");




        return true;
    }

    public boolean InitSearchData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SearchData");
        Log.d("search tags","Deleted");



        return true;
    }

    public boolean InitFilter()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+Filter_Struct.table_name);
        Log.d("filter ","deleted");
        return true;
    }
    public boolean InitSearchFilter()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SearchFilter");
        Log.d("filter_search","deleted");
        return  true;

    }


    public boolean InsertAddr(String title, String addr, String area, String city, String dist, String state, String pin, String cont, String country)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Addr_Struct.title,title);
        contentValues.put(Addr_Struct.addr, addr);
        contentValues.put(Addr_Struct.area,area);
        contentValues.put(Addr_Struct.city,city);
        contentValues.put(Addr_Struct.dist,dist);
        contentValues.put(Addr_Struct.state,state);
        contentValues.put(Addr_Struct.pin,pin);
        contentValues.put(Addr_Struct.contact,cont);
        contentValues.put(Addr_Struct.country,country);
        long row = db.insertWithOnConflict(Addr_Struct.Table_Name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("addr_inserted",""+row);

        return  true;
    }

    public boolean InsertCartData(String cartuid, String prouid, String useruid, String quantity, String size, String rate, String title, String path, String price){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cart_Struct.cartuid,cartuid);
        contentValues.put(Cart_Struct.prouid, prouid);
        contentValues.put(Cart_Struct.useruid, useruid);
        contentValues.put(Cart_Struct.quantity, quantity);
        contentValues.put(Cart_Struct.size, size);
        contentValues.put(Cart_Struct.title,title);
        contentValues.put(Cart_Struct.path,path);
        contentValues.put(Cart_Struct.rate,rate);
        contentValues.put(Cart_Struct.price,price);
        long row = db.insertWithOnConflict(Cart_Struct.Table_Name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("cart data",String.valueOf(row));


        return true;
    }

    public boolean InsertSearchFilter(String craft, String protype)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CRAFT",craft);
        contentValues.put("PROTYPE",protype);
        long row = db.insertWithOnConflict("SearchFilter",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("insert filter",String.valueOf(row));
        return true;
    }

    public ArrayList<HashMap<String,ArrayList<String>>>GetSearchFilter()
    {

        ArrayList<HashMap<String,ArrayList<String>>> data = new ArrayList<HashMap<String, ArrayList<String>>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from SearchFilter group by CRAFT",null);
        res.moveToFirst();
        Log.d("filter craft",""+res.getString(res.getColumnIndex("CRAFT")));
        while (!res.isAfterLast())
        {
            HashMap<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
            Log.d("cattag",""+res.getString(res.getColumnIndex("CRAFT")));
            Cursor c = db.rawQuery("select * from SearchFilter where CRAFT like '"+res.getString(res.getColumnIndex("CRAFT"))+"' group by PROTYPE",null);
            ArrayList<String> subcatlist = new ArrayList<String>();
            c.moveToFirst();
            while (!c.isAfterLast())
            {
                subcatlist.add(c.getString(c.getColumnIndex("PROTYPE")));
                Log.d("protype",""+c.getString(c.getColumnIndex("PROTYPE")));
                c.moveToNext();
            }
            map.put(res.getString(res.getColumnIndex("CRAFT")),subcatlist);
            data.add(map);
            res.moveToNext();
        }
        return  data;
    }

    public ArrayList<String> Craft_Search_Page()
    {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from SearchFilter group by CRAFT",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            data.add(res.getString(res.getColumnIndex("CRAFT")));
            res.moveToNext();
        }
        return data;
    }

    public ArrayList<String> Pro_Type_Search_Page()
    {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from SearchFilter group by PROTYPE",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            data.add(res.getString(res.getColumnIndex("PROTYPE")));
            res.moveToNext();
        }

        return  data;
    }

    public ArrayList<HashMap<String,String>> Get_Search_Home_Filter_Products(ArrayList<String> craft, ArrayList<String> pro_type)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        ArrayList<String> uid = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        for(int i=0;i<craft.size();i++)
        {
            Cursor res = db.rawQuery("select * from ImageData where CRAFT like '%"+craft.get(i)+"%'",null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                if (!uid.contains(res.getString(res.getColumnIndex("UID")))) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    //HashMap<String,String> map = new HashMap<String, String>();
                    map.put("uid", res.getString(res.getColumnIndex("UID")));
                    map.put("path", res.getString(res.getColumnIndex("PATH")));
                    map.put("des", res.getString(res.getColumnIndex("DES")));
                    map.put("title", res.getString(res.getColumnIndex("TITLE")));
                    map.put("price", res.getString(res.getColumnIndex("PRICE")));
                    map.put("quantity", res.getString(res.getColumnIndex("QUANTITY")));
                    map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
                    map.put("type", res.getString(res.getColumnIndex("TYPE")));
                    map.put("cat", res.getString(res.getColumnIndex("CATEGORY")));
                    map.put("subcat", res.getString(res.getColumnIndex("SUBCAT")));
                    map.put("craft", res.getString(res.getColumnIndex("CRAFT")));
                    map.put("artuid", res.getString(res.getColumnIndex("OWNER")));
                    map.put("meta", res.getString(res.getColumnIndex("META")));
                    map.put("rating", res.getString(res.getColumnIndex("RATING")));
                    map.put("protype", res.getString(res.getColumnIndex("PROTYPE")));
                    map.put("revquantity", res.getString(res.getColumnIndex("REVQUANTITY")));
                    map.put("revprice", res.getString(res.getColumnIndex("REVPRICE")));
                    map.put("sizeavail", res.getString(res.getColumnIndex("SIZEAVAIL")));
                    uid.add(res.getString(res.getColumnIndex("UID")));
                    data.add(map);
                    res.moveToNext();
                }
            }
        }

        for(int i=0;i<pro_type.size();i++)
        {
            Cursor res = db.rawQuery("select * from ImageData where PROTYPE like '%"+pro_type.get(i)+"%'",null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                if (!uid.contains(res.getString(res.getColumnIndex("UID")))) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    //HashMap<String,String> map = new HashMap<String, String>();
                    map.put("uid", res.getString(res.getColumnIndex("UID")));
                    map.put("path", res.getString(res.getColumnIndex("PATH")));
                    map.put("des", res.getString(res.getColumnIndex("DES")));
                    map.put("title", res.getString(res.getColumnIndex("TITLE")));
                    map.put("price", res.getString(res.getColumnIndex("PRICE")));
                    map.put("quantity", res.getString(res.getColumnIndex("QUANTITY")));
                    map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
                    map.put("type", res.getString(res.getColumnIndex("TYPE")));
                    map.put("cat", res.getString(res.getColumnIndex("CATEGORY")));
                    map.put("subcat", res.getString(res.getColumnIndex("SUBCAT")));
                    map.put("craft", res.getString(res.getColumnIndex("CRAFT")));
                    map.put("artuid", res.getString(res.getColumnIndex("OWNER")));
                    map.put("meta", res.getString(res.getColumnIndex("META")));
                    map.put("rating", res.getString(res.getColumnIndex("RATING")));
                    map.put("protype", res.getString(res.getColumnIndex("PROTYPE")));
                    map.put("revquantity", res.getString(res.getColumnIndex("REVQUANTITY")));
                    map.put("revprice", res.getString(res.getColumnIndex("REVPRICE")));
                    map.put("sizeavail", res.getString(res.getColumnIndex("SIZEAVAIL")));
                    uid.add(res.getString(res.getColumnIndex("UID")));
                    data.add(map);
                    res.moveToNext();
                }
            }
        }

        return data;
    }







    public boolean InsertFilterData(   ArrayList<HashMap<String,String>> data)
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        try {
            db.beginTransaction();
            for(int i =0;i<data.size();i++)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Filter_Struct.cat, data.get(i).get("category"));
                contentValues.put(Filter_Struct.subcat, data.get(i).get("subcat"));
                contentValues.put(Filter_Struct.colorfil, data.get(i).get("color"));
                contentValues.put(Filter_Struct.sizefil, data.get(i).get("size"));
                contentValues.put(Filter_Struct.producttype, data.get(i).get("protype"));
                long row = db.insertWithOnConflict(Filter_Struct.table_name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
                Log.d(" filter inserted",""+row);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        catch (SQLiteException e)
        {
            Log.d("error",e.toString());
        }
        return  true;
    }

    public boolean InsertRequestData(String prouid, String buyuid, String orduid, String des, String path, String status, String reply, String craft, String quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Request_Struct.ord_id, orduid );
        contentValues.put(Request_Struct.buy_id,buyuid);
        contentValues.put(Request_Struct.pro_id,prouid);
        contentValues.put(Request_Struct.des,des);
        contentValues.put(Request_Struct.craft,craft);
        contentValues.put(Request_Struct.quantity,quantity);
        contentValues.put(Request_Struct.path,path);
        contentValues.put(Request_Struct.status,status);
        long row  = db.insertWithOnConflict(Request_Struct.table_name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d(Request_Struct.table_name,String.valueOf(row));
        return true;
    }


    public boolean InitProfile(){

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM PROFILE");
        return true;
    }

    public boolean InitOrd(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+Request_Struct.table_name);

        return true;
    }


    public ArrayList<HashMap<String,String>> GetFilterData(String cat, String subcat)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+Filter_Struct.table_name+"  where "+Filter_Struct.cat+" like '"+cat+"' and "+Filter_Struct.subcat+" like '"+subcat+"' GROUP BY "+Filter_Struct.sizefil+", "+Filter_Struct.colorfil+", "+Filter_Struct.producttype,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("size",c.getString(c.getColumnIndex(Filter_Struct.sizefil)));
            map.put("color",c.getString(c.getColumnIndex(Filter_Struct.colorfil)));
            map.put("protype",c.getString(c.getColumnIndex(Filter_Struct.producttype)));
            data.add(map);
            c.moveToNext();
        }
        return data;
    }

    public ArrayList<HashMap<String,String>> GetSizes(String cat, String subcat)
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        ArrayList<String> sizes = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+" where "+Filter_Struct.cat+" like '"+cat+"' and "+Filter_Struct.subcat+" like '"+subcat+"' group by "+Filter_Struct.sizefil,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            ArrayList<String> sizelist = new ArrayList<String>();
            String aux_sizes = res.getString(res.getColumnIndex(Filter_Struct.sizefil));
            sizelist.addAll(Arrays.asList(aux_sizes.split(",")));
            Log.d("inside size", res.getString(res.getColumnIndex(Filter_Struct.sizefil))+" , "+sizelist.size());
            sizes.addAll(sizelist);
            res.moveToNext();
        }
        Log.d("size_before_clean",""+sizes.size());
        HashSet<String> cleansizes = new HashSet<String>();
        cleansizes.addAll(sizes);
        sizes.clear();

        sizes.addAll(cleansizes);
        Log.d("size_after_clean",""+sizes.size());
        for(int i=0;i<sizes.size();i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",sizes.get(i));
            map.put("status","false");
            map.put("col","SIZE");
            Log.d("tag_clean_size",sizes.get(i));
            list.add(map);
        }
        return list;
    }

    public ArrayList<HashMap<String,String>> GetColor(String cat, String subcat)
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+" where "+Filter_Struct.cat+" like '"+cat+"' and "+Filter_Struct.subcat+" like '"+subcat+"' group by "+Filter_Struct.colorfil,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex(Filter_Struct.colorfil)));
            map.put("status","false");
            map.put("col","COLOR");
            Log.d("inside color", res.getString(res.getColumnIndex(Filter_Struct.colorfil)));
            list.add(map);
            res.moveToNext();
        }

        return list;

    }


    public  ArrayList<HashMap<String,String>> GetProType(String cat, String subcat)
    {


        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+" where "+Filter_Struct.cat+" like '"+cat+"' and "+Filter_Struct.subcat+" like '"+subcat+"' group by "+Filter_Struct.producttype,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex(Filter_Struct.producttype)));
            map.put("status","false");
            map.put("col","PROTYPE");
            Log.d("inside color", res.getString(res.getColumnIndex(Filter_Struct.producttype)));
            list.add(map);
            res.moveToNext();
        }

        return list;


    }

    public ArrayList<HashMap<String,String>> GetSizes_Search()
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        ArrayList<String> sizes = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+"  group by "+Filter_Struct.sizefil,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            ArrayList<String> sizelist = new ArrayList<String>();
            String aux_sizes = res.getString(res.getColumnIndex(Filter_Struct.sizefil));
            sizelist.addAll(Arrays.asList(aux_sizes.split(",")));
            Log.d("inside size", res.getString(res.getColumnIndex(Filter_Struct.sizefil))+" , "+sizelist.size());
            sizes.addAll(sizelist);
            res.moveToNext();
        }
        Log.d("size_before_clean",""+sizes.size());
        HashSet<String> cleansizes = new HashSet<String>();
        cleansizes.addAll(sizes);
        sizes.clear();

        sizes.addAll(cleansizes);
        Log.d("size_after_clean",""+sizes.size());
        for(int i=0;i<sizes.size();i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",sizes.get(i));
            map.put("status","false");
            map.put("col","SIZE");
            Log.d("tag_clean_size",sizes.get(i));
            list.add(map);

        }


        return list;

    }

    public ArrayList<HashMap<String,String>> GetColor_Search()
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+" group by "+Filter_Struct.colorfil,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex(Filter_Struct.colorfil)));
            map.put("status","false");
            map.put("col","COLOR");
            Log.d("inside color", res.getString(res.getColumnIndex(Filter_Struct.colorfil)));
            list.add(map);
            res.moveToNext();
        }

        return list;

    }


    public  ArrayList<HashMap<String,String>> GetProType_Search()
    {


        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+" group by "+Filter_Struct.producttype,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex(Filter_Struct.producttype)));
            map.put("status","false");
            map.put("col","PROTYPE");
            Log.d("inside color", res.getString(res.getColumnIndex(Filter_Struct.producttype)));
            list.add(map);
            res.moveToNext();
        }

        return list;


    }




    public HashMap<String,ArrayList<HashMap<String,String>>> GetFilter_Cat_Subcat(String cat , String subcat)
    {
        HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String,String>>>();
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String,String>> sizelist = new ArrayList<HashMap<String,String>>();
        Cursor res = db.rawQuery("select * from ImageData where CATEGORY like '"+cat.toString()+"' SUBCAT like '"+subcat+"' group by SIZE",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex("SIZE")));
            map.put("status","false");
            Log.d("inside size", res.getString(res.getColumnIndex("SIZE")));
            sizelist.add(map);
            res.moveToNext();

        }

        Cursor res1 = db.rawQuery("select * from ImageData where CATEGORY like '"+cat.toString()+"' SUBCAT like '"+subcat+"' group by COLOR",null);
        res1.moveToFirst();
        while (!res1.isAfterLast())
        {

        }


        return  filterdata;

    }








    public ArrayList<HashMap<String,ArrayList<String>>>  GetCategories()

    {

        ArrayList<HashMap<String,ArrayList<String>>> data = new ArrayList<HashMap<String, ArrayList<String>>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Filter_Struct.table_name+" group by "+Filter_Struct.cat,null);
        res.moveToFirst();

        Log.d("cattag",""+res.getString(res.getColumnIndex(Filter_Struct.cat)));

        while (!res.isAfterLast())

        {

            HashMap<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

            Log.d("cattag",""+res.getString(res.getColumnIndex(Filter_Struct.cat)));
            Cursor c = db.rawQuery("select * from "+Filter_Struct.table_name+" where "+Filter_Struct.cat+" like '"+res.getString(res.getColumnIndex(Filter_Struct.cat))+"' group by "+Filter_Struct.subcat,null);

                    ArrayList<String> subcatlist = new ArrayList<String>();
            c.moveToFirst();

            while (!c.isAfterLast())

            {

                subcatlist.add(c.getString(c.getColumnIndex(Filter_Struct.subcat)));

                Log.d("subcattag",""+c.getString(c.getColumnIndex(Filter_Struct.subcat)));
                c.moveToNext();
            }

            map.put(res.getString(res.getColumnIndex(Filter_Struct.cat)),subcatlist);

            data.add(map);




            res.moveToNext();
        }


        return  data;
    }

    public ArrayList<HashMap<String,String>> GetAddresses()
    {

        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Addr_Struct.Table_Name,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("title",res.getString(res.getColumnIndex(Addr_Struct.title)));
            map.put("addr",res.getString(res.getColumnIndex(Addr_Struct.addr)));
            map.put("area",res.getString(res.getColumnIndex(Addr_Struct.area)));
            map.put("city",res.getString(res.getColumnIndex(Addr_Struct.city)));
            map.put("dist",res.getString(res.getColumnIndex(Addr_Struct.dist)));
            map.put("state",res.getString(res.getColumnIndex(Addr_Struct.state)));
            map.put("pin",res.getString(res.getColumnIndex(Addr_Struct.pin)));
            map.put("cont",res.getString(res.getColumnIndex(Addr_Struct.contact)));
            map.put("country",res.getString(res.getColumnIndex(Addr_Struct.country)));
            data.add(map);
            Log.d("inside_addr",""+res.getString(res.getColumnIndex(Addr_Struct.pin)));
            res.moveToNext();
        }

        return data;
    }

   public ArrayList<HashMap<String,String>> Get_Product_All(ArrayList<HashMap<String,String>> data, HashMap<String,String> selection)
   {
       HashSet<String> uids = new HashSet<String>();
       ArrayList<HashMap<String,String>> Product_Color_List = new ArrayList<HashMap<String, String>>();
       SQLiteDatabase db = this.getReadableDatabase();
       Log.d("check_forall_filtersize",""+data.size());
       for(int i=0;i<data.size();i++)
       {
                Cursor res = db.rawQuery("select * from ImageData where CATEGORY like '"+selection.get("category")+"' and SUBCAT like '"+selection.get("subcat")+"' and "+data.get(i).get("col")+" like '%"+data.get(i).get("tag")+"%'",null);
                Log.d("query_sql","select * from ImageData where CATEGORY like '"+selection.get("category")+"' and SUBCAT like '"+selection.get("subcat")+"' and "+data.get(i).get("col")+" like '%"+data.get(i).get("tag")+"%'") ;
                Log.d("used","get_product_all");
           res.moveToFirst();
                while (!res.isAfterLast())
                {

                    Log.d("inside_get_all",""+data.get(i));
                    if(!uids.contains(res.getString(res.getColumnIndex("UID"))))
                    {

                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("uid",res.getString(res.getColumnIndex("UID")));
                        map.put("path",res.getString(res.getColumnIndex("PATH")));
                        map.put("des",res.getString(res.getColumnIndex("DES")));
                        map.put("title", res.getString(res.getColumnIndex("TITLE")));
                        map.put("price",res.getString(res.getColumnIndex("PRICE")));
                        map.put("quantity",res.getString(res.getColumnIndex("QUANTITY")));
                        map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
                        map.put("type",res.getString(res.getColumnIndex("TYPE")));
                        map.put("cat",res.getString(res.getColumnIndex("CATEGORY")));
                        map.put("subcat",res.getString(res.getColumnIndex("SUBCAT")));
                        map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
                        map.put("artuid",res.getString(res.getColumnIndex("OWNER")));
                        map.put("meta",res.getString(res.getColumnIndex("META")));
                        map.put("rating",res.getString(res.getColumnIndex("RATING")));
                        map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
                        map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
                        map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
                        map.put("stock",res.getString(res.getColumnIndex("STOCK")));

                        map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
                        Product_Color_List.add(map);


                    }
                    Log.d("color_pro_title",res.getString(res.getColumnIndex("TITLE")));

                    uids.add(res.getString(res.getColumnIndex("UID")));
                    res.moveToNext();
                }

       }

       return  Product_Color_List;
   }

    public ArrayList<HashMap<String,String>> Get_Products_Artisian(String uid)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("artisian_uid_pro",""+uid);
        Cursor res = db.rawQuery("select * from ImageData where OWNER like '"+uid+"'",null);
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("uid",res.getString(res.getColumnIndex("UID")));
            map.put("path",res.getString(res.getColumnIndex("PATH")));
            map.put("des",res.getString(res.getColumnIndex("DES")));
            map.put("title", res.getString(res.getColumnIndex("TITLE")));
            map.put("price",res.getString(res.getColumnIndex("PRICE")));
            map.put("quantity",res.getString(res.getColumnIndex("QUANTITY")));
            map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
            map.put("type",res.getString(res.getColumnIndex("TYPE")));
            map.put("cat",res.getString(res.getColumnIndex("CATEGORY")));
            map.put("subcat",res.getString(res.getColumnIndex("SUBCAT")));
            map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
            map.put("artuid",res.getString(res.getColumnIndex("OWNER")));
            map.put("meta",res.getString(res.getColumnIndex("META")));
            map.put("rating",res.getString(res.getColumnIndex("RATING")));
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            data.add(map);

        }

        return  data;



    }

    public ArrayList<HashMap<String,String>> Get_Product_All_Search(ArrayList<HashMap<String,String>> data)
    {
        HashSet<String> uids = new HashSet<String>();
        ArrayList<HashMap<String,String>> Product_Color_List = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("check_forall_filtersize",""+data.size());
        for(int i=0;i<data.size();i++)
        {
            Cursor res = db.rawQuery("select * from ImageData where "+data.get(i).get("col")+" like '%"+data.get(i).get("tag")+"%'",null);
            Log.d("query_sql","select * from ImageData where "+data.get(i).get("col")+" like '%"+data.get(i).get("tag")+"%'") ;
            res.moveToFirst();
            while (!res.isAfterLast())
            {

                Log.d("inside_get_all",""+data.get(i));
                if(!uids.contains(res.getString(res.getColumnIndex("UID"))))
                {

                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("uid",res.getString(res.getColumnIndex("UID")));
                    map.put("path",res.getString(res.getColumnIndex("PATH")));
                    map.put("des",res.getString(res.getColumnIndex("DES")));
                    map.put("title", res.getString(res.getColumnIndex("TITLE")));
                    map.put("price",res.getString(res.getColumnIndex("PRICE")));
                    map.put("quantity",res.getString(res.getColumnIndex("QUANTITY")));
                    map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
                    map.put("type",res.getString(res.getColumnIndex("TYPE")));
                    map.put("cat",res.getString(res.getColumnIndex("CATEGORY")));
                    map.put("subcat",res.getString(res.getColumnIndex("SUBCAT")));
                    map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
                    map.put("artuid",res.getString(res.getColumnIndex("OWNER")));
                    map.put("meta",res.getString(res.getColumnIndex("META")));
                    map.put("rating",res.getString(res.getColumnIndex("RATING")));
                    map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
                    map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
                    map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));

                    map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
                    Product_Color_List.add(map);


                }
                Log.d("color_pro_title",res.getString(res.getColumnIndex("TITLE")));

                uids.add(res.getString(res.getColumnIndex("UID")));
                res.moveToNext();
            }

        }

        return  Product_Color_List;
    }


    public ArrayList<HashMap<String,String>> GetSearchFilteredData(HashMap<String,String> map1)

    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
     SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where CRAFT like '%"+map1.get("craft")+"%' or PROTYPE like '%"+map1.get("protype")+"%' or CATEGORY like '%"+map1.get("protype")+"%' or SUBCAT like '%"+map1.get("protype")+"%' order by RATING DESC ",null);

        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("uid",res.getString(res.getColumnIndex("UID")));
            map.put("title",res.getString(res.getColumnIndex("TITLE")));
            map.put("price",res.getString(res.getColumnIndex("PRICE")));
            map.put("quantity",res.getString(res.getColumnIndex("QUANTITY")));
            map.put("noimages",res.getString(res.getColumnIndex("NOIMAGES")));
            map.put("type",res.getString(res.getColumnIndex("TYPE")));
            map.put("category",res.getString(res.getColumnIndex("CATEGORY")));
            map.put("subcat",res.getString(res.getColumnIndex("SUBCAT")));
            map.put("meta",res.getString(res.getColumnIndex("META")));
            map.put("des",res.getString(res.getColumnIndex("DES")));
            map.put("artuid",res.getString(res.getColumnIndex("OWNER")));
            map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
            map.put("color",res.getString(res.getColumnIndex("COLOR")));
            map.put("size",res.getString(res.getColumnIndex("SIZE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));

            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("rating",res.getString(res.getColumnIndex("RATING")));
            map.put("path",res.getString(res.getColumnIndex("PATH")));
            data.add(map);
            res.moveToNext();

        }

        return data;
    }



    public ArrayList<HashMap<String,String>> GetFilteredData(String cat, String subcat, String color, String size, String min, String max, String protype)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where CATEGORY = '"+cat+"' and SUBCAT = '"+subcat+"' and COLOR = '"+color+"' and SIZE = '"+size+"' and PROTYPE = '"+protype+"' and PRICE between "+min+" and "+max, null  );
        res.moveToFirst();
        Log.d("filter size",""+res.getCount());
        if (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("uid",res.getString(res.getColumnIndex("UID")));
            map.put("title",res.getString(res.getColumnIndex("TITLE")));
            map.put("price",res.getString(res.getColumnIndex("PRICE")));
            map.put("quantity",res.getString(res.getColumnIndex("QUANTITY")));
            map.put("noimages",res.getString(res.getColumnIndex("NOIMAGES")));
            map.put("type",res.getString(res.getColumnIndex("TYPE")));
            map.put("category",res.getString(res.getColumnIndex("CATEGORY")));
            map.put("subcat",res.getString(res.getColumnIndex("SUBCAT")));
            map.put("meta",res.getString(res.getColumnIndex("META")));
            map.put("des",res.getString(res.getColumnIndex("DES")));
            map.put("artuid",res.getString(res.getColumnIndex("OWNER")));
            map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
            map.put("color",res.getString(res.getColumnIndex("COLOR")));
            map.put("size",res.getString(res.getColumnIndex("SIZE")));
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("rating",res.getString(res.getColumnIndex("RATING")));
            map.put("path",res.getString(res.getColumnIndex("PATH")));
            //map.put("artuid",res.getString(res.getColumnIndex("")))
            data.add(map);
            res.moveToNext();

        }


        return data;

    }







    public JSONArray GetCartDataJson()
    {
        JSONArray data = new JSONArray();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Cart_Struct.Table_Name+" group by "+Cart_Struct.prouid, null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        {
            JSONObject map = new JSONObject();
            try {
                map.put("cartuid",res.getString(res.getColumnIndex(Cart_Struct.cartuid)));
                map.put("uid",res.getString(res.getColumnIndex(Cart_Struct.prouid)));
                map.put("useruid",res.getString(res.getColumnIndex(Cart_Struct.useruid)));
                map.put("quantity",res.getString(res.getColumnIndex(Cart_Struct.quantity)));
                map.put("size",res.getString(res.getColumnIndex(Cart_Struct.size)));
                map.put("title",res.getString(res.getColumnIndex(Cart_Struct.title)));
                map.put("path",res.getString(res.getColumnIndex(Cart_Struct.path)));
                map.put("price",res.getString(res.getColumnIndex(Cart_Struct.price)));
                map.put("rate",res.getString(res.getColumnIndex(Cart_Struct.rate)));
                data.put(map);
                res.moveToNext();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // map.put("craft",res.getString(res.getColumnIndex(Request_Struct.craft)));
            //map.put("quantity",res.getString(res.getColumnIndex(Request_Struct.quantity)));
//            res.moveToNext();
        }
        return  data;


    }




    public ArrayList<HashMap<String,String>> GetCartData()
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Cart_Struct.Table_Name+" group by "+Cart_Struct.prouid, null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("cartuid",res.getString(res.getColumnIndex(Cart_Struct.cartuid)));
            map.put("uid",res.getString(res.getColumnIndex(Cart_Struct.prouid)));
            map.put("useruid",res.getString(res.getColumnIndex(Cart_Struct.useruid)));
            map.put("quantity",res.getString(res.getColumnIndex(Cart_Struct.quantity)));
            map.put("size",res.getString(res.getColumnIndex(Cart_Struct.size)));
            map.put("title",res.getString(res.getColumnIndex(Cart_Struct.title)));
            map.put("path",res.getString(res.getColumnIndex(Cart_Struct.path)));
            map.put("price",res.getString(res.getColumnIndex(Cart_Struct.price)));
            map.put("rate",res.getString(res.getColumnIndex(Cart_Struct.rate)));

           // map.put("craft",res.getString(res.getColumnIndex(Request_Struct.craft)));
            //map.put("quantity",res.getString(res.getColumnIndex(Request_Struct.quantity)));
            data.add(map);
            res.moveToNext();
        }
        return  data;
    }

    public ArrayList<HashMap<String,String>> GetSearchImageData(String query) {

        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where TITLE LIKE '%"+query+"%';", null);

        res.moveToFirst();
        while (res.isAfterLast() == false) {
            HashMap<String, String> map = new HashMap<String, String>();
            String path = res.getString(res.getColumnIndex("PATH"));


            String uid = res.getString(res.getColumnIndex("UID"));


            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String price = res.getString(res.getColumnIndex("PRICE"));
            String quantity = res.getString(res.getColumnIndex("QUANTITY"));
            int nopic = res.getInt(res.getColumnIndex("NOIMAGES"));

            map.put("uid",uid);
            map.put("path",path);
            map.put("des",des);
            map.put("title", title);
            map.put("price",price);
            map.put("quantity",quantity);
            map.put("noimages", String.valueOf(nopic));



            data.add(map);
            res.moveToNext();
        }

        return data;
    }

    public void InitArtisian(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+Artisian_Struct.Table_Name);



    }

    public void InsertArtisian(String authentic, String awards, String craft,
                               String description, String name, String noimg, String pics, String price, String ratings, String state,
    String tob, String uid){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Artisian_Struct.authentic,authentic);
        contentValues.put(Artisian_Struct.awards,awards);
        contentValues.put(Artisian_Struct.craft,craft);
        contentValues.put(Artisian_Struct.description,description);
        contentValues.put(Artisian_Struct.name,name);
        contentValues.put(Artisian_Struct.noimg,noimg);
        contentValues.put(Artisian_Struct.pics,pics);
        contentValues.put(Artisian_Struct.price,price);
        contentValues.put(Artisian_Struct.ratings,ratings);
        contentValues.put(Artisian_Struct.state,state);
        contentValues.put(Artisian_Struct.tob,tob);
        contentValues.put(Artisian_Struct.uid,uid);
        long row = db.insertWithOnConflict(Artisian_Struct.Table_Name, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("Artist", String.valueOf(row)+"inserted");

    }



    public ArrayList<HashMap<String,String>> GetOrders(String uid)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Request_Struct.table_name+" where "+Request_Struct.buy_id+" = '"+uid+"'",null);

        Log.d("fetching uid request",""+uid);
        res.moveToFirst();
        while(!res.isAfterLast())
        {

         HashMap<String,String> map = new HashMap<String, String>();
            map.put("prouid",res.getString(res.getColumnIndex(Request_Struct.pro_id)));
            map.put("buyuid",res.getString(res.getColumnIndex(Request_Struct.buy_id)));
            map.put("orduid",res.getString(res.getColumnIndex(Request_Struct.ord_id)));
            map.put("des",res.getString(res.getColumnIndex(Request_Struct.des)));
            map.put("path",res.getString(res.getColumnIndex(Request_Struct.path)));
            map.put("status",res.getString(res.getColumnIndex(Request_Struct.status)));
            map.put("craft",res.getString(res.getColumnIndex(Request_Struct.craft)));
            map.put("quantity",res.getString(res.getColumnIndex(Request_Struct.quantity)));

            data.add(map);
            res.moveToNext();


        }

    return data;

    }

    public boolean Insert_Orders(String orduid, String payuid, String pro_uid, String ordadd, String useruid, String date_time, String user_name,String paymode, String status,String price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Order_Struct.ord_uid,orduid);
        contentValues.put(Order_Struct.date_time,date_time);
        contentValues.put(Order_Struct.user_name,user_name);
        contentValues.put(Order_Struct.user_uid,useruid);
        contentValues.put(Order_Struct.pay_mode,paymode);
        contentValues.put(Order_Struct.status,status);
        contentValues.put(Order_Struct.pro_uid,pro_uid);
        contentValues.put(Order_Struct.price,price);
        contentValues.put(Order_Struct.ord_add,ordadd);
        contentValues.put(Order_Struct.pay_uid,payuid);
        long row = db.insertWithOnConflict(Order_Struct.table_name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("order_inserted",""+row);


        return true;
    }

    public ArrayList<HashMap<String,String>> Get_Orders(String uid)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Order_Struct.table_name+" where "+Order_Struct.user_uid+" = '"+uid+"';",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<>();
            map.put("orduid",res.getString(res.getColumnIndex(Order_Struct.ord_uid)));
            map.put("prouid",res.getString(res.getColumnIndex(Order_Struct.pro_uid)));
            map.put("payuid",res.getString(res.getColumnIndex(Order_Struct.pay_uid)));
            map.put("ordadd",res.getString(res.getColumnIndex(Order_Struct.ord_add)));
            map.put("useruid",res.getString(res.getColumnIndex(Order_Struct.user_uid)));
            data.add(map);
            Log.d("order_uid",res.getString(res.getColumnIndex(Order_Struct.ord_uid)));
            res.moveToNext();
        }

        return  data;
    }


    public boolean InsertImageData  (ArrayList<HashMap<String,String>> data)
    {
        SQLiteDatabase db = this.getWritableDatabase();



        try {


            db.beginTransaction();
            for(int i =0 ; i<data.size();i++)
            {

                ContentValues contentValues = new ContentValues();
                contentValues.put("UID", data.get(i).get("uid"));
                Log.d("stock", data.get(i).get("stock"));
                contentValues.put("PRICE",data.get(i).get("price"));
                contentValues.put("QUANTITY", data.get(i).get("quantity"));
                contentValues.put("NOIMAGES", data.get(i).get("noimg"));
                contentValues.put("TITLE", data.get(i).get("title"));
                contentValues.put("TYPE",data.get(i).get("type"));
                contentValues.put("CATEGORY",data.get(i).get("category"));
                contentValues.put("SUBCAT",data.get(i).get("subcat"));
                contentValues.put("META",data.get(i).get("meta"));
                contentValues.put("DES", data.get(i).get("des"));
                contentValues.put("OWNER", data.get(i).get("own"));
                contentValues.put("OWN_NAME",data.get(i).get("own_name"));
                contentValues.put("CRAFT", data.get(i).get("craft"));
                contentValues.put("COLOR",data.get(i).get("color"));
                contentValues.put("SIZE",data.get(i).get("size"));
                contentValues.put("PROTYPE",data.get(i).get("protype"));
                contentValues.put("RATING",data.get(i).get("rating"));
                contentValues.put("REVPRICE",data.get(i).get("revprice"));
                contentValues.put("REVQUANTITY",data.get(i).get("revquantity"));
                contentValues.put("SIZEAVAIL",data.get(i).get("sizeavail"));
                contentValues.put("STOCK",data.get(i).get("stock"));
               // Log.d("craft", );
//                Log.d("owner from network",""+own);

                contentValues.put("PATH", data.get(i).get("path"));
                long row = db.insertWithOnConflict("ImageData", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                Log.d("ImageData", String.valueOf(row)+"inserted");

            }

            db.setTransactionSuccessful();
            db.endTransaction();
          //  db.setTransactionSuccessful();

        }catch (SQLiteException e)
        {
         Log.d("error",e.toString());

        }

        return true;
    }


    public boolean InsertSearchTag(ArrayList<HashMap<String,String>> data)

    {




        SQLiteDatabase db = this.getWritableDatabase();

        try {

            db.beginTransaction();


            for(int i=0;i<data.size();i++)
            {


                ContentValues contentValues = new ContentValues();
                contentValues.put("TAG", data.get(i).get("tag"));
                //Log.d("TAG", tag);
                contentValues.put("SUGGEST",data.get(i).get("suggest"));
                //  Log.d("suggest", suggest);
                contentValues.put("TYPE",data.get(i).get("type"));
                //Log.d("type",type);

                long row = db.insertWithOnConflict("SearchData", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                Log.d("Search data", String.valueOf(row)+"inserted");


            }

            db.setTransactionSuccessful();
            db.endTransaction();

           // db.setTransactionSuccessful();




        }catch (SQLiteException e)
        {
            Log.d("error",e.toString());
        }


        return true;
    }


    public boolean InsertProfileData (String uid, String name, String comp, String design, String tob, String cont, String email, String addr,
                                      String city,
                                      String state, String pan, String webs)

    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Profile_Strut.Name, name);
        contentValues.put(Profile_Strut.Comp_Name, comp);
        contentValues.put(Profile_Strut.Designation, design);
        contentValues.put(Profile_Strut.ToB, tob);
        contentValues.put(Profile_Strut.Cont, cont);
        contentValues.put(Profile_Strut.Email, email);
        contentValues.put(Profile_Strut.Addr, addr);
        contentValues.put(Profile_Strut.City,city);
        contentValues.put(Profile_Strut.State,state);
        contentValues.put(Profile_Strut.Pan,pan);
        contentValues.put(Profile_Strut.Uid, uid);
        contentValues.put(Profile_Strut.Web, webs);
        long row = db.insertWithOnConflict(Profile_Strut.Table_Name,null,contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("Profile Data", String.valueOf(row)+"inserted");
        return  true;
    }


    public HashMap<String,String> GetProfile(String uid)
    {

        HashMap<String,String> map = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Profile_Strut.Table_Name+" where "+Profile_Strut.Uid+" = '"+uid+"'", null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {

            map.put("name",res.getString(res.getColumnIndex(Profile_Strut.Name)));
            map.put("comp",res.getString(res.getColumnIndex(Profile_Strut.Comp_Name)));
            map.put("design",res.getString(res.getColumnIndex(Profile_Strut.Designation)));
            map.put("tob",res.getString(res.getColumnIndex(Profile_Strut.ToB)));
            map.put("cont",res.getString(res.getColumnIndex(Profile_Strut.Cont)));
            map.put("email",res.getString(res.getColumnIndex(Profile_Strut.Email)));
            map.put("addr",res.getString(res.getColumnIndex(Profile_Strut.Addr)));
            map.put("city",res.getString(res.getColumnIndex(Profile_Strut.City)));
            map.put("state",res.getString(res.getColumnIndex(Profile_Strut.State)));
            map.put("pan",res.getString(res.getColumnIndex(Profile_Strut.Pan)));
            map.put("uid",res.getString(res.getColumnIndex(Profile_Strut.Uid)));
            map.put("web",res.getString(res.getColumnIndex(Profile_Strut.Web)));

            Log.d("Profile fetching uid","" +res.getString(res.getColumnIndex(Profile_Strut.Uid)));

            res.moveToNext();
        }

        return map;

    }



    public HashMap<String, String> getArtisian(String uid)
    {
        HashMap<String,String> map = new HashMap<String, String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Artisian_Struct.Table_Name+" where "+Artisian_Struct.uid+"= '"+uid+"';",null);
        Log.d("uid artist",uid);
        res.moveToFirst();
        while (!res.isAfterLast())
        {

            String name = res.getString(res.getColumnIndex(Artisian_Struct.name));
            String state =res.getString(res.getColumnIndex(Artisian_Struct.state));
            String craft = res.getString(res.getColumnIndex(Artisian_Struct.craft));
            String des = res.getString(res.getColumnIndex(Artisian_Struct.description));
            String tob = res.getString(res.getColumnIndex(Artisian_Struct.tob));
            String awards = res.getString(res.getColumnIndex(Artisian_Struct.awards));
            String pic = res.getString(res.getColumnIndex(Artisian_Struct.pics));
            String noimg = res.getString(res.getColumnIndex(Artisian_Struct.noimg));
            String ArtUid = res.getString(res.getColumnIndex(Artisian_Struct.uid));
            String authentic  = res.getString(res.getColumnIndex(Artisian_Struct.authentic));
            String price = res.getString(res.getColumnIndex(Artisian_Struct.price));
            String rating = res.getString(res.getColumnIndex(Artisian_Struct.ratings));
            map.put("name",name);
            map.put("state",state);
            map.put("craft",craft);
            map.put("des",des);
            map.put("tob",tob);
            map.put("awards",awards);
            map.put("pic",pic);
            map.put("noimg",noimg);
            map.put("artuid",ArtUid);
            map.put("authentic",authentic);
            Log.d("artisian_authentic",authentic);
            map.put("price",price);
            map.put("rating",rating);

            res.moveToNext();
        }

        return map;
    }


    public  ArrayList<HashMap<String,String>> GetProductByArtist(String artuids)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where OWNER like '"+artuids+"'",null);
        res.moveToFirst();

        while (!res.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            String path = res.getString(res.getColumnIndex("PATH"));
            String uid = res.getString(res.getColumnIndex("UID"));
            String craft = res.getString(res.getColumnIndex("CRAFT"));
            String artuid = res.getString(res.getColumnIndex("OWNER"));
            //String own = res.getString(res.getColumnIndex("OWN"));
            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String category = res.getString(res.getColumnIndex("CATEGORY"));
            String quantity = res.getString(res.getColumnIndex("QUANTITY"));
            String price = res.getString(res.getColumnIndex("PRICE"));
            String noimages = res.getString(res.getColumnIndex("NOIMAGES"));
            String type = res.getString(res.getColumnIndex("TYPE"));
            String subcat = res.getString(res.getColumnIndex("SUBCAT"));
            String meta = res.getString(res.getColumnIndex("META"));
            String rating = res.getString(res.getColumnIndex("RATING"));



            Log.d("sizes_avail", ""+res.getString(res.getColumnIndex("SIZEAVAIL")));
            Log.d("des",""+des);

            map.put("uid",uid);
            map.put("path",path);
            //map.put("own",own);
            map.put("des",des);
            map.put("artuid",artuid);
            map.put("title",title);
            map.put("category", category);
            map.put("quantity", quantity);
            map.put("price", price);
            map.put("noimages",noimages);
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("meta",meta);
            map.put("craft",craft);
            map.put("rating",rating);
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));

            data.add(map);

            res.moveToNext();
        }



        return data;
    }

    public ArrayList<HashMap<String,String>> getimageDataMeta(String types )
    {

        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where META = '"+types+"' ;", null);
        res.moveToFirst();

        while (!res.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            String path = res.getString(res.getColumnIndex("PATH"));
            String uid = res.getString(res.getColumnIndex("UID"));
            String craft = res.getString(res.getColumnIndex("CRAFT"));
            String artuid = res.getString(res.getColumnIndex("OWNER"));
            //String own = res.getString(res.getColumnIndex("OWN"));
            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String category = res.getString(res.getColumnIndex("CATEGORY"));
            String quantity = res.getString(res.getColumnIndex("QUANTITY"));
            String price = res.getString(res.getColumnIndex("PRICE"));
            String noimages = res.getString(res.getColumnIndex("NOIMAGES"));
            String type = res.getString(res.getColumnIndex("TYPE"));
            String subcat = res.getString(res.getColumnIndex("SUBCAT"));
            String meta = res.getString(res.getColumnIndex("META"));
            String rating = res.getString(res.getColumnIndex("RATING"));



            Log.d("sizes_avail", ""+res.getString(res.getColumnIndex("SIZEAVAIL")));
            Log.d("des",""+des);

            map.put("uid",uid);
            map.put("path",path);
            //map.put("own",own);
            map.put("des",des);
            map.put("artuid",artuid);
            map.put("title",title);
            map.put("category", category);
            map.put("quantity", quantity);
            map.put("price", price);
            map.put("noimages",noimages);
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("meta",meta);
            map.put("craft",craft);
            map.put("rating",rating);
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));

            data.add(map);

            res.moveToNext();
        }
        return data;
    }


    public ArrayList<HashMap<String,String>> getimageDatatype(String types )
    {

        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where TYPE = '"+types+"'", null);
        res.moveToFirst();

        while (!res.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            String path = res.getString(res.getColumnIndex("PATH"));
            String uid = res.getString(res.getColumnIndex("UID"));
            String craft = res.getString(res.getColumnIndex("CRAFT"));
            String artuid = res.getString(res.getColumnIndex("OWNER"));
//           String own = res.getString(res.getColumnIndex("OWN"));
            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String category = res.getString(res.getColumnIndex("CATEGORY"));
            String quantity = res.getString(res.getColumnIndex("QUANTITY"));
            String price = res.getString(res.getColumnIndex("PRICE"));
            String noimages = res.getString(res.getColumnIndex("NOIMAGES"));
            String type = res.getString(res.getColumnIndex("TYPE"));
            String subcat = res.getString(res.getColumnIndex("SUBCAT"));
            String meta = res.getString(res.getColumnIndex("META"));
            String rating = res.getString(res.getColumnIndex("RATING"));



            Log.d("sizes_avail", ""+res.getString(res.getColumnIndex("SIZEAVAIL")));
            Log.d("des",""+des);

            map.put("uid",uid);
            map.put("path",path);
         //   map.put("own",own);
            map.put("des",des);
            map.put("artuid",artuid);
            map.put("title",title);
            map.put("category", category);
            map.put("quantity", quantity);
            map.put("price", price);
            map.put("noimages",noimages);
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("meta",meta);
            map.put("craft",craft);
            map.put("rating",rating);
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));

            data.add(map);

            res.moveToNext();
        }
        return data;
    }






    public HashMap<String,String> GetProMap(String prouid, String quantity1, String size )
    {

        HashMap<String,String> map = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where UID = '"+prouid+"'", null);
        res.moveToFirst();

        while (res.isFirst())
        {
            String price;
            if (Integer.parseInt(quantity1)>Integer.parseInt(res.getString(res.getColumnIndex("REVQUANTITY"))))
            {
                 price = res.getString(res.getColumnIndex("REVPRICE"));
            }
            else
            {
                 price = res.getString(res.getColumnIndex("PRICE"));
            }

            String path = res.getString(res.getColumnIndex("PATH"));

            String uid = res.getString(res.getColumnIndex("UID"));
            String own = res.getString(res.getColumnIndex("OWNER"));
            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String category = res.getString(res.getColumnIndex("CATEGORY"));
            String quantity = quantity1;
            String noimages = res.getString(res.getColumnIndex("NOIMAGES"));
            String type = res.getString(res.getColumnIndex("TYPE"));
            String subcat = res.getString(res.getColumnIndex("SUBCAT"));
            String meta = res.getString(res.getColumnIndex("META"));
            Log.d("uid", ""+uid);
            Log.d("quantity",""+quantity);

            map.put("uid",uid);
            map.put("path",path);
            map.put("own",own);
            map.put("des",des);
            map.put("title",title);
            map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
            map.put("category", category);
            map.put("quantity", quantity);
            map.put("price", price);
            map.put("noimages",noimages);
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("meta",meta);
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("size",size);
            map.put("color",res.getString(res.getColumnIndex("COLOR")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));

            res.moveToNext();

        }
        return map;
    }



    public ArrayList<HashMap<String,String>> GetSubCategoryImageData(HashMap<String,String> selected) {


        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("Sent Data",selected.toString());
      //  db.query("sku_table", columns, "owner=? and price=?", new String[] { owner, price }, null, null, null);

       // String[]  col = {"PATH,UID,DES,TITLE,PRICE,QUANTITY,NOIMAGES,TYPE,SUBCAT,META,OWNER,CRAFT"};
        //Cursor res = db.query("ImageData",col,"CATEGORY =? and SUBCAT =?", new String[] { selected.get("category"), selected.get("subcat") }, null, null, null);
        Cursor res1 = db.rawQuery("select * from ImageData where CATEGORY like '"+selected.get("category")+"' and SUBCAT like '"+selected.get("subcat")+"';", null);
        Log.d("category size", ""+res1.getCount());
        res1.moveToFirst();
        while (!res1.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            String path = res1.getString(res1.getColumnIndex("PATH"));
            String uid = res1.getString(res1.getColumnIndex("UID"));
            String des = res1.getString(res1.getColumnIndex("DES"));
            String title = res1.getString(res1.getColumnIndex("TITLE"));
            String price = res1.getString(res1.getColumnIndex("PRICE"));
            String quantity = res1.getString(res1.getColumnIndex("QUANTITY"));
            int nopic = res1.getInt(res1.getColumnIndex("NOIMAGES"));
            String type = res1.getString(res1.getColumnIndex("TYPE"));
            String owner = res1.getString(res1.getColumnIndex("OWNER"));
            String revquantity = res1.getString(res1.getColumnIndex("REVQUANTITY"));
            String revprice = res1.getString(res1.getColumnIndex("REVPRICE"));

            String rating = res1.getString(res1.getColumnIndex("RATING"));
            Log.d("artist uid dbhelper",""+owner);

            Log.d("category", selected.get("category"));
            String subcat = res1.getString(res1.getColumnIndex("SUBCAT"));
            String meta = res1.getString(res1.getColumnIndex("META"));
            map.put("uid",uid);
            map.put("path",path);
            map.put("des",des);
            map.put("title", title);
            map.put("price",price);
            map.put("quantity",quantity);
            map.put("noimages", String.valueOf(nopic));
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("craft",res1.getString(res1.getColumnIndex("CRAFT")));
            map.put("artuid",owner);
            map.put("meta",meta);
            map.put("rating",rating);
            map.put("protype",res1.getString(res1.getColumnIndex("PROTYPE")));
            map.put("revquantity",revquantity);
            map.put("revprice",revprice);
            map.put("stock",res1.getString(res1.getColumnIndex("STOCK")));
            map.put("sizeavail",res1.getString(res1.getColumnIndex("SIZEAVAIL")));
            map.put("stock",res1.getString(res1.getColumnIndex("STOCK")));
            data.add(map);
            res1.moveToNext();
        }

        return data;

    }


    public ArrayList<HashMap<String,String>> GetSimilarProducts(String title, String protype, String craft, String uid)
    {
        ArrayList<HashMap<String ,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where TITLE LIKE '%"+title+"%' or PROTYPE like '%"+protype+"%' or CRAFT like '%"+craft+"%' limit 10;", null);

        res.moveToFirst();
        Log.d("similar products size",""+res.getCount());
        while(!res.isAfterLast())
        {

            if((!res.getString(res.getColumnIndex("UID")).equals(uid))&&(res.getString(res.getColumnIndex("PRICE"))!="1")) {


                HashMap<String, String> map = new HashMap<String, String>();
                map.put("uid", res.getString(res.getColumnIndex("UID")));
                map.put("path", res.getString(res.getColumnIndex("PATH")));
                map.put("artuid", res.getString(res.getColumnIndex("OWNER")));
                map.put("des", res.getString(res.getColumnIndex("DES")));
                map.put("title", res.getString(res.getColumnIndex("TITLE")));
                map.put("category", res.getString(res.getColumnIndex("CATEGORY")));
                map.put("quantity", res.getString(res.getColumnIndex("QUANTITY")));
                map.put("price", res.getString(res.getColumnIndex("PRICE")));
                map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
                map.put("protype", res.getString(res.getColumnIndex("PROTYPE")));
                map.put("subcat", res.getString(res.getColumnIndex("SUBCAT")));
                map.put("meta", res.getString(res.getColumnIndex("META")));
                map.put("craft", res.getString(res.getColumnIndex("CRAFT")));
                map.put("rating", res.getString(res.getColumnIndex("RATING")));
                map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
                map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
                map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
                map.put("size",res.getString(res.getColumnIndex("SIZE")));
                map.put("stock",res.getString(res.getColumnIndex("STOCK")));

                data.add(map);

            }

            res.moveToNext();


        }



        return data;
    }


    public  ArrayList<HashMap<String,String>> GetArtistProducts(String artuid)
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where OWNER = '"+artuid+"';",null);
        res.moveToFirst();
        Log.d("artpro size",""+res.getCount());
        while (!res.isAfterLast())
        {

            HashMap<String,String> map = new HashMap<String, String>();
            map.put("uid", res.getString(res.getColumnIndex("UID")));
            map.put("path", res.getString(res.getColumnIndex("PATH")));
            map.put("artuid", res.getString(res.getColumnIndex("OWNER")));
            map.put("des", res.getString(res.getColumnIndex("DES")));
            map.put("title", res.getString(res.getColumnIndex("TITLE")));
            map.put("category", res.getString(res.getColumnIndex("CATEGORY")));
            map.put("quantity", res.getString(res.getColumnIndex("QUANTITY")));
            map.put("price", res.getString(res.getColumnIndex("PRICE")));
            map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
            map.put("protype", res.getString(res.getColumnIndex("PROTYPE")));
            map.put("subcat", res.getString(res.getColumnIndex("SUBCAT")));
            map.put("meta", res.getString(res.getColumnIndex("META")));
            map.put("craft", res.getString(res.getColumnIndex("CRAFT")));
            map.put("rating", res.getString(res.getColumnIndex("RATING")));

            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));

            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            map.put("size",res.getString(res.getColumnIndex("SIZE")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));
            data.add(map);
            res.moveToNext();
        }

        return data;
    }


    public ArrayList<HashMap<String,String>> Get_Search_Tags()
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from SearchData group by TAG ;",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex("TAG")));
            map.put("uid",res.getString(res.getColumnIndex("UID")));
            map.put("type",res.getString(res.getColumnIndex("TYPE")));
            map.put("suggest",res.getString(res.getColumnIndex("SUGGEST")));
            data.add(map);
            res.moveToNext();
        }

        Log.d("search_tag",""+data.size());


        return  data;
    }







    public ArrayList<HashMap<String,String>> GetProductsFromSearch( String Uid)

    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();



        Cursor res1 = db.rawQuery("select * from SearchData where UID = '"+Uid+"';",null);
        res1.moveToFirst();

        String tag = res1.getString(res1.getColumnIndex("TAG"));
        String suggest = res1.getString(res1.getColumnIndex("TYPE"));

        //  Log.d("suggest new ", suggest);
        //Log.d("suggest uid",Uid);
        Log.d("suggest_tag",tag);
        Log.d("suggest_type",suggest);





        Cursor res = db.rawQuery("select * from ImageData where "+suggest+" = '"+tag+"' ;",null);
        Log.d("return cursor size", ""+res.getCount());

        res.moveToFirst();
        while (!res.isAfterLast()){


            HashMap<String, String> map = new HashMap<String, String>();
            String path = res.getString(res.getColumnIndex("PATH"));
            String uid = res.getString(res.getColumnIndex("UID"));
            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String price = res.getString(res.getColumnIndex("PRICE"));
            String quantity = res.getString(res.getColumnIndex("QUANTITY"));
            int nopic = res.getInt(res.getColumnIndex("NOIMAGES"));
            String type = res.getString(res.getColumnIndex("TYPE"));
            String craft = res.getString(res.getColumnIndex("CRAFT"));
            String owner = res.getString(res.getColumnIndex("OWNER"));

            String rating = res.getString(res.getColumnIndex("RATING"));
            Log.d("craft",""+owner);

            //Log.d("category", selected.get("category"));
            String subcat = res.getString(res.getColumnIndex("SUBCAT"));
            String meta = res.getString(res.getColumnIndex("META"));
            map.put("uid",uid);
            map.put("path",path);
            map.put("des",des);
            map.put("craft",craft);
            map.put("title", title);
            map.put("price",price);
            map.put("quantity",quantity);
            map.put("noimages", String.valueOf(nopic));
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("artuid",owner);
            map.put("meta",meta);
            map.put("rating",rating);
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));
            data.add(map);
            res.moveToNext();


        }




        return data;
    }

    public ArrayList<HashMap<String,String>> GetProductsFromSearch_test( String col, String value)

    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        //  Log.d("suggest new ", suggest);
        //Log.d("suggest uid",Uid);
        Log.d("suggest_tag",value);
        Log.d("suggest_type",col);




        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ImageData where "+col+" = '"+value+"' ;",null);
        Log.d("return cursor size", ""+res.getCount());

        res.moveToFirst();
        while (!res.isAfterLast()){


            HashMap<String, String> map = new HashMap<String, String>();
            String path = res.getString(res.getColumnIndex("PATH"));
            String uid = res.getString(res.getColumnIndex("UID"));
            String des = res.getString(res.getColumnIndex("DES"));
            String title = res.getString(res.getColumnIndex("TITLE"));
            String price = res.getString(res.getColumnIndex("PRICE"));
            String quantity = res.getString(res.getColumnIndex("QUANTITY"));
            int nopic = res.getInt(res.getColumnIndex("NOIMAGES"));
            String type = res.getString(res.getColumnIndex("TYPE"));
            String craft = res.getString(res.getColumnIndex("CRAFT"));
            String owner = res.getString(res.getColumnIndex("OWNER"));

            String rating = res.getString(res.getColumnIndex("RATING"));
            Log.d("craft",""+owner);

            //Log.d("category", selected.get("category"));
            String subcat = res.getString(res.getColumnIndex("SUBCAT"));
            String meta = res.getString(res.getColumnIndex("META"));
            map.put("uid",uid);
            map.put("path",path);
            map.put("des",des);
            map.put("craft",craft);
            map.put("title", title);
            map.put("price",price);
            map.put("quantity",quantity);
            map.put("noimages", String.valueOf(nopic));
            map.put("type",type);
            map.put("subcat",subcat);
            map.put("artuid",owner);
            map.put("meta",meta);
            map.put("rating",rating);
            map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
            map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));
            map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
            map.put("stock",res.getString(res.getColumnIndex("STOCK")));
            data.add(map);
            res.moveToNext();


        }




        return data;
    }

    public ArrayList<HashMap<String,String>> GetSizes_Random(String col, String value)
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        ArrayList<String> sizes = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from ImageData where "+col+" like '"+value+"'  group by "+Filter_Struct.sizefil,null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            ArrayList<String> sizelist = new ArrayList<String>();
            String aux_sizes = res.getString(res.getColumnIndex(Filter_Struct.sizefil));
            sizelist.addAll(Arrays.asList(aux_sizes.split(",")));
            Log.d("inside size", res.getString(res.getColumnIndex(Filter_Struct.sizefil))+" , "+sizelist.size());
            sizes.addAll(sizelist);
            res.moveToNext();
        }
        Log.d("size_before_clean",""+sizes.size());
        HashSet<String> cleansizes = new HashSet<String>();
        cleansizes.addAll(sizes);
        sizes.clear();

        sizes.addAll(cleansizes);
        Log.d("size_after_clean",""+sizes.size());
        for(int i=0;i<sizes.size();i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",sizes.get(i));
            map.put("status","false");
            map.put("col","SIZE");
            Log.d("tag_clean_size",sizes.get(i));
            list.add(map);
        }
        return list;
    }

    public ArrayList<HashMap<String,String>> GetColor_Random(String col, String value)
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from ImageData where "+col+" like '"+value+"' group by COLOR",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex(Filter_Struct.colorfil)));
            map.put("status","false");
            map.put("col","COLOR");
            Log.d("inside color", res.getString(res.getColumnIndex(Filter_Struct.colorfil)));
            list.add(map);
            res.moveToNext();
        }

        return list;

    }


    public  ArrayList<HashMap<String,String>> GetProType_Random(String col, String value)
    {


        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from ImageData where "+col+" like '"+value+"'  group by PROTYPE",null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("tag",res.getString(res.getColumnIndex("PROTYPE")));
            map.put("status","false");
            map.put("col","PROTYPE");
            Log.d("inside color", res.getString(res.getColumnIndex(Filter_Struct.producttype)));
            list.add(map);
            res.moveToNext();
        }

        return list;


    }

    public ArrayList<HashMap<String,String>> Get_Product_All_Test_Filter(ArrayList<HashMap<String,String>> data, HashMap<String,String> selection)
    {
        HashSet<String> uids = new HashSet<String>();
        ArrayList<HashMap<String,String>> Product_Color_List = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("check_forall_filtersize",""+data.size());
        for(int i=0;i<data.size();i++)
        {
            Cursor res = db.rawQuery("select * from ImageData where "+selection.get("col")+" like '"+selection.get("value")+"'  and "+data.get(i).get("col")+" like '%"+data.get(i).get("tag")+"%'",null);
            Log.d("query_sql","select * from ImageData where "+selection.get("col")+" like '"+selection.get("value")+"'  and "+data.get(i).get("col")+" like '%"+data.get(i).get("tag")+"%'") ;
            Log.d("used","get_product_all_test");
            res.moveToFirst();
            while (!res.isAfterLast())
            {

                Log.d("inside_get_all",""+data.get(i));
                if(!uids.contains(res.getString(res.getColumnIndex("UID"))))
                {
                    HashMap<String,String> map = new HashMap<String, String>();

                    map.put("uid",res.getString(res.getColumnIndex("UID")));
                    map.put("path",res.getString(res.getColumnIndex("PATH")));
                    map.put("des",res.getString(res.getColumnIndex("DES")));
                    map.put("title", res.getString(res.getColumnIndex("TITLE")));
                    map.put("price",res.getString(res.getColumnIndex("PRICE")));
                    map.put("quantity",res.getString(res.getColumnIndex("QUANTITY")));
                    map.put("noimages", res.getString(res.getColumnIndex("NOIMAGES")));
                    map.put("type",res.getString(res.getColumnIndex("TYPE")));
                    map.put("cat",res.getString(res.getColumnIndex("CATEGORY")));
                    map.put("subcat",res.getString(res.getColumnIndex("SUBCAT")));
                    map.put("craft",res.getString(res.getColumnIndex("CRAFT")));
                    map.put("artuid",res.getString(res.getColumnIndex("OWNER")));
                    map.put("meta",res.getString(res.getColumnIndex("META")));
                    map.put("rating",res.getString(res.getColumnIndex("RATING")));
                    map.put("protype",res.getString(res.getColumnIndex("PROTYPE")));
                    map.put("revquantity",res.getString(res.getColumnIndex("REVQUANTITY")));
                    map.put("revprice",res.getString(res.getColumnIndex("REVPRICE")));

                    map.put("sizeavail",res.getString(res.getColumnIndex("SIZEAVAIL")));
                    map.put("stock",res.getString(res.getColumnIndex("STOCK")));
                    Product_Color_List.add(map);


                }
                Log.d("color_pro_title",res.getString(res.getColumnIndex("TITLE")));

                uids.add(res.getString(res.getColumnIndex("UID")));
                res.moveToNext();
            }

        }

        return  Product_Color_List;
    }



    public Cursor getImageData(String[] sel){


        sel[0] = "%"+sel[0]+"%";

        SQLiteDatabase db = this.getReadableDatabase();

        mAliasMap = new HashMap<String, String>();

        // Unique id for the each Suggestions ( Mandatory )
        mAliasMap.put("_ID", "UID" + " as " + "_id" );

        // Text for Suggestions ( Mandatory )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, "TAG" + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);

        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_2, "SUGGEST" + " as " + SearchManager.SUGGEST_COLUMN_TEXT_2);


        // Icon for Suggestions ( Optional )
       // mAliasMap.put( SearchManager.SUGGEST_COLUMN_ICON_1, "CATEGORY" + " as " + SearchManager.SUGGEST_COLUMN_ICON_1);

        // This value will be appended to the Intent data on selecting an item from Search result or Suggestions ( Optional )
        mAliasMap.put( SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "UID" + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID );

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setProjectionMap(mAliasMap);

        queryBuilder.setTables("SearchData");

        Cursor c = queryBuilder.query(this.getReadableDatabase(),
                new String[] { "_ID",

                        SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID } ,
                " TAG LIKE ?",
                sel,
                "TAG",
                null,
                "TAG"+ " asc ",null
        );

        c.moveToFirst();



        Log.d("cursize suggest",""+c.getCount());

        return c;


    }


    public  ArrayList<HashMap<String,String>> getdatafromquery(String query){


        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from ImageData where SIZE IS NOT NULL AND SIZE !='' and (TITLE LIKE '%"+query+"%' or CRAFT LIKE '%"+query+"%' or PROTYPE LIKE '%"+query+"%') LIMIT 30 ;",null);
        Log.d("return cursor size", ""+res.getCount());

        res.moveToFirst();
        while (!res.isAfterLast())

        {

                HashMap<String, String> map = new HashMap<String, String>();
                String path = res.getString(res.getColumnIndex("PATH"));
                String uid = res.getString(res.getColumnIndex("UID"));
                String des = res.getString(res.getColumnIndex("DES"));
                String title = res.getString(res.getColumnIndex("TITLE"));
                String price = res.getString(res.getColumnIndex("PRICE"));
                String quantity = res.getString(res.getColumnIndex("QUANTITY"));
                int nopic = res.getInt(res.getColumnIndex("NOIMAGES"));
                String type = res.getString(res.getColumnIndex("TYPE"));
                String craft = res.getString(res.getColumnIndex("CRAFT"));
                String owner = res.getString(res.getColumnIndex("OWNER"));

                String rating = res.getString(res.getColumnIndex("RATING"));
                Log.d("artist uid dbhelper", "" + owner);

                //Log.d("category", selected.get("category"));
                String subcat = res.getString(res.getColumnIndex("SUBCAT"));
                String meta = res.getString(res.getColumnIndex("META"));
                map.put("uid", uid);
                map.put("craft", craft);
                map.put("path", path);
                map.put("des", des);
                map.put("title", title);
                map.put("price", price);
                map.put("quantity", quantity);
                map.put("noimages", String.valueOf(nopic));
                map.put("type", type);
                map.put("subcat", subcat);
                map.put("artuid", owner);
                map.put("meta", meta);
                map.put("rating", rating);
                map.put("protype", res.getString(res.getColumnIndex("PROTYPE")));
                map.put("revprice", res.getString(res.getColumnIndex("REVPRICE")));
                map.put("revquantity", res.getString(res.getColumnIndex("REVQUANTITY")));

                map.put("sizeavail", res.getString(res.getColumnIndex("SIZEAVAIL")));
                map.put("stock", res.getString(res.getColumnIndex("STOCK")));

                data.add(map);
                res.moveToNext();



        }




        return data;




    }


    public  boolean IsProductUnique(  String Value) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from CART  where PROUID = '"+Value+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean RemoveFromCart(String prouid)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from CART where PROUID = '"+prouid+"'");



        return  true;
    }







}
