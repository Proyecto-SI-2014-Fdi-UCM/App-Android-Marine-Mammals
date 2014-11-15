package com.example.drugsformarinemammals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class Handler_Sqlite extends SQLiteOpenHelper{

	private static final String nameBD = "DrugsForMarineMammals-DataBase";

	Context myContext;
	public Handler_Sqlite(Context ctx){
		super(ctx,nameBD, null,4);
		myContext = ctx;
	}
	
	public SQLiteDatabase open(){
		//Create and/or open a database that will be used for reading and writing.
		return this.getWritableDatabase();
	}
	
	@Override
	//This method is called when the database is created for the first time.
	public void onCreate(SQLiteDatabase db){
		String query1 = "CREATE TABLE Drug (drug_name TEXT, description TEXT, available INTEGER, license_AEMPS TEXT, license_EMA TEXT, license_FDA TEXT, priority INTEGER, PRIMARY KEY (drug_name))";

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

	public ArrayList<String> combinedSearch(String[] p) {
		if (!p[0].equals("Choose an Anatomical Target") && !p[1].equals("Choose a Therapeutic Target")) {
			String where = "anatomic_group=? and therapeutic_group=?";
			String args[]= {p[0], p[1]};
			return searchTables(p, where, args);
		}
		else if (!p[0].equals("Choose an Anatomical Target")) {
			String where = "anatomic_group=?";
			String args[]= {p[0]};
			return searchTables(p, where, args);

		}
		else if (!p[1].equals("Choose a Therapeutic Target")) {
			String where = "therapeutic_group=?";
			String args[]= {p[1]};
			return searchTables(p, where, args);
		}
		else if (!p[2].equals("Choose a Group")) {
			String args[]= {p[2]};
			return searchTDrugAplicatedAnimalType(args);
		}
		return null;
	}

	private ArrayList<String> searchTDrugAplicatedAnimalType(String[] a) {
		SQLiteDatabase db = open();
		ArrayList<String> drugsList = new ArrayList<String>();
		String drug;
		String column[] = {"drug_name"};
		Cursor cc = db.query("Drug_aplicated_to_Animal_Type", column, "group_name=?", a, null, null, null);
		if (cc.moveToFirst())
			for (cc.moveToFirst();!cc.isAfterLast();cc.moveToNext()) {
				drug = cc.getString(cc.getColumnIndex("drug_name"));
				if (!drugsList.contains(drug))
					drugsList.add(drug);
			}
		close();
		return drugsList;
	}

	private ArrayList<String> searchTables(String[] p, String w, String[] a) {
		SQLiteDatabase db = open();
		ArrayList<String> drugsList = new ArrayList<String>();
		String column[] = {"drug_name"};
		Cursor cc = db.query("Code", column, w, a, null, null, null);
		if (cc.moveToFirst())
			for (cc.moveToFirst();!cc.isAfterLast();cc.moveToNext()) {
				String drug = cc.getString(cc.getColumnIndex("drug_name"));
				if (!drugsList.contains(drug))
					if (!p[2].equals("Choose a Group")) {
						String args[] = { drug, p[2] };
						Cursor cs = db.query("Drug_aplicated_to_Animal_Type",
								column, "drug_name=? and group_name=?", args, null, null, null);
						if (cs.moveToFirst())
							for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext())
								if (!drugsList.contains(drug)) 
									drugsList.add(cs.getString(cs.getColumnIndex("drug_name")));
					} else
						drugsList.add(drug);
			}
		close();
		return drugsList;		
	}
	
	public List<Drug_Information> read_drugs_database() {
		
		List<Drug_Information> result = new ArrayList<Drug_Information>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c=db.query("Drug", null, null, null, null, null, null);
		int idName, idDescription, idAvailable, idLicense_AEMPS, idLicense_EMA, idLicense_FDA, idPriority;
		idName = c.getColumnIndex("drug_name");
		idDescription = c.getColumnIndex("description");
		idAvailable = c.getColumnIndex("available");
		idLicense_AEMPS = c.getColumnIndex("license_AEMPS");
		idLicense_EMA = c.getColumnIndex("license_EMA");
		idLicense_FDA = c.getColumnIndex("license_FDA");
		idPriority = c.getColumnIndex("priority");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result.add(new Drug_Information(c.getString(idName), c.getString(idDescription), c.getInt(idAvailable), c.getString(idLicense_AEMPS),
					c.getString(idLicense_EMA), c.getString(idLicense_FDA), c.getInt(idPriority)));
		}
		
		return result;
	}
	
	public boolean existDrug(String drug_name) {
		
		String args [] = {drug_name};
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c=db.query("Drug", null, "drug_name=?", args, null, null, null);
		return c.moveToFirst();
		
	}
	
	public String getDrugName(Integer priority) {
		
		String args [] = {priority.toString()};
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c=db.query("Drug", null, "priority=?", args, null, null, null);
		int idName;
		idName = c.getColumnIndex("drug_name");
		c.moveToFirst();
		return c.getString(idName);
		
	}
	
	public int getDrugPriority(String drug_name) {
		
		String args [] = {drug_name};
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c=db.query("Drug", null, "drug_name=?", args, null, null, null);
		int idPriority;
		idPriority = c.getColumnIndex("priority");
		c.moveToFirst();
		return c.getInt(idPriority);
		
	}
	
	public void setDrugPriority(String drug_name, Integer number) {
		
		String args [] = {drug_name};
		ContentValues tmp = new ContentValues();
		tmp.put("priority", number);
		this.getWritableDatabase().update("Drug", tmp, "drug_name=?", args);
		
	}

}
