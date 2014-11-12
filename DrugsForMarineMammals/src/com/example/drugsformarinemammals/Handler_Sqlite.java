package com.example.drugsformarinemammals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class Handler_Sqlite extends SQLiteOpenHelper{

	private static final String nameBD = "DrugsForMarineMammals-DataBase";

	Context myContext;
	public Handler_Sqlite(Context ctx){
		super(ctx,nameBD, null,1);
		myContext = ctx;
	}
	
	public SQLiteDatabase open(){
		//Create and/or open a database that will be used for reading and writing.
		return this.getWritableDatabase();
	}
	
	@Override
	//This method is called when the database is created for the first time.
	public void onCreate(SQLiteDatabase db){
		String query1 = "CREATE TABLE Drug (drug_name TEXT, description TEXT, available INTEGER, license_AEMPS TEXT, license_EMA TEXT, license_FDA TEXT, PRIMARY KEY (drug_name))";

		String query2 = "CREATE TABLE Code(code_number TEXT, anatomic_group TEXT, therapeutic_group TEXT, drug_name TEXT, FOREIGN KEY (drug_name) REFERENCES Drug(drug_name), PRIMARY KEY (code_number))";
		
		String query3 = "CREATE TABLE Animal_Type (group_name TEXT, PRIMARY KEY (group_name))";
		
		String query4 = "CREATE TABLE Drug_aplicated_to_Animal_Type (drug_name TEXT, group_name TEXT, general_note TEXT, FOREIGN KEY (drug_name) REFERENCES Drug(drug_name)," +
					"FOREIGN KEY (group_name) REFERENCES Animal_Type(group_name), PRIMARY KEY (drug_name, group_name, general_note))";
		
		String query5 = "CREATE TABLE Animal (animal_name TEXT, family TEXT, group_name TEXT, drug_name TEXT, FOREIGN KEY (drug_name) REFERENCES Drug(drug_name), " +
					"FOREIGN KEY (group_name) REFERENCES Animal_Type(group_name), PRIMARY KEY (animal_name, family, group_name, drug_name))";
		
		String query6 = "CREATE TABLE Category (category_name TEXT, PRIMARY KEY (category_name))";

		String query7 = "CREATE TABLE Animal_has_Category (animal_name TEXT, family TEXT, group_name TEXT, drug_name TEXT, category_name TEXT," +
					"reference TEXT, specific_note TEXT, posology TEXT, route TEXT, dose TEXT, FOREIGN KEY (drug_name) REFERENCES Animal(drug_name), FOREIGN KEY (group_name) REFERENCES Animal(group_name)," +
					"FOREIGN KEY (animal_name) REFERENCES Animal(animal_name), FOREIGN KEY (family) REFERENCES Animal(family), FOREIGN KEY (category_name) REFERENCES Category(category_name), PRIMARY KEY(animal_name, family, group_name, drug_name, category_name," +
					"reference, specific_note, posology, route))";

		db.execSQL(query1);	
		db.execSQL(query2);
		db.execSQL(query3);
		db.execSQL(query4);
		db.execSQL(query5);
		db.execSQL(query6);
		db.execSQL(query7);
		
		 InputStream is = null;
		    try {
		         is = myContext.getAssets().open("BBDD_DrugsForMarineMammals.sql");
		         if (is != null) {
		             db.beginTransaction();
		             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		             String line = reader.readLine();
		             while (!TextUtils.isEmpty(line)) {
		                 db.execSQL(line);
		                 line = reader.readLine();
		             }
		             db.setTransactionSuccessful();
		         }
		    } catch (Exception ex) {
		        // Muestra log             
		    } finally {
		        db.endTransaction();
		        if (is != null) {
		            try {
		                is.close();
		            } catch (IOException e) {
		                // Muestra log
		            }                
		        }
		    }			
	}

	@Override
	//This methos is called when the database needs to be upgraded.
	public void onUpgrade(SQLiteDatabase db,int old_version, int new_version){
		db.execSQL("DROP TABLE IF EXISTS Drug");
		db.execSQL("DROP TABLE IF EXISTS Code");
		db.execSQL("DROP TABLE IF EXISTS Animal_Type");
		db.execSQL("DROP TABLE IF EXISTS Drug_aplicated_to_Animal_Type");
		db.execSQL("DROP TABLE IF EXISTS Animal");
		db.execSQL("DROP TABLE IF EXISTS Category");
		db.execSQL("DROP TABLE IF EXISTS Animal_has_Category");
		onCreate(db);
	}
	
	public ArrayList<String> read_drugs_name() {
		
		ArrayList<String> result = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c=db.query("Drug", null, null, null, null, null, null);
		int idName;
		idName = c.getColumnIndex("drug_name");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result.add(c.getString(idName));
		}
		
		return result;
	}
}
