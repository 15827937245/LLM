package com.example.save;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private String mTable;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITY = "com.example.save.MyContentProvider";  //授权
    public static final Uri PERSON_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/mytable");
    private static final int TABLE_CODE_PERSON = 2;

    static {
        //关联不同的 URI 和 code，便于后续 getType
        mUriMatcher.addURI(AUTHORITY, "mytable", TABLE_CODE_PERSON);

       // mUriMatcher.match(PERSON_CONTENT_URI);
    }
    @Override
    public boolean onCreate() {
        initData();
        return false;
    }

    private void initData() {
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        return mDatabase.query(tableName,projection,selection,selectionArgs,null,sortOrder,null);
    }

    private String getTableName(Uri uri) {
        String tableName = "";
        int match = mUriMatcher.match(uri);
        switch (match){
            case TABLE_CODE_PERSON:
                tableName = "mytable";
        }
        //showLog("UriMatcher " + uri.toString() + ", result: " + match);
        return tableName;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        mDatabase.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int deleteCount = mDatabase.delete(tableName, selection, selectionArgs);
        if (deleteCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int updateCount = mDatabase.update(tableName, values, selection, selectionArgs);
        if (updateCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }
}
