package com.example.drugsformarinemammals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class Handler_Sqlite extends SQLiteOpenHelper{

	private static final String nameBD = "DrugsForMarineMammals-DataBase1";

	Context myContext;
	public Handler_Sqlite(Context ctx){
		super(ctx,nameBD, null,2);
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
		String query8 = "CREATE TABLE Therapeutic_Group (name TEXT, PRIMARY KEY (name))";

		db.execSQL(query1);
		db.execSQL(query2);
		db.execSQL(query3);
		db.execSQL(query4);
		db.execSQL(query5);
		db.execSQL(query6);
		db.execSQL(query7);
		db.execSQL(query8);
		
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
		db.execSQL("DROP TABLE IF EXISTS Therapeutic_Group");
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
	
	//General Info Drug
	public String getDescription(String drug_name){
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"description"};
		//Cursor c=this.getReadableDatabase().query("productos", columnas, null, null,null, null,null);
		Cursor c=db.query("Drug", columns, "drug_name=?", args, null, null, null);
		int indexDescription=c.getColumnIndex("description");
		c.moveToFirst();
		return c.getString(indexDescription);
		
	}

	public boolean isAvalaible(String drug_name) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"available"};
		Cursor c=db.query("Drug", columns, "drug_name=?", args, null, null, null);
		int indexAvalaible=c.getColumnIndex("available");
		c.moveToFirst();
		return c.getInt(indexAvalaible)==1;
		
	}
	
	public ArrayList<String> getCodes(String drug_name){
		ArrayList<String> solution= new ArrayList<String>();
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"code_number"};
		Cursor c=db.query("Code", columns, "drug_name=?", args, null, null, null);
		int indexCode=c.getColumnIndex("code_number");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			solution.add((c.getString(indexCode)));
		}
		return solution;
	}

	public String getAnatomicTarget(String drug_name, String code) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name,code};
		String columns[]={"anatomic_group"};
		Cursor c=db.query("Code", columns, "drug_name=? and code_number=?", args, null, null, null);
		int indexAnatomic=c.getColumnIndex("anatomic_group");
		c.moveToFirst();
		return c.getString(indexAnatomic);
	}
	
	public String getTherapeuticTarget(String drug_name, String code) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name,code};
		String columns[]={"therapeutic_group"};
		Cursor c=db.query("Code", columns, "drug_name=? and code_number=?", args, null, null, null);
		int indexTherapeutic=c.getColumnIndex("therapeutic_group");
		c.moveToFirst();
		return c.getString(indexTherapeutic);
	}
	
	public String getLicense_AEMPS(String drug_name){
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"license_AEMPS"};
		Cursor c=db.query("Drug", columns, "drug_name=?", args, null, null, null);
		int indexLicense_AEMPS=c.getColumnIndex("license_AEMPS");
		c.moveToFirst();
		return c.getString(indexLicense_AEMPS);
	}
	
	public String getLicense_EMA(String drug_name){
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"license_EMA"};
		Cursor c=db.query("Drug", columns, "drug_name=?", args, null, null, null);
		int indexLicense_EMA=c.getColumnIndex("license_EMA");
		c.moveToFirst();
		return c.getString(indexLicense_EMA);
	}
	
	public String getLicense_FDA(String drug_name){
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"license_FDA"};
		Cursor c=db.query("Drug", columns, "drug_name=?", args, null, null, null);
		int indexLicense_FDA=c.getColumnIndex("license_FDA");
		c.moveToFirst();
		return c.getString(indexLicense_FDA);
	}
	
	public ArrayList<String> getAllTherapeuticGroup(){
		ArrayList<String> solution=new ArrayList<String>();
		SQLiteDatabase db=this.getReadableDatabase();
		String columns[]={"name"};
		Cursor c=db.query("Therapeutic_Group", columns, null, null, null, null, null);
		int indexName=c.getColumnIndex("name");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			solution.add((c.getString(indexName)));
		}
		return solution;
		
	}

	public void deleteTherapeuticGroup(String name){
		
		SQLiteDatabase db = getWritableDatabase();
	    db.delete("Therapeutic_Group", "name="+"'"+name+"'", null);
	    
		
	}
	
	public void insertTherapeuticGroup(String name){
		ContentValues registro=new ContentValues();
		registro.put("name", name);
		this.getWritableDatabase().insert("Therapeutic_Group", null, registro);
	}
	
	public ArrayList<String> read_animals_family(String drug_name, String group_name) {
		
		ArrayList<String> result = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String args [] = {drug_name, group_name};
		Cursor c=db.query("Animal", null, "drug_name=? and group_name=?", args, null, null, null);
		int idFamily;
		idFamily = c.getColumnIndex("family");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			if (!result.contains(c.getString(idFamily)))
				result.add(c.getString(idFamily));
			
		}
		
		return result;
	}
	
	public HashMap<String, ArrayList<String>> read_animal_information(String drug_name, String group_name, String family) {
		
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		SQLiteDatabase db = this.getReadableDatabase();
		String args [] = {drug_name, group_name, family};
		Cursor c=db.query("Animal", null, "drug_name=? and group_name=? and family=?", args, null, null, null);
		int idName;
		idName = c.getColumnIndex("animal_name");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			if (!result.containsKey(c.getString(idName))) {
				ArrayList<String> categories = new ArrayList<String>();
				String args1 [] = {drug_name, group_name, family, c.getString(idName)};
				Cursor d=db.query("Animal_has_Category", null, "drug_name=? and group_name=? and family=? and animal_name=?", args1, null, null, null);
				int idCategory;
				idCategory = d.getColumnIndex("category_name");
				
				for(d.moveToFirst();!d.isAfterLast();d.moveToNext()) {
					if (!categories.contains(d.getString(idCategory)))
						categories.add(d.getString(idCategory));
				}
				
				result.put(c.getString(idName), categories);
			}
			
		}
		
		return result;
	}
	
	public ArrayList<Dose_Data> read_dose_information(String drug_name, String group_name, String family, String animal_name, String category_name) {
		
		ArrayList<Dose_Data> result = new ArrayList<Dose_Data>();
		SQLiteDatabase db = this.getReadableDatabase();
		String args [] = {drug_name, group_name, family, animal_name, category_name};
		Cursor c=db.query("Animal_has_Category", null, "drug_name=? and group_name=? and family=? and animal_name=? and category_name=?", args, null, null, null);
		int idAmount, idPosology, idRoute, idReference;
		idAmount = c.getColumnIndex("dose");
		idPosology = c.getColumnIndex("posology");
		idRoute = c.getColumnIndex("route");
		idReference = c.getColumnIndex("reference");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			Dose_Data data = new Dose_Data(c.getString(idAmount), c.getString(idPosology), c.getString(idRoute), c.getString(idReference));
			if (!result.contains(data))
				result.add(data);
		}
		
		return result;
	}
	
	public ArrayList<String> read_general_notes(String drug_name, String group_name) {
			
			ArrayList<String> result = new ArrayList<String>();
			SQLiteDatabase db = this.getReadableDatabase();
			String args [] = {drug_name, group_name};
			Cursor c=db.query("Drug_aplicated_to_Animal_Type", null, "drug_name=? and group_name=?", args, null, null, null);
			int idNote;
			idNote = c.getColumnIndex("general_note");
			
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
				if (!c.getString(idNote).equals(""))
					result.add(c.getString(idNote));
			}
			
			return result;
	}
	
	public ArrayList<String> read_specific_notes(String drug_name, String group_name, String animal_name, String family_name, String category_name, String posology, String route, String reference) {
			ArrayList<String> result = new ArrayList<String>();
			SQLiteDatabase db = this.getReadableDatabase();
			String args [] = {drug_name, group_name, family_name, animal_name, category_name, posology, route, reference};
			Cursor c=db.query("Animal_has_Category", null, "drug_name=? and group_name=? and family=? and animal_name=? and category_name=? and posology=? and route=? and reference=?", args, null, null, null);
			int idNote;
			idNote = c.getColumnIndex("specific_note");
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
				if (!c.getString(idNote).equals(""))
					result.add(c.getString(idNote));
			}
			
			return result;
	}


}
