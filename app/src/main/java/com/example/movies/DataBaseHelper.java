package com.example.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movies.Network.Model.Movie.MovieResponse;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static final String DATABASE_NAME = "FavoriteDB";
    private static final String TABLE_NAME = "favoriteTable";
    public static  final String KEY_ID = "id";
    public static  final String ITEM_TITLE = "itemTitle";
    public static  final String ITEM_IMAGE = "itemImage";
    public static  final String ITEM_DATE = "itemData";
    public static  final String ITEM_DESCRIPTION = "intemDescription";

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " TEXT," + ITEM_TITLE+ " TEXT,"
                + ITEM_IMAGE + " TEXT," + ITEM_DATE + " TEXT," + ITEM_DESCRIPTION + " TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    void addFavorite(MovieResponse favorite)
    {
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("KEY_ID",favorite.getId());
        values.put("ITEM_TITLE",favorite.getTitle());
        values.put("ITEM_IMAGE",favorite.getPosterPath());
        values.put("ITEM_DATE",favorite.getReleaseDate());
        values.put("ITEM_DESCRIPTION",favorite.getOverview());

        db.insert(TABLE_NAME,null,values);
    }

    public List<FavItem> getAllFavorites()
    {
        List<FavItem> favoritesList = new ArrayList<>();

        String selectQuery= "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                FavItem favItem= new FavItem();
                favItem.setKey_id(cursor.getString(0));
                favItem.setItem_title(cursor.getString(1));
                favItem.setItem_image(cursor.getString(2));
                favItem.setItem_date(cursor.getString(3));
                favItem.setItem_date(cursor.getString(4));

                favoritesList.add(favItem);

            }while (cursor.moveToNext());
        }
        return favoritesList;
    }



    void removeFavorite(FavItem favorite)
    {
        SQLiteDatabase db= this.getWritableDatabase();

       db.delete(TABLE_NAME,KEY_ID + "=?",
               new String[]{String.valueOf(favorite.getKey_id())});
               db.close();
    }


}
