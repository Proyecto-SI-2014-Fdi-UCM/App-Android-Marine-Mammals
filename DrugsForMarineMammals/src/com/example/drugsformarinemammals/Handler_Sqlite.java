package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Handler_Sqlite extends SQLiteOpenHelper{

	private static final String nameBD = "DrugsForMarineMammals-DataBase11";

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
		String query1 = "CREATE TABLE Drug (drug_name TEXT, description TEXT, available TEXT, license_AEMPS TEXT, license_EMA TEXT, license_FDA TEXT, priority INTEGER, PRIMARY KEY (drug_name))";

		String query2 = "CREATE TABLE Code(code_number TEXT, anatomic_group_name TEXT, therapeutic_group_name TEXT, drug_name TEXT, FOREIGN KEY (drug_name) REFERENCES Drug(drug_name), PRIMARY KEY (code_number))";
		
		String query3 = "CREATE TABLE Animal_Type (group_name TEXT, PRIMARY KEY (group_name))";
		
		String query4 = "CREATE TABLE Drug_aplicated_to_Animal_Type (drug_name TEXT, group_name TEXT, general_note TEXT, FOREIGN KEY (drug_name) REFERENCES Drug(drug_name)," +
					"FOREIGN KEY (group_name) REFERENCES Animal_Type(group_name), PRIMARY KEY (drug_name, group_name, general_note))";
		
		String query5 = "CREATE TABLE Animal (animal_name TEXT, family TEXT, group_name TEXT, drug_name TEXT, FOREIGN KEY (drug_name) REFERENCES Drug(drug_name), " +
					"FOREIGN KEY (group_name) REFERENCES Animal_Type(group_name), PRIMARY KEY (animal_name, family, group_name, drug_name))";
		
		String query6 = "CREATE TABLE Category (category_name TEXT, PRIMARY KEY (category_name))";

		String query7 = "CREATE TABLE Animal_has_Category (animal_name TEXT, family TEXT, group_name TEXT, drug_name TEXT, category_name TEXT," +
					"book_reference TEXT, art_reference TEXT, specific_note TEXT, posology TEXT, route TEXT, dose TEXT, FOREIGN KEY (drug_name) REFERENCES Animal(drug_name), FOREIGN KEY (group_name) REFERENCES Animal(group_name)," +
					"FOREIGN KEY (animal_name) REFERENCES Animal(animal_name), FOREIGN KEY (family) REFERENCES Animal(family), FOREIGN KEY (category_name) REFERENCES Category(category_name), PRIMARY KEY(animal_name, family, group_name, drug_name, category_name," +
					"book_reference, art_reference, specific_note, posology, route))";
		
		String query8 = "CREATE TABLE Therapeutic_Group (name TEXT, PRIMARY KEY (name))";
		
		String query9 = "CREATE TABLE Anatomic_Group (anatomic_group_name TEXT, PRIMARY KEY (anatomic_group_name))";
		
		String query10 = "CREATE TABLE Code_has_Therapeutic_Group (code_number TEXT, name TEXT, FOREIGN KEY (code_number) REFERENCES Code(code_number), FOREIGN KEY (name) REFERENCES Therapeutic_Group(name)," +
				"PRIMARY KEY (code_number, name))";
		
		String query11 = "CREATE TABLE Code_has_Anatomic_Group (code_number TEXT, anatomic_group_name TEXT, FOREIGN KEY (code_number) REFERENCES Code(code_number), FOREIGN KEY (anatomic_group_name) REFERENCES Anatomic_Group(anatomic_group_name)," +
				"PRIMARY KEY (code_number, anatomic_group_name))";

		db.execSQL(query1);
		db.execSQL(query2);
		db.execSQL(query3);
		db.execSQL(query4);
		db.execSQL(query5);
		db.execSQL(query6);
		db.execSQL(query7);
		db.execSQL(query8);
		db.execSQL(query9);
		db.execSQL(query10);
		db.execSQL(query11);
					
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
		db.execSQL("DROP TABLE IF EXISTS Code_has_Therapeutic_Group");
		db.execSQL("DROP TABLE IF EXISTS Anatomic_Group");
		db.execSQL("DROP TABLE IF EXISTS Code_has_Anatomic_Group");
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
		
		c.close();
		
		return result;
	}
	
	public ArrayList<String> combinedSearch(String[] p) {
		SQLiteDatabase db = open();
		ArrayList<String> drugList = new ArrayList<String>();
		String table_name = "Code_has_Anatomic_Group";
		String where = "anatomic_group_name=?";
		String args[]= {p[0]};
		String column[] = {"code_number"};
		String code = "";
		String drug = "";
		if (!p[0].equals("Choose an Anatomical Target")) {
			Cursor cc = db.query(table_name, column, where, args, null, null, null);
			if (cc.moveToFirst())
				if (!p[1].equals(""))
					for (cc.moveToFirst();!cc.isAfterLast();cc.moveToNext()) {
						code = cc.getString(cc.getColumnIndex("code_number"));
						table_name = "Code_has_Therapeutic_Group"; 
						where = "code_number=? and name=?";
						String args2[]= {code, p[1]};
						Cursor ct = db.query(table_name, column, where, args2, null, null, null);
						if (ct.moveToFirst())
							for (ct.moveToFirst();!ct.isAfterLast();ct.moveToNext()) {
								code = ct.getString(ct.getColumnIndex("code_number"));
								table_name = "Code";
								where = "code_number=?";
								String column2[] = {"drug_name"};
								String args3[] = {code};
								Cursor cs = db.query(table_name, column2, where, args3, null, null, null);
								if (cs.moveToFirst())
									for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
										drug = cs.getString(cs.getColumnIndex("drug_name"));
										if (!drugList.contains(drug)) 
											drugList.add(drug);
									}
								cs.close();
							}
					}				
				else {
					for (cc.moveToFirst();!cc.isAfterLast();cc.moveToNext()) {
						code = cc.getString(cc.getColumnIndex("code_number"));
						table_name = "Code";
						where = "code_number=?";
						String column2[] = {"drug_name"};
						String args3[] = {code};
						Cursor cs = db.query(table_name, column2, where, args3, null, null, null);
						if (cs.moveToFirst())
							for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
								drug = cs.getString(cs.getColumnIndex("drug_name"));
								if (!drugList.contains(drug)) 
									drugList.add(drug);
							}
						cs.close();
					}
				}
			cc.close();
		}
		else {
			if (!p[1].equals("")) {
				table_name = "Code_has_Therapeutic_Group"; 
				where = "name=?";
				String args2[]= {p[1]};
				Cursor ct = db.query(table_name, column, where, args2, null, null, null);
				if (ct.moveToFirst())
					for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
						code = ct.getString(ct.getColumnIndex("code_number"));
						table_name = "Code";
						where = "code_number=?";
						String column2[] = { "drug_name" };
						String args3[] = { code };
						Cursor cs = db.query(table_name, column2, where, args3,	null, null, null);
						if (cs.moveToFirst())
							for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
								drug = cs.getString(cs.getColumnIndex("drug_name"));
								if (!drugList.contains(drug))
									drugList.add(drug);
							}
						cs.close();
					}
				ct.close();
			}			
		}
		if (p[2].equals("Choose a Group"))
			return drugList;
		else  {
			int size = drugList.size();
			ArrayList<String> result = new ArrayList<String>();
			if (size > 0) {
				table_name = "Drug_aplicated_to_Animal_Type";
				where = "drug_name=? and group_name=?";
				String column2[] = { "drug_name" };
				for (int i=0; i<size; i++) {
					String args3[] = { drugList.get(i), p[2] };
					Cursor cg = db.query(table_name, column2, where, args3,	null, null, null);
					if (cg.moveToFirst())
						for (cg.moveToFirst();!cg.isAfterLast();cg.moveToNext()) {
							drug = cg.getString(cg.getColumnIndex("drug_name"));
							if (!result.contains(drug))
								result.add(drug);
						}
					cg.close();	
				}
				table_name = "Animal_has_Category";
				for (int i=0; i<size; i++) {
					String args3[] = { drugList.get(i), p[2] };
					Cursor cg = db.query(table_name, column2, where, args3,	null, null, null);
					if (cg.moveToFirst())
						for (cg.moveToFirst();!cg.isAfterLast();cg.moveToNext()) {
							drug = cg.getString(cg.getColumnIndex("drug_name"));
							if (!result.contains(drug))
								result.add(drug);
						}
					cg.close();	
				}
			}
			else 
				if (p[0].equals("Choose an Anatomical Target") && p[1].equals("")){
					table_name = "Drug_aplicated_to_Animal_Type";
					where = "group_name=?";
					String column2[] = { "drug_name" };
					String args3[] = { p[2] };
					Cursor cg = db.query(table_name, column2, where, args3,	null, null, null);
					if (cg.moveToFirst())
						for (cg.moveToFirst();!cg.isAfterLast();cg.moveToNext()) {
							drug = cg.getString(cg.getColumnIndex("drug_name"));
							if (!result.contains(drug))
								result.add(drug);
					}				
					table_name = "Animal_has_Category";
					cg = db.query(table_name, column2, where, args3, null, null, null);
					if (cg.moveToFirst())
						for (cg.moveToFirst();!cg.isAfterLast();cg.moveToNext()) {
							drug = cg.getString(cg.getColumnIndex("drug_name"));
							if (!result.contains(drug))
								result.add(drug);
						}
					cg.close();	
				}
			return result;
		}
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
		
		c.close();
		
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
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={drug_name};
		String columns[]={"available"};
		Cursor c=db.query("Drug", columns, "drug_name=?", args, null, null, null);
		int indexAvalaible=c.getColumnIndex("available");
		c.moveToFirst();
		return c.getString(indexAvalaible).equals("Yes");
		
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
		
		c.close();
		
		return solution;
	}

	public ArrayList<String>  getAnatomicTarget(String code_number) {
		ArrayList<String> solution= new ArrayList<String>();
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={code_number};
		String columns[]={"anatomic_group_name"};
		Cursor c=db.query("Code_has_Anatomic_Group", columns, "code_number=?", args, null, null, null);
		int indexAnatomic=c.getColumnIndex("anatomic_group_name");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			solution.add((c.getString(indexAnatomic)));
		}
		c.close();
		return solution;
		
		
	}
	
	public ArrayList<String>  getTherapeuticTarget(String code_number) {
		ArrayList<String> solution= new ArrayList<String>();
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={code_number};
		String columns[]={"name"};
		Cursor c=db.query("Code_has_Therapeutic_Group", columns, "code_number=?", args, null, null, null);
		int indexTherapeutic=c.getColumnIndex("name");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			solution.add((c.getString(indexTherapeutic)));
		}
		c.close();
		return solution;
		
		
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
		
		c.close();
		
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
		Cursor c=db.query("Animal_has_Category", null, "drug_name=? and group_name=?", args, null, null, null);
		int idFamily;
		idFamily = c.getColumnIndex("family");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			if (!result.contains(c.getString(idFamily).toUpperCase()))
				result.add(c.getString(idFamily).toUpperCase());
			
		}
		
		c.close();
		
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
				
				for (d.moveToFirst();!d.isAfterLast();d.moveToNext()) {
					String category = d.getString(idCategory);
					if (category.equals("") && !categories.contains(d.getString(idCategory)))
						categories.add(0, d.getString(idCategory));
				}
				
				for(d.moveToFirst();!d.isAfterLast();d.moveToNext()) {
					if (!categories.contains(d.getString(idCategory)))
						categories.add(d.getString(idCategory));
				}
				
				d.close();
				
				result.put(c.getString(idName), categories);
			}
			
		}
		
		c.close();
		
		return result;
	}
	
	public ArrayList<Dose_Data> read_dose_information(String drug_name, String group_name, String family, String animal_name, String category_name) {
		
		ArrayList<Dose_Data> result = new ArrayList<Dose_Data>();
		SQLiteDatabase db = this.getReadableDatabase();
		String args [] = {drug_name, group_name, family, animal_name, category_name};
		Cursor c=db.query("Animal_has_Category", null, "drug_name=? and group_name=? and family=? and animal_name=? and category_name=?", args, null, null, null);
		int idAnimal, idCategory, idAmount, idPosology, idRoute, idBookReference, idArticleReference;
		idAnimal = c.getColumnIndex("animal_name");
		idCategory = c.getColumnIndex("category_name");
		idAmount = c.getColumnIndex("dose");
		idPosology = c.getColumnIndex("posology");
		idRoute = c.getColumnIndex("route");
		idBookReference = c.getColumnIndex("book_reference");
		idArticleReference = c.getColumnIndex("art_reference");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			Dose_Data data = new Dose_Data(c.getString(idAnimal), c.getString(idCategory), c.getString(idAmount), c.getString(idPosology), c.getString(idRoute), c.getString(idBookReference), c.getString(idArticleReference));
			if (result.size() == 0)
				result.add(data);
			else {
				boolean find = false;
				int i = 0;
				while (!find && i<result.size()) {
					if (data.equals(result.get(i)))
						find = true;
					i++;
				}
				if (!find)
					result.add(data);
			}
		}
		
		c.close();
		
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
			
			c.close();
			
			return result;
	}
	
	public ArrayList<String> read_specific_notes(String drug_name, String group_name, String animal_name, String family_name, String category_name, String dose, String posology, String route, String book_reference, String article_reference) {
		ArrayList<String> result = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String args [] = {drug_name, group_name, family_name, animal_name, category_name, dose, posology, route, book_reference, article_reference};
		Cursor c=db.query("Animal_has_Category", null, "drug_name=? and group_name=? and family=? and animal_name=? and category_name=? and dose=? and posology=? and route=? and book_reference=? and art_reference=?", args, null, null, null);
		int idNote;
		idNote = c.getColumnIndex("specific_note");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			if (!c.getString(idNote).equals(""))
				result.add(c.getString(idNote));
		}
		
		c.close();
		
		return result;
}
	
	public ArrayList<Dose_Data> read_every_dose(String drug_name, String group_name, String family) {
		ArrayList<Dose_Data> result = new ArrayList<Dose_Data>();
		SQLiteDatabase db = this.getReadableDatabase();
		String args [] = {drug_name, group_name, family};
		Cursor c=db.query("Animal_has_Category", null, "drug_name=? and group_name=? and family=?", args, null, null, null);
		int idAnimal, idCategory, idAmount, idPosology, idRoute, idBookReference, idArticleReference;
		idAnimal = c.getColumnIndex("animal_name");
		idCategory = c.getColumnIndex("category_name");
		idAmount = c.getColumnIndex("dose");
		idPosology = c.getColumnIndex("posology");
		idRoute = c.getColumnIndex("route");
		idBookReference = c.getColumnIndex("book_reference");
		idArticleReference = c.getColumnIndex("art_reference");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			Dose_Data data = new Dose_Data(c.getString(idAnimal), c.getString(idCategory), c.getString(idAmount), c.getString(idPosology), c.getString(idRoute), c.getString(idBookReference), c.getString(idArticleReference));
			if (result.size() == 0)
				result.add(data);
			else {
				boolean find = false;
				int i = 0;
				while (!find && i<result.size()) {
					if (data.equals(result.get(i)))
						find = true;
					i++;
				}
				if (!find)
					result.add(data);
			}
		}
		
		c.close();
		
		return result;
	}
	//Save the general information drug in local database
	public void saveGeneralInfoDrug(ArrayList<String> generalInfo){
		String query="INSERT INTO Drug (drug_name, description, available, license_AEMPS, license_EMA, license_FDA, priority) VALUES ('"+generalInfo.get(0)+"','"+generalInfo.get(1)+"','"+generalInfo.get(2)+"','"+ generalInfo.get(3)+"','"+ generalInfo.get(4)+"','"+generalInfo.get(5)+"',"+1+");";
		this.open().execSQL(query);
		this.close();
	}

	public void saveCodeInformation(ArrayList<Type_Code> info, String drugName){
		int size=info.size();
		for(int i=0;i<size;i++){
			String query="INSERT INTO Code (code_number,anatomic_group_name,therapeutic_group_name,drug_name) VALUES('"+info.get(i).getCode()+"','"+info.get(i).getAnatomic_group()+"','"+info.get(i).getTherapeutic_group()+"','"+drugName+"');";
			this.open().execSQL(query);
		}
		this.close();
	}
	public ArrayList<Type_Code> getCodesInformation(String name){
		ArrayList<Type_Code> solution= new ArrayList<Type_Code>();
		
		SQLiteDatabase db=this.getReadableDatabase();
		String args[]={name};
		String columns[]={"code_number","anatomic_group_name","therapeutic_group_name"};
		Cursor c=db.query("Code", columns, "drug_name=?", args, null, null, null);
		int indexCode=c.getColumnIndex("code_number");
		int indexAnatomical=c.getColumnIndex("anatomic_group_name");
		int indexTherapeutic=c.getColumnIndex("therapeutic_group_name");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			Type_Code type=new Type_Code();
			type.setCode(c.getString(indexCode));
			type.setAnatomic_group(c.getString(indexAnatomical));
			type.setTherapeutic_group(c.getString(indexTherapeutic));
			solution.add(type);
		}
		c.close();
		return solution;
		
	}
	
	public void insertAnimal(String animal_name, String family, String group_name, String drug_name) {
		ContentValues animal=new ContentValues();
		animal.put("animal_name", animal_name);
		animal.put("family", family);
		animal.put("group_name", group_name);
		animal.put("drug_name", drug_name);
		this.getWritableDatabase().insert("Animal", null, animal);
		
	}
	
	public void insertCategory(String category_name) {
		ContentValues category=new ContentValues();
		category.put("category_name", category_name);
		this.getWritableDatabase().insert("Category", null, category);
		
	}
	
	public void insertDose(String animal_name, String drug_name, String family, String group_name, String category_name, String book_reference, String article_reference, String specific_note, String posology, String route, String dose) {
		ContentValues dosage=new ContentValues();
		dosage.put("animal_name", animal_name);
		dosage.put("family", family);
		dosage.put("group_name", group_name);
		dosage.put("drug_name", drug_name);
		dosage.put("category_name", category_name);
		dosage.put("book_reference", book_reference);
		dosage.put("art_reference", article_reference);
		dosage.put("specific_note", specific_note);
		dosage.put("posology", posology);
		dosage.put("route", route);
		dosage.put("dose", dose);
		this.getWritableDatabase().insert("Animal_has_Category", null, dosage);
		
	}
	
	public void insertGroup(String group_name) {
		ContentValues group=new ContentValues();
		group.put("group_name", group_name);
		this.getWritableDatabase().insert("Animal_Type", null, group);
		
	}
	
	public void insertGeneralNote(String drug_name, String group_name, String general_note) {
		ContentValues note=new ContentValues();
		note.put("drug_name", drug_name);
		note.put("group_name", group_name);
		note.put("general_note", general_note);
		this.getWritableDatabase().insert("Drug_aplicated_to_Animal_Type", null, note);
	}

	public void updateGeneralInfroDrug(ArrayList<String> generalInfo) {
		String query="UPDATE Drug SET description='"+generalInfo.get(1)+ "',available='"+generalInfo.get(2)+"',license_AEMPS='"+generalInfo.get(3)+"',license_FDA='"+generalInfo.get(4)+"'WHERE drug_name='"+ generalInfo.get(0)+"'";
		this.open().execSQL(query);
		this.close();
	}

	public void updateCodeInformation(ArrayList<Type_Code> info, String drugName) {
		int size=info.size();
		for(int i=0;i<size;i++){
			String query="UPDATE Code SET anatomic_group_name='"+info.get(i).getAnatomic_group()+ "',therapeutic_group_name='"+info.get(i).getTherapeutic_group()+"',drug_name='"+drugName+"'WHERE code_number='"+ info.get(i).getCode()+"'";
			this.open().execSQL(query);
		}
		this.close();
	}
	
	public void deleteDose(String drug_name) {
		SQLiteDatabase db = getWritableDatabase();
	    db.delete("Animal_has_Category", "drug_name="+"'"+drug_name+"'", null);
	}
	
	public void deleteNotes(String drug_name) {
		SQLiteDatabase db = getWritableDatabase();
	    db.delete("Drug_aplicated_to_Animal_Type", "drug_name="+"'"+drug_name+"'", null);
	}
}
